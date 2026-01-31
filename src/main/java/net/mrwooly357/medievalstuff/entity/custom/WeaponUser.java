package net.mrwooly357.medievalstuff.entity.custom;

public interface WeaponUser {


    static void tick(WeaponUser user) {
        if (user.getRemainingComboTicks() > 0)
            user.decreaseRemainingComboTicks();

        if (user.getRemainingComboTicks() == 0)
            user.resetCombo();
    }

    int getCombo();

    void increaseCombo();

    void resetCombo();

    int getRemainingComboTicks();

    void setRemainingComboTicks(int ticks);

    void decreaseRemainingComboTicks();
}
