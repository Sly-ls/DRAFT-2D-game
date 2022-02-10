package fr.game.main;

import fr.game.constants.AppConstants;
import fr.game.constants.AppVariables;
import fr.game.constants.panel.PanelEnum;
import fr.game.mechanics.AbstractKeyHandler;
import fr.game.mechanics.controller.GameKeyHandler;
import fr.game.panel.AbstractPanel;
import fr.game.panel.game.GamePanel;
import fr.game.constants.rendered.menu.title.TitlePanel;
import fr.game.constants.rendered.menu.worldSelection.WorldSelectionPanel;

import javax.swing.*;
import java.awt.*;

public class ApplicationManager implements Runnable{

    private Thread gameThread;

    private JFrame window;
    private AbstractPanel currentPanel;
    private PanelEnum displayType = PanelEnum.NONE;
    private PanelEnum requiredChanged;
    protected GameKeyHandler keyHandler;

    public ApplicationManager(JFrame window, PanelEnum requiredChanged) {
        this.window = window;
        this.requiredChanged=requiredChanged;
        this.keyHandler = new GameKeyHandler(this);
        startThread();
    }

    public void displayNewPanel(PanelEnum panelType){
        requiredChanged = panelType;
    }
    private void displayRequiredPanelPanel(){

        this.window.removeKeyListener(this.keyHandler);
        window.getContentPane().removeAll();
        if (this.currentPanel != null) {
            this.currentPanel.quitPanel();
        }
        switch (requiredChanged){
            case WORLD_SELECTION:
                this.currentPanel = new WorldSelectionPanel(this, this.keyHandler);
                break;
            case GAME:
                this.currentPanel = new GamePanel(this, this.keyHandler);
                break;
            case TITLE:
                this.currentPanel = new TitlePanel(this, this.keyHandler);
                break;
        }
        if(currentPanel != null) {
            this.currentPanel.setupPanel();
            this.currentPanel.setPreferredSize(new Dimension(AppVariables.SCREEN_WIDTH, AppVariables.SCREEN_HEIGHT));
            this.currentPanel.setBackground(Color.BLACK);
            this.currentPanel.setDoubleBuffered(true);
            this.currentPanel.setFocusable(true);
            window.addKeyListener(currentPanel.getKeyHandler());
            window.add(currentPanel);
        }

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.pack();

        startThread();
    }

    public void startThread(){
        if(this.gameThread == null) {
            this.gameThread = new Thread(this);
        }
        if(!gameThread.isAlive()) {
            gameThread.start();
        }
    }

    @Override
    public  void run() {
        if(requiredChanged != displayType){
            displayRequiredPanelPanel();
        }
        if(this.currentPanel != null) {
            double drawInterval = 1000000000 / AppVariables.FPS;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            while (this.gameThread != null && this.gameThread.isAlive()) {
                if(requiredChanged != displayType){
                    displayRequiredPanelPanel();
                    this.displayType = requiredChanged;
                }
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawInterval;
                lastTime = currentTime;

                if (delta >= 1) {
                    this.currentPanel.update();
                    this.currentPanel.repaint();
                    delta--;
                }
            }
        }
    }

    public AbstractKeyHandler getKeyHandler() {
        return keyHandler;
    }

    public PanelEnum getDisplayType() {
        return displayType;
    }

    public AbstractPanel  getCurrentPanel() {
        return currentPanel;
    }

}
