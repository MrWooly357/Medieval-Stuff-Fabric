package net.mrwooly357.medievalstuff.entity.spawn.rule;

import com.mojang.serialization.MapCodec;
import net.minecraft.registry.Registry;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.entity.spawn.rule.custom.LightLimitSpawnRule;
import net.mrwooly357.medievalstuff.registry.MSRegistries;
import net.mrwooly357.medievalstuff.util.MSUtil;

public final class SpawnRuleTypes {

    public static final SpawnRuleType<LightLimitSpawnRule> LIGHT_LIMIT = register(
            "light_limit", LightLimitSpawnRule.CODEC
    );

    private SpawnRuleTypes() {}


    private static <SR extends SpawnRule> SpawnRuleType<SR> register(String id, MapCodec<SR> codec) {
        return Registry.register(MSRegistries.SPAWN_RULE_TYPE, MSUtil.id(id), SpawnRuleType.of(codec));
    }

    public static void initialize() {
        MedievalStuff.logInitialization("spawn rule types");
    }
}
