package fr.game.mechanics.controller.game;

import fr.game.constants.AppConstants;
import fr.game.constants.AppVariables;
import fr.game.constants.rendered.GameValueEnum;
import fr.game.panel.game.GameController;
import fr.game.rendererd.AbstractRendered;
import fr.game.rendererd.TileManager;
import fr.game.rendererd.entity.AbstractEntity;
import fr.game.rendererd.entity.NPC.NPC;
import fr.game.rendererd.event.TargetEvent;
import fr.game.rendererd.object.GameObject;

public class CollisionChecker {

    int defaultObjectIndex = -1;

    public CollisionChecker() {
    }



    public static void checkGameObjectCollision(AbstractEntity entity){
        for(AbstractRendered objectToCheckForCollision : GameController.getInstance().getListToUpdate()){
            if(entity == objectToCheckForCollision) continue;
            boolean interactWith = false;
            entity.getSolidArea().x = entity.getWorldX()  + entity.getSolidArea().x;
            entity.getSolidArea().y = entity.getWorldY()  + entity.getSolidArea().y;
            objectToCheckForCollision.getSolidArea().x = objectToCheckForCollision.getWorldX() + objectToCheckForCollision.getSolidArea().x;
            objectToCheckForCollision.getSolidArea().y = objectToCheckForCollision.getWorldY() + objectToCheckForCollision.getSolidArea().y;


            switch (entity.getDirection()){
                case UP:
                    entity.getSolidArea().y -= entity.getStatus(GameValueEnum.BOOT);
                    interactWith = checkGameObjectCollision(entity, objectToCheckForCollision);
                    break;
                case DOWN:
                    entity.getSolidArea().y += entity.getStatus(GameValueEnum.BOOT);
                    interactWith = checkGameObjectCollision(entity, objectToCheckForCollision);
                    break;
                case LEFT:
                    entity.getSolidArea().x -= entity.getStatus(GameValueEnum.BOOT);
                    interactWith = checkGameObjectCollision(entity, objectToCheckForCollision);
                    break;
                case RIGHT:
                    entity.getSolidArea().x += entity.getStatus(GameValueEnum.BOOT);
                    interactWith = checkGameObjectCollision(entity, objectToCheckForCollision);
                    break;
            }

            entity.getSolidArea().x = entity.getSolidAreaDefaultX();
            entity.getSolidArea().y = entity.getSolidAreaDefaultY();
            objectToCheckForCollision.getSolidArea().x = objectToCheckForCollision.getSolidAreaDefaultX();
            objectToCheckForCollision.getSolidArea().y = objectToCheckForCollision.getSolidAreaDefaultY();

            //TODO fine tune, check and externalize
            if(interactWith){
                if(entity instanceof AbstractEntity){
                    if(objectToCheckForCollision instanceof NPC){
                        entity.speak((AbstractEntity) objectToCheckForCollision);
                    }else if(objectToCheckForCollision instanceof GameObject){
                        entity.use((GameObject) objectToCheckForCollision);
                    }else if(objectToCheckForCollision instanceof TargetEvent){
                        entity.trigger((TargetEvent) objectToCheckForCollision);
                    }
                }
            }
        }
    }
    public static void checkTilesCollision(AbstractRendered entity){
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX / AppVariables.tileSize;
        int entityRightCol = entityRightWorldX / AppVariables.tileSize;
        int entityTopRow = entityTopWorldY / AppVariables.tileSize;
        int entityBottomRow = entityBottomWorldY / AppVariables.tileSize;

        int tileNum1, tileNum2;

        switch (entity.getDirection()) {
            case UP:
                entityTopRow = (entityTopWorldY - entity.getStatus(GameValueEnum.BOOT)) / AppVariables.tileSize;
                tileNum1 = TileManager.getInstance().getMapTileArray()[entityLeftCol][entityTopRow];
                tileNum2 = TileManager.getInstance().getMapTileArray()[entityRightCol][entityTopRow];
                checkTilesCollision(tileNum1, tileNum2, entity);
                break;
            case DOWN:
                entityBottomRow = (entityBottomWorldY + entity.getStatus(GameValueEnum.BOOT)) / AppVariables.tileSize;
                tileNum1 = TileManager.getInstance().getMapTileArray()[entityLeftCol][entityBottomRow];
                tileNum2 = TileManager.getInstance().getMapTileArray()[entityRightCol][entityBottomRow];
                checkTilesCollision(tileNum1, tileNum2, entity);
                break;
            case LEFT:
                entityLeftCol = (entityLeftWorldX - entity.getStatus(GameValueEnum.BOOT)) / AppVariables.tileSize;
                tileNum1 = TileManager.getInstance().getMapTileArray()[entityLeftCol][entityTopRow];
                tileNum2 = TileManager.getInstance().getMapTileArray()[entityLeftCol][entityBottomRow];
                checkTilesCollision(tileNum1, tileNum2, entity);
                break;
            case RIGHT:
                entityRightCol = (entityRightWorldX + entity.getStatus(GameValueEnum.BOOT)) / AppVariables.tileSize;
                tileNum1 = TileManager.getInstance().getMapTileArray()[entityRightCol][entityTopRow];
                tileNum2 = TileManager.getInstance().getMapTileArray()[entityRightCol][entityBottomRow];
                checkTilesCollision(tileNum1, tileNum2, entity);
                break;
        }
    }

    private static void checkTilesCollision(int tileNum1, int tileNum2, AbstractRendered objectMoving){
        if(TileManager.getInstance().getTileIndex().get(tileNum1).isCollision()
                || TileManager.getInstance().getTileIndex().get(tileNum2).isCollision()){
            objectMoving.setCollisionOn(true);
        }
    }


    private static boolean checkGameObjectCollision(AbstractRendered objectMoving, AbstractRendered objectTocheck){
boolean interactWith = false;
        if(objectMoving.getSolidArea().intersects(objectTocheck.getSolidArea())){
            interactWith = true;
            if(objectTocheck.isCollision()){
                objectMoving.setCollisionOn(true);
            }
        }
        return interactWith;
    }
}
