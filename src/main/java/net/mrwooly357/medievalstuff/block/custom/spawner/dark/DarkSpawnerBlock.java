package net.mrwooly357.medievalstuff.block.custom.spawner.dark;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.mrwooly357.medievalstuff.block.MSBlockEntityTypes;
import org.jetbrains.annotations.Nullable;

public final class DarkSpawnerBlock extends BlockWithEntity implements Waterloggable {

    public static final MapCodec<DarkSpawnerBlock> CODEC = createCodec(DarkSpawnerBlock::new);
    public static final EnumProperty<State> STATE = EnumProperty.of("state", State.class);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public DarkSpawnerBlock(Settings settings) {
        super(settings);

        setDefaultState(getStateManager().getDefaultState()
                .with(STATE, State.INACTIVE)
                .with(WATERLOGGED, false));
    }


    @Override
    protected MapCodec<DarkSpawnerBlock> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DarkSpawnerBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);

        builder.add(STATE, WATERLOGGED);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext context) {
        return super.getPlacementState(context).with(WATERLOGGED, context.getWorld().getFluidState(context.getBlockPos()).getFluid().equals(Fluids.WATER));
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED))
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public @Nullable <BE extends BlockEntity> BlockEntityTicker<BE> getTicker(World world, BlockState state, BlockEntityType<BE> type) {
        return validateTicker(type, MSBlockEntityTypes.DARK_SPAWNER, (world1, pos, state1, darkSpawner) -> darkSpawner.tick(state1));
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    public enum State implements StringIdentifiable {

        INACTIVE("inactive", 0, false, 0.0D),
        WAITING_FOR_PLAYERS("waiting_for_players", 4, true, 200D),
        ACTIVE("active", 8, true, 1000.0D),
        WAITING_FOR_LOOT_EJECTION("waiting_for_loot_ejection", 8, false, 0.0D),
        EJECTING_LOOT("ejecting_loot", 8, false, 0.0D),
        COOLDOWN("cooldown", 0, false, 0.0D);

        private final String id;
        private final int luminance;
        private final boolean playAmbientSound;
        private final double displayEntityRotationSpeed;

        State(String id, int luminance, boolean playAmbientSound, double displayEntityRotationSpeed) {
            this.id = id;
            this.luminance = luminance;
            this.playAmbientSound = playAmbientSound;
            this.displayEntityRotationSpeed = displayEntityRotationSpeed;
        }


        @Override
        public String asString() {
            return id;
        }

        public int getLuminance() {
            return luminance;
        }

        public boolean shouldPlayAmbientSound() {
            return playAmbientSound;
        }

        public double getDisplayEntityRotationSpeed() {
            return displayEntityRotationSpeed;
        }
    }
}
