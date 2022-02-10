package fr.game.constants;

import fr.game.constants.world.WorldEnum;

public class AppVariables {
    //WORLD SETTING
    public static int maxWorldColumn = 50;
    public static int maxWorldRow = 50;
    //SCREEN SETTINGS
    public static int FPS = 60;
    public static boolean goesAroundScreen = true;
    public static int originalTileSize = 16;
    public static int scale = 3;
    public static int maxScreenColumn = 27;
    public static int maxScreenRow = 18;
    public static int tileSize = originalTileSize * scale;
    public static int SCREEN_WIDTH = tileSize * maxScreenColumn;
    public static int SCREEN_HEIGHT = tileSize * maxScreenRow;
    public static int maxWorldX = maxWorldColumn * tileSize;
    public static int maxWorldY = maxWorldRow * tileSize;
    //DIALOG SETTINGS
    public static final int messageStayFor = FPS * 2;


    public static void resetTo(WorldEnum worldSelected) {
        //WORLD SETTING
        maxWorldColumn = worldSelected.getWorldCol();
        maxWorldRow = worldSelected.getWorldRow();
        //SCREEN SETTINGS
        goesAroundScreen = worldSelected.isGoesAroundScreen();
        originalTileSize = worldSelected.getOriginialTileSize();
        scale = worldSelected.getScale();
        maxScreenColumn = worldSelected.getMaxScreenColumn();
        maxScreenRow = worldSelected.getMaxScreenRow();
        refresh();
    }


    public static void refresh() {
        tileSize = AppVariables.originalTileSize * AppVariables.scale;
        SCREEN_WIDTH = tileSize * AppVariables.maxScreenColumn;
        SCREEN_HEIGHT = tileSize * AppVariables.maxScreenRow;
        AppVariables.maxWorldX = AppVariables.maxWorldColumn * tileSize;
        maxWorldY = AppVariables.maxWorldRow * tileSize;
    }
}
