package net.mrwooly357.medievalstuff.entity.spawn.selector.custom;

import com.mojang.serialization.MapCodec;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelector;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelectorDataHolder;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelectorType;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelectorTypes;

import java.util.List;

public class EachElementSpawnSelector extends SpawnSelector {

    public static final EachElementSpawnSelector INSTANCE = new EachElementSpawnSelector();
    public static final MapCodec<EachElementSpawnSelector> CODEC = MapCodec.unit(INSTANCE);

    protected EachElementSpawnSelector() {}


    @Override
    protected SpawnSelectorType<EachElementSpawnSelector> getType() {
        return SpawnSelectorTypes.EACH_ELEMENT;
    }

    @Override
    public <SSDH extends SpawnSelectorDataHolder> List<SSDH> select(List<SSDH> objects, SpawnContext context) {
        return objects;
    }


    public static class Data extends SpawnSelector.Data {

        public static final Data INSTANCE = new Data();
        public static final MapCodec<Data> CODEC = MapCodec.unit(INSTANCE);

        protected Data() {}


        @Override
        protected SpawnSelectorType<EachElementSpawnSelector> getSelectorType() {
            return SpawnSelectorTypes.EACH_ELEMENT;
        }
    }
}
