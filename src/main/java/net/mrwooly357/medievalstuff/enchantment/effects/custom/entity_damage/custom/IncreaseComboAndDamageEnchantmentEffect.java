package net.mrwooly357.medievalstuff.enchantment.effects.custom.entity_damage.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.mrwooly357.medievalstuff.attachment.custom.ComboData;
import net.mrwooly357.medievalstuff.enchantment.effects.custom.entity_damage.EnchantmentEntityDamageEffect;
import net.mrwooly357.medievalstuff.entity.custom.WeaponUser;

import java.util.Optional;

public record IncreaseComboAndDamageEnchantmentEffect() implements EnchantmentEntityDamageEffect {

    public static final IncreaseComboAndDamageEnchantmentEffect INSTANCE = new IncreaseComboAndDamageEnchantmentEffect();
    public static final MapCodec<IncreaseComboAndDamageEnchantmentEffect> CODEC = MapCodec.unit(INSTANCE);


    @Override
    public MapCodec<IncreaseComboAndDamageEnchantmentEffect> getCodec() {
        return CODEC;
    }

    @Override
    public float apply(ServerWorld world, int level, ItemStack stack, Optional<Entity> attacker, Entity target, float damage, DamageSource damageSource) {
        if (attacker.isPresent() && attacker.orElseThrow() instanceof WeaponUser weaponUser
                && (!(target instanceof LivingEntity living) || !living.isBlocking())
                && (!(target instanceof ServerPlayerEntity player) || !player.isInCreativeMode())) {
            ComboData data = weaponUser.getComboData();
            int ticksAfterPreviousIncrease = data.ticksAfterPreviousIncrease();
            int combo = data.combo();
            boolean increase = false;

            if (ticksAfterPreviousIncrease > 10) {
                combo++;
                increase = true;
            }

            weaponUser.setComboData(combo, increase ? 0 : ticksAfterPreviousIncrease, level * 20);

            return combo > 1 ? damage + damage * combo * level / 100.0F : damage;
        } else
            return damage;
    }
}
