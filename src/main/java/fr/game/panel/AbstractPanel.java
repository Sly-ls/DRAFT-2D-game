package fr.game.panel;

import fr.game.constants.AppConstants;
import fr.game.constants.panel.PanelStateEnum;
import fr.game.main.ApplicationManager;
import fr.game.mechanics.controller.GameKeyHandler;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractPanel extends JPanel{

    protected ApplicationManager applicationManager;
    protected GameKeyHandler keyHandler;
    protected PanelStateEnum panelState  = PanelStateEnum.NONE;

    public AbstractPanel(ApplicationManager applicationManager,GameKeyHandler keyHandler){
        this.applicationManager = applicationManager;
        this.keyHandler = keyHandler;
        this.addKeyListener(keyHandler);
    }


    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
    }

    public abstract void quitPanel();

    public abstract void setupPanel();

    public abstract void update();

    public abstract AbstractUI getUiPanel();

    public PanelStateEnum getPanelState() {
        return panelState;
    }

    public void setPanelState(PanelStateEnum panelState) {
        if(AppConstants.DEBUG) {
            System.out.println(" changing panelState from "+ this.panelState
                    +" to "+ panelState);
        }
        this.panelState = panelState;
    }

    public ApplicationManager getApplicationManager() {
        return applicationManager;
    }

    public GameKeyHandler getKeyHandler() {
        return keyHandler;
    }

}
