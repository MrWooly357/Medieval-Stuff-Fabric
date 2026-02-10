package net.mrwooly357.medievalstuff.entity.spawn.function;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextAware;
import net.mrwooly357.medievalstuff.entity.spawn.function.condition.SpawnFunctionCondition;
import net.mrwooly357.medievalstuff.util.TriFunction;
import net.mrwooly357.medievalstuff.util.TriPredicate;

import java.util.ArrayList;
import java.util.List;

public abstract class SpawnFunction implements SpawnContextAware, TriFunction<Entity, SpawnContext, SpawnReason, Pair<Entity, SpawnReason>> {

    public static final Codec<SpawnFunction> CODEC = SpawnFunctionType.CODEC.dispatch(SpawnFunction::getType, SpawnFunctionType::codec);
    public static final TriPredicate<Entity, SpawnContext, SpawnReason> DEFAULT_COMBINED_CONDITION = (entity, context, reason) -> true;

    protected final List<SpawnFunctionCondition> conditions;
    protected final TriPredicate<Entity, SpawnContext, SpawnReason> combinedCondition;

    protected SpawnFunction(List<SpawnFunctionCondition> conditions) {
        this.conditions = conditions;

        if (!conditions.isEmpty())
            combinedCondition = SpawnFunctionCondition.combine(conditions);
        else
            combinedCondition = DEFAULT_COMBINED_CONDITION;
    }


    protected abstract SpawnFunctionType<? extends SpawnFunction> getType();

    @Override
    public final Pair<Entity, SpawnReason> apply(Entity entity, SpawnContext context, SpawnReason reason) {
        conditions.forEach(context::check);

        if (combinedCondition.test(entity, context, reason))
            return applyEffects(entity, context, reason);
        else
            return Pair.of(entity, reason);
    }

    protected abstract Pair<Entity, SpawnReason> applyEffects(Entity entity, SpawnContext context, SpawnReason reason);

    public static TriFunction<Entity, SpawnContext, SpawnReason, Pair<Entity, SpawnReason>> combine(List<SpawnFunction> functions) {
        TriFunction<Entity, SpawnContext, SpawnReason, Pair<Entity, SpawnReason>> function = functions.getFirst();
        int size = functions.size();

        for (int i = 1; i < size; i++) {
            int finalI = i;
            TriFunction<Entity, SpawnContext, SpawnReason, Pair<Entity, SpawnReason>> finalFunction = function;
            function = (entity, context, reason) -> {
                Pair<Entity, SpawnReason> pair = finalFunction.apply(entity, context, reason);

                return functions.get(finalI).apply(pair.getFirst(), context, pair.getSecond());
            };
        }

        return function;
    }


    protected abstract static class Builder<B extends Builder<B>> {

        protected final List<SpawnFunctionCondition> conditions = new ArrayList<>();

        protected Builder() {}


        public B condition(SpawnFunctionCondition condition) {
            if (!conditions.contains(condition)) {
                conditions.add(condition);

                return getThisBuilder();
            } else
                throw new IllegalArgumentException("Duplicate spawn function condition " + condition + ".");
        }

        protected abstract B getThisBuilder();
    }
}
