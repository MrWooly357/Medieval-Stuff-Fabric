package net.mrwooly357.medievalstuff.entity.spawn.entry.custom;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.mrwooly357.medievalstuff.entity.spawn.SpawnTable;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnCondition;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntryType;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntryTypes;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.SpawnPosFinder;
import net.mrwooly357.medievalstuff.entity.spawn.rule.SpawnRule;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelector;
import net.mrwooly357.medievalstuff.registry.MSRegistryKeys;
import net.mrwooly357.medievalstuff.util.MSUtil;

import java.util.List;

public final class TableSpawnEntry extends LeafSpawnEntry {

    public static final MapCodec<TableSpawnEntry> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    SpawnSelector.Data.CODEC.fieldOf("selector_data").forGetter(entry -> entry.selectorData),
                    SpawnCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions),
                    MSUtil.SPAWN_REASON_CODEC.fieldOf("reason").forGetter(entry -> entry.reason),
                    SpawnPosFinder.CODEC.fieldOf("pos_finder").forGetter(entry -> entry.posFinder),
                    Codec.BOOL.optionalFieldOf("ignore_vanilla_rules", false).forGetter(entry -> entry.ignoreVanillaRules),
                    SpawnRule.CODEC.listOf().optionalFieldOf("rules", List.of()).forGetter(entry -> entry.rules),
                    SpawnFunction.CODEC.listOf().optionalFieldOf("functions", List.of()).forGetter(entry -> entry.functions),
                    RegistryKey.createCodec(MSRegistryKeys.SPAWN_TABLE).fieldOf("table").forGetter(entry -> entry.table)
            )
                    .apply(instance, TableSpawnEntry::new)
    );

    private final RegistryKey<SpawnTable> table;

    private TableSpawnEntry(
            SpawnSelector.Data selectorData,
            List<SpawnCondition> conditions,
            SpawnReason reason,
            SpawnPosFinder posFinder,
            boolean ignoreVanillaRules,
            List<SpawnRule> rules,
            List<SpawnFunction> functions,
            RegistryKey<SpawnTable> table
    ) {
        super(selectorData, conditions, reason, posFinder, ignoreVanillaRules, rules, functions);

        this.table = table;
    }


    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected SpawnEntryType<TableSpawnEntry> getType() {
        return SpawnEntryTypes.TABLE;
    }

    @Override
    protected List<Pair<Entity, SpawnReason>> createEntities(SpawnContext context) {
        return context.getWorld().getRegistryManager().getWrapperOrThrow(MSRegistryKeys.SPAWN_TABLE).getOrThrow(table).value().generateEntities(context);
    }

    @Override
    public List<EntityType<?>> getEntities(ServerWorld world) {
        return world.getRegistryManager()
                .getWrapperOrThrow(MSRegistryKeys.SPAWN_TABLE)
                .getOrThrow(table)
                .value()
                .getEntities(world);
    }


    public static final class Builder extends LeafSpawnEntry.Builder<Builder> {

        private Builder() {}


        public TableSpawnEntry build(SpawnSelector.Data selectorData, SpawnPosFinder posFinder, SpawnReason reason, RegistryKey<SpawnTable> table) {
            return new TableSpawnEntry(selectorData, List.copyOf(conditions), reason, posFinder, ignoreVanillaRules, List.copyOf(rules), List.copyOf(functions), table);
        }

        @Override
        protected Builder getThisBuilder() {
            return this;
        }
    }
}
