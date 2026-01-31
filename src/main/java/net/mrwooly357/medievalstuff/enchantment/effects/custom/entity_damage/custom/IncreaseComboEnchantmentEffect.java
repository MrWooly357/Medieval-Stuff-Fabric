package net.mrwooly357.medievalstuff.enchantment.effects.custom.entity_damage.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.mrwooly357.medievalstuff.enchantment.effects.custom.entity_damage.EnchantmentEntityDamageEffect;
import net.mrwooly357.medievalstuff.entity.custom.WeaponUser;

public record IncreaseComboEnchantmentEffect() implements EnchantmentEntityDamageEffect {

    public static final IncreaseComboEnchantmentEffect INSTANCE = new IncreaseComboEnchantmentEffect();
    public static final MapCodec<IncreaseComboEnchantmentEffect> CODEC = MapCodec.unit(INSTANCE);


    @Override
    public MapCodec<IncreaseComboEnchantmentEffect> getCodec() {
        return CODEC;
    }

    @Override
    public float apply(ServerWorld world, int level, ItemStack stack, Entity user, float damage, DamageSource damageSource) {
        if (user instanceof WeaponUser weaponUser) {
            weaponUser.increaseCombo();
            weaponUser.setRemainingComboTicks(level * 20);
            int combo = weaponUser.getCombo();

            return combo > 1 ? damage + damage * combo * level / 100.0F : damage;
        } else
            return damage;
    }
}
