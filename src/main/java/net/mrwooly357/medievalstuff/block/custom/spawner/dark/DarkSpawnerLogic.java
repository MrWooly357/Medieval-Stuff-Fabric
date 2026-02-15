package net.mrwooly357.medievalstuff.block.custom.spawner.dark;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public final class DarkSpawnerLogic {

    private final DarkSpawnerConfig config;
    private final DarkSpawnerData data;

    DarkSpawnerLogic() {
        this(DarkSpawnerConfig.DEFAULT, new DarkSpawnerData());
    }

    DarkSpawnerLogic(DarkSpawnerConfig config, DarkSpawnerData data) {
        this.config = config;
        this.data = data;
    }


    Codec<DarkSpawnerLogic> createCodec(World world) {
        return RecordCodecBuilder.create(
                instance -> instance.group(
                        DarkSpawnerConfig.CODEC.optionalFieldOf("config", DarkSpawnerConfig.DEFAULT).forGetter(logic -> logic.config),
                        DarkSpawnerData.createCodec(world).optionalFieldOf("data", new DarkSpawnerData()).forGetter(logic -> logic.data)
                )
                        .apply(instance, DarkSpawnerLogic::new)
        );
    }

    void serverTick(ServerWorld world, BlockPos pos, BlockState state) {
        if (state.get(DarkSpawnerBlock.STATE).equals(DarkSpawnerBlock.State.ACTIVE)) {
        } else if (!state.get(DarkSpawnerBlock.STATE).equals(DarkSpawnerBlock.State.WAITING_FOR_PLAYERS) && !world.getNonSpectatingEntities(PlayerEntity.class, config.playerDetectionArea().offset(pos)).isEmpty())
            world.setBlockState(pos, state.with(DarkSpawnerBlock.STATE, DarkSpawnerBlock.State.WAITING_FOR_PLAYERS));
        else if (!world.getNonSpectatingEntities(PlayerEntity.class, config.activationArea().offset(pos)).isEmpty())
            world.setBlockState(pos, state.with(DarkSpawnerBlock.STATE, DarkSpawnerBlock.State.ACTIVE));
    }

    void clientTick(ClientWorld world, BlockPos pos, BlockState state) {}

    @Override
    public int hashCode() {
        return Objects.hash(config, data);
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object) || (object instanceof DarkSpawnerLogic logic
                && config.equals(logic.config)
                && data.equals(logic.data));
    }

    @Override
    public String toString() {
        return "DarkSpawnerLogic[config: " + config
                + ", data: " + data + "]";
    }
}
