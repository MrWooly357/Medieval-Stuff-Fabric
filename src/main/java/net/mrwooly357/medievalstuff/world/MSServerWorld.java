package net.mrwooly357.medievalstuff.world;

import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.world.mark.WorldMark;
import net.mrwooly357.medievalstuff.world.mark.WorldMarkType;

import java.util.Optional;

public interface MSServerWorld {


    <WM extends WorldMark> Optional<WM> getMarkForPos(BlockPos pos, WorldMarkType<WM> type);

    void markPos(BlockPos pos, WorldMark mark);
}
