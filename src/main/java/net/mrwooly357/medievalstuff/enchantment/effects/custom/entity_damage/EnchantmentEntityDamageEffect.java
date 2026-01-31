package net.mrwooly357.medievalstuff.enchantment.effects.custom.entity_damage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.mrwooly357.medievalstuff.registry.MSRegistries;

import java.util.function.Function;

public interface EnchantmentEntityDamageEffect {

    Codec<EnchantmentEntityDamageEffect> CODEC = MSRegistries.ENCHANTMENT_ENTITY_DAMAGE_EFFECT_TYPE.getCodec().dispatch(EnchantmentEntityDamageEffect::getCodec, Function.identity());


    MapCodec<? extends EnchantmentEntityDamageEffect> getCodec();

    float apply(ServerWorld world, int level, ItemStack stack, Entity user, float damage, DamageSource damageSource);
}
