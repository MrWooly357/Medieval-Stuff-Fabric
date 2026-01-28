package net.mrwooly357.medievalstuff.entity.spawn.condition.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.random.Random;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnCondition;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnConditionType;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnConditionTypes;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;

public class RandomChanceSpawnCondition extends SpawnCondition {

    public static final MapCodec<RandomChanceSpawnCondition> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    FloatProvider.createValidatedCodec(0.0F, 1.0F).fieldOf("chance").forGetter(condition -> condition.chance)
            )
                    .apply(instance, RandomChanceSpawnCondition::new)
    );

    protected final FloatProvider chance;

    public RandomChanceSpawnCondition(FloatProvider chance) {
        this.chance = chance;
    }


    public static RandomChanceSpawnCondition of(FloatProvider chance) {
        return new RandomChanceSpawnCondition(chance);
    }

    @Override
    protected SpawnConditionType<RandomChanceSpawnCondition> getType() {
        return SpawnConditionTypes.RANDOM_CHANCE;
    }

    @Override
    public boolean test(SpawnContext context) {
        Random random = context.getWorld().getRandom();

        return random.nextFloat() <= chance.get(random);
    }
}
