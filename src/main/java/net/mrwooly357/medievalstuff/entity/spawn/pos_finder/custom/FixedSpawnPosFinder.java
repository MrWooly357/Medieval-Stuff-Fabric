package net.mrwooly357.medievalstuff.entity.spawn.pos_finder.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.Vec3d;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextParameters;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.SpawnPosFinder;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.SpawnPosFinderType;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.SpawnPosFinderTypes;

import java.util.List;

public class FixedSpawnPosFinder extends SpawnPosFinder {

    public static final MapCodec<FixedSpawnPosFinder> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Vec3d.CODEC.fieldOf("offset").forGetter(finder -> finder.offset)
            )
                    .apply(instance, FixedSpawnPosFinder::new)
    );

    protected final Vec3d offset;

    protected FixedSpawnPosFinder(Vec3d offset) {
        this.offset = offset;
    }


    public static FixedSpawnPosFinder of(Vec3d pos) {
        return new FixedSpawnPosFinder(pos);
    }

    public static FixedSpawnPosFinder of(double x, double y, double z) {
        return of(new Vec3d(x, y, z));
    }

    @Override
    protected SpawnPosFinderType<FixedSpawnPosFinder> getType() {
        return SpawnPosFinderTypes.FIXED;
    }

    @Override
    public Vec3d nextPos(SpawnContext context, List<Vec3d> banned) {
        Vec3d pos = context.get(SpawnContextParameters.ORIGIN).orElseThrow().add(offset);

        return !banned.contains(pos) ? pos : Vec3d.ZERO;
    }

    @Override
    public int getMaxAttempts() {
        return 1;
    }
}
