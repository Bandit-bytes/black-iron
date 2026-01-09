package net.bandit.black_iron.client.renderer;

import mod.azure.azurelib.render.armor.AzArmorRenderer;
import mod.azure.azurelib.render.armor.AzArmorRendererConfig;
import net.bandit.black_iron.BlackIronMod;
import net.minecraft.resources.ResourceLocation;

public class CustomHelmetRenderer extends AzArmorRenderer {

    private static final ResourceLocation GEO = BlackIronMod.modResource(
            "geo/blackironhelmet.geo.json"
    );

    private static final ResourceLocation TEX = BlackIronMod.modResource(
            "textures/item/black_iron_helmet.png"
    );

    public CustomHelmetRenderer() {
        super(
                AzArmorRendererConfig.builder(GEO, TEX)
                        .build()
        );
    }
}

