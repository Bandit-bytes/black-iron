package net.bandit.black_iron.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomHelmet extends ArmorItem  {

    public CustomHelmet(ArmorMaterial material, ArmorItem.Type type, Item.Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("black_iron.helmet.tooltip").withStyle(ChatFormatting.DARK_GRAY));
        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }
}
