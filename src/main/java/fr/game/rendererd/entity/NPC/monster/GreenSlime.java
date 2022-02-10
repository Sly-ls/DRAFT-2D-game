package fr.game.rendererd.entity.NPC.monster;

import fr.game.constants.rendered.EntityEnum;
import fr.game.rendererd.entity.NPC.NPC;

public class GreenSlime extends NPC {
    public GreenSlime(EntityEnum entityType, int worldX, int worldY) {
        super(entityType, worldX, worldY);
    }



    public void setDialog(){
        this.dialogArray[0] = "GRRRrrrr";
        this.dialogArray[1] = "RRaaawWW";
    }
}
