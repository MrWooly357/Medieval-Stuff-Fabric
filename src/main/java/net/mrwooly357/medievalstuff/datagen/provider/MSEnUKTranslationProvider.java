package net.mrwooly357.medievalstuff.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.mrwooly357.medievalstuff.enchantment.MSEnchantments;

import java.util.concurrent.CompletableFuture;

public final class MSEnUKTranslationProvider extends FabricLanguageProvider {

    public MSEnUKTranslationProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_uk", registryLookup);
    }


    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translation) {
        translation.addEnchantment(MSEnchantments.COMBO, "Combo");
    }
}
