package net.bandit.black_iron;


import mod.azure.azurelib.AzureLib;
import mod.azure.azurelib.animation.cache.AzIdentityRegistry;
import mod.azure.azurelib.render.armor.AzArmorRenderer;
import mod.azure.azurelib.render.armor.AzArmorRendererRegistry;
import net.bandit.black_iron.block.ModBlocks;
import net.bandit.black_iron.client.renderer.CustomHelmetRenderer;
import net.bandit.black_iron.item.ModItemGroups;
import net.bandit.black_iron.item.ModItems;
import net.bandit.black_iron.client.ModItemPropertiesClient;
import net.bandit.black_iron.world.OreJsonRegistration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlackIronMod implements ModInitializer {
	public static final String MOD_ID = "black_iron";
	public static final Logger LOGGER = LoggerFactory.getLogger("Black Iron");

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		AzureLib.initialize();
		ModBlocks.registerModBlocks();
		OreJsonRegistration.registerOreGeneration();

		ModItems.registerModItems();
	}

	public static void registerClient() {
		ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
			ModItemPropertiesClient.addCustomItemProperties();
		});
	}

	public static void initAzIdentityRegistry() {
		AzIdentityRegistry.register(
				ModItems.BLACK_IRON_HELMET.asItem()
		);

	}
	public static void initClientAzRenders() {
		AzArmorRendererRegistry.register(
				CustomHelmetRenderer::new,
		ModItems.BLACK_IRON_HELMET.asItem()
				);
	}

		public static ResourceLocation modResource (String name){
			return new ResourceLocation(MOD_ID, name);
		}
	}