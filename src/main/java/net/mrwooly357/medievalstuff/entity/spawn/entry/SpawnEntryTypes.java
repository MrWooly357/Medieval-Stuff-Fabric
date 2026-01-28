package net.mrwooly357.medievalstuff.entity.spawn.entry;

import com.mojang.serialization.MapCodec;
import net.minecraft.registry.Registry;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.entity.spawn.entry.custom.EntitySpawnEntry;
import net.mrwooly357.medievalstuff.registry.MSRegistries;
import net.mrwooly357.medievalstuff.util.MSUtil;

public final class SpawnEntryTypes {

    public static final SpawnEntryType<EntitySpawnEntry> ENTITY = register(
            "entity", EntitySpawnEntry.CODEC
    );

    private SpawnEntryTypes() {}


    private static <SE extends SpawnEntry> SpawnEntryType<SE> register(String id, MapCodec<SE> codec) {
        return Registry.register(MSRegistries.SPAWN_ENTRY_TYPE, MSUtil.id(id), SpawnEntryType.of(codec));
    }

    public static void initialize() {
        MedievalStuff.logInitialization("spawn entry types");
    }
}
