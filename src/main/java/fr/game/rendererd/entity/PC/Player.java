package fr.game.rendererd.entity.PC;

import fr.game.constants.AppConstants;
import fr.game.constants.AppVariables;
import fr.game.constants.core.DirectionsEnum;
import fr.game.constants.rendered.EntityEnum;
import fr.game.constants.rendered.GameValueEnum;
import fr.game.mechanics.controller.game.Camera;
import fr.game.panel.game.GameController;
import fr.game.rendererd.AbstractRendered;
import fr.game.rendererd.entity.AbstractEntity;
import fr.game.rendererd.object.GameObject;
import fr.game.rendererd.object.ObjectToInteractWith;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends AbstractEntity {
    public Player(EntityEnum objectTypeEnum,
                  int worldX, int worldY) {
        //FIX ME
        super(objectTypeEnum, worldX,worldY);
        this.status.pickOrDrop(GameValueEnum.KEY, 0);
        this.status.pickOrDrop(GameValueEnum.RED_KEY, 0);
    }


    public void update(){

        if(GameController.getInstance().getPanel().getKeyHandler().isUpPressed() || GameController.getInstance().getPanel().getKeyHandler().isDownPressed() ||
                GameController.getInstance().getPanel().getKeyHandler().isRightPressed() || GameController.getInstance().getPanel().getKeyHandler().isLeftPressed() ){
            this.collisionOn = false;
            if(GameController.getInstance().getPanel().getKeyHandler().isUpPressed()){
                this.direction = DirectionsEnum.UP;
                printDebugUpadte(this.direction);
            }
            if(GameController.getInstance().getPanel().getKeyHandler().isDownPressed()){
                this.direction = DirectionsEnum.DOWN;
                printDebugUpadte(this.direction);
            }
            if(GameController.getInstance().getPanel().getKeyHandler().isLeftPressed()){
                this.direction = DirectionsEnum.LEFT;
                printDebugUpadte(this.direction);
            }
            if(GameController.getInstance().getPanel().getKeyHandler().isRightPressed()){
                this.direction = DirectionsEnum.RIGHT;
                printDebugUpadte(this.direction);
            }

            move();
        }
    }

    public void printDebugUpadte(DirectionsEnum direction){
        if(AppConstants.DEBUG_KEYBOARD) {
            System.out.println(getClass() + " this.direction = " + direction.name());
        }
    }

    private void interactNpc(GameObject npcToInteract) {
        if(AppConstants.DEBUG) {
        System.out.println("interactNpc.npcToInteract " + npcToInteract);
        }
    }

    public void pickUpObject(GameObject objectToPickUp) {
            if(AppConstants.DEBUG) {
        System.out.println("pickUpObject.objectToPickUp " + objectToPickUp);
            }
    }

    public void draw(Graphics2D graphics2D) {

        BufferedImage image = null;

        spriteCounter++;
        switch (this.direction){
            case UP:
                image=up.getBufferedImage(spriteNum);
                break;
            case DOWN:
                image=down.getBufferedImage(spriteNum);
                break;
            case LEFT:
                image=left.getBufferedImage(spriteNum);
                break;
            case RIGHT:
                image=right.getBufferedImage(spriteNum);
                break;
        }

        graphics2D.drawImage(image, Camera.getInstance().getScreenX(this.getWorldX()), Camera.getInstance().getScreenY(this.getWorldY()),
                AppVariables.tileSize, AppVariables.tileSize, null);

        graphics2D.drawImage(image,
                Camera.getInstance().getScreenX(this.getWorldX()),
                Camera.getInstance().getScreenY(this.getWorldY()),
                AppVariables.tileSize, AppVariables.tileSize, null);
    }

    @Override
    public void setAction() {
    }

    @Override
    public void speak(AbstractEntity entity){
        entity.speak(this);
    }

    @Override
    public void use(ObjectToInteractWith gameObject){
        gameObject.use(this);
    }

    @Override
    public void pickup(ObjectToInteractWith object) {
        if(object instanceof GameObject) {
            this.status.pickOrDrop(((GameObject) object).getType(), ((GameObject) object).getQuantity());
            GameController.getInstance().removeObjectFromTheWorld((AbstractRendered) object);
        }
    }

}
