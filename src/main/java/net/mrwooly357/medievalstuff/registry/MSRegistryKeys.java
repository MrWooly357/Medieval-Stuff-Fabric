package net.mrwooly357.medievalstuff.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.mrwooly357.medievalstuff.enchantment.effects.custom.entity_damage.EnchantmentEntityDamageEffect;
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

import java.util.function.Supplier;

public final class MSRegistryKeys {

    public static final RegistryKey<Registry<SpawnTable>> SPAWN_TABLE = dynamicOf(
            "spawn_table", () -> SpawnTable.CODEC
    );
    public static final RegistryKey<Registry<MapCodec<? extends EnchantmentEntityDamageEffect>>> ENCHANTMENT_ENTITY_DAMAGE_EFFECT_TYPE = of("enchantment_entity_damage_effect_type");
    public static final RegistryKey<Registry<SpawnContextType>> SPAWN_CONTEXT_TYPE = of("spawn_context_type");
    public static final RegistryKey<Registry<SpawnSelectorType<?>>> SPAWN_SELECTOR_TYPE = of("spawn_selector_type");
    public static final RegistryKey<Registry<SpawnConditionType<?>>> SPAWN_CONDITION_TYPE = of("spawn_condition_type");
    public static final RegistryKey<Registry<SpawnPosFinderType<?>>> SPAWN_POS_FINDER_TYPE = of("spawn_pos_finder_type");
    public static final RegistryKey<Registry<SpawnRuleType<?>>> SPAWN_RULE_TYPE = of("spawn_rule_type");
    public static final RegistryKey<Registry<SpawnFunctionConditionType<?>>> SPAWN_FUNCTION_CONDITION_TYPE = of("spawn_function_condition_type");
    public static final RegistryKey<Registry<SpawnFunctionType<?>>> SPAWN_FUNCTION_TYPE = of("spawn_function_type");
    public static final RegistryKey<Registry<SpawnEntryType<?>>> SPAWN_ENTRY_TYPE = of("spawn_entry_type");

    private MSRegistryKeys() {}


    private static <A> RegistryKey<Registry<A>> of(String id) {
        return RegistryKey.ofRegistry(MSUtil.id(id));
    }

    private static <A> RegistryKey<Registry<A>> dynamicOf(String id, Supplier<Codec<A>> codec) {
        RegistryKey<Registry<A>> key = of(id);
        MSRegistries.DYNAMIC_REGISTRY_INITIALIZERS.add(() -> DynamicRegistries.register(key, codec.get()));

        return key;
    }
}
