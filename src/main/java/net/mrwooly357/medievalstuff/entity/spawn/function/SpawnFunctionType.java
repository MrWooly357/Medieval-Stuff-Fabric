package net.mrwooly357.medievalstuff.entity.spawn.function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.mrwooly357.medievalstuff.registry.MSRegistries;

public record SpawnFunctionType<SF extends SpawnFunction>(MapCodec<SF> codec) {

    public static final Codec<SpawnFunctionType<?>> CODEC = MSRegistries.SPAWN_FUNCTION_TYPE.getCodec();
}
