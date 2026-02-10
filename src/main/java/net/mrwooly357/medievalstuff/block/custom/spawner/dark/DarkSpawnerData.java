package net.mrwooly357.medievalstuff.block.custom.spawner.dark;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.world.World;

import java.util.*;

public final class DarkSpawnerData {


    private int wave;
    private final Set<Pair<Integer, Entity>> pendingSpawns;
    private final Set<UUID> trackedEntities;
    private int ticksRemainingBeforeNextIntermediateSpawns;
    private final List<EntityType<?>> entities;
    private double displayEntityRotation;
    private double lastDisplayEntityRotation;
    private int remainingDisplayEntityDisplayTicks;

    DarkSpawnerData() {
        this(
                0,
                new HashSet<>(),
                new HashSet<>(),
                0,
                new ArrayList<>(),
                0.0D,
                0.0D,
                0
        );
    }

    DarkSpawnerData(
            int wave,
            Set<Pair<Integer, Entity>> pendingSpawns,
            Set<UUID> trackedEntities,
            int ticksRemainingBeforeNextIntermediateSpawns,
            List<EntityType<?>> entities,
            double displayEntityRotation,
            double lastDisplayEntityRotation,
            int remainingDisplayEntityDisplayTicks
    ) {
        this.wave = wave;
        this.pendingSpawns = pendingSpawns;
        this.trackedEntities = trackedEntities;
        this.ticksRemainingBeforeNextIntermediateSpawns = ticksRemainingBeforeNextIntermediateSpawns;
        this.entities = entities;
        this.displayEntityRotation = displayEntityRotation;
        this.lastDisplayEntityRotation = lastDisplayEntityRotation;
        this.remainingDisplayEntityDisplayTicks = remainingDisplayEntityDisplayTicks;
    }


    static Codec<DarkSpawnerData> createCodec(World world) {
        return RecordCodecBuilder.create(
                instance -> instance.group(
                        Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("wave", 0).forGetter(DarkSpawnerData::getWave),
                        Codec.list(Codec.pair(Codec.intRange(0, Integer.MAX_VALUE), NbtCompound.CODEC.xmap(nbt -> EntityType.getEntityFromNbt(nbt, world).orElseThrow(), entity -> entity.writeNbt(new NbtCompound()))))
                                .xmap(HashSet::new, List::copyOf).optionalFieldOf("pending_spawns", new HashSet<>())
                                .xmap(set -> (Set<Pair<Integer, Entity>>) set, set -> set instanceof HashSet<Pair<Integer, Entity>> hashSet ? hashSet : new HashSet<>(set))
                                .forGetter(DarkSpawnerData::getPendingSpawns),
                        Codec.list(Codec.STRING.xmap(UUID::fromString, UUID::toString)).xmap(HashSet::new, List::copyOf).optionalFieldOf("tracked_entities", new HashSet<>())
                                .xmap(set -> (Set<UUID>) set, set -> set instanceof HashSet<UUID> hashSet ? hashSet : new HashSet<>(set))
                                .forGetter(DarkSpawnerData::getTrackedEntities),
                        Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("ticks_remaining_before_next_intermediate_spawns", 0).forGetter(DarkSpawnerData::getTicksRemainingBeforeNextIntermediateSpawns),
                        Codec.list(Registries.ENTITY_TYPE.getCodec()).optionalFieldOf("entities", new ArrayList<>()).forGetter(DarkSpawnerData::getEntities),
                        Codec.doubleRange(0.0D, 360.0D).optionalFieldOf("display_entity_rotation", 0.0D).forGetter(DarkSpawnerData::getDisplayEntityRotation),
                        Codec.doubleRange(0.0D, 360.0D).optionalFieldOf("last_display_entity_rotation", 0.0D).forGetter(DarkSpawnerData::getLastDisplayEntityRotation),
                        Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("remaining_display_entity_display_ticks", 0).forGetter(DarkSpawnerData::getRemainingDisplayEntityDisplayTicks)
                )
                        .apply(instance, DarkSpawnerData::new)
        );
    }

    int getWave() {
        return wave;
    }

    void setWave(int wave) {
        this.wave = wave;
    }

    Set<Pair<Integer, Entity>> getPendingSpawns() {
        return pendingSpawns;
    }

    Set<UUID> getTrackedEntities() {
        return trackedEntities;
    }

    int getTicksRemainingBeforeNextIntermediateSpawns() {
        return ticksRemainingBeforeNextIntermediateSpawns;
    }

    void setTicksRemainingBeforeNextIntermediateSpawns(int ticksRemainingBeforeNextIntermediateSpawns) {
        this.ticksRemainingBeforeNextIntermediateSpawns = ticksRemainingBeforeNextIntermediateSpawns;
    }

    List<EntityType<?>> getEntities() {
        return entities;
    }

    double getDisplayEntityRotation() {
        return displayEntityRotation;
    }

    void setDisplayEntityRotation(double displayEntityRotation) {
        this.displayEntityRotation = displayEntityRotation;
    }

    double getLastDisplayEntityRotation() {
        return lastDisplayEntityRotation;
    }

    void setLastDisplayEntityRotation(double lastDisplayEntityRotation) {
        this.lastDisplayEntityRotation = lastDisplayEntityRotation;
    }

    int getRemainingDisplayEntityDisplayTicks() {
        return remainingDisplayEntityDisplayTicks;
    }

    void setRemainingDisplayEntityDisplayTicks(int remainingDisplayEntityDisplayTicks) {
        this.remainingDisplayEntityDisplayTicks = remainingDisplayEntityDisplayTicks;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                wave,
                pendingSpawns,
                trackedEntities,
                ticksRemainingBeforeNextIntermediateSpawns,
                entities,
                displayEntityRotation,
                lastDisplayEntityRotation,
                remainingDisplayEntityDisplayTicks
        );
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object) || (object instanceof DarkSpawnerData data
                && wave == data.wave
                && pendingSpawns.equals(data.pendingSpawns)
                && trackedEntities.equals(data.trackedEntities)
                && ticksRemainingBeforeNextIntermediateSpawns == data.ticksRemainingBeforeNextIntermediateSpawns
                && entities.equals(data.entities)
                && Double.compare(displayEntityRotation, data.displayEntityRotation) == 0
                && Double.compare(lastDisplayEntityRotation, data.lastDisplayEntityRotation) == 0
                && remainingDisplayEntityDisplayTicks == data.remainingDisplayEntityDisplayTicks);
    }

    @Override
    public String toString() {
        return "DarkSpawnerData[wave: " + wave
                + ", pending_spawns: " + pendingSpawns
                + ", tracked_entities: " + trackedEntities
                + ", ticks_remaining_before_next_intermediate_spawns: " + ticksRemainingBeforeNextIntermediateSpawns
                + ", entities: " + entities
                + ", display_entity_rotation: " + displayEntityRotation
                + ", last_display_entity_rotation: " + lastDisplayEntityRotation
                + ", remaining_display_entity_display_ticks: " + remainingDisplayEntityDisplayTicks + "]";
    }
}
