package net.mrwooly357.medievalstuff.entity.spawn.context;

import net.minecraft.registry.Registry;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.registry.MSRegistries;
import net.mrwooly357.medievalstuff.util.MSUtil;

import java.util.function.Function;

public final class SpawnContextTypes {

    public static final SpawnContextType GENERIC = register(
            "generic", builder -> builder
                    .require(SpawnContextParameters.ORIGIN)
    );

    private SpawnContextTypes() {}


    private static SpawnContextType register(String id, Function<SpawnContextType.Builder, SpawnContextType.Builder> builder) {
        return Registry.register(MSRegistries.SPAWN_CONTEXT_TYPE, MSUtil.id(id), builder.apply(SpawnContextType.builder()).build());
    }

    public static void initialize() {
        MedievalStuff.logInitialization("spawn context types");
    }
}
