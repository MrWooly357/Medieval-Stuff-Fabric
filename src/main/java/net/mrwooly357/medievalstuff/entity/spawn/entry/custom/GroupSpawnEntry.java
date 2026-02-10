package net.mrwooly357.medievalstuff.entity.spawn.entry.custom;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
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
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.SpawnPosFinder;
import net.mrwooly357.medievalstuff.entity.spawn.rule.SpawnRule;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelector;
import net.mrwooly357.medievalstuff.util.MSUtil;

import java.util.ArrayList;
import java.util.List;

public final class GroupSpawnEntry extends LeafSpawnEntry {

    public static final MapCodec<GroupSpawnEntry> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    SpawnSelector.Data.CODEC.fieldOf("selector_data").forGetter(entry -> entry.selectorData),
                    SpawnCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions),
                    MSUtil.SPAWN_REASON_CODEC.fieldOf("reason").forGetter(entry -> entry.reason),
                    SpawnPosFinder.CODEC.fieldOf("pos_finder").forGetter(entry -> entry.posFinder),
                    Codec.BOOL.optionalFieldOf("ignore_vanilla_rules", false).forGetter(entry -> entry.ignoreVanillaRules),
                    SpawnRule.CODEC.listOf().optionalFieldOf("rules", List.of()).forGetter(entry -> entry.rules),
                    SpawnFunction.CODEC.listOf().optionalFieldOf("functions", List.of()).forGetter(entry -> entry.functions),
                    SpawnEntry.CODEC.listOf(2, Integer.MAX_VALUE).fieldOf("entries").forGetter(entry -> entry.entries)
            )
                    .apply(instance, GroupSpawnEntry::new)
    );

    private final List<SpawnEntry> entries;

    private GroupSpawnEntry(
            SpawnSelector.Data selectorData,
            List<SpawnCondition> conditions,
            SpawnReason reason,
            SpawnPosFinder posFinder,
            boolean ignoreVanillaRules,
            List<SpawnRule> rules,
            List<SpawnFunction> functions,
            List<SpawnEntry> entries
    ) {
        super(selectorData, conditions, reason, posFinder, ignoreVanillaRules, rules, functions);

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
    protected List<Pair<Entity, SpawnReason>> createEntities(SpawnContext context) {
        List<Pair<Entity, SpawnReason>> entities = new ArrayList<>();
        entries.forEach(entry -> entities.addAll(entry.generateEntities(context)));

        return entities;
    }

    @Override
    public List<EntityType<?>> getEntities(ServerWorld world) {
        List<EntityType<?>> entities = new ArrayList<>();
        entries.forEach(entry -> entities.addAll(entry.getEntities(world)));

        return entities;
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

        public GroupSpawnEntry build(SpawnSelector.Data selectorData, SpawnPosFinder posFinder, SpawnReason reason) {
            return new GroupSpawnEntry(selectorData, List.copyOf(conditions), reason, posFinder, ignoreVanillaRules, List.copyOf(rules), List.copyOf(functions), List.copyOf(entries));
        }

        @Override
        protected Builder getThisBuilder() {
            return this;
        }
    }
}
