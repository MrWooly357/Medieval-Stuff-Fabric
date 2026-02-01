package net.mrwooly357.medievalstuff.enchantment.effects.custom.entity_damage;

import com.mojang.serialization.MapCodec;
import net.minecraft.registry.Registry;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.enchantment.effects.custom.entity_damage.custom.IncreaseComboAndDamageEnchantmentEffect;
import net.mrwooly357.medievalstuff.registry.MSRegistries;
import net.mrwooly357.medievalstuff.util.MSUtil;

public final class MSEnchantmentEntityDamageEffects {

    private MSEnchantmentEntityDamageEffects() {}


    public static void initialize() {
        MedievalStuff.logInitialization("enchantment entity damage effects");
        register("increase_combo_and_damage", IncreaseComboAndDamageEnchantmentEffect.CODEC);
    }

    private static void register(String id, MapCodec<? extends EnchantmentEntityDamageEffect> codec) {
        Registry.register(MSRegistries.ENCHANTMENT_ENTITY_DAMAGE_EFFECT_TYPE, MSUtil.id(id), codec);
    }
}
