package net.mrwooly357.medievalstuff.entity.spawn.function;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextAware;
import net.mrwooly357.medievalstuff.entity.spawn.function.condition.SpawnFunctionCondition;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public abstract class SpawnFunction implements SpawnContextAware, BiFunction<Entity, SpawnContext, Entity> {

    public static final Codec<SpawnFunction> CODEC = SpawnFunctionType.CODEC.dispatch(SpawnFunction::getType, type -> type.codec);
    public static final BiPredicate<Entity, SpawnContext> DEFAULT_COMBINED_CONDITION = (entity, context) -> true;

    protected final List<SpawnFunctionCondition> conditions;
    protected final BiPredicate<Entity, SpawnContext> combinedCondition;

    protected SpawnFunction(List<SpawnFunctionCondition> conditions) {
        this.conditions = conditions;

        if (!conditions.isEmpty())
            combinedCondition = SpawnFunctionCondition.combine(conditions);
        else
            combinedCondition = DEFAULT_COMBINED_CONDITION;
    }


    protected abstract SpawnFunctionType<? extends SpawnFunction> getType();

    protected static <SF extends SpawnFunction> Products.P1<RecordCodecBuilder.Mu<SF>, List<SpawnFunctionCondition>> addDefaultField(RecordCodecBuilder.Instance<SF> instance) {
        return instance.group(
                SpawnFunctionCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(function -> function.conditions)
        );
    }

    @Override
    public final Entity apply(Entity entity, SpawnContext context) {
        conditions.forEach(context::check);

        if (combinedCondition.test(entity, context))
            return applyEffects(entity, context);
        else
            return entity;
    }

    protected abstract Entity applyEffects(Entity entity, SpawnContext context);

    public static BiFunction<Entity, SpawnContext, Entity> combine(List<SpawnFunction> functions) {
        BiFunction<Entity, SpawnContext, Entity> function = functions.getFirst();
        int size = functions.size();

        for (int i = 1; i < size; i++) {
            int finalI = i;
            BiFunction<Entity, SpawnContext, Entity> finalFunction = function;
            function = (entity, context) -> functions.get(finalI).apply(finalFunction.apply(entity, context), context);
        }

        return function;
    }
}
