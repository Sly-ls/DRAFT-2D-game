package fr.game.rendererd.entity;

import fr.game.constants.AppConstants;
import fr.game.constants.AppVariables;
import fr.game.constants.core.DirectionsEnum;
import fr.game.constants.rendered.EntityEnum;
import fr.game.constants.rendered.GameValueEnum;
import fr.game.main.utils.NumberUtils;
import fr.game.mechanics.controller.game.CollisionChecker;
import fr.game.mechanics.core.ScaledImage;
import fr.game.rendererd.AbstractRendered;
import fr.game.rendererd.event.TargetEvent;
import fr.game.rendererd.object.ObjectToInteractWith;

import java.awt.*;

public abstract class AbstractEntity extends AbstractRendered implements InteractWithObject, InteractWithCaracter {

    protected EntityEnum type;
    protected String name;

    //animation stat
    protected ScaledImage up,
            down,
            left,
            right;

    protected int spriteCounter = 0;
    protected int spriteNum = 0;

    //NPC stat
    protected String dialogArray[] = new String[4];

    //entity stat
    protected boolean collisionOn = false;

    public AbstractEntity(EntityEnum objectTypeEnum,
                          int worldX, int worldY) {
        super();
        this.type = objectTypeEnum;
        this.name = objectTypeEnum.getName();
        this.solidAreaDefaultX = objectTypeEnum.getSolidAreaDefaultX();
        this.solidAreaDefaultY = objectTypeEnum.getSolidAreaDefaultY();
        this.solidAreaDefaultWitdh = objectTypeEnum.getSolidAreaDefaultWitdh();
        this.solidAreaDefaultHeigth  = objectTypeEnum.getSolidAreaDefaultHeigth();
        this.direction  = objectTypeEnum.getDirection();
        this.collision = objectTypeEnum.isCollision();
        this.solidArea = new Rectangle(solidAreaDefaultX, solidAreaDefaultY,
                solidAreaDefaultWitdh, solidAreaDefaultHeigth);
        this.worldX = worldX * AppVariables.tileSize;
        this.worldY = worldY * AppVariables.tileSize;

        this.status.pickOrDrop(GameValueEnum.BOOT, objectTypeEnum.getSpeed());
        this.status.pickOrDrop(GameValueEnum.MAX_LIFE, objectTypeEnum.getMaxLife());
        this.status.pickOrDrop(GameValueEnum.HEALTH, objectTypeEnum.getMaxLife());

        getAnimationImage(objectTypeEnum);
    }

    private void getAnimationImage(EntityEnum objectTypeEnum){

        this.up = objectTypeEnum.getPathUp();
        this.down = objectTypeEnum.getPathDown();
        this.left = objectTypeEnum.getPathLeft();
        this.right = objectTypeEnum.getPathRight();


    }

    public abstract void setAction();


    public void update(){
        this.collisionOn=false;
        setAction();
        move();
    }
    protected void move(){
        if(!AppConstants.DEBUG ||(AppConstants.DEBUG && AppConstants.DEBUG_TILE_COLLISION) ) {
            CollisionChecker.checkTilesCollision(this);
        }
        if(!AppConstants.DEBUG ||(AppConstants.DEBUG && AppConstants.DEBUG_OBJECT_COLLISION) ) {
            CollisionChecker.checkGameObjectCollision(this);
        }

        if(!this.collisionOn){
            this.spriteCounter++;
            switch (this.direction){
                case UP:
                    if(this.spriteCounter > 10) {
                        this.spriteNum = NumberUtils.incrementCommand(1, spriteNum, up.bufferedImage.length - 1, true);
                    }
                    this.worldY = NumberUtils.incrementCommand(-this.getStatus(GameValueEnum.BOOT), this.worldY, AppVariables.maxWorldY, AppVariables.goesAroundScreen);
                    break;
                case DOWN:
                    if(this.spriteCounter > 10) {
                        this.spriteNum = NumberUtils.incrementCommand(1, spriteNum, down.bufferedImage.length - 1, true);
                    }
                    this.worldY = NumberUtils.incrementCommand(this.getStatus(GameValueEnum.BOOT), this.worldY, AppVariables.maxWorldY, AppVariables.goesAroundScreen);
                    break;
                case LEFT:
                    if(this.spriteCounter > 10) {
                        this.spriteNum = NumberUtils.incrementCommand(1,spriteNum,left.bufferedImage.length-1,true);
                    }
                    this.worldX = NumberUtils.incrementCommand(-this.getStatus(GameValueEnum.BOOT), this.worldX, AppVariables.maxWorldX, AppVariables.goesAroundScreen);
                    break;
                case RIGHT:
                    if(this.spriteCounter > 10) {
                        this.spriteNum = NumberUtils.incrementCommand(1,spriteNum,right.bufferedImage.length-1,true);
                    }
                    this.worldX = NumberUtils.incrementCommand(this.getStatus(GameValueEnum.BOOT), this.worldX, AppVariables.maxWorldX, AppVariables.goesAroundScreen);
                    break;
            }
            if(this.spriteCounter > 10){
                this.spriteCounter=0;
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void speak(AbstractEntity entity){
        System.out.println("(Entity)" + this.name + " at " + this.getWorldX()+","+this.getWorldY() + " speak with " + entity.getName() + " at " + entity.getWorldX()+","+entity.getWorldY());

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
    @Override
    public void use(ObjectToInteractWith gameObject){
        if(AppConstants.DEBUG) {
            System.out.println(this.getClass() + " " + this.name + " at " + this.getWorldX() + "," + this.getWorldY() + " uses " + gameObject.getName() + " at " + gameObject.getWorldX() + "," + gameObject.getWorldY());
        }
        switch (gameObject.getDirection()){
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


    @Override
    public void trigger(TargetEvent newEvent){
        newEvent.use(this);
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public void setSolidArea(Rectangle solidArea) {
        this.solidArea = solidArea;
    }

    public boolean isCollisionOn() {
        return collisionOn;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    public DirectionsEnum getDirection() {
        return direction;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public void setSolidAreaDefaultX(int solidAreaDefaultX) {
        this.solidAreaDefaultX = solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    public void setSolidAreaDefaultY(int solidAreaDefaultY) {
        this.solidAreaDefaultY = solidAreaDefaultY;
    }

}
