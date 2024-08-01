package net.bandit.black_iron.block;

import net.bandit.black_iron.BlackIronMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModBlocks {
    public static final Block BLACK_IRON_ORE = registerBlock("black_iron_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(BlackIronMod.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(BlackIronMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        BlackIronMod.LOGGER.info("Registering ModBlocks for " + BlackIronMod.MOD_ID);
    }
}
