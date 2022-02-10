package fr.game.rendererd.entity.NPC;

import fr.game.constants.AppConstants;
import fr.game.constants.AppVariables;
import fr.game.constants.core.DirectionsEnum;
import fr.game.constants.core.FontEnum;
import fr.game.constants.core.MessageTypeEnum;
import fr.game.constants.rendered.EntityEnum;
import fr.game.main.utils.NumberUtils;
import fr.game.mechanics.controller.game.Camera;
import fr.game.mechanics.core.Randomizer;
import fr.game.mechanics.core.MessageBox;
import fr.game.rendererd.entity.AbstractEntity;
import fr.game.rendererd.object.GameObject;
import fr.game.rendererd.object.ObjectToInteractWith;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC extends AbstractEntity {


    protected int dialogCounter = 0;
    protected int actionLockCounter = 0;

    public NPC(EntityEnum objectTypeEnum,
               int worldX, int worldY) {
        super(objectTypeEnum, worldX, worldY);

        setDialog();
        setAction();
    }

    @Override
    public void speak(AbstractEntity entity){
        if(dialogCounter >= dialogArray.length){
            dialogCounter = 0;
        }
        MessageTypeEnum messageType = MessageTypeEnum.DIALOG;
        if(entity instanceof NPC){
            messageType = MessageTypeEnum.WORLD_EVENT;
        }
         MessageBox.getInstance().sendMessage(messageType,dialogArray[dialogCounter],
                FontEnum.BEDROCK,Color.BLACK, 40,
                true, 28,
                this.worldX,this.worldY);

        if(AppConstants.DEBUG){
            System.out.println(getClass() + " " + name + " at " + this.getWorldX()+","+this.getWorldY() + " speak with " + entity.getName() + " at " + entity.getWorldX()+","+entity.getWorldY()
                    +"\nIl dit : " + dialogArray[dialogCounter]);
        }
        dialogCounter++;

        switch (entity.getDirection()){
            case UP:
                this.direction = DirectionsEnum.DOWN;
                break;
            case DOWN:
                this.direction = DirectionsEnum.UP;
                break;
            case LEFT:
                this.direction = DirectionsEnum.RIGHT;
                break;
            case RIGHT:
                this.direction = DirectionsEnum.LEFT;
                break;
        }
    }
    public void setDialog(){
        this.dialogArray[0] = "DIALOGUE DU NPC PAS IMPLEMENTE !";
    }

    public void draw(Graphics2D graphics2D) {


            BufferedImage image = null;

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
            graphics2D.drawImage(image, Camera.getInstance().getScreenX(this.getWorldX()), Camera.getInstance().getScreenY(this.getWorldY()), null);

    }

    @Override
    public void setAction() {
        if(actionLockCounter >= 1* AppVariables.FPS) {
            Randomizer randomizer = new Randomizer();
            int randomNumber = randomizer.getRandomNumberInRange(100);
            if (randomNumber <= 25) {
                this.spriteNum = NumberUtils.incrementCommand(1,spriteNum,down.bufferedImage.length-1,true);
                this.direction = DirectionsEnum.UP;
            } else if (randomNumber <= 50) {
                this.spriteNum = NumberUtils.incrementCommand(1,spriteNum,left.bufferedImage.length-1,true);
                this.direction = DirectionsEnum.LEFT;
            } else if (randomNumber <= 75) {
                this.spriteNum = NumberUtils.incrementCommand(1,spriteNum,up.bufferedImage.length-1,true);
                this.direction = DirectionsEnum.RIGHT;
            } else if (randomNumber <= 100) {
                this.spriteNum = NumberUtils.incrementCommand(1,spriteNum,right.bufferedImage.length-1,true);
                this.direction = DirectionsEnum.DOWN;
            }
            actionLockCounter = 0;
        }
        actionLockCounter++;
    }

    @Override
    public void pickup(ObjectToInteractWith gameObject) {
        if(AppConstants.DEBUG){
            System.out.println(getClass() + " " + this.name + " at " + this.worldX+","+this.worldY + " could pickup "
                    +gameObject.getName() +" at " +gameObject.getWorldX() +"," +gameObject.getWorldY());
        }
    }
}
