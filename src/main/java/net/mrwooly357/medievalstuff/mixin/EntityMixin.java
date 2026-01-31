package net.mrwooly357.medievalstuff.mixin;

import net.minecraft.entity.Entity;
import net.mrwooly357.medievalstuff.attachment.MSAttachmentTypes;
import net.mrwooly357.medievalstuff.attachment.custom.WeaponUserData;
import net.mrwooly357.medievalstuff.entity.custom.WeaponUser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements WeaponUser {


    @Inject(method = "tick", at = @At("TAIL"))
    private void injectTick(CallbackInfo ci) {
        WeaponUser.tick(this);
    }

    @Override
    public int getCombo() {
        return ((Entity) (Object) this).getAttachedOrCreate(MSAttachmentTypes.WEAPON_USER_DATA).combo();
    }

    @Override
    public void increaseCombo() {
        WeaponUserData data = ((Entity) (Object) this).getAttachedOrCreate(MSAttachmentTypes.WEAPON_USER_DATA);
        ((Entity) (Object) this).setAttached(MSAttachmentTypes.WEAPON_USER_DATA, new WeaponUserData(data.combo() + 1, data.remainingComboTicks()));
    }

    @Override
    public void resetCombo() {
        ((Entity) (Object) this).setAttached(MSAttachmentTypes.WEAPON_USER_DATA, new WeaponUserData(0, ((Entity) (Object) this).getAttachedOrCreate(MSAttachmentTypes.WEAPON_USER_DATA).remainingComboTicks()));
    }

    @Override
    public int getRemainingComboTicks() {
        return ((Entity) (Object) this).getAttachedOrCreate(MSAttachmentTypes.WEAPON_USER_DATA).remainingComboTicks();
    }

    @Override
    public void setRemainingComboTicks(int ticks) {
        ((Entity) (Object) this).setAttached(MSAttachmentTypes.WEAPON_USER_DATA, new WeaponUserData(((Entity) (Object) this).getAttachedOrCreate(MSAttachmentTypes.WEAPON_USER_DATA).combo() + 1, ticks));
    }

    @Override
    public void decreaseRemainingComboTicks() {
        WeaponUserData data = ((Entity) (Object) this).getAttachedOrCreate(MSAttachmentTypes.WEAPON_USER_DATA);
        ((Entity) (Object) this).setAttached(MSAttachmentTypes.WEAPON_USER_DATA, new WeaponUserData(data.combo(), data.remainingComboTicks() - 1));
    }
}
