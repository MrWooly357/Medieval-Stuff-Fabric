package net.mrwooly357.medievalstuff.entity.spawn.condition;

import com.mojang.serialization.Codec;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextAware;

import java.util.List;
import java.util.function.Predicate;

public abstract class SpawnCondition implements SpawnContextAware, Predicate<SpawnContext> {

    public static final Codec<SpawnCondition> CODEC = SpawnConditionType.CODEC.dispatch(SpawnCondition::getType, type -> type.codec);

    protected SpawnCondition() {}


    protected abstract SpawnConditionType<? extends SpawnCondition> getType();

    public static Predicate<SpawnContext> combine(List<SpawnCondition> conditions) {
        Predicate<SpawnContext> condition = conditions.getFirst();
        int size = conditions.size();

        for (int i = 1; i < size; i++)
            condition = condition.and(conditions.get(i));

        return condition;
    }
}
