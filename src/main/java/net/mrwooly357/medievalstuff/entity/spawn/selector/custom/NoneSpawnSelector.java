package net.mrwooly357.medievalstuff.entity.spawn.selector.custom;

import com.mojang.serialization.MapCodec;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelector;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelectorDataHolder;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelectorType;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelectorTypes;

import java.util.List;

public final class NoneSpawnSelector extends SpawnSelector {

    public static final NoneSpawnSelector INSTANCE = new NoneSpawnSelector();
    public static final MapCodec<NoneSpawnSelector> CODEC = MapCodec.unit(INSTANCE);

    private NoneSpawnSelector() {}


    @Override
    protected SpawnSelectorType<NoneSpawnSelector> getType() {
        return SpawnSelectorTypes.NONE;
    }

    @Override
    public <SSDH extends SpawnSelectorDataHolder> List<SSDH> select(List<SSDH> objects, SpawnContext context) {
        return List.of();
    }


    public static final class Data extends SpawnSelector.Data {

        public static final Data INSTANCE = new Data();
        public static final MapCodec<Data> CODEC = MapCodec.unit(INSTANCE);

        private Data() {}


        @Override
        protected SpawnSelectorType<NoneSpawnSelector> getSelectorType() {
            return SpawnSelectorTypes.NONE;
        }
    }
}
