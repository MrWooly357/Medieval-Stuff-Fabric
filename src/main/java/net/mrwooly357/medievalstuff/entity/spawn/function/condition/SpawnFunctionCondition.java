package net.mrwooly357.medievalstuff.entity.spawn.function.condition;

import com.mojang.serialization.Codec;
import net.minecraft.entity.Entity;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextAware;

import java.util.List;
import java.util.function.BiPredicate;

public abstract class SpawnFunctionCondition implements SpawnContextAware, BiPredicate<Entity, SpawnContext> {

    public static final Codec<SpawnFunctionCondition> CODEC = SpawnFunctionConditionType.CODEC.dispatch(SpawnFunctionCondition::getType, type -> type.codec);

    protected SpawnFunctionCondition() {}


    protected abstract SpawnFunctionConditionType<? extends SpawnFunctionCondition> getType();

    public static BiPredicate<Entity, SpawnContext> combine(List<SpawnFunctionCondition> conditions) {
        BiPredicate<Entity, SpawnContext> condition = conditions.getFirst();
        int size = conditions.size();

        for (int i = 1; i < size; i++)
            condition = condition.and(conditions.get(i));

        return condition;
    }
}
