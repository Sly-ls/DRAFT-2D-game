package fr.game.rendererd;

import fr.game.constants.AppConstants;
import fr.game.constants.AppVariables;
import fr.game.constants.core.DirectionsEnum;
import fr.game.constants.core.FontEnum;
import fr.game.constants.core.GameUnitEnum;
import fr.game.constants.rendered.GameValueEnum;
import fr.game.mechanics.controller.game.Camera;
import fr.game.panel.game.GameController;
import fr.game.rendererd.entity.NPC.NPC;
import fr.game.rendererd.entity.PC.Player;
import fr.game.rendererd.event.TargetEvent;
import fr.game.rendererd.object.GameObject;

import java.awt.*;

public abstract class AbstractRendered   {

    //Coordinate
    protected int worldX;
    protected int worldY;
    protected Rectangle solidArea;
    protected int solidAreaDefaultX, solidAreaDefaultY;
    protected int solidAreaDefaultWitdh;
    protected int solidAreaDefaultHeigth;

    //Stats
    protected Inventory status;
    protected boolean collisionOn = false;
    protected DirectionsEnum direction;
    protected boolean collision = false;

    public AbstractRendered() {
        this.status = new Inventory();
    }

    public abstract void update();
    public abstract void draw(Graphics2D graphics2D);

    public void drawDebug(Graphics2D graphics2D){

        if(AppConstants.DEBUG) {
            if(this instanceof NPC) {
                graphics2D.setColor(new Color(255, 0, 255, 255));
            }else if (this instanceof Player){
                GameController.getInstance().getPanel().getUiPanel().setUIFont(FontEnum.ARIAL,20, Color.GREEN);
                StringBuffer messageCamera = new StringBuffer();
                messageCamera.append("c").append(Camera.getInstance().getCameraWorldX(GameUnitEnum.TILE))
                        .append(",").append(Camera.getInstance().getCameraWorldY(GameUnitEnum.TILE));
                graphics2D.drawString(messageCamera.toString(),
                        Camera.getInstance().getCameraWorldX(GameUnitEnum.TILE)+ AppVariables.tileSize/2,
                        Camera.getInstance().getCameraWorldY(GameUnitEnum.TILE)+ AppVariables.tileSize/2);
                graphics2D.setColor(new Color(0, 0, 255, 255));
            }else if(this instanceof TargetEvent){
                graphics2D.setColor(new Color(255, 0, 0, 255));
            }else if (this instanceof GameObject){
                graphics2D.setColor(new Color(255, 255, 0, 255));
            }else{
                graphics2D.setColor(new Color(255, 255, 255, 255));
            }

            graphics2D.fillRect(Camera.getInstance().getScreenX(this.getWorldX()) + this.getSolidArea().x,
                    Camera.getInstance().getScreenY(this.getWorldY()) + this.getSolidArea().y,
                    this.getSolidArea().width,
                    this.getSolidArea().height);

            GameController.getInstance().getPanel().getUiPanel().setUIFont(FontEnum.ARIAL,20,Color.GREEN);
            StringBuffer messagePlayer = new StringBuffer();
            messagePlayer.append("p").append(this.getWorldX()/ AppVariables.tileSize)
                    .append(",").append(this.getWorldY()/ AppVariables.tileSize);
            graphics2D.drawString(messagePlayer.toString(),
                    Camera.getInstance().getScreenX(this.getWorldX()),
                    Camera.getInstance().getScreenY(this.getWorldY()) + AppVariables.tileSize);
        }
    }
    public int getStatus(GameValueEnum valueType) {
        return status.getInventory(valueType);
    }
    public Inventory getStatus() {
        return status;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
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

    public boolean isCollisionOn() {
        return collisionOn;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    public DirectionsEnum getDirection() {
        return direction;
    }

    public boolean isCollision() {
        return collision;
    }

}
