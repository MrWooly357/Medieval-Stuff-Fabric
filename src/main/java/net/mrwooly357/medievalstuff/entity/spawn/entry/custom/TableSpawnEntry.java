package net.mrwooly357.medievalstuff.entity.spawn.entry.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
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

import java.util.List;

public final class TableSpawnEntry extends LeafSpawnEntry {

    public static final MapCodec<TableSpawnEntry> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    RegistryKey.createCodec(MSRegistryKeys.SPAWN_TABLE).fieldOf("table").forGetter(entry -> entry.table)
            )
                    .and(addLeafFields(instance))
                    .apply(instance, TableSpawnEntry::new)
    );

    private final RegistryKey<SpawnTable> table;

    private TableSpawnEntry(
            RegistryKey<SpawnTable> table,
            SpawnSelector.Data selectorData,
            List<SpawnCondition> conditions,
            SpawnPosFinder posFinder,
            boolean ignoreVanillaRules,
            List<SpawnRule> rules,
            List<SpawnFunction> functions
    ) {
        super(selectorData, conditions, posFinder, ignoreVanillaRules, rules, functions);

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
    protected List<Entity> createEntities(SpawnContext context) {
        return context.getWorld().getRegistryManager().getWrapperOrThrow(MSRegistryKeys.SPAWN_TABLE).getOrThrow(table).value().generateEntities(context);
    }


    public static final class Builder extends LeafSpawnEntry.Builder<Builder> {

        private Builder() {}


        public TableSpawnEntry build(SpawnSelector.Data selectorData, SpawnPosFinder posFinder, RegistryKey<SpawnTable> table) {
            return new TableSpawnEntry(table, selectorData, List.copyOf(conditions), posFinder, ignoreVanillaRules, List.copyOf(rules), List.copyOf(functions));
        }

        @Override
        protected Builder getThisBuilder() {
            return this;
        }
    }
}
