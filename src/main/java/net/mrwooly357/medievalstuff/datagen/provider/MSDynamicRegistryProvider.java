package net.mrwooly357.medievalstuff.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import net.mrwooly357.medievalstuff.entity.spawn.SpawnTable;
import net.mrwooly357.medievalstuff.entity.spawn.SpawnTables;
import net.mrwooly357.medievalstuff.registry.MSRegistries;

import java.util.concurrent.CompletableFuture;

public final class MSDynamicRegistryProvider extends FabricDynamicRegistryProvider {

    public MSDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }


    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        generateSpawnTables(registries, entries);
    }

    private static void generateSpawnTables(RegistryWrapper.WrapperLookup registries, Entries entries) {
        RegistryWrapper.Impl<SpawnTable> registry = registries.getWrapperOrThrow(MSRegistries.SPAWN_TABLE_KEY);
        entries.add(registry, SpawnTables.TEST);
    }

    @Override
    public String getName() {
        return "Dynamic Registries";
    }
}
