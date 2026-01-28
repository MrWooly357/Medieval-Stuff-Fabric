package net.mrwooly357.medievalstuff.entity.spawn;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnCondition;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextType;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.rule.SpawnRule;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class SpawnTable {

    public static final Codec<SpawnTable> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    SpawnContextType.CODEC.fieldOf("context_type").forGetter(table -> table.contextType),
                    SpawnSelector.CODEC.fieldOf("selector").forGetter(table -> table.selector),
                    SpawnCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(table -> table.conditions),
                    SpawnRule.CODEC.listOf().optionalFieldOf("rules", List.of()).forGetter(table -> table.rules),
                    SpawnPool.CODEC.listOf(1, Integer.MAX_VALUE).fieldOf("pools").forGetter(table -> table.pools),
                    SpawnFunction.CODEC.listOf().optionalFieldOf("functions", List.of()).forGetter(table -> table.functions)
            )
                    .apply(instance, SpawnTable::new)
    );

    private final SpawnContextType contextType;
    private final SpawnSelector selector;
    private final List<SpawnCondition> conditions;
    private final List<SpawnRule> rules;
    private final List<SpawnPool> pools;
    private final List<SpawnFunction> functions;

    private SpawnTable(SpawnContextType contextType, SpawnSelector selector, List<SpawnCondition> conditions, List<SpawnRule> rules, List<SpawnPool> pools, List<SpawnFunction> functions) {
        this.contextType = contextType;
        this.selector = selector;
        this.conditions = conditions;
        this.rules = rules;
        this.pools = pools;
        this.functions = functions;
        this.pools.forEach(pool -> pool.setEntryCombinedData(conditions, rules, functions));
    }


    public static Builder builder() {
        return new Builder();
    }

    public List<Entity> generateEntities(SpawnContext context) {
        SpawnContextType contextType1 = context.getType();

        if (!contextType.equals(contextType1))
            throw new IllegalArgumentException("Given spawn context type isn't allowed in this spawn table! Allowed: " + contextType + ", given: " + contextType1 + ".");

        conditions.forEach(context::check);
        rules.forEach(context::check);
        functions.forEach(context::check);
        List<Entity> entities = new ArrayList<>();
        selector.select(pools).forEach(pool -> entities.addAll(pool.generateEntities(context)));

        return List.copyOf(entities);
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object) || (object instanceof SpawnTable table
                && contextType.equals(table.contextType)
                && selector.equals(table.selector)
                && conditions.equals(table.conditions)
                && rules.equals(table.rules)
                && pools.equals(table.pools)
                && functions.equals(table.functions));
    }

    @Override
    public int hashCode() {
        return Objects.hash(contextType, selector, conditions, rules, pools, functions);
    }

    @Override
    public String toString() {
        return "SpawnWave[context_type: " + contextType
                + ", selector: " + selector
                + ", conditions: " + conditions
                + ", rules: " + rules
                + ", pools: " + pools
                + ", functions: " + functions + "]";
    }


    public static final class Builder {

        private final List<SpawnCondition> conditions = new ArrayList<>();
        private final List<SpawnRule> rules = new ArrayList<>();
        private final List<SpawnPool> pools = new ArrayList<>();
        private final List<SpawnFunction> functions = new ArrayList<>();

        private Builder() {}


        public Builder condition(SpawnCondition condition) {
            if (!conditions.contains(condition)) {
                conditions.add(condition);

                return this;
            } else
                throw new IllegalArgumentException("Duplicate SpawnCondition! Duplicate: " + condition);
        }

        public Builder rule(SpawnRule rule) {
            if (!rules.contains(rule)) {
                rules.add(rule);

                return this;
            } else
                throw new IllegalArgumentException("Duplicate SpawnRule! Duplicate: " + rule);
        }

        public Builder pool(SpawnPool pool) {
            if (!pools.contains(pool)) {
                pools.add(pool);

                return this;
            } else
                throw new IllegalArgumentException("Duplicate SpawnPool! Duplicate: " + pool);
        }

        public Builder function(SpawnFunction function) {
            if (!functions.contains(function)) {
                functions.add(function);

                return this;
            } else
                throw new IllegalArgumentException("Duplicate SpawnFunction! Duplicate: " + function);
        }

        public SpawnTable build(SpawnContextType contextType, SpawnSelector selector) {
            return new SpawnTable(contextType, selector, List.copyOf(conditions), List.copyOf(rules), List.copyOf(pools), List.copyOf(functions));
        }

        @Override
        public boolean equals(Object object) {
            return super.equals(object) || (object instanceof Builder builder
                    && conditions.equals(builder.conditions)
                    && rules.equals(builder.rules)
                    && pools.equals(builder.pools)
                    && functions.equals(builder.functions));
        }

        @Override
        public int hashCode() {
            return Objects.hash(conditions, rules, pools, functions);
        }

        @Override
        public String toString() {
            return "SpawnTable.Builder[conditions: " + conditions
                    + ", rules: " + rules
                    + ", pools: " + pools
                    + ", functions: " + functions + "]";
        }
    }
}
