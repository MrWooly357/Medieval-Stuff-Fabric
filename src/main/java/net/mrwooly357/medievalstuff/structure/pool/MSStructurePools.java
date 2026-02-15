package net.mrwooly357.medievalstuff.structure.pool;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.structure.pool.StructurePool;
import net.mrwooly357.medievalstuff.util.MSUtil;
import net.mrwooly357.medievalstuff.world.gen.structure.custom.dungeon.small.SmallTaigaDungeonData;

public final class MSStructurePools {

    private MSStructurePools() {}


    public static RegistryKey<StructurePool> of(String id) {
        return RegistryKey.of(RegistryKeys.TEMPLATE_POOL, MSUtil.id(id));
    }

    public static void bootstrap(Registerable<StructurePool> registerable) {
        SmallTaigaDungeonData.bootstrap(registerable);
    }

    public static void generateEach(RegistryWrapper.Impl<StructurePool> registry, FabricDynamicRegistryProvider.Entries entries) {
        SmallTaigaDungeonData.generatePools(registry, entries);
    }
}
