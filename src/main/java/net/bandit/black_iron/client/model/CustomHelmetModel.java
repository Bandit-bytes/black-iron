package net.bandit.black_iron.client.model;

import mod.azure.azurelib.model.GeoModel;
import net.bandit.black_iron.BlackIronMod;
import net.bandit.black_iron.item.custom.CustomHelmet;
import net.minecraft.resources.ResourceLocation;

public class CustomHelmetModel extends GeoModel<CustomHelmet> {
    private static  final ResourceLocation model = new ResourceLocation(BlackIronMod.MOD_ID, "geo/blackironhelmet.geo.json");
    private static  final ResourceLocation texture = new ResourceLocation(BlackIronMod.MOD_ID, "textures/item/black_iron_helmet.png");
    private static  final ResourceLocation animation = new ResourceLocation(BlackIronMod.MOD_ID, "geo/black_iron_helmet.json");
    @Override
    public ResourceLocation getModelResource(CustomHelmet customHelmet) {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(CustomHelmet customHelmet) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(CustomHelmet customHelmet) {
        return animation;
    }
}
