package fr.game.rendererd.object;

import fr.game.constants.AppConstants;
import fr.game.constants.AppVariables;
import fr.game.constants.core.FontEnum;
import fr.game.constants.core.MessageTypeEnum;
import fr.game.constants.rendered.GameValueEnum;
import fr.game.mechanics.controller.game.Camera;
import fr.game.mechanics.core.ScaledImage;
import fr.game.mechanics.core.MessageBox;
import fr.game.panel.game.GameController;
import fr.game.rendererd.AbstractRendered;
import fr.game.rendererd.entity.AbstractEntity;

import java.awt.*;

public class GameObject extends AbstractRendered implements ObjectToInteractWith{

    private int quantity;
    protected GameValueEnum type;

    protected String name;
    protected ScaledImage image;

    public GameObject(GameValueEnum objectTypeEnum,
                      int worldX, int worldY) {
        super();

        this.type = objectTypeEnum;
        this.name = objectTypeEnum.getName();
        this.solidAreaDefaultX = objectTypeEnum.getSolidAreaDefaultX();
        this.solidAreaDefaultY = objectTypeEnum.getSolidAreaDefaultY();
        this.solidAreaDefaultWitdh = objectTypeEnum.getSolidAreaDefaultWitdh();
        this.solidAreaDefaultHeigth  = objectTypeEnum.getSolidAreaDefaultHeigth();
        this.direction  = objectTypeEnum.getDirection();
        this.image = new ScaledImage(objectTypeEnum.getPath());
        this.collision = objectTypeEnum.isCollision();

        this.solidArea = new Rectangle(solidAreaDefaultX, solidAreaDefaultY, solidAreaDefaultWitdh, solidAreaDefaultHeigth);

        this.worldX = worldX * AppVariables.tileSize;
        this.worldY = worldY * AppVariables.tileSize;
        this.quantity = 1;
        //FIXME to put in gameValueEnum
        if(GameValueEnum.BOOT == this.type){
            this.quantity = 2;
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics2D){
        draw(graphics2D, 0);
    }
    public void draw(Graphics2D graphics2D, int imageIndex){

        graphics2D.drawImage(image.getBufferedImage(), Camera.getInstance().getScreenX(this.getWorldX()), Camera.getInstance().getScreenY(this.getWorldY()),
                AppVariables.tileSize, AppVariables.tileSize, null);

        if(AppConstants.DEBUG) {
            Color colorBlack = new Color(0, 255, 0, 255);
            graphics2D.setColor(colorBlack);
            graphics2D.fillRect(Camera.getInstance().getScreenX(this.getWorldX()) + this.getSolidArea().x,
                    Camera.getInstance().getScreenY(this.getWorldY()) + this.getSolidArea().y,
                    this.getSolidArea().width,
                    this.getSolidArea().height);
        }
    }

    @Override
    public void use(AbstractEntity entity) {
        switch (this.type){
            case KEY:
                entity.getStatus().pickOrDrop(this.type,1);
                pickupThisObject(entity);
                GameController.getInstance().removeObjectFromTheWorld(this);
                MessageBox.getInstance().sendMessage(MessageTypeEnum.WORLD_EVENT,"Tu as rammassé une " + this.name,
                        FontEnum.BEDROCK,Color.YELLOW, 40,
                        false, 28,
                        this.worldX,this.worldY);
                break;
            case RED_KEY:
                entity.getStatus().pickOrDrop(this.type,1);
                pickupThisObject(entity);
                GameController.getInstance().removeObjectFromTheWorld(this);
                MessageBox.getInstance().sendMessage(MessageTypeEnum.WORLD_EVENT,"Tu as rammassé une " + this.name,
                        FontEnum.BEDROCK,Color.RED, 40,
                        false, 28,
                        this.worldX,this.worldY);
                break;

            case DOOR:
                if(entity.getStatus().getInventory(GameValueEnum.KEY) > 0){
                    GameController.getInstance().removeObjectFromTheWorld(this);
                    GameController.getInstance().getPlayer().getStatus().pickOrDrop(GameValueEnum.KEY, -1);
                    MessageBox.getInstance().sendMessage(MessageTypeEnum.DIALOG,"Une nouvelle porte s'ouvre...",
                            FontEnum.BEDROCK,Color.YELLOW, 40,
                            true, 28,
                            Camera.getInstance().getScreenX(this.worldX), Camera.getInstance().getScreenY(this.worldY));
                }else{
                    MessageBox.getInstance().sendMessage(MessageTypeEnum.WORLD_EVENT,"Il faut une clef JAUNE !",
                            FontEnum.BEDROCK,Color.YELLOW, 40,
                            false, 28,
                            this.worldX,this.worldY);

                }
                break;
            case RED_DOOR:
                if(entity.getStatus().getInventory(GameValueEnum.RED_KEY) > 0){
                    GameController.getInstance().removeObjectFromTheWorld(this);
                    GameController.getInstance().getPlayer().getStatus().pickOrDrop(GameValueEnum.RED_KEY, -1);
                    MessageBox.getInstance().sendMessage(MessageTypeEnum.DIALOG,"Une nouvelle porte s'ouvre...",
                            FontEnum.BEDROCK,Color.RED, 40,
                            true, 28,
                            Camera.getInstance().getScreenX(this.worldX), Camera.getInstance().getScreenY(this.worldY));
                }else{
                    MessageBox.getInstance().sendMessage(MessageTypeEnum.WORLD_EVENT,"Il te faut une clef ROUGE !",
                            FontEnum.BEDROCK,Color.RED, 40,
                            false, 28,
                            this.worldX,this.worldY);;

                }
                break;
            case BOOT:
                pickupThisObject(entity);
                MessageBox.getInstance().sendMessage(MessageTypeEnum.MESSAGE_TO_PLAYER,"VITE VITESSE !",
                        FontEnum.BEDROCK,Color.BLUE, 28,
                        true, 28,
                        this.worldX,this.worldY);;
                break;
            case CHEST:
                pickupThisObject(entity);
                MessageBox.getInstance().sendMessage(MessageTypeEnum.WORLD_EVENT,"TRESOR !",
                        FontEnum.BEDROCK,Color.MAGENTA, 28,
                        true, 28,
                        this.worldX,this.worldY);;
                break;
            case HEALTH:
                break;
            case HOURGLASS:
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

    public GameValueEnum getType() {
        return type;
    }

    public void setType(GameValueEnum type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }
}
