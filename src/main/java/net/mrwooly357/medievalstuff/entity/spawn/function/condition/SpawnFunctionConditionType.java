package net.mrwooly357.medievalstuff.entity.spawn.function.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.mrwooly357.medievalstuff.registry.MSRegistries;

public final class SpawnFunctionConditionType<SFC extends SpawnFunctionCondition> {

    public static final Codec<SpawnFunctionConditionType<?>> CODEC = MSRegistries.SPAWN_FUNCTION_CONDITION_TYPE.getCodec();

    final MapCodec<SFC> codec;

    private SpawnFunctionConditionType(MapCodec<SFC> codec) {
        this.codec = codec;
    }


    public static <SFC extends SpawnFunctionCondition> SpawnFunctionConditionType<SFC> of(MapCodec<SFC> codec) {
        return new SpawnFunctionConditionType<>(codec);
    }
}
