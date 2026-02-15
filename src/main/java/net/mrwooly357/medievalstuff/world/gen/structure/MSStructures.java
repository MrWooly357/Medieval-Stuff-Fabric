package net.mrwooly357.medievalstuff.world.gen.structure;

import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.StructureLiquidSettings;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.collection.Pool;
import net.minecraft.world.StructureSpawns;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureTerrainAdaptation;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import net.minecraft.world.gen.structure.DimensionPadding;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.Structure;
import net.mrwooly357.medievalstuff.util.MSUtil;
import net.mrwooly357.medievalstuff.util.tag.MSBiomeTags;
import net.mrwooly357.medievalstuff.world.gen.structure.custom.dungeon.small.SmallTaigaDungeonData;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public final class MSStructures {

    public static final RegistryKey<Structure> SMALL_TAIGA_DUNGEON = of("small_taiga_dungeon");

    private MSStructures() {}


    private static RegistryKey<Structure> of(String id) {
        return RegistryKey.of(RegistryKeys.STRUCTURE, MSUtil.id(id));
    }

    public static void bootstrap(Registerable<Structure> registerable) {
        RegistryEntryLookup<Biome> biomes = registerable.getRegistryLookup(RegistryKeys.BIOME);
        RegistryEntryLookup<StructurePool> templatePools = registerable.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);
        registerable.register(
                SMALL_TAIGA_DUNGEON,
                new JigsawStructure(
                        new Structure.Config.Builder(biomes.getOrThrow(MSBiomeTags.SMALL_TAIGA_DUNGEON_GENERATES_IN))
                                .step(GenerationStep.Feature.UNDERGROUND_STRUCTURES)
                                .terrainAdaptation(StructureTerrainAdaptation.BURY)
                                .spawnOverrides(
                                        Arrays.stream(SpawnGroup.values())
                                                .collect(Collectors.toMap(group -> group, group -> new StructureSpawns(StructureSpawns.BoundingBox.PIECE, Pool.empty())))
                                )
                                .build(),
                        templatePools.getOrThrow(SmallTaigaDungeonData.START_POOL),
                        Optional.empty(),
                        4,
                        UniformHeightProvider.create(YOffset.fixed(16), YOffset.fixed(128)),
                        false,
                        Optional.empty(),
                        116,
                        SmallTaigaDungeonData.ALIAS_BINDINGS,
                        new DimensionPadding(8),
                        StructureLiquidSettings.IGNORE_WATERLOGGING
                )
        );
    }
}
