package net.mrwooly357.medievalstuff.entity.spawn;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnCondition;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntry;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.rule.SpawnRule;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelector;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelectorDataHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class SpawnPool implements SpawnSelectorDataHolder {

    public static final Codec<SpawnPool> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    SpawnSelector.Data.CODEC.fieldOf("selector_data").forGetter(pool -> pool.selectorData),
                    SpawnSelector.CODEC.fieldOf("selector").forGetter(pool -> pool.selector),
                    SpawnCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(pool -> pool.conditions),
                    SpawnRule.CODEC.listOf().optionalFieldOf("rules", List.of()).forGetter(pool -> pool.rules),
                    SpawnEntry.CODEC.listOf().optionalFieldOf("entries", List.of()).forGetter(pool -> pool.entries),
                    SpawnFunction.CODEC.listOf().optionalFieldOf("functions", List.of()).forGetter(pool -> pool.functions)
            )
                    .apply(instance, SpawnPool::new)
    );

    private final SpawnSelector.Data selectorData;
    private final SpawnSelector selector;
    private final List<SpawnCondition> conditions;
    private final List<SpawnRule> rules;
    private final List<SpawnEntry> entries;
    private final List<SpawnFunction> functions;

    public SpawnPool(SpawnSelector.Data selectorData, SpawnSelector selector, List<SpawnCondition> conditions, List<SpawnRule> rules, List<SpawnEntry> entries, List<SpawnFunction> functions) {
        this.selectorData = selectorData;
        this.selector = selector;
        this.conditions = conditions;
        this.rules = rules;
        this.entries = entries;
        this.functions = functions;
    }


    public static Builder builder() {
        return new Builder();
    }

    void setEntryCombinedData(List<SpawnCondition> tableConditions, List<SpawnRule> tableRules, List<SpawnFunction> tableFunctions) {
        entries.forEach(entry -> {
            entry.setCombinedCondition(conditions, tableConditions);
            entry.setCombinedRule(rules, tableRules);
            entry.setCombinedFunction(functions, tableFunctions);
        });
    }

    List<Entity> generateEntities(SpawnContext context) {
        conditions.forEach(context::check);
        rules.forEach(context::check);
        functions.forEach(context::check);
        List<Entity> entities = new ArrayList<>();
        selector.select(entries, context).forEach(entry -> entities.addAll(entry.generateEntities(context)));

        return List.copyOf(entities);
    }

    @Override
    public SpawnSelector.Data getSpawnSelectorData() {
        return selectorData;
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object) || (object instanceof SpawnPool pool
                && selectorData.equals(pool.selectorData)
                && selector.equals(pool.selector)
                && conditions.equals(pool.conditions)
                && rules.equals(pool.rules)
                && entries.equals(pool.entries)
                && functions.equals(pool.functions));
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectorData, selector, conditions, rules, entries, functions);
    }

    @Override
    public String toString() {
        return "SpawnPool[selector_data: " + selectorData
                + ", selector: " + selector
                + ", conditions: " + conditions
                + ", rules: " + rules
                + ", entries: " + entries
                + ", functions: " + functions + "]";
    }


    public static final class Builder {

        private final List<SpawnCondition> conditions = new ArrayList<>();
        private final List<SpawnRule> rules = new ArrayList<>();
        private final List<SpawnEntry> entries = new ArrayList<>();
        private final List<SpawnFunction> functions = new ArrayList<>();

        private Builder() {}


        public Builder condition(SpawnCondition condition) {
            if (!conditions.contains(condition)) {
                conditions.add(condition);

                return this;
            } else
                throw new IllegalArgumentException("Duplicate SpawnCondition! Duplicate: " + condition + ".");
        }

        public Builder rule(SpawnRule rule) {
            if (!rules.contains(rule)) {
                rules.add(rule);

                return this;
            } else
                throw new IllegalArgumentException("Duplicate SpawnRule! Duplicate: " + rule + ".");
        }

        public Builder entry(SpawnEntry entry) {
            if (!entries.contains(entry)) {
                entries.add(entry);

                return this;
            } else
                throw new IllegalArgumentException("Duplicate SpawnEntry! Duplicate: " + entry + ".");
        }

        public Builder function(SpawnFunction function) {
            if (!functions.contains(function)) {
                functions.add(function);

                return this;
            } else
                throw new IllegalArgumentException("Duplicate SpawnFunction! Duplicate: " + function + ".");
        }

        public SpawnPool build(SpawnSelector.Data selectorData, SpawnSelector selector) {
            return new SpawnPool(selectorData, selector, List.copyOf(conditions), List.copyOf(rules), List.copyOf(entries), List.copyOf(functions));
        }

        @Override
        public boolean equals(Object object) {
            return super.equals(object) || (object instanceof Builder builder
                    && conditions.equals(builder.conditions)
                    && rules.equals(builder.rules)
                    && entries.equals(builder.entries)
                    && functions.equals(builder.functions));
        }

        @Override
        public int hashCode() {
            return Objects.hash(conditions, rules, entries, functions);
        }

        @Override
        public String toString() {
            return "SpawnPool.Builder[conditions: " + conditions
                    + ", rules: " + rules
                    + ", entries: " + entries
                    + ", functions: " + functions + "]";
        }
    }
}
