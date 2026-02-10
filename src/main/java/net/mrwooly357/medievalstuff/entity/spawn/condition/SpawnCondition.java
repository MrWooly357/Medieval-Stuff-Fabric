package net.mrwooly357.medievalstuff.entity.spawn.condition;

import com.mojang.serialization.Codec;
import net.minecraft.entity.SpawnReason;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextAware;

import java.util.List;
import java.util.function.BiPredicate;

public abstract class SpawnCondition implements SpawnContextAware, BiPredicate<SpawnContext, SpawnReason> {

    public static final Codec<SpawnCondition> CODEC = SpawnConditionType.CODEC.dispatch(SpawnCondition::getType, SpawnConditionType::codec);

    protected SpawnCondition() {}


    protected abstract SpawnConditionType<? extends SpawnCondition> getType();

    public static BiPredicate<SpawnContext, SpawnReason> combine(List<SpawnCondition> conditions) {
        BiPredicate<SpawnContext, SpawnReason> condition = conditions.getFirst();
        int size = conditions.size();

        for (int i = 1; i < size; i++)
            condition = condition.and(conditions.get(i));

        return condition;
    }
}
