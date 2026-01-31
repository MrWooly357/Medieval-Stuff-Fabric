package net.mrwooly357.medievalstuff.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.mrwooly357.medievalstuff.enchantment.MSEnchantments;
import net.mrwooly357.medievalstuff.entity.spawn.SpawnTable;
import net.mrwooly357.medievalstuff.entity.spawn.SpawnTables;
import net.mrwooly357.medievalstuff.registry.MSRegistryKeys;

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
        entries.add(spawnTableRegistry, SpawnTables.TEST);
    }

    @Override
    public String getName() {
        return "Dynamic Registries";
    }
}
