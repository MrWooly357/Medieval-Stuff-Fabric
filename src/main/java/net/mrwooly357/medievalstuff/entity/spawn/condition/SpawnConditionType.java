package net.mrwooly357.medievalstuff.entity.spawn.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.mrwooly357.medievalstuff.registry.MSRegistries;

public final class SpawnConditionType<SC extends SpawnCondition> {

    public static final Codec<SpawnConditionType<?>> CODEC = MSRegistries.SPAWN_CONDITION_TYPE.getCodec();

    final MapCodec<SC> codec;

    private SpawnConditionType(MapCodec<SC> codec) {
        this.codec = codec;
    }


    public static <SC extends SpawnCondition> SpawnConditionType<SC> of(MapCodec<SC> codec) {
        return new SpawnConditionType<>(codec);
    }
}
