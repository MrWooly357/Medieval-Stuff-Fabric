package net.mrwooly357.medievalstuff.entity.spawn.function.condition;

import com.mojang.serialization.Codec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextAware;
import net.mrwooly357.medievalstuff.util.TriPredicate;

import java.util.List;

public abstract class SpawnFunctionCondition implements SpawnContextAware, TriPredicate<Entity, SpawnContext, SpawnReason> {

    public static final Codec<SpawnFunctionCondition> CODEC = SpawnFunctionConditionType.CODEC.dispatch(SpawnFunctionCondition::getType, type -> type.codec);

    protected SpawnFunctionCondition() {}


    protected abstract SpawnFunctionConditionType<? extends SpawnFunctionCondition> getType();

    public static TriPredicate<Entity, SpawnContext, SpawnReason> combine(List<SpawnFunctionCondition> conditions) {
        TriPredicate<Entity, SpawnContext, SpawnReason> condition = conditions.getFirst();
        int size = conditions.size();

        for (int i = 1; i < size; i++)
            condition = condition.and(conditions.get(i));

        return condition;
    }
}
