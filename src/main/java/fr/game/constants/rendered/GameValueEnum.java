package fr.game.constants.rendered;

import fr.game.constants.AppVariables;
import fr.game.constants.core.DirectionsEnum;
import fr.game.constants.game.GameValuetype;

public enum GameValueEnum {

    CHEST(GameValuetype.OBJECT, "COFFRE AU TRESOR", new String[]{"/objects/chest.png"},
            false,false,
            0,0, AppVariables.tileSize, AppVariables.tileSize, DirectionsEnum.DOWN),
    KEY(GameValuetype.OBJECT, "CLEF JAUNE",new String[]{"/objects/key.png"},
            false,false,
            0, 0, AppVariables.tileSize, AppVariables.tileSize,DirectionsEnum.DOWN),
    RED_KEY(GameValuetype.OBJECT, "CLEF ROUGE",new String[]{"/objects/red_key.png"},
            false, false,
            0,0, AppVariables.tileSize, AppVariables.tileSize,DirectionsEnum.DOWN),
    BOOT(GameValuetype.STAT, "BONUS DE VITESSE",new String[]{"/objects/boots.png"},
            false,false,
            0,0, AppVariables.tileSize, AppVariables.tileSize,DirectionsEnum.DOWN),
    DOOR(GameValuetype.OBJECT, "PORTE EN BOIS",new String[]{"/objects/door.png"},
            true,false,
            0,0, AppVariables.tileSize, AppVariables.tileSize,DirectionsEnum.DOWN),
    RED_DOOR(GameValuetype.OBJECT, "PORTE ROUGE",new String[]{"/objects/red_door.png"},
            true,false,
            0,0, AppVariables.tileSize,
            AppVariables.tileSize,DirectionsEnum.DOWN),
    HOURGLASS(GameValuetype.STAT, "HourGlass", new String[]{"/icons/hourglass.png"},
            false,false,
            0,0, AppVariables.tileSize, AppVariables.tileSize,DirectionsEnum.DOWN),
    HEALTH(GameValuetype.STAT, "SANTE", new String[]{"/icons/life/heart_full.png","/icons/life/heart_half.png","/icons/life/heart_blank.png"},
            false, false,
            0,0, AppVariables.tileSize, AppVariables.tileSize,DirectionsEnum.DOWN),
    MAX_LIFE(GameValuetype.STAT, "MAX_LIFE", new String[]{"/icons/life/heart_blank.png"},
            false, false,
            0,0, AppVariables.tileSize, AppVariables.tileSize,DirectionsEnum.DOWN),
    GROUND_TORCH(GameValuetype.OBJECT, "TORCHE", new String[]{"/objects/groundtorch/groundtorch.png"
            ,"/objects/groundtorch/flame/grndtorchflameani3.png"
            ,"/objects/groundtorch/flame/grndtorchflameani4.png"
            ,"/objects/groundtorch/flame/grndtorchflameani5.png"
            ,"/objects/groundtorch/flame/grndtorchflameani6.png"
            ,"/objects/groundtorch/flame/grndtorchflameani7.png"
            ,"/objects/groundtorch/flame/grndtorchflameani8.png"
            ,"/objects/groundtorch/flame/grndtorchflameani9.png"},
            false, true,
            0,0, AppVariables.tileSize, AppVariables.tileSize,DirectionsEnum.DOWN)
    ;

    GameValuetype type;
    String name;
    String path[];
    boolean collision;
    boolean animated;
    int solidAreaDefaultX;
    int solidAreaDefaultY;
    int solidAreaDefaultWitdh;
    int solidAreaDefaultHeigth;
    DirectionsEnum direction = DirectionsEnum.DOWN;

    GameValueEnum(GameValuetype type, String name, String[] path,
                  boolean collision, boolean animated,
                  int solidAreaDefaultX, int solidAreaDefaultY, int solidAreaDefaultWitdh, int solidAreaDefaultHeigth,
                  DirectionsEnum direction) {
        this.type = type;
        this.name = name;
        this.path = path;
        this.collision = collision;
        this.animated = animated;
        this.solidAreaDefaultX = solidAreaDefaultX;
        this.solidAreaDefaultY = solidAreaDefaultY;
        this.solidAreaDefaultWitdh = solidAreaDefaultWitdh;
        this.solidAreaDefaultHeigth = solidAreaDefaultHeigth;
        this.direction = direction;
    }



    public String getName() {
        return name;
    }

    public String[] getPath() {
        return path;
    }
    public boolean isCollision() {
        return collision;
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
    public DirectionsEnum getDirection() {
        return direction;
    }
    public boolean isAnimated() {
        return animated;
    }
}
