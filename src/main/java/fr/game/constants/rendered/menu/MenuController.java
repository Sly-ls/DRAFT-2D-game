package fr.game.constants.rendered.menu;

import fr.game.constants.world.WorldEnum;
import fr.game.mechanics.AbstractController;
import fr.game.panel.AbstractPanel;
import fr.game.constants.rendered.menu.title.TitlePanel;
import fr.game.constants.rendered.menu.worldSelection.WorldSelectionPanel;

public class MenuController extends AbstractController {

    //SINGLETON
    static MenuController instance;
    private MenuController(AbstractPanel panel){
        super(panel);

        if(this.panel instanceof TitlePanel){
            setCursorX(0);
            setCursorY(0);
            setCursorMaxX(2);
            setCursorMaxY(0);
        }
        if(this.panel instanceof WorldSelectionPanel){
            setCursorX(0);
            setCursorY(0);
            setCursorMaxX(WorldEnum.values().length-1);
            setCursorMaxY(0);
        }
    }
    public static MenuController createInstance(AbstractPanel panelWithCursor) {
        instance = new MenuController(panelWithCursor);
        return instance;
    }
    public static MenuController getInstance() {
        if(instance==null){
            //lévera une NullPointerException
        }
        return instance;
    }
}
