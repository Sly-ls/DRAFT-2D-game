package fr.game.constants.rendered;

import fr.game.constants.AppVariables;
import fr.game.constants.core.DirectionsEnum;
import fr.game.constants.core.MessageTypeEnum;

public enum TargetEventEnum {
    TELEPORT(MessageTypeEnum.DIALOG, "Teleportation",
            true,true, DirectionsEnum.ANY,
    AppVariables.tileSize/4, AppVariables.tileSize/4, AppVariables.tileSize/2, AppVariables.tileSize/2,
            new String[]{"/events/teleporterEvent.png"})
    ,TRAP(MessageTypeEnum.WORLD_EVENT, "TRAP TRAP TRAP",
            false,false, DirectionsEnum.ANY,
          AppVariables.tileSize/4, AppVariables.tileSize/4, AppVariables.tileSize/2, AppVariables.tileSize/2,
            new String[]{"/events/trapEvent.png"})
    ,HEALING_POTION(MessageTypeEnum.MESSAGE_TO_PLAYER, "Healing power...",
            false,true, DirectionsEnum.ANY,
                    AppVariables.tileSize/4, AppVariables.tileSize/4, AppVariables.tileSize/2, AppVariables.tileSize/2,
            new String[]{"/icons/life/heart_half.png"} )
    ;



    MessageTypeEnum messageType;
    String message;
    boolean collision;
    boolean visibile;
    DirectionsEnum direction;
    int solidAreaDefaultX;
    int solidAreaDefaultY;
    int solidAreaDefaultWitdh;
    int solidAreaDefaultHeigth;
    String[] imagePath;

    TargetEventEnum(MessageTypeEnum messageType, String message,
                    boolean collision, boolean visibile, DirectionsEnum direction,
                    int solidAreaDefaultX, int solidAreaDefaultY, int solidAreaDefaultWitdh, int solidAreaDefaultHeigth,
                    String[] imagePath) {
        this.messageType = messageType;
        this.message = message;
        this.collision = collision;
        this.visibile = visibile;
        this.direction = direction;
        this.solidAreaDefaultX = solidAreaDefaultX;
        this.solidAreaDefaultY = solidAreaDefaultY;
        this.solidAreaDefaultWitdh = solidAreaDefaultWitdh;
        this.solidAreaDefaultHeigth = solidAreaDefaultHeigth;
        this.imagePath = imagePath;
    }

    public String getMessage() {
        return message;
    }

    public boolean isCollision() {
        return collision;
    }

    public DirectionsEnum getDirection() {
        return direction;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    public int getSolidAreaDefaultWitdh() {
        return solidAreaDefaultWitdh;
    }

    public int getSolidAreaDefaultHeigth() {
        return solidAreaDefaultHeigth;
    }

    public String[] getImagePath() {
        return imagePath;
    }

    public boolean isVisibile() {
        return visibile;
    }

    public MessageTypeEnum getMessageType() {
        return messageType;
    }
}
