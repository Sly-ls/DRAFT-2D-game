package fr.game.rendererd.entity;

import fr.game.rendererd.event.TargetEvent;
import fr.game.rendererd.object.GameObject;
import fr.game.rendererd.object.ObjectToInteractWith;

public interface InteractWithObject {
    void trigger(TargetEvent newEvent);
    void use(ObjectToInteractWith gameObject);

    void pickup(ObjectToInteractWith gameObject);
}
