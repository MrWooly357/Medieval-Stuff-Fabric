package net.mrwooly357.medievalstuff.mixin;

import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.world.ServerWorld;
import net.mrwooly357.medievalstuff.item.component.MSEnchantmentEffectComponentTypes;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {


    @Inject(method = "modifyDamage", at = @At("TAIL"))
    private void modifyDamage(ServerWorld world, int level, ItemStack stack, Entity target, DamageSource damageSource, MutableFloat damage, CallbackInfo ci) {
        applyEffects(
                getEffect(MSEnchantmentEffectComponentTypes.DAMAGE),
                createEnchantedDamageLootContext(world, level, target, damageSource),
                effect -> damage.setValue(effect.apply(world, level, stack, Optional.ofNullable(damageSource.getAttacker()), target, damage.getValue(), damageSource))
        );
    }

    @Shadow
    private static <T> void applyEffects(List<EnchantmentEffectEntry<T>> enchantmentEffectEntries, LootContext lootContext, Consumer<T> effectConsumer) {}

    @Shadow
    public abstract <T> List<T> getEffect(ComponentType<List<T>> type);

    @Shadow
    public static LootContext createEnchantedDamageLootContext(ServerWorld world, int level, Entity entity, DamageSource damageSource) {
        return null;
    }
}
