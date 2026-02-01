package net.mrwooly357.medievalstuff.enchantment;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.mrwooly357.medievalstuff.enchantment.effects.custom.entity_damage.custom.IncreaseComboAndDamageEnchantmentEffect;
import net.mrwooly357.medievalstuff.item.component.MSEnchantmentEffectComponentTypes;
import net.mrwooly357.medievalstuff.util.MSUtil;
import net.mrwooly357.medievalstuff.util.tag.MSItemTags;

public final class MSEnchantments {

    public static final RegistryKey<Enchantment> COMBO = of("combo");

    private MSEnchantments() {}


    private static RegistryKey<Enchantment> of(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, MSUtil.id(id));
    }

    public static void bootstrap(Registerable<Enchantment> registry) {
        RegistryEntryLookup<Item> items = registry.getRegistryLookup(RegistryKeys.ITEM);
        RegistryEntryLookup<Enchantment> enchantments = registry.getRegistryLookup(RegistryKeys.ENCHANTMENT);
        register(
                registry,
                COMBO,
                Enchantment.builder(
                        Enchantment.definition(
                                items.getOrThrow(MSItemTags.MELEE_WEAPONS),
                                5,
                                5,
                                Enchantment.leveledCost(1, 10),
                                Enchantment.leveledCost(20, 10),
                                1,
                                AttributeModifierSlot.MAINHAND
                        )
                )
                        .addEffect(MSEnchantmentEffectComponentTypes.DAMAGE, IncreaseComboAndDamageEnchantmentEffect.INSTANCE)
        );
    }

    private static void register(Registerable<Enchantment> registry, RegistryKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.getValue()));
    }
}
