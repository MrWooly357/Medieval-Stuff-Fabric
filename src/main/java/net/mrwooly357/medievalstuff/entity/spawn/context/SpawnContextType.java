package net.mrwooly357.medievalstuff.entity.spawn.context;

import com.mojang.serialization.Codec;
import net.mrwooly357.medievalstuff.registry.MSRegistries;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class SpawnContextType {

    public static final Codec<SpawnContextType> CODEC = MSRegistries.SPAWN_CONTEXT_TYPE.getCodec();

    private final Set<SpawnContextParameter<?>> requiredParameters;

    private SpawnContextType(Set<SpawnContextParameter<?>> requiredParameters) {
        this.requiredParameters = requiredParameters;
    }


    public static Builder builder() {
        return new Builder();
    }

    void check(SpawnContext context) {
        for (SpawnContextParameter<?> parameter : requiredParameters)

            if (context.get(parameter).isEmpty())
                throw new IllegalArgumentException("Given spawn context doesn't contain required parameters for spawn context type " + MSRegistries.SPAWN_CONTEXT_TYPE.getId(this) + "!");
    }


    public static final class Builder {

        private final Set<SpawnContextParameter<?>> requiredParameters = new HashSet<>();

        private Builder() {}


        public Builder require(SpawnContextParameter<?> parameter) {
            if (requiredParameters.add(parameter))
                return this;
            else
                throw new IllegalArgumentException("SpawnContextParameter " + parameter.id + " is already required!");
        }

        public SpawnContextType build() {
            return new SpawnContextType(Set.copyOf(requiredParameters));
        }

        @Override
        public boolean equals(Object object) {
            return super.equals(object) || (object instanceof Builder builder
                    && requiredParameters.equals(builder.requiredParameters));
        }

        @Override
        public int hashCode() {
            return Objects.hash(requiredParameters);
        }

        @Override
        public String toString() {
            return "SpawnContextType.Builder[required_parameters: " + requiredParameters + "]";
        }
    }
}
