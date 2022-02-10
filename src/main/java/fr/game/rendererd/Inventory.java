package fr.game.rendererd;

import fr.game.constants.rendered.GameValueEnum;
import fr.game.main.utils.NumberUtils;
import fr.game.panel.game.GameController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//TODO to transform this into a <K,V> , i need to get move the switch part to Entity
public class Inventory {

        final int max_Object = 99;
        protected HashMap<GameValueEnum, Integer> inventory;

        public Inventory() {
            this.inventory = new HashMap<GameValueEnum, Integer>();;
        }

        public boolean pickOrDrop(GameValueEnum objectTypeEnum, int quantity) {
            boolean pick = false;
            if(quantity>0){
                pick = true;
            }
            int quantityInInventory = this.inventory.getOrDefault(objectTypeEnum,0);
            this.inventory.put(objectTypeEnum,quantityInInventory);
            switch (objectTypeEnum){
                case HEALTH:
                    quantityInInventory = NumberUtils.incrementCommand(quantity,quantityInInventory,this.inventory.getOrDefault(GameValueEnum.MAX_LIFE,0),false);
                    break;
                default:
                    quantityInInventory = NumberUtils.incrementCommand(quantity,quantityInInventory,max_Object,false);
                    break;
            }
            this.inventory.put(objectTypeEnum,quantityInInventory);
            return pick;
        }

    public int getInventory(GameValueEnum valueType) {
        return inventory.getOrDefault(valueType,0);
    }
    public Set<Map.Entry<GameValueEnum, Integer>> getInventory() {
        return inventory.entrySet();
    }
}
