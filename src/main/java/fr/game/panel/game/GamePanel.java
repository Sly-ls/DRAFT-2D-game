package fr.game.panel.game;

import fr.game.constants.rendered.GameValueEnum;
import fr.game.constants.panel.PanelStateEnum;
import fr.game.mechanics.controller.GameKeyHandler;
import fr.game.mechanics.controller.game.LightController;
import fr.game.mechanics.controller.game.SoundController;
import fr.game.main.ApplicationManager;
import fr.game.panel.AbstractPanel;
import fr.game.panel.AbstractUI;
import fr.game.rendererd.AbstractRendered;
import fr.game.rendererd.TileManager;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class GamePanel extends AbstractPanel {

    //COMPONENT MECHANICS
    protected GameUI uiPanel;
    //TODO put order process in camera
    public Comparator coordinateComparator = new GameController.CoordinateComparator();

    public GamePanel(ApplicationManager applicationManager, GameKeyHandler keyHandler) {
        super(applicationManager, keyHandler);
        /*
        use only one keyHandker instanciante by the Application manager
        it solve the issue the last time
        but i need to handle the panel change and will eventually create a panel manel for for the JPanel to draw
       this.keyHandler = new GameKeyHandler(gameController);
       + need to do that in the menu manager
         */
    }


    @Override
    public void quitPanel(){
        SoundController.getInstance().stopMusic();
    }
    @Override
    public void setupPanel(){
        GameController.createInstance(this);
        GameController.getInstance().setupGame();
        this.uiPanel = new GameUI(this);
        this.setPanelState(PanelStateEnum.PLAY);
    }

    public void update(){
        CheckVictoryCondition();
        if (this.panelState == PanelStateEnum.PLAY) {
            List<AbstractRendered> listToUpdate = GameController.getInstance().getListToUpdate();
            for(AbstractRendered toUpdate : listToUpdate){
                toUpdate.update();
            }
            LightController.getInstance().update();
        }

        if (this.panelState == PanelStateEnum.PAUSE || this.panelState == PanelStateEnum.DIALOG) {
        }
    }

    public void CheckVictoryCondition(){
        if(GameController.getInstance().getPlayer().getStatus(GameValueEnum.CHEST) > 0){
            this.setPanelState(PanelStateEnum.VICTORY);
        }
    }
    @Override
    public AbstractUI getUiPanel() {
        return uiPanel;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        List<AbstractRendered> listToDisplay = GameController.getInstance().getListToDisplay();

        uiPanel.load(graphics2D);
        TileManager.getInstance().draw(graphics2D);
        for (AbstractRendered toDisplay : listToDisplay) {
            toDisplay.draw(graphics2D);
        }
        LightController.getInstance().drawNaturalLight(graphics2D);
        uiPanel.draw(graphics2D);
        uiPanel.drawMessage();
        switch (this.panelState) {
            //TILE
            default:
            case PAUSE:
                break;
            case VICTORY:
                uiPanel.drawVictoryScreen(graphics2D);
                break;
            case PLAY:
                break;
        }
        for (AbstractRendered toDisplay : listToDisplay) {
            toDisplay.drawDebug(graphics2D);
        }
        graphics2D.dispose();

    }

    public GameKeyHandler getKeyHandler() {
        return keyHandler;
    }
}


