package net.bandit.black_iron.item;

import net.bandit.black_iron.BlackIronMod;
import net.bandit.black_iron.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModItemGroups {
    public static final CreativeModeTab BLACK_IRON_GROUP = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            new ResourceLocation(BlackIronMod.MOD_ID, "black_iron"),
            FabricItemGroup.builder().title(Component.translatable("itemgroup.black_iron"))
                    .icon(() -> new ItemStack(ModItems.BLACK_IRON_BOW))
                    .displayItems((displayContext, entries) -> {
                        entries.accept(ModBlocks.BLACK_IRON_ORE);
                        entries.accept(ModItems.BLACK_IRON_RAW);
                        entries.accept(ModItems.BLACK_IRON_INGOT);
                        entries.accept(ModItems.BLACK_IRON_BOW);
                        entries.accept(ModItems.BLACK_IRON_SWORD);
                        entries.accept(ModItems.BLACK_IRON_AXE);
                        entries.accept(ModItems.BLACK_IRON_PICKAXE);
                        entries.accept(ModItems.BLACK_IRON_SHOVEL);
                        entries.accept(ModItems.BLACK_IRON_HOE);
                        entries.accept(ModItems.BLACK_IRON_HELMET);
                        entries.accept(ModItems.BLACK_IRON_CHESTPLATE);
                        entries.accept(ModItems.BLACK_IRON_LEGGINGS);
                        entries.accept(ModItems.BLACK_IRON_BOOTS);
                    }).build());


    public static void registerItemGroups() {
        BlackIronMod.LOGGER.info("Registering Item Groups for " + BlackIronMod.MOD_ID);
    }
}
