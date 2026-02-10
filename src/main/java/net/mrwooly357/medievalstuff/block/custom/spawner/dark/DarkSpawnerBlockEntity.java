package net.mrwooly357.medievalstuff.block.custom.spawner.dark;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.block.MSBlockEntityTypes;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public final class DarkSpawnerBlockEntity extends BlockEntity {

    private static final Logger LOGGER = MedievalStuff.LOGGER;

    private DarkSpawnerLogic logic = new DarkSpawnerLogic();

    public DarkSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(MSBlockEntityTypes.DARK_SPAWNER, pos, state);
    }


    @Override
    public boolean copyItemDataRequiresOperator() {
        return true;
    }

    void tick(BlockState state) {
        World world = getWorld();

        if (world.isClient())
            logic.clientTick((ClientWorld) world, getPos(), state);
        else
            logic.serverTick((ServerWorld) world, getPos(), state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        logic.createCodec(world).encodeStart(NbtOps.INSTANCE, logic)
                .ifSuccess(element -> nbt.put("logic", element))
                .ifError(error -> LOGGER.error("Failed to encode dark spawner logic {}", error.message()));
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        logic.createCodec(world)
                .parse(NbtOps.INSTANCE, nbt.getCompound("logic"))
                .resultOrPartial(LOGGER::error)
                .ifPresent(logic -> this.logic = logic);
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
