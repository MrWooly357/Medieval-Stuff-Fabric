package net.mrwooly357.medievalstuff.entity.spawn.pos_finder.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.SpawnPosFinder;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.SpawnPosFinderType;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.SpawnPosFinderTypes;
import net.mrwooly357.medievalstuff.util.MSUtil;

import java.util.List;

public class RandomSpawnPosFinder extends SpawnPosFinder {

    public static final MapCodec<RandomSpawnPosFinder> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    MSUtil.BOX_CODEC.fieldOf("area").forGetter(finder -> finder.area)
            )
                    .apply(instance, RandomSpawnPosFinder::new)
    );

    protected final Box area;
    protected final int maxAttempts;

    public RandomSpawnPosFinder(Box area) {
        this.area = area;
        maxAttempts = (int) (area.getLengthX() * area.getLengthY() * area.getLengthZ());
    }


    @Override
    protected SpawnPosFinderType<RandomSpawnPosFinder> getType() {
        return SpawnPosFinderTypes.RANDOM;
    }

    @Override
    public Vec3d nextPos(SpawnContext context, List<Vec3d> banned) {
        return null;
    }

    @Override
    public int getMaxAttempts() {
        return maxAttempts;
    }
}
