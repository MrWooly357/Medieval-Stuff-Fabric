package net.mrwooly357.medievalstuff.entity.spawn.rule;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.mrwooly357.medievalstuff.registry.MSRegistries;

public record SpawnRuleType<SR extends SpawnRule>(MapCodec<SR> codec) {

    public static final Codec<SpawnRuleType<?>> CODEC = MSRegistries.SPAWN_RULE_TYPE.getCodec();
}
