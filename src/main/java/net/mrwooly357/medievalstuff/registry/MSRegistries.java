package net.mrwooly357.medievalstuff.registry;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.entity.spawn.SpawnTable;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnConditionType;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextType;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntryType;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunctionType;
import net.mrwooly357.medievalstuff.entity.spawn.function.condition.SpawnFunctionConditionType;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.SpawnPosFinderType;
import net.mrwooly357.medievalstuff.entity.spawn.rule.SpawnRuleType;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelectorType;
import net.mrwooly357.medievalstuff.util.MSUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class MSRegistries {

    private static final List<Runnable> DYNAMIC_REGISTRY_INITIALIZERS = new ArrayList<>();
    public static final RegistryKey<Registry<SpawnContextType>> SPAWN_CONTEXT_TYPE_KEY = createKey("spawn_context_type");
    public static final RegistryKey<Registry<SpawnSelectorType<?>>> SPAWN_SELECTOR_TYPE_KEY = createKey("spawn_selector_type");
    public static final RegistryKey<Registry<SpawnConditionType<?>>> SPAWN_CONDITION_TYPE_KEY = createKey("spawn_condition_type");
    public static final RegistryKey<Registry<SpawnPosFinderType<?>>> SPAWN_POS_FINDER_TYPE_KEY = createKey("spawn_pos_finder_type");
    public static final RegistryKey<Registry<SpawnRuleType<?>>> SPAWN_RULE_TYPE_KEY = createKey("spawn_rule_type");
    public static final RegistryKey<Registry<SpawnFunctionConditionType<?>>> SPAWN_FUNCTION_CONDITION_TYPE_KEY = createKey("spawn_function_condition_type");
    public static final RegistryKey<Registry<SpawnFunctionType<?>>> SPAWN_FUNCTION_TYPE_KEY = createKey("spawn_function_type");
    public static final RegistryKey<Registry<SpawnEntryType<?>>> SPAWN_ENTRY_TYPE_KEY = createKey("spawn_entry_type");
    public static final RegistryKey<Registry<SpawnTable>> SPAWN_TABLE_KEY = createDynamic(
            "spawn_table", () -> SpawnTable.CODEC
    );
    public static final Registry<SpawnContextType> SPAWN_CONTEXT_TYPE = create(SPAWN_CONTEXT_TYPE_KEY);
    public static final Registry<SpawnSelectorType<?>> SPAWN_SELECTOR_TYPE = create(SPAWN_SELECTOR_TYPE_KEY);
    public static final Registry<SpawnConditionType<?>> SPAWN_CONDITION_TYPE = create(SPAWN_CONDITION_TYPE_KEY);
    public static final Registry<SpawnPosFinderType<?>> SPAWN_POS_FINDER_TYPE = create(SPAWN_POS_FINDER_TYPE_KEY);
    public static final Registry<SpawnRuleType<?>> SPAWN_RULE_TYPE = create(SPAWN_RULE_TYPE_KEY);
    public static final Registry<SpawnFunctionConditionType<?>> SPAWN_FUNCTION_CONDITION_TYPE = create(SPAWN_FUNCTION_CONDITION_TYPE_KEY);
    public static final Registry<SpawnFunctionType<?>> SPAWN_FUNCTION_TYPE = create(SPAWN_FUNCTION_TYPE_KEY);
    public static final Registry<SpawnEntryType<?>> SPAWN_ENTRY_TYPE = create(SPAWN_ENTRY_TYPE_KEY);

    private MSRegistries() {}


    private static <A> RegistryKey<Registry<A>> createKey(String id) {
        return RegistryKey.ofRegistry(MSUtil.id(id));
    }

    private static <A> RegistryKey<Registry<A>> createDynamic(String id, Supplier<Codec<A>> codec) {
        RegistryKey<Registry<A>> key = createKey(id);
        DYNAMIC_REGISTRY_INITIALIZERS.add(() -> DynamicRegistries.register(key, codec.get()));

        return key;
    }

    private static <A> Registry<A> create(RegistryKey<Registry<A>> key) {
        return FabricRegistryBuilder.createSimple(key)
                .attribute(RegistryAttribute.SYNCED)
                .buildAndRegister();
    }

    public static void initialize() {
        MedievalStuff.logInitialization("registries");
        DYNAMIC_REGISTRY_INITIALIZERS.forEach(Runnable::run);
        DYNAMIC_REGISTRY_INITIALIZERS.clear();
    }
}
