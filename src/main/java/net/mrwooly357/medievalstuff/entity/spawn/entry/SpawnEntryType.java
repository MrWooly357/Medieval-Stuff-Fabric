package net.mrwooly357.medievalstuff.entity.spawn.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.mrwooly357.medievalstuff.registry.MSRegistries;

public final class SpawnEntryType<SE extends SpawnEntry> {

    public static final Codec<SpawnEntryType<?>> CODEC = MSRegistries.SPAWN_ENTRY_TYPE.getCodec();

    final MapCodec<SE> codec;

    private SpawnEntryType(MapCodec<SE> codec) {
        this.codec = codec;
    }


    public static <SE extends SpawnEntry> SpawnEntryType<SE> of(MapCodec<SE> codec) {
        return new SpawnEntryType<>(codec);
    }
}
