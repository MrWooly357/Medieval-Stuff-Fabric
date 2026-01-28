package net.mrwooly357.medievalstuff.entity.spawn.function.condition;

import com.mojang.serialization.MapCodec;
import net.minecraft.registry.Registry;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.registry.MSRegistries;
import net.mrwooly357.medievalstuff.util.MSUtil;

public final class SpawnFunctionConditionTypes {

    private SpawnFunctionConditionTypes() {}


    private static <SFC extends SpawnFunctionCondition> SpawnFunctionConditionType<SFC> register(String id, MapCodec<SFC> codec) {
        return Registry.register(MSRegistries.SPAWN_FUNCTION_CONDITION_TYPE, MSUtil.id(id), SpawnFunctionConditionType.of(codec));
    }

    public static void initialize() {
        MedievalStuff.logInitialization("spawn function condition types");
    }
}
