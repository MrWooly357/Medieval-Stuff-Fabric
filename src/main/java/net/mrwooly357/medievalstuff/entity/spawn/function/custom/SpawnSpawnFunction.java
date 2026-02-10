package net.mrwooly357.medievalstuff.entity.spawn.function.custom;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunctionType;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunctionTypes;
import net.mrwooly357.medievalstuff.entity.spawn.function.condition.SpawnFunctionCondition;

import java.util.List;

public final class SpawnSpawnFunction extends SpawnFunction {

    public static final MapCodec<SpawnSpawnFunction> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    SpawnFunctionCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(function -> function.conditions)
            )
                    .apply(instance, SpawnSpawnFunction::new)
    );

    private SpawnSpawnFunction(List<SpawnFunctionCondition> conditions) {
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
    protected Pair<Entity, SpawnReason> applyEffects(Entity entity, SpawnContext context, SpawnReason reason) {
        context.getWorld().spawnNewEntityAndPassengers(entity);

        return Pair.of(entity, reason);
    }


    public static final class Builder extends SpawnFunction.Builder<Builder> {

        private Builder() {}


        public SpawnSpawnFunction build() {
            return new SpawnSpawnFunction(List.copyOf(conditions));
        }

        @Override
        protected Builder getThisBuilder() {
            return this;
        }
    }
}
