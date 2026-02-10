package net.mrwooly357.medievalstuff.entity.spawn;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextTypes;
import net.mrwooly357.medievalstuff.entity.spawn.selector.custom.NoneSpawnSelector;
import net.mrwooly357.medievalstuff.registry.MSRegistryKeys;
import net.mrwooly357.medievalstuff.util.MSUtil;

public final class SpawnTables {

    public static final RegistryKey<SpawnTable> EMPTY = of("empty");

    private SpawnTables() {}


    private static RegistryKey<SpawnTable> of(String id) {
        return RegistryKey.of(MSRegistryKeys.SPAWN_TABLE, MSUtil.id(id));
    }

    public static void bootstrap(Registerable<SpawnTable> registerable) {
        registerable.register(
                SpawnTables.EMPTY,
                SpawnTable.builder()
                        .build(SpawnContextTypes.GENERIC, NoneSpawnSelector.INSTANCE)
        );
    }
}
