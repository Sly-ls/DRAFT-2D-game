package fr.game.constants.rendered;

import fr.game.constants.AppVariables;
import fr.game.constants.core.DirectionsEnum;
import fr.game.mechanics.core.ScaledImage;

public enum EntityEnum {

    OLD_MAN("OLD_MAN", 1, 6,
            new ScaledImage(new String[]{"/characters/old_man/oldman_up_1.png","/characters/old_man/oldman_up_2.png"}),
            new ScaledImage(new String[]{"/characters/old_man/oldman_down_1.png","/characters/old_man/oldman_down_2.png"}),
            new ScaledImage(new String[]{"/characters/old_man/oldman_left_1.png","/characters/old_man/oldman_left_2.png"}),
            new ScaledImage(new String[]{"/characters/old_man/oldman_right_1.png","/characters/old_man/oldman_right_2.png"}),
            true, DirectionsEnum.LEFT,
            AppVariables.tileSize/4, AppVariables.tileSize/4, AppVariables.tileSize/2, AppVariables.tileSize/2)
    ,BLUE_BOY("BLUE_BOY", 3,6,
            new ScaledImage( new String[]{"/characters/blue_boy/boy_up_1.png","/characters/blue_boy/boy_up_2.png"}),
            new ScaledImage(new String[]{"/characters/blue_boy/boy_down_1.png","/characters/blue_boy/boy_down_2.png"}),
            new ScaledImage(new String[]{"/characters/blue_boy/boy_left_1.png","/characters/blue_boy/boy_left_2.png"}),
            new ScaledImage(new String[]{"/characters/blue_boy/boy_right_1.png","/characters/blue_boy/boy_right_2.png"}),
            true, DirectionsEnum.DOWN,
            AppVariables.tileSize/4, AppVariables.tileSize/4, AppVariables.tileSize/2, AppVariables.tileSize/2)
    ,GREEN_SLIME("GREEN_SLIME", 1,4,
            new ScaledImage( new String[]{"/characters/monster/greenslime_down_1.png","/characters/monster/greenslime_down_2.png"}),
            new ScaledImage( new String[]{"/characters/monster/greenslime_down_1.png","/characters/monster/greenslime_down_2.png"}),
            new ScaledImage( new String[]{"/characters/monster/greenslime_down_1.png","/characters/monster/greenslime_down_2.png"}),
            new ScaledImage( new String[]{"/characters/monster/greenslime_down_1.png","/characters/monster/greenslime_down_2.png"}),
            true, DirectionsEnum.DOWN,
            AppVariables.tileSize/4, AppVariables.tileSize/4, AppVariables.tileSize/2, AppVariables.tileSize/2);


    final private String name;
    final private int speed;
    final private int maxLife;
    final private ScaledImage pathUp;
    final private ScaledImage pathDown;
    final private ScaledImage pathLeft;
    final private ScaledImage pathRight;
    final private int solidAreaDefaultX;
    final private int solidAreaDefaultY;
    final private int solidAreaDefaultWitdh;
    final private int solidAreaDefaultHeigth;
    final private boolean collision;
    DirectionsEnum direction;

    EntityEnum(String name, int speed, int maxLife,
               ScaledImage pathUp, ScaledImage pathDown, ScaledImage pathLeft, ScaledImage pathRight,
               boolean collision, DirectionsEnum direction,
               int solidAreaDefaultX, int solidAreaDefaultY, int solidAreaDefaultWitdh, int solidAreaDefaultHeigth
    ) {
        this.name = name;
        this.speed = speed;
        this.maxLife = maxLife;
        this.pathUp = pathUp;
        this.pathDown = pathDown;
        this.pathLeft = pathLeft;
        this.pathRight = pathRight;
        this.collision = collision;
        this.solidAreaDefaultX = solidAreaDefaultX;
        this.solidAreaDefaultY = solidAreaDefaultY;
        this.solidAreaDefaultWitdh = solidAreaDefaultWitdh;
        this.solidAreaDefaultHeigth = solidAreaDefaultHeigth;
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public ScaledImage getPathUp() {
        return pathUp;
    }

    public ScaledImage getPathDown() {
        return pathDown;
    }

    public ScaledImage getPathLeft() {
        return pathLeft;
    }

    public ScaledImage getPathRight() {
        return pathRight;
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

    public boolean isCollision() {
        return collision;
    }

    public DirectionsEnum getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public int getMaxLife() {
        return maxLife;
    }
}
