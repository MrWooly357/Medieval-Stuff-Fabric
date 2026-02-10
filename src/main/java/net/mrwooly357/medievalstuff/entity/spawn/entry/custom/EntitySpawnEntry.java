package net.mrwooly357.medievalstuff.entity.spawn.entry.custom;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.intprovider.IntProvider;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnCondition;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntryType;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntryTypes;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.SpawnPosFinder;
import net.mrwooly357.medievalstuff.entity.spawn.rule.SpawnRule;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelector;
import net.mrwooly357.medievalstuff.util.MSUtil;

import java.util.ArrayList;
import java.util.List;

public final class EntitySpawnEntry extends LeafSpawnEntry {

    public static final MapCodec<EntitySpawnEntry> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    SpawnSelector.Data.CODEC.fieldOf("selector_data").forGetter(entry -> entry.selectorData),
                    SpawnCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions),
                    MSUtil.SPAWN_REASON_CODEC.fieldOf("reason").forGetter(entry -> entry.reason),
                    SpawnPosFinder.CODEC.fieldOf("pos_finder").forGetter(entry -> entry.posFinder),
                    Codec.BOOL.optionalFieldOf("ignore_vanilla_rules", false).forGetter(entry -> entry.ignoreVanillaRules),
                    SpawnRule.CODEC.listOf().optionalFieldOf("rules", List.of()).forGetter(entry -> entry.rules),
                    SpawnFunction.CODEC.listOf().optionalFieldOf("functions", List.of()).forGetter(entry -> entry.functions),
                    Registries.ENTITY_TYPE.getCodec().fieldOf("entity").forGetter(entry -> entry.entity),
                    IntProvider.NON_NEGATIVE_CODEC.fieldOf("count").forGetter(entry -> entry.count)
            )
                    .apply(instance, EntitySpawnEntry::new)
    );

    private final EntityType<?> entity;
    private final IntProvider count;

    private EntitySpawnEntry(
            SpawnSelector.Data selectorData,
            List<SpawnCondition> conditions,
            SpawnReason reason,
            SpawnPosFinder posFinder,
            boolean ignoreVanillaRules,
            List<SpawnRule> rules,
            List<SpawnFunction> functions,
            EntityType<?> entity,
            IntProvider count
    ) {
        super(selectorData, conditions, reason, posFinder, ignoreVanillaRules, rules, functions);

        this.entity = entity;
        this.count = count;
    }


    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected SpawnEntryType<EntitySpawnEntry> getType() {
        return SpawnEntryTypes.ENTITY;
    }

    @Override
    protected List<Pair<Entity, SpawnReason>> createEntities(SpawnContext context) {
        List<Pair<Entity, SpawnReason>> entities = new ArrayList<>();
        ServerWorld world = context.getWorld();
        int amount = count.get(world.getRandom());

        for (int i = 0; i < amount; i++)
            entities.add(Pair.of(entity.create(world), reason));

        return List.copyOf(entities);
    }

    @Override
    public List<EntityType<?>> getEntities(ServerWorld world) {
        return List.of(entity);
    }


    public static final class Builder extends LeafSpawnEntry.Builder<Builder> {

        private Builder() {}


        public EntitySpawnEntry build(SpawnSelector.Data selectorData, SpawnPosFinder posFinder, SpawnReason reason, EntityType<?> entity, IntProvider count) {
            return new EntitySpawnEntry(selectorData, List.copyOf(conditions), reason, posFinder, ignoreVanillaRules, List.copyOf(rules), List.copyOf(functions), entity, count);
        }

        @Override
        protected Builder getThisBuilder() {
            return this;
        }
    }
}
