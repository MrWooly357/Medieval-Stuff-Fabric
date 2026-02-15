package net.mrwooly357.medievalstuff.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.attachment.MSAttachmentTypes;
import net.mrwooly357.medievalstuff.attachment.custom.ChunkMarksData;
import net.mrwooly357.medievalstuff.world.MSServerWorld;
import net.mrwooly357.medievalstuff.world.mark.WorldMark;
import net.mrwooly357.medievalstuff.world.mark.WorldMarkType;
import org.spongepowered.asm.mixin.Mixin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin implements MSServerWorld {


    @SuppressWarnings("unchecked")
    @Override
    public <WM extends WorldMark> Optional<WM> getMarkForPos(BlockPos pos, WorldMarkType<WM> type) {
        return (Optional<WM>) Optional.of(((ServerWorld) (Object) this).getChunk(pos).getAttachedOrCreate(MSAttachmentTypes.CHUNK_MARKS_DATA).marks().get(pos).get(type));
    }

    @Override
    public void markPos(BlockPos pos, WorldMark mark) {
        ServerWorld world = (ServerWorld) (Object) this;
        Map<BlockPos, Map<WorldMarkType<?>, WorldMark>> marks = new HashMap<>(world.getAttachedOrCreate(MSAttachmentTypes.CHUNK_MARKS_DATA).marks());
        marks.putIfAbsent(pos, new HashMap<>());
        marks.get(pos).put(mark.getType(), mark);
        world.setAttached(MSAttachmentTypes.CHUNK_MARKS_DATA, new ChunkMarksData(marks));
    }
}
