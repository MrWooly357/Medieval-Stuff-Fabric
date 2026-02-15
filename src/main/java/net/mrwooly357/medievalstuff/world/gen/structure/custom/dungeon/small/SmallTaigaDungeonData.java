package net.mrwooly357.medievalstuff.world.gen.structure.custom.dungeon.small;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.structure.pool.alias.StructurePoolAliasBinding;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.structure.pool.MSStructurePools;

import java.util.List;

public final class SmallTaigaDungeonData {

    public static final RegistryKey<StructurePool> START_POOL = pool("entrance");
    public static final List<StructurePoolAliasBinding> ALIAS_BINDINGS = List.of();

    private SmallTaigaDungeonData() {}


    private static RegistryKey<StructurePool> pool(String id) {
        return MSStructurePools.of("small_taiga_dungeon/" + id);
    }

    public static void bootstrap(Registerable<StructurePool> registerable) {
        RegistryEntryLookup<StructurePool> pools = registerable.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);
        RegistryEntry<StructurePool> empty = pools.getOrThrow(StructurePools.EMPTY);
        registerable.register(
                START_POOL,
                new StructurePool(
                        empty,
                        List.of(
                                Pair.of(StructurePoolElement.ofSingle(poolId("entrance_1")), 1),
                                Pair.of(StructurePoolElement.ofSingle(poolId("entrance_2")), 1),
                                Pair.of(StructurePoolElement.ofSingle(poolId("entrance_3")), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );
    }

    private static String poolId(String path) {
        return MedievalStuff.MOD_ID + ":small_taiga_dungeon/" + path;
    }

    public static void generatePools(RegistryWrapper.Impl<StructurePool> registry, FabricDynamicRegistryProvider.Entries entries) {
        entries.add(registry, START_POOL);
    }
}
