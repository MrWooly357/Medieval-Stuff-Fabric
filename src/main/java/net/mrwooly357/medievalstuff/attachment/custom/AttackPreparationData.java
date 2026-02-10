package net.mrwooly357.medievalstuff.attachment.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record AttackPreparationData(int remainingPreparationTicks) {

    public static final Codec<AttackPreparationData> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("remaining_preparation_ticks", 0).forGetter(data -> data.remainingPreparationTicks)
            )
                    .apply(instance, AttackPreparationData::new)
    );
    public static final PacketCodec<PacketByteBuf, AttackPreparationData> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, AttackPreparationData::remainingPreparationTicks,
            AttackPreparationData::new
    );


    @Override
    public boolean equals(Object object) {
        return (this == object) || (object instanceof AttackPreparationData(int otherRemainingPreparationTicks)
                && remainingPreparationTicks == otherRemainingPreparationTicks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remainingPreparationTicks);
    }

    @Override
    public @NotNull String toString() {
        return "AttackPreparationData[remaining_preparation_ticks: " + remainingPreparationTicks + "]";
    }
}
