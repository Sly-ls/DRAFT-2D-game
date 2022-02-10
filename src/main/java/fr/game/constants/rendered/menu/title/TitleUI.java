package fr.game.constants.rendered.menu.title;

import fr.game.constants.AppConstants;
import fr.game.constants.AppVariables;
import fr.game.constants.core.FontEnum;
import fr.game.constants.rendered.GameValueEnum;
import fr.game.constants.core.MessageTypeEnum;
import fr.game.mechanics.core.ScaledImage;
import fr.game.panel.AbstractUI;
import fr.game.mechanics.core.MessageBox;
import fr.game.constants.rendered.menu.MenuController;

import java.awt.*;

public class TitleUI extends AbstractUI {
    ScaledImage gameIcon;
    TitlePanel currentPanel;

    public TitleUI(TitlePanel currentPanel) {
        super(currentPanel);
        this.currentPanel = currentPanel;
    }

    public void drawMenu(Graphics2D graphics2D) {
        int cursorValue = MenuController.getInstance().getCursor().getKey();
        this.gameIcon = new ScaledImage(GameValueEnum.HOURGLASS.getPath()[0]);


//        GAME ICON
        this.graphics2D.drawImage(this.gameIcon.getBufferedImage(),
                (AppVariables.SCREEN_WIDTH /2 + AppVariables.SCREEN_WIDTH /4) - (AppVariables.tileSize*6)/2,
                AppVariables.tileSize*5 - (AppVariables.tileSize*6)/2 ,
                AppVariables.tileSize*8,
                AppVariables.tileSize*6,null);

        int yToTop = AppVariables.tileSize*2;
        int xFromLeft = AppVariables.tileSize*2;

        MessageBox.getInstance().sendMessage(MessageTypeEnum.MENU_TEXT, AppConstants.GAME_TITLE,
                FontEnum.BEDROCK,new Color(71, 234, 196,100), 100,
                false, 28,
                AppVariables.tileSize*2-3 ,yToTop-3);
        MessageBox.getInstance().sendMessage(MessageTypeEnum.MENU_TEXT, AppConstants.GAME_TITLE,
                FontEnum.BEDROCK, Color.WHITE, 100,
                false, 28,
                AppVariables.tileSize*2,yToTop);

        //Subtitle
        yToTop += AppVariables.tileSize;
        MessageBox.getInstance().sendMessage(MessageTypeEnum.NANO_TEXT,"Time is passing by... " + System.currentTimeMillis(),
                FontEnum.BEDROCK,new Color(71, 234, 196,100), 28,
                false, 28,
                AppVariables.tileSize*2,yToTop);

        //Menu
        yToTop += AppVariables.tileSize*3;
        MessageBox.getInstance().sendMessage(MessageTypeEnum.MENU_TEXT,"Jouer",
                FontEnum.BEDROCK,new Color(71, 234, 196,100), 60,
                cursorValue == 0, 50,
                AppVariables.tileSize*3,yToTop);
        yToTop += AppVariables.tileSize*2;
        MessageBox.getInstance().sendMessage(MessageTypeEnum.MENU_TEXT,"Mondes...",
                FontEnum.BEDROCK,new Color(71, 234, 196,100), 60,
                cursorValue == 1, PADDING_DEFAULT,
                AppVariables.tileSize*3,yToTop);
        yToTop += AppVariables.tileSize*2;
        MessageBox.getInstance().sendMessage(MessageTypeEnum.MENU_TEXT,"Quitter",
                FontEnum.BEDROCK,new Color(71, 234, 196,100), 60,
                cursorValue == 2, PADDING_DEFAULT,
                AppVariables.tileSize*3,yToTop);

    }

}
