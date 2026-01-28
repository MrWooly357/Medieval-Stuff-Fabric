package net.mrwooly357.medievalstuff.entity.spawn;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.floatprovider.ConstantFloatProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.mrwooly357.medievalstuff.entity.spawn.condition.custom.RandomChanceSpawnCondition;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextTypes;
import net.mrwooly357.medievalstuff.entity.spawn.entry.custom.EntitySpawnEntry;
import net.mrwooly357.medievalstuff.entity.spawn.function.custom.InitializeSpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.function.custom.SpawnSpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.custom.FixedSpawnPosFinder;
import net.mrwooly357.medievalstuff.entity.spawn.selector.custom.EachElementSpawnSelector;
import net.mrwooly357.medievalstuff.registry.MSRegistries;
import net.mrwooly357.medievalstuff.util.MSUtil;

public final class SpawnTables {

    public static final RegistryKey<SpawnTable> TEST = create("test");

    private SpawnTables() {}


    private static RegistryKey<SpawnTable> create(String id) {
        return RegistryKey.of(MSRegistries.SPAWN_TABLE_KEY, MSUtil.id(id));
    }

    public static void bootstrap(Registerable<SpawnTable> registerable) {
        registerable.register(SpawnTables.TEST,
                SpawnTable.builder()
                        .pool(
                                SpawnPool.builder()
                                        .entry(
                                                EntitySpawnEntry.builder()
                                                        .ignoreVanillaSpawnRules()
                                                        .build(EachElementSpawnSelector.Data.INSTANCE, FixedSpawnPosFinder.of(Vec3d.ZERO), EntityType.ZOMBIE, UniformIntProvider.create(1, 2))
                                        )
                                        .build(EachElementSpawnSelector.Data.INSTANCE, EachElementSpawnSelector.INSTANCE)
                        )
                        .pool(
                                SpawnPool.builder()
                                        .condition(RandomChanceSpawnCondition.of(ConstantFloatProvider.create(0.65F)))
                                        .entry(
                                                EntitySpawnEntry.builder()
                                                        .ignoreVanillaSpawnRules()
                                                        .build(EachElementSpawnSelector.Data.INSTANCE, FixedSpawnPosFinder.of(Vec3d.ZERO), EntityType.SKELETON, ConstantIntProvider.create(1))
                                        )
                                        .build(EachElementSpawnSelector.Data.INSTANCE, EachElementSpawnSelector.INSTANCE)
                        )
                        .function(
                                InitializeSpawnFunction.builder()
                                        .build(SpawnReason.TRIGGERED)
                        )
                        .function(
                                SpawnSpawnFunction.builder()
                                        .build()
                        )
                        .build(SpawnContextTypes.GENERIC, EachElementSpawnSelector.INSTANCE)
        );
    }
}
