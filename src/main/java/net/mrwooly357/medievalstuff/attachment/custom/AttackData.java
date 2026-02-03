package net.mrwooly357.medievalstuff.attachment.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record AttackData(int preparationTicks, int remainingPreparationTicks) {

    public static final Codec<AttackData> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("preparation_ticks", 0).forGetter(data -> data.preparationTicks),
                    Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("remaining_preparation_ticks", 0).forGetter(data -> data.remainingPreparationTicks)
            )
                    .apply(instance, AttackData::new)
    );
    public static final PacketCodec<PacketByteBuf, AttackData> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, AttackData::preparationTicks,
            PacketCodecs.VAR_INT, AttackData::remainingPreparationTicks,
            AttackData::new
    );


    @Override
    public boolean equals(Object object) {
        return (this == object) || (object instanceof AttackData(int otherPreparationTicks, int otherRemainingPreparationTicks)
                && preparationTicks == otherPreparationTicks
                && remainingPreparationTicks == otherRemainingPreparationTicks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(preparationTicks, remainingPreparationTicks);
    }

    @Override
    public @NotNull String toString() {
        return "AttackData[preparation_ticks: " + preparationTicks
                + ", remaining_preparation_ticks: " + remainingPreparationTicks + "]";
    }
}
