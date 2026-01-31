package net.mrwooly357.medievalstuff.block.custom.candelabrum;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.ToIntFunction;

public class CandelabrumBlock extends Block implements Waterloggable {

    public static final MapCodec<CandelabrumBlock> CODEC = createCodec(CandelabrumBlock::new);
    public static final IntProperty CANDLES = Properties.CANDLES;
    public static final EnumProperty<PlacementType> PLACEMENT_TYPE = EnumProperty.of("placement_type", PlacementType.class);
    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty LIT = Properties.LIT;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final ToIntFunction<BlockState> STATE_TO_LUMINANCE = state -> state.get(LIT) ? state.get(CANDLES) * 3 : 0;

    public CandelabrumBlock(Settings settings) {
        super(settings);

        setDefaultState(getStateManager().getDefaultState()
                .with(CANDLES, 1)
                .with(PLACEMENT_TYPE, PlacementType.FLOOR)
                .with(FACING, Direction.UP)
                .with(LIT, false)
                .with(WATERLOGGED, false));
    }


    @Override
    protected MapCodec<CandelabrumBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);

        builder.add(CANDLES, PLACEMENT_TYPE, FACING, LIT, WATERLOGGED);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext context) {
        Direction direction = context.getPlayerLookDirection().getOpposite();

        return Objects.requireNonNull(super.getPlacementState(context))
                .with(PLACEMENT_TYPE, switch (direction) {
                    case UP -> PlacementType.FLOOR;
                    case NORTH, EAST, SOUTH, WEST -> PlacementType.WALL;
                    case DOWN -> PlacementType.CEILING;
                })
                .with(FACING, direction)
                .with(WATERLOGGED, context.getWorld().getFluidState(context.getBlockPos()).isOf(Fluids.WATER));
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(Properties.WATERLOGGED))
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return super.canPlaceAt(state, world, pos) && (sideCoversSmallSquare(world, pos.down(), Direction.UP) || sideCoversSmallSquare(world, pos.north(), Direction.SOUTH)
                || sideCoversSmallSquare(world, pos.east(), Direction.WEST) || sideCoversSmallSquare(world, pos.south(), Direction.NORTH)
                || sideCoversSmallSquare(world, pos.west(), Direction.EAST) || sideCoversSmallSquare(world, pos.up(), Direction.DOWN));
    }

    @Override
    protected void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        super.onProjectileHit(world, state, hit, projectile);
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }


    public enum PlacementType implements StringIdentifiable {

        FLOOR("floor"),
        WALL("wall"),
        CEILING("ceiling");

        public static final Codec<PlacementType> CODEC = StringIdentifiable.createCodec(PlacementType::values);

        private final String id;

        PlacementType(String id) {
            this.id = id;
        }


        @Override
        public String asString() {
            return id;
        }
    }
}
