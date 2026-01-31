package net.mrwooly357.medievalstuff.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.block.custom.candelabrum.CandelabrumBlock;
import net.mrwooly357.medievalstuff.util.MSUtil;

public final class MSBlocks {

    public static final Block IRON_CANDELABRUM = register(
            "iron_candelabrum", createCandelabrum(MapColor.PALE_YELLOW)
    );
    public static final Block WHITE_IRON_CANDELABRUM = register(
            "white_iron_candelabrum", createCandelabrum(MapColor.WHITE_GRAY)
    );
    public static final Block ORANGE_IRON_CANDELABRUM = register(
            "orange_iron_candelabrum", createCandelabrum(MapColor.ORANGE)
    );
    public static final Block MAGENTA_IRON_CANDELABRUM = register(
            "magenta_iron_candelabrum", createCandelabrum(MapColor.MAGENTA)
    );
    public static final Block LIGHT_BLUE_IRON_CANDELABRUM = register(
            "light_blue_iron_candelabrum", createCandelabrum(MapColor.LIGHT_BLUE)
    );
    public static final Block YELLOW_IRON_CANDELABRUM = register(
            "yellow_iron_candelabrum", createCandelabrum(MapColor.YELLOW)
    );
    public static final Block LIME_IRON_CANDELABRUM = register(
            "lime_iron_candelabrum", createCandelabrum(MapColor.LIME)
    );
    public static final Block PINK_IRON_CANDELABRUM = register(
            "pink_iron_candelabrum", createCandelabrum(MapColor.PINK)
    );
    public static final Block GRAY_IRON_CANDELABRUM = register(
            "gray_iron_candelabrum", createCandelabrum(MapColor.GRAY)
    );
    public static final Block LIGHT_GRAY_IRON_CANDELABRUM = register(
            "light_gray_iron_candelabrum", createCandelabrum(MapColor.LIGHT_GRAY)
    );
    public static final Block CYAN_IRON_CANDELABRUM = register(
            "cyan_iron_candelabrum", createCandelabrum(MapColor.CYAN)
    );
    public static final Block PURPLE_IRON_CANDELABRUM = register(
            "purple_iron_candelabrum", createCandelabrum(MapColor.PURPLE)
    );
    public static final Block BLUE_IRON_CANDELABRUM = register(
            "blue_iron_candelabrum", createCandelabrum(MapColor.BLUE)
    );
    public static final Block BROWN_IRON_CANDELABRUM = register(
            "brown_iron_candelabrum", createCandelabrum(MapColor.BROWN)
    );
    public static final Block GREEN_IRON_CANDELABRUM = register(
            "green_iron_candelabrum", createCandelabrum(MapColor.GREEN)
    );
    public static final Block RED_IRON_CANDELABRUM = register(
            "red_iron_candelabrum", createCandelabrum(MapColor.RED)
    );
    public static final Block BLACK_IRON_CANDELABRUM = register(
            "black_iron_candelabrum", createCandelabrum(MapColor.BLACK)
    );

    private MSBlocks() {}


    private static Block register(String id, Block block) {
        return Registry.register(Registries.BLOCK, MSUtil.id(id), block);
    }

    private static Block createCandelabrum(MapColor color) {
        return new CandelabrumBlock(
                AbstractBlock.Settings.create()
                        .strength(4.0F, 5.0F)
                        .solid()
                        .nonOpaque()
                        .luminance(CandelabrumBlock.STATE_TO_LUMINANCE)
                        .pistonBehavior(PistonBehavior.DESTROY)
                        .mapColor(color)
                        .sounds(BlockSoundGroup.METAL)
        );
    }

    public static void initialize() {
        MedievalStuff.logInitialization("blocks");
    }
}
