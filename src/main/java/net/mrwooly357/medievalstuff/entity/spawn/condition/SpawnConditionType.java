package net.mrwooly357.medievalstuff.entity.spawn.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.mrwooly357.medievalstuff.registry.MSRegistries;

public record SpawnConditionType<SC extends SpawnCondition>(MapCodec<SC> codec) {

    public static final Codec<SpawnConditionType<?>> CODEC = MSRegistries.SPAWN_CONDITION_TYPE.getCodec();
}
