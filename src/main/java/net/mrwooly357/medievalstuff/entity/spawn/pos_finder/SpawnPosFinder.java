package net.mrwooly357.medievalstuff.entity.spawn.pos_finder;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.Vec3d;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextAware;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextParameter;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextParameters;

import java.util.Set;

public abstract class SpawnPosFinder implements SpawnContextAware {

    public static final Codec<SpawnPosFinder> CODEC = SpawnPosFinderType.CODEC.dispatch(SpawnPosFinder::getType, type -> type.codec);

    protected SpawnPosFinder() {}


    protected abstract SpawnPosFinderType<? extends SpawnPosFinder> getType();

    public abstract Vec3d nextPos(SpawnContext context);

    public abstract int getMaxAttempts();

    @Override
    public Set<SpawnContextParameter<?>> getRequiredParameters() {
        return Set.of(SpawnContextParameters.ORIGIN);
    }
}
