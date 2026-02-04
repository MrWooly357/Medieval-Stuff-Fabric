package net.mrwooly357.medievalstuff.entity.custom;

import net.mrwooly357.medievalstuff.attachment.custom.ComboData;

public interface WeaponUser {


    static void tick(WeaponUser user) {
        ComboData data = user.getComboData();
        int remainingComboTicks = data.remainingComboTicks();
        int ticksAfterPrevious = data.ticksAfterPrevious();

        if (remainingComboTicks > 0 || ticksAfterPrevious < Integer.MAX_VALUE) {
            remainingComboTicks--;
            int combo = data.combo();

            if (remainingComboTicks == 0)
                combo = 0;

            user.setComboData(combo, ticksAfterPrevious + 1, remainingComboTicks);
        }
    }

    ComboData getComboData();

    void setComboData(int combo, int ticksAfterPrevious, int remainingComboTicks);
}
