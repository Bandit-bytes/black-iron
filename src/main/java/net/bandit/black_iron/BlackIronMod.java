package net.bandit.black_iron;


import mod.azure.azurelib.AzureLib;
import net.bandit.black_iron.block.ModBlocks;
import net.bandit.black_iron.item.ModItemGroups;
import net.bandit.black_iron.item.ModItems;
import net.bandit.black_iron.client.ModItemPropertiesClient;
import net.bandit.black_iron.world.OreJsonRegistration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlackIronMod implements ModInitializer {
	public static final String MOD_ID = "black_iron";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

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
}