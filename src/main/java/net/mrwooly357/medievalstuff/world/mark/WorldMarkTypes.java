package net.mrwooly357.medievalstuff.world.mark;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registry;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.registry.MSRegistries;
import net.mrwooly357.medievalstuff.util.MSUtil;

public final class WorldMarkTypes {

    private WorldMarkTypes() {}


    private static <WM extends WorldMark> WorldMarkType<WM> register(String id, MapCodec<WM> codec, PacketCodec<RegistryByteBuf, WM> packetCodec) {
        return Registry.register(MSRegistries.WORLD_MARK_TYPE, MSUtil.id(id), new WorldMarkType<>(codec, packetCodec));
    }

    public static void initialize() {
        MedievalStuff.logInitialization("world mark types");
    }
}
