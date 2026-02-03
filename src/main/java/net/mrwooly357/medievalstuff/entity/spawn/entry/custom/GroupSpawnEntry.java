package net.mrwooly357.medievalstuff.entity.spawn.entry.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnCondition;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntry;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntryType;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntryTypes;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.SpawnPosFinder;
import net.mrwooly357.medievalstuff.entity.spawn.rule.SpawnRule;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelector;

import java.util.ArrayList;
import java.util.List;

public final class GroupSpawnEntry extends LeafSpawnEntry {

    public static final MapCodec<GroupSpawnEntry> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    SpawnEntry.CODEC.listOf(2, Integer.MAX_VALUE).fieldOf("entries").forGetter(entry -> entry.entries)
            )
                    .and(addLeafFields(instance))
                    .apply(instance, GroupSpawnEntry::new)
    );

    private final List<SpawnEntry> entries;

    private GroupSpawnEntry(
            List<SpawnEntry> entries,
            SpawnSelector.Data selectorData,
            List<SpawnCondition> conditions,
            SpawnPosFinder posFinder,
            boolean ignoreVanillaRules,
            List<SpawnRule> rules,
            List<SpawnFunction> functions
    ) {
        super(selectorData, conditions, posFinder, ignoreVanillaRules, rules, functions);

        this.entries = entries;
    }


    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected SpawnEntryType<GroupSpawnEntry> getType() {
        return SpawnEntryTypes.GROUP;
    }

    @Override
    protected List<Entity> createEntities(SpawnContext context) {
        List<Entity> entities = new ArrayList<>();
        entries.forEach(entry -> entities.addAll(entry.generateEntities(context)));

        return List.copyOf(entities);
    }


    public static final class Builder extends LeafSpawnEntry.Builder<Builder> {

        private final List<SpawnEntry> entries = new ArrayList<>();

        private Builder() {}


        public Builder entry(SpawnEntry entry) {
            if (!entries.contains(entry)) {
                entries.add(entry);

                return getThisBuilder();
            } else
                throw new IllegalArgumentException("Duplicate spawn entry! Duplicate: " + entry + ".");
        }

        public GroupSpawnEntry build(SpawnSelector.Data data, SpawnPosFinder posFinder) {
            return new GroupSpawnEntry(List.copyOf(entries), data, List.copyOf(conditions), posFinder, ignoreVanillaRules, List.copyOf(rules), List.copyOf(functions));
        }

        @Override
        protected Builder getThisBuilder() {
            return this;
        }
    }
}
