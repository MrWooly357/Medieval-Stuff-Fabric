package net.mrwooly357.medievalstuff.entity.spawn.entry.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnCondition;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntry;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntryType;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntryTypes;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelector;

import java.util.List;

public final class EmptySpawnEntry extends SpawnEntry {

    public static final MapCodec<EmptySpawnEntry> CODEC = RecordCodecBuilder.mapCodec(
            instance -> addDefaultFields(instance)
                    .apply(instance, EmptySpawnEntry::new)
    );

    private EmptySpawnEntry(SpawnSelector.Data selectorData, List<SpawnCondition> conditions) {
        super(selectorData, conditions);
    }


    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected SpawnEntryType<EmptySpawnEntry> getType() {
        return SpawnEntryTypes.EMPTY;
    }

    @Override
    protected List<Entity> tryGenerateEntities(SpawnContext context) {
        return List.of();
    }


    public static final class Builder extends SpawnEntry.Builder<Builder> {

        private Builder() {}


        public EmptySpawnEntry build(SpawnSelector.Data selectorData) {
            return new EmptySpawnEntry(selectorData, List.copyOf(conditions));
        }

        @Override
        protected Builder getThisBuilder() {
            return this;
        }
    }
}
