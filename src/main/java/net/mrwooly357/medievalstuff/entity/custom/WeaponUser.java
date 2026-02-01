package net.mrwooly357.medievalstuff.entity.custom;

import net.mrwooly357.medievalstuff.attachment.custom.WeaponUserData;

public interface WeaponUser {


    static void tick(WeaponUser user) {
        WeaponUserData data = user.getWeaponUserData();
        int remainingComboTicks = data.remainingComboTicks();

        if (remainingComboTicks > 0) {
            remainingComboTicks--;
            int combo = data.combo();

            if (remainingComboTicks == 0)
                combo = 0;

            user.setWeaponUserData(combo, remainingComboTicks);
        }
    }

    WeaponUserData getWeaponUserData();

    void setWeaponUserData(int combo, int remainingComboTicks);
}
