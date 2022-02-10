package fr.game.rendererd.entity.NPC.neutral;

import fr.game.constants.rendered.EntityEnum;
import fr.game.rendererd.entity.NPC.NPC;

public class OldMan extends NPC {
    public OldMan(EntityEnum objectTypeEnum, int worldX, int worldY) {
        super(objectTypeEnum, worldX, worldY);
    }

@Override
    public void setDialog(){
        this.dialogArray[0] = "Bonjour !";
        this.dialogArray[1] = "J'étais un grand mage quand j'étais plus jeune.";
        this.dialogArray[2] = "J'aime la chantilly, vraiment beaucoup, mais il faut pas en\nabuser, car c'est mauvais pour la santé.";
        this.dialogArray[3] = "Attention quand tu traverse la route.";
    }
}
