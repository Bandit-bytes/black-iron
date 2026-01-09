package net.bandit.black_iron.item.custom;

import net.bandit.black_iron.item.ModToolMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlackIronAxe extends SwordItem {

    public BlackIronAxe(ModToolMaterial tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties
                .stacksTo(1)
                .rarity(Rarity.EPIC));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player) {
            Level world = attacker.level();
            BlockPos pos = target.blockPosition();
            AABB areaOfEffect = new AABB(pos).inflate(3);

            List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, areaOfEffect, entity -> entity != attacker && entity != target);
            for (LivingEntity entity : entities) {
                entity.hurt(target.damageSources().playerAttack(player), 4.0F);
            }

            world.playSound(null, pos, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(3), entity -> entity != player);
            for (LivingEntity entity : entities) {
                entity.hurt(player.damageSources().playerAttack(player), 3.0F);
            }
            player.getCooldowns().addCooldown(this, 80);
            level.playSound(null, player.blockPosition(), SoundEvents.AXE_STRIP, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResultHolder.success(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("item.black_iron.black_iron_axe.tooltip1").withStyle(net.minecraft.ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.black_iron.black_iron_axe.tooltip2").withStyle(net.minecraft.ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("item.black_iron.black_iron_axe.tooltip3").withStyle(net.minecraft.ChatFormatting.GOLD));
        super.appendHoverText(stack, world, tooltip, context);
    }
}
