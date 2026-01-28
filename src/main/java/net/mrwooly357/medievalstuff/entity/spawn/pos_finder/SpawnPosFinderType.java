package net.mrwooly357.medievalstuff.entity.spawn.pos_finder;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.mrwooly357.medievalstuff.registry.MSRegistries;

public final class SpawnPosFinderType<SPF extends SpawnPosFinder> {

    public static final Codec<SpawnPosFinderType<?>> CODEC = MSRegistries.SPAWN_POS_FINDER_TYPE.getCodec();

    final MapCodec<SPF> codec;

    private SpawnPosFinderType(MapCodec<SPF> codec) {
        this.codec = codec;
    }


    public static <SPF extends SpawnPosFinder> SpawnPosFinderType<SPF> of(MapCodec<SPF> codec) {
        return new SpawnPosFinderType<>(codec);
    }
}
