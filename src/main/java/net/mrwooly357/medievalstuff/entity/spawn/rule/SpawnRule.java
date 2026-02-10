package net.mrwooly357.medievalstuff.entity.spawn.rule;

import com.mojang.serialization.Codec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.Vec3d;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextAware;
import net.mrwooly357.medievalstuff.util.TetraPredicate;

import java.util.List;

public abstract class SpawnRule implements SpawnContextAware, TetraPredicate<Vec3d, Entity, SpawnContext, SpawnReason> {

    public static final Codec<SpawnRule> CODEC = SpawnRuleType.CODEC.dispatch(SpawnRule::getType, SpawnRuleType::codec);

    protected SpawnRule() {}


    protected abstract SpawnRuleType<? extends SpawnRule> getType();

    public static TetraPredicate<Vec3d, Entity, SpawnContext, SpawnReason> combine(List<SpawnRule> rules) {
        TetraPredicate<Vec3d, Entity, SpawnContext, SpawnReason> predicate = rules.getFirst();
        int size = rules.size();

        for (int i = 1; i < size; i++)
            predicate = predicate.and(rules.get(i));

        return predicate;
    }
}
