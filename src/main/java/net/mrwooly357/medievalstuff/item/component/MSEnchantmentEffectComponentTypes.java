package net.mrwooly357.medievalstuff.item.component;

import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.enchantment.effects.custom.entity_damage.EnchantmentEntityDamageEffect;
import net.mrwooly357.medievalstuff.util.MSUtil;

import java.util.List;
import java.util.function.UnaryOperator;

public final class MSEnchantmentEffectComponentTypes {

    public static final ComponentType<List<EnchantmentEffectEntry<EnchantmentEntityDamageEffect>>> DAMAGE = register(
            "damage", builder -> builder
                    .codec(EnchantmentEffectEntry.createCodec(EnchantmentEntityDamageEffect.CODEC, LootContextTypes.ENCHANTED_DAMAGE).listOf())
    );

    private MSEnchantmentEffectComponentTypes() {}


    private static <A> ComponentType<A> register(String id, UnaryOperator<ComponentType.Builder<A>> builder) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, MSUtil.id(id), builder.apply(ComponentType.builder()).build());
    }

    public static void initialize() {
        MedievalStuff.logInitialization("enchantment effect component types");
    }
}
