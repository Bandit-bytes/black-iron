package net.bandit.black_iron.item.custom;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlackIronBow extends BowItem {

    public BlackIronBow(Item.Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        // Check if the player can use the bow (has arrows or creative mode)
        boolean hasArrows = !player.getProjectile(itemStack).isEmpty() || player.getAbilities().instabuild;

        if (!hasArrows) {
            return InteractionResultHolder.fail(itemStack);
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity entity, int timeLeft) {
        if (entity instanceof Player player) {
            ItemStack arrowStack = player.getProjectile(stack);

            if (arrowStack.isEmpty() && !player.getAbilities().instabuild) {
                return;
            }

            int charge = this.getUseDuration(stack) - timeLeft;
            float pullProgress = getPowerForTime(charge);

            if (pullProgress < 0.1) {
                return;
            }

            boolean hasInfinityEnchantment = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;

            if (!world.isClientSide) {
                ArrowItem arrowItem = (ArrowItem) (arrowStack.getItem() instanceof ArrowItem ? arrowStack.getItem() : Items.ARROW);
                AbstractArrow arrowEntity = arrowItem.createArrow(world, arrowStack, player);
                arrowEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, pullProgress * 3.0F, 1.0F);

                if (pullProgress == 1.0F) {
                    arrowEntity.setCritArrow(true);
                }

                int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
                if (powerLevel > 0) {
                    arrowEntity.setBaseDamage(arrowEntity.getBaseDamage() + powerLevel * 0.5 + 0.5);
                }

                int punchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
                if (punchLevel > 0) {
                    arrowEntity.setKnockback(punchLevel);
                }

                if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
                    arrowEntity.setSecondsOnFire(100);
                }

                if (hasInfinityEnchantment || player.getAbilities().instabuild) {
                    arrowEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }

                world.addFreshEntity(arrowEntity);

                world.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F,
                        1.0F / (world.random.nextFloat() * 0.4F + 1.2F) + pullProgress * 0.5F);

                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
            }

            if (!hasInfinityEnchantment && !player.getAbilities().instabuild) {
                arrowStack.shrink(1);
                if (arrowStack.isEmpty()) {
                    player.getInventory().removeItem(arrowStack);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("item.black_iron.black_iron_bow.tooltip1").withStyle(net.minecraft.ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.black_iron.black_iron_bow.tooltip2").withStyle(net.minecraft.ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("item.black_iron.black_iron_bow.tooltip3").withStyle(net.minecraft.ChatFormatting.GOLD));
    }
}
