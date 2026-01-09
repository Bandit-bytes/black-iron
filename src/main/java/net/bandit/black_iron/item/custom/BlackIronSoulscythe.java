package net.bandit.black_iron.item.custom;

import net.bandit.black_iron.item.ModToolMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlackIronSoulscythe extends HoeItem {

    public BlackIronSoulscythe(ModToolMaterial tier, int attackDamage, float attackSpeed, Properties properties) {
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
                AABB area = new AABB(pos).inflate(2.75);

                List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area,
                        e -> e != attacker && e.isAlive());

                int hits = 0;
                for (LivingEntity e : entities) {
                    e.hurt(player.damageSources().playerAttack(player), 3.0F);
                    hits++;
                }
                if (hits >= 2) {
                    float heal = Math.min(3.0F, 1.0F + hits * 0.5F);
                    player.heal(heal);
                }

                level.playSound(null, pos, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0F, 1.2F);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            AABB area = player.getBoundingBox().inflate(2.5);
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area,
                    e -> e != player && e.isAlive());

            int hits = 0;
            for (LivingEntity e : entities) {
                e.hurt(player.damageSources().playerAttack(player), 2.5F);
                hits++;
            }

            if (hits > 0) {
                player.heal(Math.min(2.5F, hits * 0.5F));
                level.playSound(null, player.blockPosition(), SoundEvents.SOUL_ESCAPE, SoundSource.PLAYERS, 0.8F, 1.0F);
            } else {
                level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.7F, 0.8F);
            }

            player.getCooldowns().addCooldown(this, 80); // 4s
            return InteractionResultHolder.success(stack);
        }

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("item.black_iron.black_iron_soulscythe.tooltip1")
                .withStyle(net.minecraft.ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.black_iron.black_iron_soulscythe.tooltip2")
                .withStyle(net.minecraft.ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("item.black_iron.black_iron_soulscythe.tooltip3")
                .withStyle(net.minecraft.ChatFormatting.GOLD));
        super.appendHoverText(stack, world, tooltip, context);
    }
}
