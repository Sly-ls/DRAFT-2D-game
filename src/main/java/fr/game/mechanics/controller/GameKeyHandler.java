package fr.game.mechanics.controller;

import fr.game.constants.panel.PanelEnum;
import fr.game.constants.panel.PanelStateEnum;
import fr.game.constants.world.WorldEnum;
import fr.game.main.ApplicationManager;
import fr.game.main.utils.NumberUtils;
import fr.game.mechanics.AbstractKeyHandler;
import fr.game.constants.rendered.menu.MenuController;
import fr.game.rendererd.TileManager;

import java.awt.event.KeyEvent;

public class GameKeyHandler extends AbstractKeyHandler {

    public GameKeyHandler(ApplicationManager appManager) {
        super(appManager);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        code =  e.getKeyCode();
        if(this.appManager.getDisplayType() == PanelEnum.GAME){
            switch (code) {
                case KeyEvent.VK_P:
                    if (this.appManager.getCurrentPanel().getPanelState()== PanelStateEnum.PAUSE) {
                        this.appManager.getCurrentPanel().setPanelState(PanelStateEnum.PLAY);
                    } else if (this.appManager.getCurrentPanel().getPanelState() == PanelStateEnum.PLAY) {
                        this.appManager.getCurrentPanel().setPanelState(PanelStateEnum.PAUSE);
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        code =  e.getKeyCode();
        isKeyReleasd = true;
        switch (code){
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                MenuController.getInstance().setCursorX(
                        NumberUtils.incrementCommand(+1,
                                MenuController.getInstance().getCursor().getKey(),
                                MenuController.getInstance().getCursorMax().getKey(), true));
                break;
            case KeyEvent.VK_Z:
            case KeyEvent.VK_UP:
                MenuController.getInstance().setCursorX(
                        NumberUtils.incrementCommand(-1,
                                MenuController.getInstance().getCursor().getKey(),
                                MenuController.getInstance().getCursorMax().getKey(), true));
                break;
            case KeyEvent.VK_SPACE:
                if (this.appManager.getCurrentPanel().getPanelState() == PanelStateEnum.VICTORY) {
                    this.appManager.displayNewPanel(PanelEnum.TITLE);
                }
                if (this.appManager.getCurrentPanel().getPanelState() == PanelStateEnum.MENU_SCREEN) {

                    if (MenuController.getInstance().getCursor().getKey() == 0) {
                        this.appManager.displayNewPanel(PanelEnum.GAME);
                    }
                    if (MenuController.getInstance().getCursor().getKey() == 1) {
                        this.appManager.displayNewPanel(PanelEnum.WORLD_SELECTION);
                    }
                    if (MenuController.getInstance().getCursor().getKey() == 2) {
                        System.exit(0);
                    }
                }
                if (this.appManager.getDisplayType() == PanelEnum.WORLD_SELECTION) {
                    int worldSelectedCode = MenuController.getInstance().getCursor().getKey();
                    TileManager.getInstance().setWorldSelected(WorldEnum.getWorldByCode(worldSelectedCode));
                    this.appManager.displayNewPanel(PanelEnum.GAME);
                }
                if (this.appManager.getCurrentPanel().getPanelState() == PanelStateEnum.DIALOG) {
                    this.appManager.getCurrentPanel().setPanelState(PanelStateEnum.PLAY);
                }
                break;
            default:
                break;
        }
    }


}
