package net.mrwooly357.medievalstuff.world.mark;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.mrwooly357.medievalstuff.registry.MSRegistries;

public record WorldMarkType<WM extends WorldMark>(MapCodec<WM> codec, PacketCodec<RegistryByteBuf, WM> packetCodec) {

    public static final Codec<WorldMarkType<?>> CODEC = MSRegistries.WORLD_MARK_TYPE.getCodec();
    public static final PacketCodec<RegistryByteBuf, WorldMarkType<?>> PACKET_CODEC = PacketCodecs.registryCodec(CODEC);
}
