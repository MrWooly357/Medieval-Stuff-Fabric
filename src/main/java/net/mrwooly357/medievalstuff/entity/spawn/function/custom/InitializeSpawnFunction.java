package net.mrwooly357.medievalstuff.entity.spawn.function.custom;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunctionType;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunctionTypes;
import net.mrwooly357.medievalstuff.entity.spawn.function.condition.SpawnFunctionCondition;

import java.util.List;

public final class InitializeSpawnFunction extends SpawnFunction {

    public static final MapCodec<InitializeSpawnFunction> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    SpawnFunctionCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(function -> function.conditions)
            )
                    .apply(instance, InitializeSpawnFunction::new)
    );

    private InitializeSpawnFunction(List<SpawnFunctionCondition> conditions) {
        super(conditions);
    }


    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected SpawnFunctionType<InitializeSpawnFunction> getType() {
        return SpawnFunctionTypes.INITIALIZE;
    }

    @Override
    protected Pair<Entity, SpawnReason> applyEffects(Entity entity, SpawnContext context, SpawnReason reason) {
        if (entity instanceof MobEntity mob) {
            World world = entity.getWorld();
            mob.initialize((ServerWorld) world, world.getLocalDifficulty(entity.getBlockPos()), reason, null);

            return Pair.of(entity, reason);
        } else
            throw new IllegalArgumentException("Can't initialize a non-mob entity!");
    }


    public static final class Builder extends SpawnFunction.Builder<Builder> {

        private Builder() {}


        public InitializeSpawnFunction build() {
            return new InitializeSpawnFunction(List.copyOf(conditions));
        }

        @Override
        protected Builder getThisBuilder() {
            return this;
        }
    }
}
