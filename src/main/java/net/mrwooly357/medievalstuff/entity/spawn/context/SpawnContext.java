package net.mrwooly357.medievalstuff.entity.spawn.context;

import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.server.world.ServerWorld;

import java.util.*;

public final class SpawnContext {

    private final SpawnContextType type;
    private final ServerWorld world;
    private final RegistryEntryLookup.RegistryLookup registryLookup;
    private final Map<SpawnContextParameter<?>, Object> parameters;

    private SpawnContext(SpawnContextType type, ServerWorld world, RegistryEntryLookup.RegistryLookup registryLookup, Map<SpawnContextParameter<?>, Object> parameters) {
        this.type = type;
        this.world = world;
        this.registryLookup = registryLookup;
        this.parameters = parameters;
        type.check(this);
    }


    public static Builder builder() {
        return new Builder();
    }

    public SpawnContextType getType() {
        return type;
    }

    public ServerWorld getWorld() {
        return world;
    }

    public RegistryEntryLookup.RegistryLookup getRegistryLookup() {
        return registryLookup;
    }

    public <A> A getRequired(SpawnContextParameter<A> parameter) {
        return parameter.cast(parameters.get(parameter));
    }

    public <A> Optional<A> get(SpawnContextParameter<A> parameter) {
        return Optional.ofNullable(parameter.cast(parameters.get(parameter)));
    }

    public void check(SpawnContextAware aware) {
        if (!parameters.keySet().containsAll(aware.getRequiredParameters()))
            throw new IllegalArgumentException("Given spawn context doesn't contain all parameters required by " + aware + "!");
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object) || (object instanceof SpawnContext context
                && type.equals(context.type)
                && world.equals(context.world)
                && registryLookup.equals(context.registryLookup)
                && parameters.equals(context.parameters));
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, world, registryLookup, parameters);
    }

    @Override
    public String toString() {
        return "SpawnContext[entity: " + type
                + ", world: " + world
                + ", registry_lookup: " + registryLookup
                + ", parameters: " + parameters + "]";
    }


    public static final class Builder {

        private final Map<SpawnContextParameter<?>, Object> parameters = new HashMap<>();

        private Builder() {}


        public <A> Builder parameter(SpawnContextParameter<A> parameter, A value) {
            if (parameters.put(parameter, value) != null)
                throw new IllegalArgumentException("SpawnContextParameter " + parameter.id + " already has a value! Duplicate: " + value + ".");

            return this;
        }

        public SpawnContext build(SpawnContextType type, ServerWorld world) {
            return new SpawnContext(type, world, world.getRegistryManager().createRegistryLookup(), parameters);
        }

        @Override
        public boolean equals(Object object) {
            return super.equals(object) || (object instanceof Builder builder
                    && parameters.equals(builder.parameters));
        }

        @Override
        public int hashCode() {
            return Objects.hash(parameters);
        }

        @Override
        public String toString() {
            return "SpawnContext.Builder[parameters: " + parameters + "]";
        }
    }
}
