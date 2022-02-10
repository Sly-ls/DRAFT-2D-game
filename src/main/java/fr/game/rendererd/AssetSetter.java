package fr.game.rendererd;

import fr.game.constants.AppVariables;
import fr.game.constants.rendered.EntityEnum;
import fr.game.constants.rendered.GameValueEnum;
import fr.game.constants.rendered.LightSourceEnum;
import fr.game.constants.rendered.TargetEventEnum;
import fr.game.constants.world.WorldEnum;
import fr.game.constants.world.asset.AssetWorldDescription;
import fr.game.constants.world.asset.EventWorldDescription;
import fr.game.constants.world.asset.NPCWorldDescription;
import fr.game.panel.game.GameController;
import fr.game.rendererd.entity.NPC.NPC;
import fr.game.rendererd.entity.NPC.monster.GreenSlime;
import fr.game.rendererd.entity.NPC.neutral.OldMan;
import fr.game.rendererd.event.TargetEvent;
import fr.game.rendererd.object.GameObject;
import fr.game.rendererd.object.LightSource;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public class AssetSetter {

    public AssetSetter() {
    }

    private static void setObject(AssetWorldDescription assetDesc){

        for(Map.Entry<GameValueEnum, List<Pair<Integer, Integer>>> descriptionEntry : assetDesc.getAssetSetter().entrySet()) {
            for(Pair<Integer,Integer> coordinate : descriptionEntry.getValue()){
                GameController.getInstance().addNewObject(
                        new GameObject(
                                descriptionEntry.getKey(), coordinate.getKey(), coordinate.getValue()));
            }
        }

    }
    private static void setEvent(EventWorldDescription eventDesc){

        for(Map.Entry<TargetEventEnum, List<Pair<Integer, Integer>>> descriptionEntry : eventDesc.getAssetSetter().entrySet()) {
            for(Pair<Integer,Integer> coordinate : descriptionEntry.getValue()){
                GameController.getInstance().addNewObject(
                        new TargetEvent(
                                descriptionEntry.getKey(), coordinate.getKey(), coordinate.getValue()));
            }
        }
    }
    private static void setNPC(NPCWorldDescription npcDesc){
        for(Map.Entry<EntityEnum, List<Pair<Integer, Integer>>> descriptionEntry : npcDesc.getAssetSetter().entrySet()) {
            for(Pair<Integer,Integer> coordinate : descriptionEntry.getValue()){
                NPC npcToAdd = null;
                switch (descriptionEntry.getKey()){
                    case OLD_MAN:
                        npcToAdd = new OldMan( descriptionEntry.getKey(), coordinate.getKey(), coordinate.getValue());
                        break;
                    case GREEN_SLIME:
                        npcToAdd = new GreenSlime(descriptionEntry.getKey(), coordinate.getKey(), coordinate.getValue());
                        break;
                }
                GameController.getInstance().addNewObject(npcToAdd);
            }
        }
    }

    public static void setup() {
        WorldEnum worldSelected = TileManager.getInstance().getWorldSelected();
        if(worldSelected != null){
            if (NPCWorldDescription.getDescriptorByWorld(worldSelected) != null) {
                setNPC(NPCWorldDescription.getDescriptorByWorld(worldSelected));
            }
            if (NPCWorldDescription.getDescriptorByWorld(worldSelected) != null) {

                setObject(AssetWorldDescription.getDescriptorByWorld(worldSelected));
            }
            if (NPCWorldDescription.getDescriptorByWorld(worldSelected) != null) {

                setEvent(EventWorldDescription.getDescriptorByWorld(worldSelected));
            }
            if (NPCWorldDescription.getDescriptorByWorld(worldSelected) != null) {

                setLightSource(AssetWorldDescription.getDescriptorByWorld(worldSelected));
            }
        }
    }

    private static void setLightSource(AssetWorldDescription descriptorByWorld) {
        GameController.getInstance().addNewObject(
                new LightSource(LightSourceEnum.GROUND_TORCH,
                        (GameController.getInstance().getPlayer().getWorldX()/ AppVariables.tileSize)+5,
                        (GameController.getInstance().getPlayer().getWorldY()/ AppVariables.tileSize)+5));
    }
}
