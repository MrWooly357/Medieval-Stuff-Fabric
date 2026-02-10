package net.mrwooly357.medievalstuff.entity.spawn.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.mrwooly357.medievalstuff.registry.MSRegistries;

public record SpawnEntryType<SE extends SpawnEntry>(MapCodec<SE> codec) {

    public static final Codec<SpawnEntryType<?>> CODEC = MSRegistries.SPAWN_ENTRY_TYPE.getCodec();
}
