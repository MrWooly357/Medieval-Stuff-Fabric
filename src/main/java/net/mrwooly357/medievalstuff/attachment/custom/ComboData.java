package net.mrwooly357.medievalstuff.attachment.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record ComboData(int combo, int remainingComboTicks) {

    public static final Codec<ComboData> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("combo", 0).forGetter(ComboData::combo),
                    Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("remaining_combo_ticks", 0).forGetter(ComboData::remainingComboTicks)
            )
                    .apply(instance, ComboData::new)
    );
    public static final PacketCodec<PacketByteBuf, ComboData> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, ComboData::combo,
            PacketCodecs.VAR_INT, ComboData::remainingComboTicks,
            ComboData::new
    );


    @Override
    public boolean equals(Object object) {
        return (this == object) || (object instanceof ComboData(int otherCombo, int otherRemainingComboTicks)
                && combo == otherCombo
                && remainingComboTicks == otherRemainingComboTicks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(combo, remainingComboTicks);
    }

    @Override
    public @NotNull String toString() {
        return "ComboData[combo: " + combo
                + ", remaining_combo_ticks: " + remainingComboTicks + "]";
    }
}
