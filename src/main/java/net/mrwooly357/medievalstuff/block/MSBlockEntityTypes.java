package net.mrwooly357.medievalstuff.block;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.block.custom.spawner.dark.DarkSpawnerBlockEntity;
import net.mrwooly357.medievalstuff.util.MSUtil;

public final class MSBlockEntityTypes {

    public static final BlockEntityType<DarkSpawnerBlockEntity> DARK_SPAWNER = register(
            "dark_spawner", DarkSpawnerBlockEntity::new, MSBlocks.DARK_SPAWNER
    );

    private MSBlockEntityTypes() {}


    private static <BE extends BlockEntity> BlockEntityType<BE> register(String id, BlockEntityType.BlockEntityFactory<BE> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, MSUtil.id(id), BlockEntityType.Builder.create(factory, blocks).build());
    }

    public static void initialize() {
        MedievalStuff.logInitialization("block entity types");
    }
}
