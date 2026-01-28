package net.mrwooly357.medievalstuff.entity.spawn.context;

import net.minecraft.util.Identifier;

public final class SpawnContextParameter<A> {

    private final Class<A> type;
    final Identifier id;

    private SpawnContextParameter(Class<A> type, Identifier id) {
        this.type = type;
        this.id = id;
    }


    public static <A> SpawnContextParameter<A> of(Class<A> type, Identifier id) {
        return new SpawnContextParameter<>(type, id);
    }

    A cast(Object object) {
        return type.cast(object);
    }
}
