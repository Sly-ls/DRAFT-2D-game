package fr.game.constants.world.asset;

import fr.game.constants.rendered.TargetEventEnum;
import fr.game.constants.world.WorldEnum;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EventWorldDescription {

    ASSET_CUTE_MAP_BLUEBOY_TUTO(WorldEnum.CUTE_MAP_BLUEBOY_TUTO, new HashMap(){
        {
            put(TargetEventEnum.TRAP, new ArrayList(){
                {add(new Pair<>(21, 22));}
            });
        }
        {
            put(TargetEventEnum.HEALING_POTION, new ArrayList(){
                {add(new Pair<>(20, 20));}
            });
        }
        {
            put(TargetEventEnum.TELEPORT, new ArrayList(){
                {add(new Pair<>(21, 21));}
                {add(new Pair<>(30, 30));}
            });
        }
    }),
    ASSET_3(WorldEnum.TREASURE_HUNT, new HashMap(){
        {
            put(TargetEventEnum.TRAP, new ArrayList(){
                {add(new Pair<>(21, 22));}
            });
        }
        {
            put(TargetEventEnum.HEALING_POTION, new ArrayList(){
                {add(new Pair<>(20, 20));}
            });
        }
        {
            put(TargetEventEnum.TELEPORT, new ArrayList(){
                {add(new Pair<>(21, 21));}
                {add(new Pair<>(30, 30));}
            });
        }
    }),
    ASSET_TEST_MAP(WorldEnum.TEST_MAP, new HashMap(){
        {
            put(TargetEventEnum.TRAP, new ArrayList(){
                {add(new Pair<>(21, 22));}
            });
        }
        {
            put(TargetEventEnum.HEALING_POTION, new ArrayList(){
                {add(new Pair<>(20, 20));}
            });
        }
        {
            put(TargetEventEnum.TELEPORT, new ArrayList(){
                {add(new Pair<>(21, 21));}
                {add(new Pair<>(30, 30));}
            });
        }
    }),
    ASSET_THE_ISLAND(WorldEnum.THE_ISLAND, new HashMap() ),
    ASSET_SPIDERLAND(WorldEnum.SPIDERLAND,new HashMap(){
        {
            put(TargetEventEnum.TRAP, new ArrayList(){
                {add(new Pair<>(21, 22));}
            });
        }
        {
            put(TargetEventEnum.HEALING_POTION, new ArrayList(){
                {add(new Pair<>(20, 20));}
            });
        }
        {
            put(TargetEventEnum.TELEPORT, new ArrayList(){
                {add(new Pair<>(21, 21));}
                {add(new Pair<>(30, 30));}
            });
        }
    });

    WorldEnum worldEnum;
    Map<TargetEventEnum, List<Pair<Integer,Integer>>> assetSetter;

    EventWorldDescription(WorldEnum worldEnum, Map<TargetEventEnum, List<Pair<Integer, Integer>>> assetSetter) {
        this.worldEnum = worldEnum;
        this.assetSetter = assetSetter;
    }

    public WorldEnum getWorldEnum() {
        return worldEnum;
    }

    public Map<TargetEventEnum, List<Pair<Integer, Integer>>> getAssetSetter() {
        return assetSetter;
    }

    public static EventWorldDescription getDescriptorByWorld(WorldEnum worldSelectedCode) {
        for(EventWorldDescription assetDesc : EventWorldDescription.values()){
            if(assetDesc.getWorldEnum() == worldSelectedCode){
                return assetDesc;
            }
        }
        return null;
    }
}
