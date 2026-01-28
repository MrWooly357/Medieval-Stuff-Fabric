package net.mrwooly357.medievalstuff.entity.spawn.rule;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.mrwooly357.medievalstuff.registry.MSRegistries;

public final class SpawnRuleType<SR extends SpawnRule> {

    public static final Codec<SpawnRuleType<?>> CODEC = MSRegistries.SPAWN_RULE_TYPE.getCodec();

    final MapCodec<SR> codec;

    private SpawnRuleType(MapCodec<SR> codec) {
        this.codec = codec;
    }


    public static <SR extends SpawnRule> SpawnRuleType<SR> of(MapCodec<SR> codec) {
        return new SpawnRuleType<>(codec);
    }
}
