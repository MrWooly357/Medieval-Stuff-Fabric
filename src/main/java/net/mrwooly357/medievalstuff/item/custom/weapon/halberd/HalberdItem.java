package net.mrwooly357.medievalstuff.item.custom.weapon.halberd;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.item.custom.weapon.WeaponItem;

public class HalberdItem extends WeaponItem {

    public HalberdItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }


    @Override
    protected void onClick(ItemStack stack, Hand hand, ServerPlayerEntity player, Identifier button) {}
}
