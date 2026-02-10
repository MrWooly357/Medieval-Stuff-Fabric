package net.mrwooly357.medievalstuff.entity.custom;

import net.mrwooly357.medievalstuff.attachment.custom.AttackPreparationData;
import net.mrwooly357.medievalstuff.attachment.custom.ComboData;

public interface WeaponUser {


    default void weaponUserTick() {
        AttackPreparationData preparationData = getAttackPreparationData();
        int remainingPreparationTicks = preparationData.remainingPreparationTicks();

        if (remainingPreparationTicks > 0)
            setAttackPreparationData(remainingPreparationTicks - 1);

        ComboData comboData = getComboData();
        int remainingComboTicks = comboData.remainingComboTicks();
        int ticksAfterPrevious = comboData.ticksAfterPreviousIncrease();

        if (remainingComboTicks > 0 || ticksAfterPrevious < Integer.MAX_VALUE) {
            remainingComboTicks--;
            int combo = comboData.combo();

            if (remainingComboTicks == 0)
                combo = 0;

            setComboData(combo, ticksAfterPrevious + 1, remainingComboTicks);
        }
    }

    AttackPreparationData getAttackPreparationData();

    void setAttackPreparationData(int remainingPreparationTicks);

    ComboData getComboData();

    void setComboData(int combo, int ticksAfterPreviousIncrease, int remainingComboTicks);
}
