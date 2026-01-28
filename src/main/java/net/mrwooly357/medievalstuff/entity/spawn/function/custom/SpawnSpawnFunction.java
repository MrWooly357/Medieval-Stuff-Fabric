package net.mrwooly357.medievalstuff.entity.spawn.function.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunctionType;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunctionTypes;
import net.mrwooly357.medievalstuff.entity.spawn.function.condition.SpawnFunctionCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpawnSpawnFunction extends SpawnFunction {

    public static final MapCodec<SpawnSpawnFunction> CODEC = RecordCodecBuilder.mapCodec(
            instance -> addDefaultField(instance)
                    .apply(instance, SpawnSpawnFunction::new)
    );

    protected SpawnSpawnFunction(List<SpawnFunctionCondition> conditions) {
        super(conditions);
    }


    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected SpawnFunctionType<SpawnSpawnFunction> getType() {
        return SpawnFunctionTypes.SPAWN;
    }

    @Override
    protected Entity applyEffects(Entity entity, SpawnContext context) {
        context.getWorld().spawnNewEntityAndPassengers(entity);

        return entity;
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

        public SpawnSpawnFunction build() {
            return new SpawnSpawnFunction(List.copyOf(conditions));
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
            return "SpawnSpawnFunction.Builder[conditions: " + conditions + "]";
        }
    }
}
