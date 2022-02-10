package fr.game.rendererd.object;

import fr.game.constants.AppConstants;
import fr.game.constants.core.FontEnum;
import fr.game.constants.core.MessageTypeEnum;
import fr.game.constants.rendered.LightSourceEnum;
import fr.game.mechanics.controller.game.Camera;
import fr.game.mechanics.core.MessageBox;
import fr.game.mechanics.core.ScaledImage;
import fr.game.panel.game.GameController;
import fr.game.rendererd.entity.AbstractEntity;

import java.awt.*;

public class LightSource extends GameObject implements ObjectToInteractWith{

    protected LightSourceEnum type;
    protected int power;
    protected int durability;
    protected int radius;
    protected boolean lightOn;

    public LightSource(LightSourceEnum objectTypeEnum,
                       int worldX, int worldY) {
        super(objectTypeEnum.getGameObject(), worldX, worldY);

        this.type = objectTypeEnum;
        this.power = objectTypeEnum.getPower();
        this.durability = objectTypeEnum.getDurability();
        this.radius = objectTypeEnum.getRadius();
        this.lightOn = true;
    }

    @Override
    public void update() {
        if(AppConstants.DEBUG) {
            System.out.println("ligth");
        }
    }

    @Override
    public void draw(Graphics2D graphics2D){
        draw(graphics2D, 0);
    }
    public void draw(Graphics2D graphics2D, int imageIndex){


    }

    @Override
    public void use(AbstractEntity entity) {
        switch (this.type){
            case GROUND_TORCH:
                pickupThisObject(entity);
                GameController.getInstance().removeObjectFromTheWorld(this);
                MessageBox.getInstance().sendMessage(MessageTypeEnum.WORLD_EVENT,"Tu as rammassé une " + this.name,
                        FontEnum.BEDROCK,Color.YELLOW, 40,
                        false, 28,
                        this.worldX,this.worldY);
                break;
        }
    }
private void pickupThisObject(AbstractEntity entity){
    entity.pickup(this);
}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ScaledImage getImage() {
        return image;
    }

    public void setImage(ScaledImage image) {
        this.image = image;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    public void setType(LightSourceEnum type) {
        this.type = type;
    }

}
