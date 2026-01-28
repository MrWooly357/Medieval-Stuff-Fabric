package net.mrwooly357.medievalstuff.entity.spawn.selector;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.mrwooly357.medievalstuff.registry.MSRegistries;

public final class SpawnSelectorType<SS extends SpawnSelector> {

    public static final Codec<SpawnSelectorType<?>> CODEC = MSRegistries.SPAWN_SELECTOR_TYPE.getCodec();

    final MapCodec<SS> codec;
    final MapCodec<? extends SpawnSelector.Data> dataCodec;

    private SpawnSelectorType(MapCodec<SS> codec, MapCodec<? extends SpawnSelector.Data> dataCodec) {
        this.codec = codec;
        this.dataCodec = dataCodec;
    }


    public static <SS extends SpawnSelector> SpawnSelectorType<SS> of(MapCodec<SS> codec, MapCodec<? extends SpawnSelector.Data> dataCodec) {
        return new SpawnSelectorType<>(codec, dataCodec);
    }
}
