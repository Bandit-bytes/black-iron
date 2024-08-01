package net.bandit.black_iron.client.renderer;

import mod.azure.azurelib.renderer.GeoArmorRenderer;
import net.bandit.black_iron.client.model.CustomHelmetModel;
import net.bandit.black_iron.item.custom.CustomHelmet;
import net.minecraft.resources.ResourceLocation;

public class CustomHelmetRenderer extends GeoArmorRenderer<CustomHelmet> {

    public CustomHelmetRenderer() {
        super(new CustomHelmetModel());

    }

}
