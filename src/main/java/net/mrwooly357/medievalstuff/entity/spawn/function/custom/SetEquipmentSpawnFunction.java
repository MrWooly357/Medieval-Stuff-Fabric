package net.mrwooly357.medievalstuff.entity.spawn.function.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.EquipmentTable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunctionType;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunctionTypes;
import net.mrwooly357.medievalstuff.entity.spawn.function.condition.SpawnFunctionCondition;

import java.util.*;

public class SetEquipmentSpawnFunction extends SpawnFunction {

    public static final MapCodec<SetEquipmentSpawnFunction> CODEC = RecordCodecBuilder.mapCodec(
            instance -> addDefaultField(instance)
                    .and(EquipmentTable.CODEC.fieldOf("equipment").forGetter(function -> function.equipment))
                    .apply(instance, SetEquipmentSpawnFunction::new)
    );

    private final EquipmentTable equipment;

    public SetEquipmentSpawnFunction(List<SpawnFunctionCondition> conditions, EquipmentTable equipment) {
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
    public Entity applyEffects(Entity entity, SpawnContext context) {
        if (entity instanceof MobEntity mob) {
            mob.setEquipmentFromTable(equipment);

            return entity;
        } else
            throw new IllegalArgumentException("Can't set a non-mob entity's equipment!");
    }


    public static final class Builder {

        private final List<SpawnFunctionCondition> conditions = new ArrayList<>();
        private final Map<EquipmentSlot, Float> dropChances = new HashMap<>();

        private Builder() {}


        public Builder condition(SpawnFunctionCondition condition) {
            if (!conditions.contains(condition)) {
                conditions.add(condition);

                return this;
            } else
                throw new IllegalArgumentException("Duplicate spawn function condition! Duplicate: " + condition + ".");
        }

        public Builder dropChance(EquipmentSlot slot, float chance) {
            if (dropChances.put(slot, chance) != null)
                throw new IllegalArgumentException("EquipmentSlot " + slot + " already has a drop chance! Duplicate: " + chance + ".");

            return this;
        }

        public SetEquipmentSpawnFunction build(RegistryKey<LootTable> equipment) {
            return new SetEquipmentSpawnFunction(List.copyOf(conditions), new EquipmentTable(equipment, Map.copyOf(dropChances)));
        }

        @Override
        public boolean equals(Object object) {
            return super.equals(object) || (object instanceof Builder builder
                    && dropChances.equals(builder.dropChances));
        }

        @Override
        public int hashCode() {
            return Objects.hash(dropChances);
        }

        @Override
        public String toString() {
            return "SetEquipmentSpawnFunction.Builder[conditions: " + conditions
                    + ", drop_chances: " + dropChances + "]";
        }
    }
}
