package net.mrwooly357.medievalstuff.entity.spawn.entry;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnCondition;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.rule.SpawnRule;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelector;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelectorDataHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class SpawnEntry implements SpawnSelectorDataHolder {

    public static final Codec<SpawnEntry> CODEC = SpawnEntryType.CODEC.dispatch(SpawnEntry::getType, type -> type.codec);
    protected static final Predicate<SpawnContext> DEFAULT_COMBINED_CONDITION = context -> true;

    protected final SpawnSelector.Data selectorData;
    public final List<SpawnCondition> conditions;
    protected Predicate<SpawnContext> combinedCondition = null;

    protected SpawnEntry(SpawnSelector.Data selectorData, List<SpawnCondition> conditions) {
        this.selectorData = selectorData;
        this.conditions = conditions;
    }


    protected abstract SpawnEntryType<? extends SpawnEntry> getType();

    protected static <SE extends SpawnEntry> Products.P2<RecordCodecBuilder.Mu<SE>, SpawnSelector.Data, List<SpawnCondition>> addDefaultFields(RecordCodecBuilder.Instance<SE> instance) {
        return instance.group(
                SpawnSelector.Data.CODEC.fieldOf("selector_data").forGetter(entry -> entry.selectorData),
                SpawnCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions)
        );
    }

    protected SpawnSelector.Data getSelectorData() {
        return selectorData;
    }

    protected List<SpawnCondition> getConditions() {
        return conditions;
    }

    public void setCombinedCondition(List<SpawnCondition> poolConditions, List<SpawnCondition> tableConditions) {
        List<SpawnCondition> conditions1 = new ArrayList<>();

        if (!poolConditions.isEmpty())
            conditions1.addAll(poolConditions);

        if (!tableConditions.isEmpty())
            conditions1.addAll(tableConditions);

        if (!conditions.isEmpty())
            conditions1.addAll(conditions);

        if (!conditions1.isEmpty())
            combinedCondition = SpawnCondition.combine(conditions1);
        else
            combinedCondition = DEFAULT_COMBINED_CONDITION;
    }

    public void setCombinedRule(List<SpawnRule> poolRules, List<SpawnRule> tableRules) {}

    public void setCombinedFunction(List<SpawnFunction> poolFunctions, List<SpawnFunction> tableFunctions) {}

    public final List<Entity> generateEntities(SpawnContext context) {
        conditions.forEach(context::check);

        if (combinedCondition.test(context))
            return tryGenerateEntities(context);
        else
            return List.of();
    }

    protected abstract List<Entity> tryGenerateEntities(SpawnContext context);

    @Override
    public final SpawnSelector.Data getSpawnSelectorData() {
        return selectorData;
    }


    protected abstract static class Builder<B extends Builder<B>> {

        protected final List<SpawnCondition> conditions = new ArrayList<>();

        protected Builder() {}


        public B condition(SpawnCondition condition) {
            if (!conditions.contains(condition)) {
                conditions.add(condition);

                return getThisBuilder();
            } else
                throw new IllegalArgumentException("Duplicate spawn condition! Duplicate: " + condition + ".");
        }

        protected abstract B getThisBuilder();
    }
}
