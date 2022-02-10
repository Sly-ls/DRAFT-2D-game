package fr.game.panel.game;

import fr.game.constants.game.SoundDescriptionEnum;
import fr.game.constants.rendered.EntityEnum;
import fr.game.mechanics.AbstractController;
import fr.game.mechanics.controller.game.Camera;
import fr.game.mechanics.controller.game.SoundController;
import fr.game.rendererd.AbstractRendered;
import fr.game.rendererd.AssetSetter;
import fr.game.rendererd.TileManager;
import fr.game.rendererd.entity.PC.Player;
import javafx.util.Pair;

import java.util.*;

public class GameController extends AbstractController{

    //SINGLETON titleController
    static GameController instance;
    private GameController(GamePanel panel){
        super(panel);
        this.gamePanel = panel;
    }
    public static GameController createInstance(GamePanel panelWithCursor) {
        if(instance==null){
            instance = new GameController(panelWithCursor);
        }
        return instance;
    }
    public static GameController getInstance() {
        if(instance==null){
            //lévera une NullPointerException
        }
        return instance;
    }
    /*le game controller ne devrait faire qu'echanger des messages avec le panel.
        Pour les tiles, c'est le tileManager qui fait le draw
        puis les AbstractRendered
        puis l'UI, puis les message
        chaque composant est chargé de son renduavec sa method draw
     */
    //pour les changement de controller et de key handler
    private Player player;
    private Map<Pair<Integer, Integer>, List<AbstractRendered>> gameObjects = new HashMap<>();
    private GamePanel gamePanel;
    private Comparator coordinateComparator = new CoordinateComparator();


    public void addNewObject(AbstractRendered newObject){
        Pair newCoordinate = new Pair<>(new Integer(newObject.getWorldX()),new Integer(newObject.getWorldY()));
        List toUpdate = gameObjects.getOrDefault(newCoordinate, new ArrayList<>());
        toUpdate.add(newObject);
        gameObjects.put(newCoordinate, toUpdate);
    }
    public void removeObjectFromTheWorld(AbstractRendered abstractRendered){
        Pair thisCoordinate = new Pair<>(abstractRendered.getWorldX(), abstractRendered.getWorldY());
        List objectOnWorld = this.getGameObjects().getOrDefault(thisCoordinate,new ArrayList<>());
        objectOnWorld.remove(abstractRendered);
    }
    public void setupGame(){
        TileManager.getInstance().loadWorld();
        int playerX = TileManager.getWorldSelected().getPlayerStartingX();
        int playerY = TileManager.getWorldSelected().getPlayerStartingY();
        this.player = new Player(EntityEnum.BLUE_BOY, playerX,playerY);
        SoundController.getInstance().playMusic(SoundDescriptionEnum.BLUEBOYADVENTURE);
        AssetSetter.setup();
    }



    public Player getPlayer() {
        return player;
    }


    public List<AbstractRendered> getListToDisplay() {

        List<AbstractRendered> listToDisplay = new ArrayList<>();
        if (player != null) {
            listToDisplay.add(player);
            Camera.getInstance().centerCameraOnPlayer(player);
        }
        for (Map.Entry<Pair<Integer, Integer>, List<AbstractRendered>> gameObjectByCoordinate : gameObjects.entrySet()) {
            for (AbstractRendered objectToUpdate : gameObjects.get(gameObjectByCoordinate.getKey())) {
                if(Camera.getInstance().isDisplayable(objectToUpdate)) {
                    listToDisplay.add(objectToUpdate);
                }
            }
        }
        listToDisplay.sort(coordinateComparator);
        return listToDisplay;
    }

    public List<AbstractRendered> getListToUpdate() {
        List<AbstractRendered> listToUpdate = new ArrayList<>();
        if (player != null) {
            listToUpdate.add(player);
        }
        for (Map.Entry<Pair<Integer, Integer>, List<AbstractRendered>> gameObjectByCoordinate : gameObjects.entrySet()) {
            for (AbstractRendered objectToUpdate : gameObjects.get(gameObjectByCoordinate.getKey())) {
                listToUpdate.add(objectToUpdate);
            }
        }
        return listToUpdate;
    }
    public Map<Pair<Integer, Integer>, List<AbstractRendered>> getGameObjects() {
        return gameObjects;
    }

    public GamePanel getPanel() {
        return gamePanel;
    }

    public static class CoordinateComparator implements Comparator<AbstractRendered> {

        @Override
        public int compare(AbstractRendered rendered1, AbstractRendered rendered2) {
            return Integer.compare(rendered1.getWorldY(), rendered2.getWorldY());
        }
    }
}
