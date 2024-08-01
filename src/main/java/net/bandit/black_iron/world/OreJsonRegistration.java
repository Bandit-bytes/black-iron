package net.bandit.black_iron.world;

import net.bandit.black_iron.BlackIronMod;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class OreJsonRegistration {
    public static final ResourceKey<PlacedFeature> BLACK_IRON_ORE_PLACED_KEY = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(BlackIronMod.MOD_ID, "black_iron_ore_placed"));

    public static void registerOreGeneration() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, BLACK_IRON_ORE_PLACED_KEY);
        System.out.println("Registered ore generation");
    }
}