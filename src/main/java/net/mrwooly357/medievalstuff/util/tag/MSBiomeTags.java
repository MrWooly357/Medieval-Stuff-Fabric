package net.mrwooly357.medievalstuff.util.tag;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import net.mrwooly357.medievalstuff.util.MSUtil;

public final class MSBiomeTags {

    public static final TagKey<Biome> SMALL_TAIGA_DUNGEON_GENERATES_IN = of("small_taiga_dungeon_generates_in");

    private MSBiomeTags() {}


    private static TagKey<Biome> of(String id) {
        return TagKey.of(RegistryKeys.BIOME, MSUtil.id(id));
    }
}
