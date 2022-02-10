package fr.game.rendererd;

import fr.game.constants.AppConstants;
import fr.game.constants.AppVariables;
import fr.game.constants.core.FontEnum;
import fr.game.constants.core.GameUnitEnum;
import fr.game.constants.world.WorldEnum;
import fr.game.constants.world.tiles.TileSet;
import fr.game.constants.world.tiles.TileSetEnum;
import fr.game.constants.world.tiles.TilesDescriptionEnum_ISLAND;
import fr.game.constants.world.tiles.TilesDescriptionEnum_TEST_MAP;
import fr.game.mechanics.controller.game.Camera;
import fr.game.mechanics.core.ScaledImage;
import fr.game.panel.game.GameController;
import fr.game.rendererd.tile.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.*;

public class TileManager {

    private TileManager()
    {
        if(AppConstants.DEBUG){
            worldSelected = WorldEnum.TEST_MAP;
        }else if(this.worldSelected == null){
            worldSelected = WorldEnum.THE_ISLAND;
        }}
    private static TileManager INSTANCE = null;
    public static TileManager getInstance()
    {
        if (INSTANCE == null)
        {   INSTANCE = new TileManager();
        }
        return INSTANCE;
    }
    private static TileManager instance;
    private Map<Integer, Tile> tileIndex;
    private int mapTileArray[][];
    private static WorldEnum worldSelected;

    public void loadWorld(){
        AppVariables.resetTo(worldSelected);
        Set<Integer> imageToLoad = loadMap();
        getTileImage(imageToLoad);
    }
    private void getTileImage(Set<Integer> listToload) {
        //TODO improve with list of tilesDescription
        TileSet tileSet = worldSelected.getTileSet();
        this.tileIndex = new HashMap<>();
        if(tileSet instanceof TilesDescriptionEnum_ISLAND){
            for(TilesDescriptionEnum_ISLAND tilesDescription : TilesDescriptionEnum_ISLAND.values()){
                tileIndex.put(tilesDescription.getCode(), new Tile(tilesDescription));
            }

        }else if(tileSet instanceof TilesDescriptionEnum_TEST_MAP){
            for(TilesDescriptionEnum_TEST_MAP tilesDescription : TilesDescriptionEnum_TEST_MAP.values()){
                tileIndex.put(tilesDescription.getCode(), new Tile(tilesDescription));
            }
        } else if (tileSet instanceof TileSetEnum){
            if(AppConstants.DEBUG && AppConstants.REBUILD_TILESET){
                cleanTileSet();
                cutTileSetToTile();
            }
            Map<Integer, ScaledImage> imageIndex = loadTileSetToTile(listToload);
            for(Map.Entry<Integer,ScaledImage> imageEntry : imageIndex.entrySet()){
                tileIndex.put(imageEntry.getKey(), new Tile(imageEntry.getKey(), imageEntry.getValue()));
            }
        }
    }

    public Map<Integer, ScaledImage> loadTileSetToTile(Set<Integer> codesRequired){
        boolean loadAll = false;
        if(AppConstants.DEBUG && AppConstants.REBUILD_TILESET) {
            loadAll = true;
        }

        Map<Integer, ScaledImage> tileSetInGame = new HashMap<>();
        Map<TileSetEnum, List<File>> fileToLoadByTileSetEnum = new HashMap<>();
        for(TileSetEnum tileSet : TileSetEnum.values()){
            File directoryPath = new File(tileSet.getAbsoluteRootDirectory()+tileSet.getNamedDirectory());
            File filesList[] = directoryPath.listFiles();
            List<File>  listOfFileToLoad = fileToLoadByTileSetEnum.getOrDefault(tileSet,new ArrayList());
            listOfFileToLoad.addAll(Arrays.asList(filesList));
            fileToLoadByTileSetEnum.put(tileSet, listOfFileToLoad);
        }
        for(Map.Entry<TileSetEnum, List<File>> entry : fileToLoadByTileSetEnum.entrySet()) {
            TileSetEnum tileSet = entry.getKey();
            for(File file : entry.getValue()){
                int fileNameToInt = Integer.valueOf(file.getName().replace(".png",""));
                if(loadAll ||(codesRequired == null || codesRequired.contains(fileNameToInt))){
                    tileSetInGame.put(fileNameToInt,
                            new ScaledImage(tileSet.getRessourcesRootDirectory()+tileSet.getNamedDirectory()+file.getName()));
                    if(AppConstants.DEBUG) {
                        System.out.println("____________CACHED_________");
                        System.out.println("File name: " + file.getName());
                        System.out.println("File path: " + file.getAbsolutePath());
                        System.out.println("Size :" + file.getTotalSpace());
                        System.out.println("----------------------------");
                    }
                }
            }
        }
        return tileSetInGame;
    }
    public void cleanTileSet(){
        //TODO testing Value, to be used in DEBUG
        Map<TileSetEnum, List<File>> fileToLoadByTileSetEnum = new HashMap<>();
        for(TileSetEnum tileSet : TileSetEnum.values()){
            File directoryPath = new File(tileSet.getAbsoluteRootDirectory()+tileSet.getNamedDirectory());
            File filesList[] = directoryPath.listFiles();
            List<File>  listOfFileToLoad = fileToLoadByTileSetEnum.getOrDefault(tileSet,new ArrayList());
            listOfFileToLoad.addAll(Arrays.asList(filesList));
            fileToLoadByTileSetEnum.put(tileSet, listOfFileToLoad);
        }
        for(Map.Entry<TileSetEnum, List<File>> entry : fileToLoadByTileSetEnum.entrySet()) {
            TileSetEnum tileSet = entry.getKey();
            for(File file : entry.getValue()){
                file.delete();
                if(AppConstants.DEBUG) {
                    System.out.println("_________DELETED____________");
                    System.out.println("File name: " + file.getName());
                    System.out.println("File path: " + file.getAbsolutePath());
                    System.out.println("Size :" + file.getTotalSpace());
                    System.out.println("----------------------------");
                }
            }
        }
    }
    public void cutTileSetToTile(){
        //TODO testing Value, to be used in DEBUG
        boolean loadAll = true;

        int code =1;
        for(TileSetEnum tileSetEnum : TileSetEnum.values()){
            BufferedImage tilesetToCut = null;
            try {
                tilesetToCut = ImageIO.read(getClass().getResourceAsStream(tileSetEnum.getRessourcesRootDirectory()+tileSetEnum.getOriginalTileset()));
                int tileSize = tileSetEnum.getTileSize();
                int margin = tileSetEnum.getMargin();
                int originalPadding = tileSetEnum.getPadding();
                int nbCol = (tilesetToCut.getWidth())/(tileSize);
                int nbRow = (tilesetToCut.getHeight())/(tileSize);
                int row = 0;
                int col = 0;
                BufferedImage subImage;
                //condition here for limiting the siez during testing

                while(col < nbCol && row < nbRow && code < 1500){
                    File file = null;
                    if ((row==0 && col>tileSetEnum.getSkipFirt())
                            ||(row==nbRow-1 && col < nbCol-tileSetEnum.getSkipLast())
                            ||(row != 0 || row != nbRow-1)){
                        int padingX = originalPadding*col;
                        int padingY = originalPadding*row;
                        subImage = tilesetToCut.getSubimage((col * tileSize + padingX + margin), (row * tileSize) + padingY + margin, tileSize, tileSize);
                        file = new File(tileSetEnum.getAbsoluteRootDirectory() + tileSetEnum.getNamedDirectory() + code + ".png");
                        ImageIO.write(subImage, "png", file);
                        code++;
                        if(AppConstants.DEBUG) {
                                new StringBuffer("_________CREATED____________")
                                        .append("File name: ")
                                        .append(file.getName())
                                        .append("File path: ")
                                        .append(file.getAbsolutePath())
                                        .append("Size :")
                                        .append(file.getTotalSpace())
                                        .append("----------------------------");
                        }
                    }
                    col++;
                    if (col == nbCol){
                        col = 0;
                        row++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private Set<Integer> loadMap(){
        String mapPath = worldSelected.getPath();
        Set<Integer> imageToLoad = new HashSet<>();
        try{
            mapTileArray = new int[AppVariables.maxWorldColumn][AppVariables.maxWorldRow];
            InputStream inputStream = getClass().getResourceAsStream(mapPath);
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
            int col = 0;
            int row = 0;

            while (col < AppVariables.maxWorldColumn && row < AppVariables.maxWorldRow){
                String line = bufferReader.readLine();
                while (col < AppVariables.maxWorldColumn){

                    String numbers[] = line.split(" ");
                    int tileCode = Integer.parseInt(numbers[col]);
                    mapTileArray[col][row] = tileCode;
                    imageToLoad.add(tileCode);
                    col++;
                }
                if (col == AppVariables.maxWorldColumn){
                    row++;
                    col = 0;
                }
            }

            bufferReader.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return imageToLoad;
    }
    public void draw(Graphics2D graphics2D){

        int tileWorldX = 0;
        int tileWorldY = 0;
        int worldColNum = AppVariables.maxWorldColumn;
        int worldRowNum = AppVariables.maxWorldRow;
        while (tileWorldX < AppVariables.maxWorldColumn && tileWorldY < AppVariables.maxWorldRow){
            int tileNum = mapTileArray[tileWorldX][tileWorldY];
            int worldX = AppVariables.tileSize * tileWorldX;
            int worldY = AppVariables.tileSize * tileWorldY;

            if(Camera.getInstance().isDisplayable(worldX,worldY)) {
                graphics2D.drawImage(tileIndex.get(tileNum).getImage().getBufferedImage(),
                        Camera.getInstance().getScreenX(worldX),
                        Camera.getInstance().getScreenY(worldY), null);

                if(AppConstants.DEBUG) {
                    GameController.getInstance().getPanel().getUiPanel().setUIFont(FontEnum.ARIAL,12, Color.WHITE);
                    graphics2D.drawString(
                            tileWorldX + "," + tileWorldY,
                            Camera.getInstance().getScreenX(worldX),
                            Camera.getInstance().getScreenY(worldY)
                    );
                    graphics2D.drawString(
                            tileIndex.get(tileNum).getCode()+"," +tileIndex.get(tileNum).isCollision(),
                            Camera.getInstance().getScreenX(worldX),
                            Camera.getInstance().getScreenY(worldY)+12
                    );
                }
            }

            tileWorldX++;
            if (tileWorldX == AppVariables.maxWorldColumn){
                tileWorldX = 0;
                tileWorldY++;
            }
        }

    }

    public int[][] getMapTileArray() {
        return mapTileArray;
    }
    public void setMapTileArray(int[][] mapTileArray) {
        this.mapTileArray = mapTileArray;
    }
    public Map<Integer, Tile> getTileIndex() {
        return tileIndex;
    }
    public void setTileIndex(Map<Integer, Tile> tileIndex) {
        this.tileIndex = tileIndex;
    }
    public static WorldEnum getWorldSelected() {
        return worldSelected;
    }

    public void setWorldSelected(WorldEnum worldSelected) {
        this.worldSelected = worldSelected;
    }
}
