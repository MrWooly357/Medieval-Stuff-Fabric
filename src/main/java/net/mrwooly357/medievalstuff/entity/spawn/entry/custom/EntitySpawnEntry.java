package net.mrwooly357.medievalstuff.entity.spawn.entry.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.intprovider.IntProvider;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnCondition;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntryType;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntryTypes;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.SpawnPosFinder;
import net.mrwooly357.medievalstuff.entity.spawn.rule.SpawnRule;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EntitySpawnEntry extends LeafSpawnEntry {

    public static final MapCodec<EntitySpawnEntry> CODEC = RecordCodecBuilder.mapCodec(
            instance -> addLeafFields(instance)
                    .and(instance.group(
                            Registries.ENTITY_TYPE.getCodec().fieldOf("entity").forGetter(entry -> entry.entity),
                            IntProvider.NON_NEGATIVE_CODEC.fieldOf("count").forGetter(entry -> entry.count)
                    ))
                    .apply(instance, EntitySpawnEntry::new)
    );

    protected final EntityType<?> entity;
    protected final IntProvider count;

    protected EntitySpawnEntry(
            SpawnSelector.Data selectorData,
            List<SpawnCondition> conditions,
            SpawnPosFinder posFinder,
            boolean ignoreVanillaRules,
            List<SpawnRule> rules,
            List<SpawnFunction> functions,
            EntityType<?> entity,
            IntProvider count
    ) {
        super(selectorData, conditions, posFinder, ignoreVanillaRules, rules, functions);

        this.entity = entity;
        this.count = count;
    }


    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected SpawnEntryType<EntitySpawnEntry> getType() {
        return SpawnEntryTypes.ENTITY;
    }

    @Override
    protected List<Entity> createEntities(SpawnContext context) {
        List<Entity> entities = new ArrayList<>();
        ServerWorld world = context.getWorld();
        int amount = count.get(world.getRandom());

        for (int i = 0; i < amount; i++)
            entities.add(entity.create(world));

        return List.copyOf(entities);
    }


    public static final class Builder {

        private final List<SpawnCondition> conditions = new ArrayList<>();
        private boolean ignoreVanillaSpawnRules = false;
        private final List<SpawnRule> rules = new ArrayList<>();
        private final List<SpawnFunction> functions = new ArrayList<>();

        private Builder() {}


        public Builder condition(SpawnCondition condition) {
            if (!conditions.contains(condition)) {
                conditions.add(condition);

                return this;
            } else
                throw new IllegalArgumentException("Duplicate spawn condition! Duplicate: " + condition + ".");
        }

        public Builder ignoreVanillaSpawnRules() {
            if (!ignoreVanillaSpawnRules) {
                ignoreVanillaSpawnRules = true;

                return this;
            } else
                throw new UnsupportedOperationException("Ignore vanilla spawn rules is already set to true!");
        }

        public Builder rule(SpawnRule rule) {
            if (!rules.contains(rule)) {
                rules.add(rule);

                return this;
            } else
                throw new IllegalArgumentException("Duplicate spawn rule! Duplicate: " + rule + ".");
        }

        public Builder function(SpawnFunction function) {
            if (!functions.contains(function)) {
                functions.add(function);

                return this;
            } else
                throw new IllegalArgumentException("Duplicate spawn function! Duplicate: " + function + ".");
        }

        public EntitySpawnEntry build(SpawnSelector.Data selectorData, SpawnPosFinder posFinder, EntityType<?> entity, IntProvider count) {
            return new EntitySpawnEntry(selectorData, List.copyOf(conditions), posFinder, ignoreVanillaSpawnRules, List.copyOf(rules), List.copyOf(functions), entity, count);
        }

        @Override
        public boolean equals(Object object) {
            return super.equals(object) || (object instanceof Builder builder
                    && conditions.equals(builder.conditions)
                    && ignoreVanillaSpawnRules == builder.ignoreVanillaSpawnRules
                    && rules.equals(builder.rules)
                    && functions.equals(builder.functions));
        }

        @Override
        public int hashCode() {
            return Objects.hash(conditions, ignoreVanillaSpawnRules, rules, functions);
        }

        @Override
        public String toString() {
            return "EntitySpawnEntry.Builder[conditions: " + conditions
                    + ", ignore_vanilla_spawn_rules: " + ignoreVanillaSpawnRules
                    + ", rules: " + rules
                    + ", functions: " + functions + "]";
        }
    }
}
