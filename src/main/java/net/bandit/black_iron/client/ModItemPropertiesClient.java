package net.bandit.black_iron.client;


import net.bandit.black_iron.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.multiplayer.ClientLevel;

@Environment(EnvType.CLIENT)
public class ModItemPropertiesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        addCustomItemProperties();
    }

    public static void addCustomItemProperties() {
        makeBow();
    }

    private static void makeBow() {
        ItemProperties.register(ModItems.BLACK_IRON_BOW, new ResourceLocation("pull"), (ItemStack stack, ClientLevel world, LivingEntity entity, int seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemProperties.register(ModItems.BLACK_IRON_BOW, new ResourceLocation("pulling"), (ItemStack stack, ClientLevel world, LivingEntity entity, int seed) -> {
            return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
        });
    }
}
