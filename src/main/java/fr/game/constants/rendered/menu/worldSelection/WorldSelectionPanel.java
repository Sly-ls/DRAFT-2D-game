package fr.game.constants.rendered.menu.worldSelection;

import fr.game.constants.panel.PanelStateEnum;
import fr.game.main.ApplicationManager;
import fr.game.mechanics.controller.GameKeyHandler;
import fr.game.panel.AbstractPanel;
import fr.game.panel.AbstractUI;
import fr.game.constants.rendered.menu.MenuController;

import java.awt.*;

public class WorldSelectionPanel extends AbstractPanel {

    WorldSelectionUI uiPanel;

    public WorldSelectionPanel(ApplicationManager applicationManager, GameKeyHandler keyHandler) {
        super(applicationManager, keyHandler);
        MenuController.createInstance(this);
        this.applicationManager = applicationManager;
        this.uiPanel = new WorldSelectionUI(this);
        this.setPanelState(PanelStateEnum.MENU_SCREEN);
    }

    @Override
    public void setupPanel() {

    }

    @Override
    public void quitPanel() {
    }

    public void update() {

    }

    @Override
    public AbstractUI getUiPanel() {
        return uiPanel;
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        uiPanel.load(graphics2D);
        uiPanel.drawMenu(graphics2D);
        uiPanel.drawMessage();

        graphics2D.dispose();
    }

}
