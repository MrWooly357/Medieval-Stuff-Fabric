package net.mrwooly357.medievalstuff.entity.spawn.entry.custom;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnCondition;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntry;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntryType;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntryTypes;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelector;
import net.mrwooly357.medievalstuff.util.MSUtil;

import java.util.List;

public final class EmptySpawnEntry extends SpawnEntry {

    public static final MapCodec<EmptySpawnEntry> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    SpawnSelector.Data.CODEC.fieldOf("selector_data").forGetter(entry -> entry.selectorData),
                    SpawnCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions),
                    MSUtil.SPAWN_REASON_CODEC.fieldOf("reason").forGetter(entry -> entry.reason)
            )
                    .apply(instance, EmptySpawnEntry::new)
    );

    private EmptySpawnEntry(SpawnSelector.Data selectorData, List<SpawnCondition> conditions, SpawnReason reason) {
        super(selectorData, conditions, reason);
    }


    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected SpawnEntryType<EmptySpawnEntry> getType() {
        return SpawnEntryTypes.EMPTY;
    }

    @Override
    protected List<Pair<Entity, SpawnReason>> tryGenerateEntities(SpawnContext context) {
        return List.of();
    }

    @Override
    public List<EntityType<?>> getEntities(ServerWorld world) {
        return List.of();
    }


    public static final class Builder extends SpawnEntry.Builder<Builder> {

        private Builder() {}


        public EmptySpawnEntry build(SpawnSelector.Data selectorData, SpawnReason reason) {
            return new EmptySpawnEntry(selectorData, List.copyOf(conditions), reason);
        }

        @Override
        protected Builder getThisBuilder() {
            return this;
        }
    }
}
