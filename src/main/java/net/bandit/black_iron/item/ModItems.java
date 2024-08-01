package net.bandit.black_iron.item;

import net.bandit.black_iron.BlackIronMod;
import net.bandit.black_iron.item.custom.BlackIronBow;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class ModItems {

    // Declare items as public static final
    public static final Item BLACK_IRON = registerItem("black_iron", new Item(new FabricItemSettings()));
    public static final Item BLACK_IRON_BOW = registerItem("black_iron_bow", new BlackIronBow(new FabricItemSettings()));

    public static final Item BLACK_IRON_HELMET = registerItem("black_iron_helmet",
            new ArmorItem(ModArmorMaterials.BLACK_IRON, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item BLACK_IRON_CHESTPLATE = registerItem("black_iron_chestplate",
            new ArmorItem(ModArmorMaterials.BLACK_IRON, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item BLACK_IRON_LEGGINGS = registerItem("black_iron_leggings",
            new ArmorItem(ModArmorMaterials.BLACK_IRON, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item BLACK_IRON_BOOTS = registerItem("black_iron_boots",
            new ArmorItem(ModArmorMaterials.BLACK_IRON, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    // Method to register an item
    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(BlackIronMod.MOD_ID, name), item);
    }

    // Static block to initialize items
    public static void registerModItems() {
        System.out.println("Registering Mod Items for " + BlackIronMod.MOD_ID);
    }
}
