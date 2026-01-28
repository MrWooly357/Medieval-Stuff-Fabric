package net.mrwooly357.medievalstuff.entity.spawn.condition;

import com.mojang.serialization.MapCodec;
import net.minecraft.registry.Registry;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.entity.spawn.condition.custom.RandomChanceSpawnCondition;
import net.mrwooly357.medievalstuff.registry.MSRegistries;
import net.mrwooly357.medievalstuff.util.MSUtil;

public final class SpawnConditionTypes {

    public static final SpawnConditionType<RandomChanceSpawnCondition> RANDOM_CHANCE = register(
            "random_chance", RandomChanceSpawnCondition.CODEC
    );

    private SpawnConditionTypes() {}


    private static <SC extends SpawnCondition> SpawnConditionType<SC> register(String id, MapCodec<SC> codec) {
        return Registry.register(MSRegistries.SPAWN_CONDITION_TYPE, MSUtil.id(id), SpawnConditionType.of(codec));
    }

    public static void initialize() {
        MedievalStuff.logInitialization("spawn condition types");
    }
}
