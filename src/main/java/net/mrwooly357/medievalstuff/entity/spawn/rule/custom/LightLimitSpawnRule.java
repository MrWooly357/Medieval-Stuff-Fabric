package net.mrwooly357.medievalstuff.entity.spawn.rule.custom;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.Range;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.rule.SpawnRule;
import net.mrwooly357.medievalstuff.entity.spawn.rule.SpawnRuleType;
import net.mrwooly357.medievalstuff.entity.spawn.rule.SpawnRuleTypes;

public final class LightLimitSpawnRule extends SpawnRule {

    private static final Range<Integer> DEFAULT = new Range<>(0, 15);
    public static final MapCodec<LightLimitSpawnRule> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    createLightLimitCodec("block_light_limit").forGetter(rule -> rule.blockLightLimit),
                    createLightLimitCodec("sky_light_limit").forGetter(rule -> rule.skyLightLimit)
            )
                    .apply(instance, LightLimitSpawnRule::new)
    );

    private final Range<Integer> blockLightLimit;
    private final Range<Integer> skyLightLimit;

    protected LightLimitSpawnRule(Range<Integer> blockLightLimit, Range<Integer> skyLightLimit) {
        this.blockLightLimit = blockLightLimit;
        this.skyLightLimit = skyLightLimit;
    }


    private static MapCodec<Range<Integer>> createLightLimitCodec(String id) {
        return Range.CODEC.optionalFieldOf(id, DEFAULT).validate(range -> DEFAULT.contains(range) ? DataResult.success(range) : DataResult.error(() -> "Light values must be within range " + DEFAULT));
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public SpawnRuleType<LightLimitSpawnRule> getType() {
        return SpawnRuleTypes.LIGHT_LIMIT;
    }

    @Override
    public boolean test(Vec3d pos, Entity entity, SpawnContext context, SpawnReason reason) {
        ServerWorld world = context.getWorld();
        BlockPos pos1 = BlockPos.ofFloored(pos);

        return blockLightLimit.contains(world.getLightLevel(LightType.BLOCK, pos1)) && skyLightLimit.contains(world.getLightLevel(LightType.SKY, pos1));
    }


    public static final class Builder {

        private int minBlockLight = 0;
        private int maxBlockLight = 15;
        private int minSkyLight = 0;
        private int maxSkyLight = 15;

        private Builder() {}


        public Builder minBlockLight(int value) {
            minBlockLight = value;

            return this;
        }

        public Builder maxBlockLight(int value) {
            maxBlockLight = value;

            return this;
        }

        public Builder minSkyLight(int value) {
            minSkyLight = value;

            return this;
        }

        public Builder maxSkyLight(int value) {
            maxSkyLight = value;

            return this;
        }

        public LightLimitSpawnRule build() {
            return new LightLimitSpawnRule(new Range<>(minBlockLight, maxBlockLight), new Range<>(minSkyLight, maxSkyLight));
        }
    }
}
