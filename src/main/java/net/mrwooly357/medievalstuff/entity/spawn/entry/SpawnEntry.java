package net.mrwooly357.medievalstuff.entity.spawn.entry;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnCondition;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.rule.SpawnRule;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelector;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelectorDataHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public abstract class SpawnEntry implements SpawnSelectorDataHolder {

    public static final Codec<SpawnEntry> CODEC = SpawnEntryType.CODEC.dispatch(SpawnEntry::getType, SpawnEntryType::codec);
    protected static final BiPredicate<SpawnContext, SpawnReason> DEFAULT_COMBINED_CONDITION = (context, reason) -> true;

    protected final SpawnSelector.Data selectorData;
    protected final List<SpawnCondition> conditions;
    protected BiPredicate<SpawnContext, SpawnReason> combinedCondition = null;
    protected final SpawnReason reason;

    protected SpawnEntry(SpawnSelector.Data selectorData, List<SpawnCondition> conditions, SpawnReason reason) {
        this.selectorData = selectorData;
        this.conditions = conditions;
        this.reason = reason;
    }


    protected abstract SpawnEntryType<? extends SpawnEntry> getType();

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

    public abstract List<EntityType<?>> getEntities(ServerWorld world);

    public final List<Pair<Entity, SpawnReason>> generateEntities(SpawnContext context) {
        conditions.forEach(context::check);

        if (combinedCondition.test(context, reason))
            return tryGenerateEntities(context);
        else
            return List.of();
    }

    protected abstract List<Pair<Entity, SpawnReason>> tryGenerateEntities(SpawnContext context);

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
