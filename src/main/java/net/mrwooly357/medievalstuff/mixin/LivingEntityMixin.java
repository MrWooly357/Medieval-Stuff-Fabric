package net.mrwooly357.medievalstuff.mixin;

import net.minecraft.entity.LivingEntity;
import net.mrwooly357.medievalstuff.attachment.MSAttachmentTypes;
import net.mrwooly357.medievalstuff.attachment.custom.ComboData;
import net.mrwooly357.medievalstuff.entity.custom.WeaponUser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements WeaponUser {


    @Inject(method = "tick", at = @At("TAIL"))
    private void injectTick(CallbackInfo ci) {
        if (!((LivingEntity) (Object) this).getWorld().isClient())
            WeaponUser.tick(this);
    }

    @Override
    public ComboData getWeaponUserData() {
        return ((LivingEntity) (Object) this).getAttachedOrCreate(MSAttachmentTypes.COMBO_DATA);
    }

    @Override
    public void setWeaponUserData(int combo, int remainingComboTicks) {
        ((LivingEntity) (Object) this).setAttached(MSAttachmentTypes.COMBO_DATA, new ComboData(combo, remainingComboTicks));
    }
}
