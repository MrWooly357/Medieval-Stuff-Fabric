package net.mrwooly357.medievalstuff.entity.spawn.selector;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.mrwooly357.medievalstuff.registry.MSRegistries;

public record SpawnSelectorType<SS extends SpawnSelector>(MapCodec<SS> codec, MapCodec<? extends SpawnSelector.Data> dataCodec) {

    public static final Codec<SpawnSelectorType<?>> CODEC = MSRegistries.SPAWN_SELECTOR_TYPE.getCodec();
}
