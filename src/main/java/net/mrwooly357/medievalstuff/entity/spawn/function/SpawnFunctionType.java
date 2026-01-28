package net.mrwooly357.medievalstuff.entity.spawn.function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.mrwooly357.medievalstuff.registry.MSRegistries;

public final class SpawnFunctionType<SF extends SpawnFunction> {

    public static final Codec<SpawnFunctionType<?>> CODEC = MSRegistries.SPAWN_FUNCTION_TYPE.getCodec();

    final MapCodec<SF> codec;

    private SpawnFunctionType(MapCodec<SF> codec) {
        this.codec = codec;
    }


    public static <SF extends SpawnFunction> SpawnFunctionType<SF> of(MapCodec<SF> codec) {
        return new SpawnFunctionType<>(codec);
    }
}
