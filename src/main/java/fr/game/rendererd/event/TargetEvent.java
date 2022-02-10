package fr.game.rendererd.event;

import fr.game.constants.AppConstants;
import fr.game.constants.AppVariables;
import fr.game.constants.core.DirectionsEnum;
import fr.game.constants.core.GameUnitEnum;
import fr.game.constants.core.MessageTypeEnum;
import fr.game.constants.rendered.GameValueEnum;
import fr.game.constants.rendered.TargetEventEnum;
import fr.game.mechanics.controller.game.Camera;
import fr.game.mechanics.core.ScaledImage;
import fr.game.mechanics.core.MessageBox;
import fr.game.panel.game.GameController;
import fr.game.rendererd.AbstractRendered;
import fr.game.rendererd.entity.AbstractEntity;
import fr.game.rendererd.entity.NPC.NPC;
import fr.game.rendererd.object.ObjectToInteractWith;

import java.awt.*;

public class TargetEvent extends AbstractRendered  implements ObjectToInteractWith {

    TargetEventEnum eventType;
    String name;
    boolean triggered = false;
    boolean resetable = true;
    boolean canTouchEvent = true;
    boolean visible = false;
    ScaledImage image;
    AbstractEntity target;

    public TargetEvent(TargetEventEnum eventType,
                       int worldX, int worldY) {
        super();
        this.eventType = eventType;
        this.name = eventType.name();
        this.solidAreaDefaultX = eventType.getSolidAreaDefaultX();
        this.solidAreaDefaultY = eventType.getSolidAreaDefaultY();
        this.solidAreaDefaultWitdh = eventType.getSolidAreaDefaultWitdh();
        this.solidAreaDefaultHeigth  = eventType.getSolidAreaDefaultHeigth();
        this.direction  = eventType.getDirection();
        this.collision = eventType.isCollision();
        this.visible = eventType.isVisibile();

        if(eventType.getImagePath() != null){
            image = new ScaledImage(eventType.getImagePath());
        }

        this.solidArea = new Rectangle(solidAreaDefaultX, solidAreaDefaultY, solidAreaDefaultWitdh, solidAreaDefaultHeigth);

        this.worldX = worldX * AppVariables.tileSize;
        this.worldY = worldY * AppVariables.tileSize;
    }

    public void reload(){
        if(target != null) {
            int xDistance = Math.abs(this.getWorldX() - this.target.getWorldX());
            int yDistance = Math.abs(this.getWorldY() - this.target.getWorldY());
            int distance = Math.max(xDistance, yDistance);
            if (distance > AppVariables.tileSize){
                if(resetable) {

                    this.visible = eventType.isVisibile();
                    this.triggered = false;
                    this.canTouchEvent = true;
                }
                this.target = null;
            }
        }
    }

    private void teleport() {
        //FIXME needTocheckForCollisionWithin the tile
        this.target.setWorldX(26* AppVariables.tileSize);
        this.target.setWorldX(26* AppVariables.tileSize);
    }

    private void damagePit() {
        this.target.getStatus().pickOrDrop(GameValueEnum.HEALTH,-1);
        this.visible = true;
    }

    private void healingPool() {
        this.target.getStatus().pickOrDrop(GameValueEnum.HEALTH,1);

    }

    @Override
    public void update() {
        if(canTouchEvent && target != null) {
            target.getSolidArea().x = target.getWorldX() + target.getSolidArea().x;
            target.getSolidArea().y = target.getWorldY() + target.getSolidArea().y;
            this.getSolidArea().x = this.getWorldX() + this.getSolidArea().x;
            this.getSolidArea().y = this.getWorldY() + this.getSolidArea().y;
            if (collision && target.isCollision()){
                switch (this.target.getDirection()){
                    case UP:
                        target.getSolidArea().y -= this.target.getStatus(GameValueEnum.BOOT);
                        break;
                    case DOWN:
                        target.getSolidArea().y += this.target.getStatus(GameValueEnum.BOOT);
                        break;
                    case LEFT:
                        target.getSolidArea().x-= this.target.getStatus(GameValueEnum.BOOT);
                        break;
                    case RIGHT:
                        target.getSolidArea().x += this.target.getStatus(GameValueEnum.BOOT);
                        break;
                }
            }
            if (target.getSolidArea().intersects(this.getSolidArea())
                    && (target.getDirection() == this.getDirection() || this.getDirection() == DirectionsEnum.ANY)
                    && this.triggered == false) {
                this.triggered= true;
            }
            target.getSolidArea().x = target.getSolidAreaDefaultY();
            target.getSolidArea().y = target.getSolidAreaDefaultY();
            this.solidArea.x = this.solidAreaDefaultX;
            this.solidArea.y = this.solidAreaDefaultY;
        }
        reload();
    }


    @Override
    public void draw(Graphics2D graphics2D) {
        Color subWindowColor  = new Color(46, 194, 165, 50);
        Color subWindowFontColor  = new Color(255,255,255, 255);
        if(isTriggered()) {
            GameController.getInstance().getPanel().getUiPanel().drawSubWindow(
                    Camera.getInstance().getScreenX(this.worldX)  - AppVariables.tileSize/ AppVariables.originalTileSize,
                    Camera.getInstance().getScreenY(this.worldY)  - AppVariables.tileSize/ AppVariables.originalTileSize,
                    AppVariables.tileSize + 2*(AppVariables.tileSize/ AppVariables.originalTileSize),
                    AppVariables.tileSize + 2*(AppVariables.tileSize/ AppVariables.originalTileSize),
                    new Color(46, 194, 165, 50),
                    new Color(255,255,255, 255)

            );
        }
        if(visible && image != null){
            graphics2D.drawImage(image.getBufferedImage(), Camera.getInstance().getScreenX(this.worldX), Camera.getInstance().getScreenY(this.worldY),null);
        }
        if(isTriggered() && canTouchEvent) {
            MessageTypeEnum messageTypeToSend = eventType.getMessageType();
            boolean isNPC = this.target instanceof NPC;
            if (messageTypeToSend == MessageTypeEnum.DIALOG && isNPC){
                messageTypeToSend = MessageTypeEnum.WORLD_EVENT;
            }
            MessageBox.getInstance().sendMessage(messageTypeToSend, eventType.getMessage(),
                    this.worldX,
                    this.worldY,
                    true);
            switch (eventType){
                case TRAP:
                    damagePit();
                    break;
                case TELEPORT:
                    teleport();
                    break;
                case HEALING_POTION:
                    healingPool();
                    break;
            }
            this.canTouchEvent = false;
            printlDebug();
        }
    }
    public void printlDebug(){

        if (AppConstants.DEBUG) {
            System.out.println(this.target.getName() +" triggered " + this.eventType.name() + " at "
                    + Camera.getInstance().getScreenX(this.worldX + AppVariables.SCREEN_WIDTH/2, GameUnitEnum.TILE)
                    +","
                    + Camera.getInstance().getScreenY(this.worldY + AppVariables.SCREEN_WIDTH/2, GameUnitEnum.TILE)
                    +" !"
            );
        }
    }
    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered(boolean eventDone) {
        this.triggered = eventDone;
    }

    @Override
    public void use(AbstractEntity entity) {
        this.target=entity;
        if(AppConstants.DEBUG) {
            System.out.println(getClass() + " " + this.eventType.name() + " is used by " + entity.getName());
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
