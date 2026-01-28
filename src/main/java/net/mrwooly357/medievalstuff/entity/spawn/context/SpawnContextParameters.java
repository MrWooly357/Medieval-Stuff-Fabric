package net.mrwooly357.medievalstuff.entity.spawn.context;

import net.minecraft.util.math.Vec3d;
import net.mrwooly357.medievalstuff.util.MSUtil;

public final class SpawnContextParameters {

    public static final SpawnContextParameter<Vec3d> ORIGIN = of(
            Vec3d.class, "origin"
    );

    private SpawnContextParameters() {}


    private static <A> SpawnContextParameter<A> of(Class<A> type, String id) {
        return SpawnContextParameter.of(type, MSUtil.id(id));
    }
}
