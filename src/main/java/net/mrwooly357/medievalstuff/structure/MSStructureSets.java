package net.mrwooly357.medievalstuff.structure;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.StructureSet;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.structure.Structure;
import net.mrwooly357.medievalstuff.util.MSUtil;
import net.mrwooly357.medievalstuff.world.gen.structure.MSStructures;

import java.util.List;

public final class MSStructureSets {

    public static final RegistryKey<StructureSet> SMALL_DUNGEONS = of("small_dungeons");

    private MSStructureSets() {}


    public static RegistryKey<StructureSet> of(String id) {
        return RegistryKey.of(RegistryKeys.STRUCTURE_SET, MSUtil.id(id));
    }

    public static void bootstrap(Registerable<StructureSet> registerable) {
        RegistryEntryLookup<Structure> structures = registerable.getRegistryLookup(RegistryKeys.STRUCTURE);
        registerable.register(
                SMALL_DUNGEONS,
                new StructureSet(
                        List.of(
                                StructureSet.createEntry(structures.getOrThrow(MSStructures.SMALL_TAIGA_DUNGEON))
                        ),
                        new RandomSpreadStructurePlacement(16, 8, SpreadType.LINEAR, 24579855))
        );
    }
}
