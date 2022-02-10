package fr.game.constants.rendered;

import fr.game.constants.AppVariables;
import fr.game.constants.core.DirectionsEnum;

public enum LightSourceEnum{

    GROUND_TORCH(GameValueEnum.GROUND_TORCH,
            150, AppVariables.tileSize*4, 500
            , DirectionsEnum.ANY)
    ;

    GameValueEnum gameObject;

    LightSourceEnum(GameValueEnum gameObject, int power, int radius, int durability, DirectionsEnum direction) {
        this.gameObject = gameObject;
        this.power = power;
        this.radius = radius;
        this.durability = durability;
        this.direction = direction;
    }

    int power;
    int radius;
    int durability;
    DirectionsEnum direction;

    public GameValueEnum getGameObject() {
        return gameObject;
    }

    public int getPower() {
        return power;
    }

    public int getRadius() {
        return radius;
    }

    public int getDurability() {
        return durability;
    }

    public DirectionsEnum getDirection() {
        return direction;
    }
}
