package fr.game.rendererd.object;

import fr.game.constants.core.DirectionsEnum;
import fr.game.rendererd.entity.AbstractEntity;

public interface ObjectToInteractWith {

    public void use(AbstractEntity entiy);

    String getName();

    int getWorldX();

    int getWorldY();

    DirectionsEnum getDirection();
}
