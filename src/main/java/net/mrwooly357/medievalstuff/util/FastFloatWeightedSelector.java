package net.mrwooly357.medievalstuff.util;

import net.minecraft.util.math.random.Random;

import java.util.NavigableMap;
import java.util.TreeMap;

public class FastFloatWeightedSelector<A> {

    private final NavigableMap<Float, A> elements = new TreeMap<>();
    private final Random random;
    private float totalWeight = 0.0F;

    public FastFloatWeightedSelector(Random random) {
        this.random = random;
    }


    public void add(A element, float weight) {
        if (elements.containsValue(element))
            throw new IllegalArgumentException("Can't add a duplicate element in fast float weighted selector! Duplicate: " + element + ".");
        else if (weight <= 0.0F)
            throw new IllegalArgumentException("Can't add an element with weight of 0 or less to a fast float weighted selector! Given weight: " + weight + ".");
        else {
            totalWeight += weight;
            elements.put(totalWeight, element);
        }
    }

    public A next() {
        return elements.ceilingEntry(random.nextFloat() * totalWeight).getValue();
    }
}
