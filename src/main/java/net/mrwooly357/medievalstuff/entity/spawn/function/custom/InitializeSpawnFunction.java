package net.mrwooly357.medievalstuff.entity.spawn.function.custom;

import com.mojang.serialization.Codec;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class InitializeSpawnFunction extends SpawnFunction {

    public static final MapCodec<InitializeSpawnFunction> CODEC = RecordCodecBuilder.mapCodec(
            instance -> addDefaultField(instance)
                    .and(Codec.STRING.fieldOf("spawn_reason").xmap(s -> SpawnReason.valueOf(s.toUpperCase(Locale.ROOT)), Enum::name).forGetter(function -> function.spawnReason))
                    .apply(instance, InitializeSpawnFunction::new)

    );

    private final SpawnReason spawnReason;

    protected InitializeSpawnFunction(List<SpawnFunctionCondition> conditions, SpawnReason spawnReason) {
        super(conditions);

        this.spawnReason = spawnReason;
    }


    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected SpawnFunctionType<InitializeSpawnFunction> getType() {
        return SpawnFunctionTypes.INITIALIZE;
    }

    @Override
    public Entity applyEffects(Entity entity, SpawnContext context) {
        if (entity instanceof MobEntity mob) {
            World world = entity.getWorld();
            mob.initialize((ServerWorld) world, world.getLocalDifficulty(entity.getBlockPos()), spawnReason, null);

            return entity;
        } else
            throw new IllegalArgumentException("Can't initialize a non-mob entity!");
    }


    public static final class Builder {

        private final List<SpawnFunctionCondition> conditions = new ArrayList<>();

        private Builder() {}


        public Builder condition(SpawnFunctionCondition condition) {
            if (!conditions.contains(condition)) {
                conditions.add(condition);

                return this;
            } else
                throw new IllegalArgumentException("Duplicate spawn function condition! Duplicate: " + condition + ".");
        }

        public InitializeSpawnFunction build(SpawnReason spawnReason) {
            return new InitializeSpawnFunction(List.copyOf(conditions), spawnReason);
        }

        @Override
        public boolean equals(Object object) {
            return super.equals(object) || (object instanceof Builder builder
                    && conditions.equals(builder.conditions));
        }

        @Override
        public int hashCode() {
            return Objects.hash(conditions);
        }

        @Override
        public String toString() {
            return "InitializeSpawnFunction.Builder[conditions: " + conditions + "]";
        }
    }
}
