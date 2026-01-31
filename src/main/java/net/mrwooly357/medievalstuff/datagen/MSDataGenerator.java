package net.mrwooly357.medievalstuff.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.datagen.provider.MSDynamicRegistryProvider;
import net.mrwooly357.medievalstuff.datagen.provider.MSEnUKTranslationProvider;
import net.mrwooly357.medievalstuff.datagen.provider.MSEnUSTranslationProvider;
import net.mrwooly357.medievalstuff.enchantment.MSEnchantments;
import net.mrwooly357.medievalstuff.entity.spawn.SpawnTables;
import net.mrwooly357.medievalstuff.registry.MSRegistryKeys;
import org.jetbrains.annotations.NotNull;

public final class MSDataGenerator implements DataGeneratorEntrypoint {


	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();
		pack.addProvider(MSDynamicRegistryProvider::new);
		pack.addProvider(MSEnUSTranslationProvider::new);
		pack.addProvider(MSEnUKTranslationProvider::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder builder) {
		builder.addRegistry(RegistryKeys.ENCHANTMENT, MSEnchantments::bootstrap);
		builder.addRegistry(MSRegistryKeys.SPAWN_TABLE, SpawnTables::bootstrap);
	}

	@Override
	public @NotNull String getEffectiveModId() {
		return MedievalStuff.MOD_ID;
	}
}
