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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlackIronHellspike extends PickaxeItem {

    public BlackIronHellspike(ModToolMaterial tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties
                .stacksTo(1)
                .rarity(Rarity.EPIC));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player) {
            Level level = attacker.level();
            if (!level.isClientSide()) {
                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 80, 0)); // 4s

                if (target.getArmorValue() > 0) {
                    target.hurt(player.damageSources().playerAttack(player), 2.0F);
                    level.playSound(null, target.blockPosition(), SoundEvents.ANVIL_HIT, SoundSource.PLAYERS, 0.8F, 1.4F);
                } else {
                    level.playSound(null, target.blockPosition(), SoundEvents.PLAYER_ATTACK_STRONG, SoundSource.PLAYERS, 0.8F, 1.1F);
                }
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            LivingEntity hit = findLivingEntityInFront(player, 3.0);
            if (hit != null) {
                hit.hurt(player.damageSources().playerAttack(player), 6.0F);
                hit.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1)); // 5s weakness II
                level.playSound(null, hit.blockPosition(), SoundEvents.TRIDENT_HIT, SoundSource.PLAYERS, 1.0F, 1.0F);
                player.getCooldowns().addCooldown(this, 90);
                return InteractionResultHolder.success(stack);
            }

            level.playSound(null, player.blockPosition(), SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 0.7F, 1.3F);
            player.getCooldowns().addCooldown(this, 20);
        }

        return InteractionResultHolder.pass(stack);
    }

    /**
     * Very simple "in front" check without raytracing:
     * grabs the closest LivingEntity in a small cone-ish area.
     */
    @Nullable
    private LivingEntity findLivingEntityInFront(Player player, double range) {
        Level level = player.level();
        var look = player.getLookAngle().normalize();

        var box = player.getBoundingBox().expandTowards(look.scale(range)).inflate(1.0);

        List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, box,
                e -> e != player && e.isAlive());

        LivingEntity best = null;
        double bestDist = Double.MAX_VALUE;

        for (LivingEntity e : list) {
            var to = e.position().subtract(player.position());
            double dist = to.length();
            if (dist > range) continue;

            double dot = to.normalize().dot(look);
            if (dot < 0.6) continue;

            if (dist < bestDist) {
                bestDist = dist;
                best = e;
            }
        }

        return best;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("item.black_iron.black_iron_hellspike.tooltip1")
                .withStyle(net.minecraft.ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.black_iron.black_iron_hellspike.tooltip2")
                .withStyle(net.minecraft.ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("item.black_iron.black_iron_hellspike.tooltip3")
                .withStyle(net.minecraft.ChatFormatting.GOLD));
        super.appendHoverText(stack, world, tooltip, context);
    }
}
