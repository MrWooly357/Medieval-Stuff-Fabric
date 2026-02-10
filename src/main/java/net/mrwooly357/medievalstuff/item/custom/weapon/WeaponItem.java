package net.mrwooly357.medievalstuff.item.custom.weapon;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.entity.custom.WeaponUser;

public abstract class WeaponItem extends ToolItem {

    protected WeaponItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHit(stack, target, attacker);

        return true;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postDamageEntity(stack, target, attacker);

        ItemStack afterDamage = damageAfterHit(stack, target, attacker);

        if (!ItemStack.areItemsEqual(stack, afterDamage))
            attacker.equipStack(EquipmentSlot.MAINHAND, afterDamage);
    }

    protected ItemStack damageAfterHit(ItemStack stack, LivingEntity attacker, LivingEntity target) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);

        return stack;
    }

    protected abstract void onClick(ItemStack stack, Hand hand, ServerPlayerEntity player, Identifier button);

    protected void attackAfter(ItemStack stack, Hand hand, WeaponUser user, int ticks) {
        //user.setAttackData(ticks, 0);
    }
}
