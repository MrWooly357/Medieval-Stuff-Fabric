package net.mrwooly357.medievalstuff.entity.spawn.selector;

import com.mojang.serialization.Codec;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;

import java.util.List;

public abstract class SpawnSelector {

    public static final Codec<SpawnSelector> CODEC = SpawnSelectorType.CODEC.dispatch(SpawnSelector::getType, type -> type.codec);

    protected SpawnSelector() {}


    protected abstract SpawnSelectorType<? extends SpawnSelector> getType();

    public abstract <SSDH extends SpawnSelectorDataHolder> List<SSDH> select(List<SSDH> objects, SpawnContext context);


    public static abstract class Data {

        public static final Codec<Data> CODEC = SpawnSelectorType.CODEC.dispatch(Data::getSelectorType, type -> type.dataCodec);

        protected Data() {}


        protected abstract SpawnSelectorType<? extends SpawnSelector> getSelectorType();
    }
}
