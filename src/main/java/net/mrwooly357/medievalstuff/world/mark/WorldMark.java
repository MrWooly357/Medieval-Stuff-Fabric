package net.mrwooly357.medievalstuff.world.mark;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

public interface WorldMark {

    Codec<WorldMark> CODEC = WorldMarkType.CODEC.dispatch(WorldMark::getType, WorldMarkType::codec);
    PacketCodec<RegistryByteBuf, WorldMark> PACKET_CODEC = WorldMarkType.PACKET_CODEC.dispatch(WorldMark::getType, WorldMarkType::packetCodec);


    WorldMarkType<? extends WorldMark> getType();
}
