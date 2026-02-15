package net.mrwooly357.medievalstuff.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.structure.StructureSet;
import net.minecraft.world.gen.structure.Structure;
import net.mrwooly357.medievalstuff.enchantment.MSEnchantments;
import net.mrwooly357.medievalstuff.entity.spawn.SpawnTable;
import net.mrwooly357.medievalstuff.entity.spawn.SpawnTables;
import net.mrwooly357.medievalstuff.registry.MSRegistryKeys;
import net.mrwooly357.medievalstuff.structure.MSStructureSets;
import net.mrwooly357.medievalstuff.structure.pool.MSStructurePools;
import net.mrwooly357.medievalstuff.world.gen.structure.MSStructures;

import java.util.concurrent.CompletableFuture;

public final class MSDynamicRegistryProvider extends FabricDynamicRegistryProvider {

    public MSDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }


    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        RegistryWrapper.Impl<Enchantment> enchantmentRegistry = registries.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        entries.add(enchantmentRegistry, MSEnchantments.COMBO);

        RegistryWrapper.Impl<SpawnTable> spawnTableRegistry = registries.getWrapperOrThrow(MSRegistryKeys.SPAWN_TABLE);
        entries.add(spawnTableRegistry, SpawnTables.EMPTY);
        MSStructurePools.generateEach(registries.getWrapperOrThrow(RegistryKeys.TEMPLATE_POOL), entries);

        RegistryWrapper.Impl<Structure> structureRegistry = registries.getWrapperOrThrow(RegistryKeys.STRUCTURE);
        entries.add(structureRegistry, MSStructures.SMALL_TAIGA_DUNGEON);

        RegistryWrapper.Impl<StructureSet> structureSetRegistry = registries.getWrapperOrThrow(RegistryKeys.STRUCTURE_SET);
        entries.add(structureSetRegistry, MSStructureSets.SMALL_DUNGEONS);
    }

    @Override
    public String getName() {
        return "Dynamic Registries";
    }
}
