package net.bandit.black_iron.item.custom;

import net.bandit.black_iron.item.ModToolMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlackIronDeathspade extends ShovelItem {

    public BlackIronDeathspade(ModToolMaterial tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties
                .stacksTo(1)
                .rarity(Rarity.EPIC));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player) {
            Level level = attacker.level();
            if (!level.isClientSide()) {
                BlockPos pos = target.blockPosition();
                AABB area = new AABB(pos).inflate(2.5);

                List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area,
                        e -> e != attacker && e != target && e.isAlive());

                for (LivingEntity e : entities) {
                    e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 0)); // 1.5s slow
                }

                level.playSound(null, pos, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0F, 0.8F);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            BlockPos pos = player.blockPosition();
            AABB area = player.getBoundingBox().inflate(3.5);

            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area,
                    e -> e != player && e.isAlive());

            for (LivingEntity e : entities) {
                Vec3 away = e.position().subtract(player.position()).normalize().scale(1.1);
                e.push(away.x, 0.35, away.z);
                e.hurt(player.damageSources().playerAttack(player), 4.0F);
                e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
            }

            player.getCooldowns().addCooldown(this, 100); // 5s
            level.playSound(null, pos, SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 1.0F, 0.9F);
            return InteractionResultHolder.success(stack);
        }

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("item.black_iron.black_iron_deathspade.tooltip1")
                .withStyle(net.minecraft.ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.black_iron.black_iron_deathspade.tooltip2")
                .withStyle(net.minecraft.ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("item.black_iron.black_iron_deathspade.tooltip3")
                .withStyle(net.minecraft.ChatFormatting.GOLD));
        super.appendHoverText(stack, world, tooltip, context);
    }
}
