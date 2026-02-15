package net.mrwooly357.medievalstuff.registry;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.enchantment.effects.custom.entity_damage.EnchantmentEntityDamageEffect;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnConditionType;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextType;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntryType;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunctionType;
import net.mrwooly357.medievalstuff.entity.spawn.function.condition.SpawnFunctionConditionType;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.SpawnPosFinderType;
import net.mrwooly357.medievalstuff.entity.spawn.rule.SpawnRuleType;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelectorType;
import net.mrwooly357.medievalstuff.world.mark.WorldMarkType;

import java.util.ArrayList;
import java.util.List;

public final class MSRegistries {

    static final List<Runnable> DYNAMIC_REGISTRY_INITIALIZERS = new ArrayList<>();
    public static final Registry<MapCodec<? extends EnchantmentEntityDamageEffect>> ENCHANTMENT_ENTITY_DAMAGE_EFFECT_TYPE = of(MSRegistryKeys.ENCHANTMENT_ENTITY_DAMAGE_EFFECT_TYPE);
    public static final Registry<SpawnContextType> SPAWN_CONTEXT_TYPE = of(MSRegistryKeys.SPAWN_CONTEXT_TYPE);
    public static final Registry<SpawnSelectorType<?>> SPAWN_SELECTOR_TYPE = of(MSRegistryKeys.SPAWN_SELECTOR_TYPE);
    public static final Registry<SpawnConditionType<?>> SPAWN_CONDITION_TYPE = of(MSRegistryKeys.SPAWN_CONDITION_TYPE);
    public static final Registry<SpawnPosFinderType<?>> SPAWN_POS_FINDER_TYPE = of(MSRegistryKeys.SPAWN_POS_FINDER_TYPE);
    public static final Registry<SpawnRuleType<?>> SPAWN_RULE_TYPE = of(MSRegistryKeys.SPAWN_RULE_TYPE);
    public static final Registry<SpawnFunctionConditionType<?>> SPAWN_FUNCTION_CONDITION_TYPE = of(MSRegistryKeys.SPAWN_FUNCTION_CONDITION_TYPE);
    public static final Registry<SpawnFunctionType<?>> SPAWN_FUNCTION_TYPE = of(MSRegistryKeys.SPAWN_FUNCTION_TYPE);
    public static final Registry<SpawnEntryType<?>> SPAWN_ENTRY_TYPE = of(MSRegistryKeys.SPAWN_ENTRY_TYPE);
    public static final Registry<WorldMarkType<?>> WORLD_MARK_TYPE = of(MSRegistryKeys.WORLD_MARK_TYPE);
    public static final Registry<AttachmentType<?>> ATTACHMENT_TYPE = of(MSRegistryKeys.ATTACHMENT_TYPE);

    private MSRegistries() {}


    private static <A> Registry<A> of(RegistryKey<Registry<A>> key) {
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
