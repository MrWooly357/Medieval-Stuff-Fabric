package net.mrwooly357.medievalstuff.entity.spawn.selector.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelector;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelectorDataHolder;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelectorType;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelectorTypes;
import net.mrwooly357.medievalstuff.util.FastFloatWeightedSelector;

import java.util.ArrayList;
import java.util.List;

public class WeightedSpawnSelector extends SpawnSelector {

    public static final MapCodec<WeightedSpawnSelector> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    IntProvider.NON_NEGATIVE_CODEC.fieldOf("count").forGetter(selector -> selector.count)
            )
                    .apply(instance, WeightedSpawnSelector::new)
    );

    protected final IntProvider count;

    protected WeightedSpawnSelector(IntProvider count) {
        this.count = count;
    }


    @Override
    protected SpawnSelectorType<WeightedSpawnSelector> getType() {
        return SpawnSelectorTypes.WIGHTED;
    }

    @Override
    public <SSDH extends SpawnSelectorDataHolder> List<SSDH> select(List<SSDH> objects, SpawnContext context) {
        Random random = context.getWorld().getRandom();
        FastFloatWeightedSelector<SSDH> selector = new FastFloatWeightedSelector<>(random);
        objects.forEach(ssdh -> selector.add(ssdh, ((Data) ssdh.getSpawnSelectorData()).weight.get(random)));
        List<SSDH> elements = new ArrayList<>();
        int i = count.get(random);

        for (int j = 0; j < i; j++)
            elements.add(selector.next());

        return List.copyOf(elements);
    }


    public static class Data extends SpawnSelector.Data {

        public static final MapCodec<Data> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        FloatProvider.createValidatedCodec(0.0F, Float.MAX_VALUE).fieldOf("weight").forGetter(data -> data.weight)
                )
                        .apply(instance, Data::new)
        );

        protected final FloatProvider weight;

        protected Data(FloatProvider rolls) {
            this.weight = rolls;
        }


        public static Data of(FloatProvider weight) {
            return new Data(weight);
        }

        @Override
        protected SpawnSelectorType<WeightedSpawnSelector> getSelectorType() {
            return SpawnSelectorTypes.WIGHTED;
        }
    }
}
