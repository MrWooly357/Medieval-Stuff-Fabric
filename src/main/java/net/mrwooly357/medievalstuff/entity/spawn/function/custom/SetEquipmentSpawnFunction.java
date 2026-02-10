package net.mrwooly357.medievalstuff.entity.spawn.function.custom;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.EquipmentTable;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunctionType;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunctionTypes;
import net.mrwooly357.medievalstuff.entity.spawn.function.condition.SpawnFunctionCondition;

import java.util.*;

public final class SetEquipmentSpawnFunction extends SpawnFunction {

    public static final MapCodec<SetEquipmentSpawnFunction> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    SpawnFunctionCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(function -> function.conditions),
                    EquipmentTable.CODEC.fieldOf("equipment").forGetter(function -> function.equipment)
            )
                    .apply(instance, SetEquipmentSpawnFunction::new)
    );

    private final EquipmentTable equipment;

    private SetEquipmentSpawnFunction(List<SpawnFunctionCondition> conditions, EquipmentTable equipment) {
        super(conditions);

        this.equipment = equipment;
    }


    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected SpawnFunctionType<SetEquipmentSpawnFunction> getType() {
        return SpawnFunctionTypes.SET_EQUIPMENT;
    }

    @Override
    public Pair<Entity, SpawnReason> applyEffects(Entity entity, SpawnContext context, SpawnReason reason) {
        if (entity instanceof MobEntity mob) {
            mob.setEquipmentFromTable(equipment);

            return Pair.of(entity, reason);
        } else
            throw new IllegalArgumentException("Can't set a non-mob entity's equipment!");
    }


    public static final class Builder extends SpawnFunction.Builder<Builder> {

        private final Map<EquipmentSlot, Float> dropChances = new HashMap<>();

        private Builder() {}


        public Builder dropChance(EquipmentSlot slot, float chance) {
            if (dropChances.put(slot, chance) != null)
                throw new IllegalArgumentException("Equipment slot " + slot + " already has a drop chance! Duplicate: " + chance + ".");

            return this;
        }

        public SetEquipmentSpawnFunction build(RegistryKey<LootTable> equipment) {
            return new SetEquipmentSpawnFunction(List.copyOf(conditions), new EquipmentTable(equipment, Map.copyOf(dropChances)));
        }

        @Override
        protected Builder getThisBuilder() {
            return this;
        }
    }
}
