package net.mrwooly357.medievalstuff.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.util.MSUtil;

public final class MSItems {

    private MSItems() {}


    private static Item register(String id, Item item) {
        return Registry.register(Registries.ITEM, MSUtil.id(id), item);
    }

    public static void initialize() {
        MedievalStuff.logInitialization("items");
    }
}
