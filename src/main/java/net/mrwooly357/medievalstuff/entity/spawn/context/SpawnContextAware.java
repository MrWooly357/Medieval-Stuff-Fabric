package net.mrwooly357.medievalstuff.entity.spawn.context;

import java.util.Set;

public interface SpawnContextAware {


    default Set<SpawnContextParameter<?>> getRequiredParameters() {
        return Set.of();
    }
}
