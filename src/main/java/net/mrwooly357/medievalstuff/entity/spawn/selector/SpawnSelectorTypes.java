package net.mrwooly357.medievalstuff.entity.spawn.selector;

import com.mojang.serialization.MapCodec;
import net.minecraft.registry.Registry;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.entity.spawn.selector.custom.EachElementSpawnSelector;
import net.mrwooly357.medievalstuff.entity.spawn.selector.custom.WeightedSpawnSelector;
import net.mrwooly357.medievalstuff.registry.MSRegistries;
import net.mrwooly357.medievalstuff.util.MSUtil;

public final class SpawnSelectorTypes {

    public static final SpawnSelectorType<EachElementSpawnSelector> EACH_ELEMENT = register(
            "each_element", EachElementSpawnSelector.CODEC, EachElementSpawnSelector.Data.CODEC
    );
    public static final SpawnSelectorType<WeightedSpawnSelector> WIGHTED = register(
            "weighted", WeightedSpawnSelector.CODEC, WeightedSpawnSelector.Data.CODEC
    );

    private SpawnSelectorTypes() {}


    private static <SS extends SpawnSelector> SpawnSelectorType<SS> register(String id, MapCodec<SS> codec, MapCodec<? extends SpawnSelector.Data> dataCodec) {
        return Registry.register(MSRegistries.SPAWN_SELECTOR_TYPE, MSUtil.id(id), SpawnSelectorType.of(codec, dataCodec));
    }

    public static void initialize() {
        MedievalStuff.logInitialization("spawn selector types");
    }
}
