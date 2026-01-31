package net.mrwooly357.medievalstuff.util.tag;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.mrwooly357.medievalstuff.util.MSUtil;

public final class MSItemTags {

    public static final TagKey<Item> MELEE_WEAPONS = of("melee_weapons");

    private MSItemTags() {}


    private static TagKey<Item> of(String id) {
        return TagKey.of(RegistryKeys.ITEM, MSUtil.id(id));
    }
}
