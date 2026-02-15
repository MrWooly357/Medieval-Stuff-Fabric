package net.mrwooly357.medievalstuff.attachment.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.world.mark.WorldMark;
import net.mrwooly357.medievalstuff.world.mark.WorldMarkType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public record ChunkMarksData(Map<BlockPos, Map<WorldMarkType<?>, WorldMark>> marks) {

    public static final ChunkMarksData DEFAULT = new ChunkMarksData(Map.of());
    public static final Codec<ChunkMarksData> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.unboundedMap(BlockPos.CODEC, Codec.unboundedMap(WorldMarkType.CODEC, WorldMark.CODEC)).optionalFieldOf("marks", new HashMap<>()).forGetter(ChunkMarksData::marks)
            )
                    .apply(instance, ChunkMarksData::new)
    );
    public static final PacketCodec<RegistryByteBuf, ChunkMarksData> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.map(HashMap::new, BlockPos.PACKET_CODEC, PacketCodecs.map(HashMap::new, WorldMarkType.PACKET_CODEC, WorldMark.PACKET_CODEC)), ChunkMarksData::marks,
            ChunkMarksData::new
    );


    @Override
    public @NotNull String toString() {
        return "ChunkMarksData[marks: " + marks + "]";
    }
}
