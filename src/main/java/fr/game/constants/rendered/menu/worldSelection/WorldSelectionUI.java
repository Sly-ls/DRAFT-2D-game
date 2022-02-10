package fr.game.constants.rendered.menu.worldSelection;

import fr.game.constants.AppVariables;
import fr.game.constants.core.FontEnum;
import fr.game.constants.core.MessageTypeEnum;
import fr.game.constants.rendered.GameValueEnum;
import fr.game.constants.rendered.menu.MenuController;
import fr.game.constants.world.WorldEnum;
import fr.game.mechanics.core.MessageBox;
import fr.game.mechanics.core.ScaledImage;
import fr.game.panel.AbstractUI;

import java.awt.*;

public class WorldSelectionUI extends AbstractUI {
    ScaledImage gameIcon;
    WorldSelectionPanel currentPanel;

    public WorldSelectionUI(WorldSelectionPanel currentPanel) {
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
                AppVariables.tileSize*6,
                AppVariables.tileSize*6,null);

        int yToTop = AppVariables.tileSize*1;

        //Title
        MessageBox.getInstance().sendMessage(MessageTypeEnum.NANO_TEXT,"Choisis ton monde :",
                FontEnum.BEDROCK,new Color(71, 234, 196,100), 68,
                false, 28,
                AppVariables.tileSize*2,yToTop);

        //Menu
        yToTop += AppVariables.tileSize*2;
        int i = 0;
        for(WorldEnum worldEnum : WorldEnum.values()){
            MessageBox.getInstance().sendMessage(MessageTypeEnum.MENU_TEXT,
                    (i+1)+". " + worldEnum.getTitle(),
                    FontEnum.BEDROCK,new Color(71, 234, 196,100), 40,
                    cursorValue == i, 50,
                    AppVariables.tileSize*3,yToTop);
            yToTop += AppVariables.tileSize*2;
            i++;
        }

    }


}
