package net.mrwooly357.medievalstuff.entity.spawn.pos_finder;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.mrwooly357.medievalstuff.registry.MSRegistries;

public record SpawnPosFinderType<SPF extends SpawnPosFinder>(MapCodec<SPF> codec) {

    public static final Codec<SpawnPosFinderType<?>> CODEC = MSRegistries.SPAWN_POS_FINDER_TYPE.getCodec();
}
