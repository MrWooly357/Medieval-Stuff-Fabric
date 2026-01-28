package net.mrwooly357.medievalstuff.entity.spawn.rule;

import com.mojang.serialization.Codec;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextAware;
import net.mrwooly357.medievalstuff.util.TriPredicate;

import java.util.List;

public abstract class SpawnRule implements SpawnContextAware, TriPredicate<Vec3d, Entity, SpawnContext> {

    public static final Codec<SpawnRule> CODEC = SpawnRuleType.CODEC.dispatch(SpawnRule::getType, type -> type.codec);

    protected SpawnRule() {}


    protected abstract SpawnRuleType<? extends SpawnRule> getType();

    public static TriPredicate<Vec3d, Entity, SpawnContext> combine(List<SpawnRule> rules) {
        TriPredicate<Vec3d, Entity, SpawnContext> predicate = rules.getFirst();
        int size = rules.size();

        for (int i = 1; i < size; i++)
            predicate = predicate.and(rules.get(i));

        return predicate;
    }
}
