package net.mrwooly357.medievalstuff.entity.spawn.function;

import com.mojang.serialization.MapCodec;
import net.minecraft.registry.Registry;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.entity.spawn.function.custom.InitializeSpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.function.custom.SetEquipmentSpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.function.custom.SpawnSpawnFunction;
import net.mrwooly357.medievalstuff.registry.MSRegistries;
import net.mrwooly357.medievalstuff.util.MSUtil;

public final class SpawnFunctionTypes {

    public static final SpawnFunctionType<InitializeSpawnFunction> INITIALIZE = of(
            "initialize", InitializeSpawnFunction.CODEC
    );
    public static final SpawnFunctionType<SetEquipmentSpawnFunction> SET_EQUIPMENT = of(
            "set_equipment", SetEquipmentSpawnFunction.CODEC
    );
    public static final SpawnFunctionType<SpawnSpawnFunction> SPAWN = of(
            "spawn", SpawnSpawnFunction.CODEC
    );

    private SpawnFunctionTypes() {}


    private static <SF extends SpawnFunction> SpawnFunctionType<SF> of(String id, MapCodec<SF> codec) {
        return Registry.register(MSRegistries.SPAWN_FUNCTION_TYPE, MSUtil.id(id), new SpawnFunctionType<>(codec));
    }

    public static void initialize() {
        MedievalStuff.logInitialization("spawn function types");
    }
}
