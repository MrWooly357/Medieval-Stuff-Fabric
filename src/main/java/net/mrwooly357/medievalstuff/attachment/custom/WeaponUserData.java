package net.mrwooly357.medievalstuff.attachment.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record WeaponUserData(int combo, int remainingComboTicks) {

    public static final Codec<WeaponUserData> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("combo", 0).forGetter(WeaponUserData::combo),
                    Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("remaining_combo_ticks", 0).forGetter(WeaponUserData::remainingComboTicks)
            )
                    .apply(instance, WeaponUserData::new)
    );
    public static final PacketCodec<PacketByteBuf, WeaponUserData> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, WeaponUserData::combo,
            PacketCodecs.VAR_INT, WeaponUserData::remainingComboTicks,
            WeaponUserData::new
    );


    @Override
    public boolean equals(Object object) {
        return (this == object) || (object instanceof WeaponUserData(int otherCombo, int otherRemainingComboTicks)
                && combo == otherCombo
                && remainingComboTicks == otherRemainingComboTicks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(combo, remainingComboTicks);
    }

    @Override
    public @NotNull String toString() {
        return "WeaponUserData[combo: " + combo
                + ", remaining_combo_ticks: " + remainingComboTicks + "]";
    }
}
