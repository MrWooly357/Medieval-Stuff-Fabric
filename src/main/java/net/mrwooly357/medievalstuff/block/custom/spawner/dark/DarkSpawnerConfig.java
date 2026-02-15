package net.mrwooly357.medievalstuff.block.custom.spawner.dark;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.mrwooly357.medievalstuff.entity.spawn.SpawnTable;
import net.mrwooly357.medievalstuff.registry.MSRegistryKeys;
import net.mrwooly357.medievalstuff.util.MSUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public record DarkSpawnerConfig(
        Box playerDetectionArea,
        Box activationArea,
        IntProvider waves,
        Map<Integer, RegistryKey<SpawnTable>> spawns,
        Map<Integer, IntProvider> simultaneousSpawns,
        Map<Integer, IntProvider> simultaneousSpawnsAddedPerPlayer,
        Map<Integer, IntProvider> intermediateSpawns,
        Map<Integer, IntProvider> ticksBetweenIntermediateSpawns,
        Map<Integer, IntProvider> intermediateSpawnsAddedPerPlayer,
        Box startEjectingLootDetectionArea,
        boolean perPlayerLoot,
        RegistryKey<LootTable> loot,
        IntProvider cooldownDuration
) {

    public static final DarkSpawnerConfig DEFAULT = new DarkSpawnerConfig(
            new Box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D),
            new Box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D),
            ConstantIntProvider.ZERO,
            Map.of(),
            Map.of(),
            Map.of(),
            Map.of(),
            Map.of(),
            Map.of(),
            new Box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D),
            false,
            LootTables.EMPTY,
            ConstantIntProvider.ZERO
    );
    public static final Codec<DarkSpawnerConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    MSUtil.BOX_CODEC.fieldOf("player_detection_area").forGetter(DarkSpawnerConfig::playerDetectionArea),
                    MSUtil.BOX_CODEC.fieldOf("activation_area").forGetter(DarkSpawnerConfig::activationArea),
                    IntProvider.POSITIVE_CODEC.fieldOf("waves").forGetter(DarkSpawnerConfig::waves),
                    Codec.unboundedMap(Codec.intRange(1, Integer.MAX_VALUE), RegistryKey.createCodec(MSRegistryKeys.SPAWN_TABLE)).fieldOf("spawns").forGetter(DarkSpawnerConfig::spawns),
                    Codec.unboundedMap(Codec.intRange(1, Integer.MAX_VALUE), IntProvider.NON_NEGATIVE_CODEC).fieldOf("simultaneous_spawns").forGetter(DarkSpawnerConfig::simultaneousSpawns),
                    Codec.unboundedMap(Codec.intRange(1, Integer.MAX_VALUE), IntProvider.NON_NEGATIVE_CODEC).fieldOf("simultaneous_spawns_added_per_player").forGetter(DarkSpawnerConfig::simultaneousSpawnsAddedPerPlayer),
                    Codec.unboundedMap(Codec.intRange(1, Integer.MAX_VALUE), IntProvider.NON_NEGATIVE_CODEC).fieldOf("intermediate_spawns").forGetter(DarkSpawnerConfig::intermediateSpawns),
                    Codec.unboundedMap(Codec.intRange(1, Integer.MAX_VALUE), IntProvider.POSITIVE_CODEC).fieldOf("ticks_between_intermediate_spawns").forGetter(DarkSpawnerConfig::ticksBetweenIntermediateSpawns),
                    Codec.unboundedMap(Codec.intRange(1, Integer.MAX_VALUE), IntProvider.NON_NEGATIVE_CODEC).fieldOf("intermediate_spawns_added_per_player").forGetter(DarkSpawnerConfig::intermediateSpawnsAddedPerPlayer),
                    MSUtil.BOX_CODEC.fieldOf("start_ejecting_loot_detection_area").forGetter(DarkSpawnerConfig::startEjectingLootDetectionArea),
                    Codec.BOOL.optionalFieldOf("per_player_loot", true).forGetter(DarkSpawnerConfig::perPlayerLoot),
                    RegistryKey.createCodec(RegistryKeys.LOOT_TABLE).fieldOf("loot").forGetter(DarkSpawnerConfig::loot),
                    IntProvider.NON_NEGATIVE_CODEC.fieldOf("cooldown_duration").forGetter(DarkSpawnerConfig::cooldownDuration)
            )
                    .apply(instance, DarkSpawnerConfig::new)
    );

    @Override
    public @NotNull String toString() {
        return "DarkSpawnerConfig[player_detection_area: " + playerDetectionArea
                + ", activation_area: " + activationArea
                + ", waves: " + waves
                + ", spawns: " + spawns
                + ", simultaneous_spawns: " + simultaneousSpawns
                + ", simultaneous_spawns_adder_per_player: " + simultaneousSpawnsAddedPerPlayer
                + ", intermediate_spawns: " + intermediateSpawns
                + ", ticks_between_intermediate_spawns: " + ticksBetweenIntermediateSpawns
                + ", intermediate_spawns_added_per_player: " + intermediateSpawnsAddedPerPlayer
                + ", start_ejecting_loot_detection_area: " + startEjectingLootDetectionArea
                + ", per_player_loot: " + perPlayerLoot
                + ", loot: " + loot
                + ", cooldown_duration: " + cooldownDuration + "]";
    }
}
