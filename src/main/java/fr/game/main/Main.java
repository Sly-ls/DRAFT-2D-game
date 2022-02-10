package fr.game.main;

import fr.game.constants.AppConstants;
import fr.game.constants.panel.PanelEnum;
import fr.game.rendererd.TileManager;

import javax.swing.*;

public class Main {

    public static void main (String[] args){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle(AppConstants.GAME_TITLE);
        ApplicationManager gameManager = new ApplicationManager(window, PanelEnum.TITLE);

//        TileManager.getInstance().cleanTileSet();
//        TileManager.getInstance().cutTileSetToTile();
    }



}
