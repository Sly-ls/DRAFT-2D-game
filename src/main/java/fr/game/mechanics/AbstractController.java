package fr.game.mechanics;

import fr.game.constants.world.WorldEnum;
import fr.game.panel.AbstractPanel;
import fr.game.constants.rendered.menu.title.TitlePanel;
import fr.game.constants.rendered.menu.worldSelection.WorldSelectionPanel;
import javafx.util.Pair;

//distingate la partie abstract Message box, de la partie game(camera) de la partie controller(panel)
/*
il faudrait faire une message box et camera des singleton
et ramener la aprtie pointer ici
 */
public abstract class AbstractController {
    protected AbstractPanel panel;
    private Pair<Integer,Integer> cursor = new Pair<>(0,0);
    private Pair<Integer,Integer> cursorMax = new Pair<>(0,0);

    protected AbstractController(AbstractPanel titlePanel){
        this.panel= titlePanel;
        if(panel instanceof TitlePanel){
            setCursorX(0);
            setCursorY(0);
            setCursorMaxX(2);
            setCursorMaxY(0);
        }
        if(panel instanceof WorldSelectionPanel){
            setCursorX(0);
            setCursorY(0);
            setCursorMaxX(WorldEnum.values().length);
            setCursorMaxY(0);
        }
    }

    //CONTROLLER METHOD
    public void setCursorMaxY(Integer cursorY) {
        this.cursorMax = new Pair<>(this.cursorMax.getKey(),cursorY);
    }
    public void setCursorMaxX(Integer cursorX) {
        this.cursorMax = new Pair<>(cursorX,this.cursorMax.getValue());
    }

    public Pair<Integer, Integer> getCursor() {
        return cursor;
    }

    public void setCursor(Pair<Integer, Integer> cursor) {
        this.cursor = cursor;
    }
    public void setCursorY(Integer cursorY) {
        this.cursor = new Pair<>(this.cursor.getKey(),cursorY);
    }
    public void setCursorX(Integer cursorX) {
        this.cursor = new Pair<>(cursorX,this.cursor.getValue());
    }
    public Pair<Integer, Integer> getCursorMax() {
        return cursorMax;
    }
}
