package fr.game.constants.world;

import fr.game.constants.world.tiles.TileSetEnum;
import fr.game.constants.world.tiles.TileSet;
import fr.game.constants.world.tiles.TilesDescriptionEnum_ISLAND;
import fr.game.constants.world.tiles.TilesDescriptionEnum_TEST_MAP;

public enum WorldEnum {

    CUTE_MAP_BLUEBOY_TUTO(0, "CUTE_MAP_BLUEBOY_TUTO", TilesDescriptionEnum_TEST_MAP.WATER,
            "/maps/50x50_map.txt", true,
            26,26,
            50,50,16, 3
            , true ,16 ,12),
    TREASURE_HUNT(1, "TREASURE_HUNT", TilesDescriptionEnum_TEST_MAP.WATER,
            "/maps/BigMap2.txt", false,
            23,22,
            50,100,16, 3
            , true ,16 ,12),
    TEST_MAP(2, "TEST_MAP", TilesDescriptionEnum_TEST_MAP.WATER,
            "/maps/BigMapTestingArea.txt", true,
            26,26,
            50,100,16, 3
            , true ,16 ,12),
    THE_ISLAND(3, "THE_ISLAND", TilesDescriptionEnum_ISLAND.WATER,
            "/maps/Labyrinth30012022.csv", false,
            21,97,
            50,100,16, 3
            , true, 16 ,12),
    SPIDERLAND(4, "SPIDERLAND", TileSetEnum.SUMMER_WC2,
            "/maps/CarteVDG.txt", false,
                    50,50,
                    100,100,32, 2
                    , true, 16 ,12);



    public static WorldEnum worldSelected = WorldEnum.CUTE_MAP_BLUEBOY_TUTO;

    String title;
    TileSet tileSet;
    String path;
    boolean isForDebug;
    int playerStartingX;
    int playerStartingY;
    int worldCol;
    int worldRow;
    int originialTileSize;
    int scale;
    boolean goesAroundScreen;
    int maxScreenColumn;
    int maxScreenRow;
    int code;

    //CONSTRUCTOR
    WorldEnum(int code, String title, TileSet tileSet, String path, boolean isForDebug,
              int playerStartingX, int playerStartingY,
              int worldCol, int worldRow, int originialTileSize, int scale,
              boolean goesAroundScreen, int maxScreenColumn, int maxScreenRow) {
        this.code = code;
        this.title = title;
        this.tileSet = tileSet;
        this.isForDebug = isForDebug;
        this.path = path;
        this.playerStartingX = playerStartingX;
        this.playerStartingY = playerStartingY;
        this.worldRow = worldRow;
        this.worldCol = worldCol;
        this.scale = scale;
        this.originialTileSize = originialTileSize;
        this.goesAroundScreen = goesAroundScreen;
        this.maxScreenColumn = maxScreenColumn;
        this.maxScreenRow = maxScreenRow;
    }
//GETTER && SETTER
    public static WorldEnum getWorldSelected() {
        return worldSelected;
    }
    public static WorldEnum getWorldByCode(int worldSelectedCode) {
        for(WorldEnum worldEnum : WorldEnum.values()){
            if(worldEnum.getCode() == worldSelectedCode){
                return worldEnum;
            }
        }
        return null;
    }
    public String getTitle() {
        return title;
    }
    public TileSet getTileSet() {
        return tileSet;
    }
    public String getPath() {
        return path;
    }
    public int getWorldCol() {
        return worldCol;
    }
    public int getWorldRow() {
        return worldRow;
    }
    public int getOriginialTileSize() {
        return originialTileSize;
    }
    public boolean isGoesAroundScreen() {
        return goesAroundScreen;
    }
    public int getMaxScreenColumn() {
        return maxScreenColumn;
    }
    public int getMaxScreenRow() {
        return maxScreenRow;
    }
    public int getScale() {
        return scale;
    }
    public int getCode() {
        return code;
    }
    public int getPlayerStartingX() {
        return playerStartingX;
    }
    public int getPlayerStartingY() {
        return playerStartingY;
    }
    public boolean isForDebug() {
        return isForDebug;
    }
}
