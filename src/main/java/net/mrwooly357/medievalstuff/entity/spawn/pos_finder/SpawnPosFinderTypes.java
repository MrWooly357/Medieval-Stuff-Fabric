package net.mrwooly357.medievalstuff.entity.spawn.pos_finder;

import com.mojang.serialization.MapCodec;
import net.minecraft.registry.Registry;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.custom.FixedSpawnPosFinder;
import net.mrwooly357.medievalstuff.registry.MSRegistries;
import net.mrwooly357.medievalstuff.util.MSUtil;

public final class SpawnPosFinderTypes {

    public static final SpawnPosFinderType<FixedSpawnPosFinder> FIXED = register(
            "fixed", FixedSpawnPosFinder.CODEC
    );

    private SpawnPosFinderTypes() {}


    private static <SPF extends SpawnPosFinder> SpawnPosFinderType<SPF> register(String id, MapCodec<SPF> codec) {
        return Registry.register(MSRegistries.SPAWN_POS_FINDER_TYPE, MSUtil.id(id), SpawnPosFinderType.of(codec));
    }

    public static void initialize() {
        MedievalStuff.logInitialization("spawn pos finder types");
    }
}
