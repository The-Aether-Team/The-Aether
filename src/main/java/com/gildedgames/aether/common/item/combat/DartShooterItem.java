package com.gildedgames.aether.common.item.combat;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.projectile.dart.AbstractDartEntity;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;
import java.util.function.Supplier;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class DartShooterItem extends ProjectileWeaponItem implements Vanishable
{
    protected final Supplier<Item> dartType;

    public DartShooterItem(Supplier<Item> dartType, Properties builder) {
        super(builder);
        this.dartType = dartType;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack heldItem, Level worldIn, LivingEntity livingEntity) {
        if (livingEntity instanceof Player) {
            Player playerentity = (Player) livingEntity;
            boolean ammoExists = playerentity.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, heldItem) > 0;
            ItemStack ammoItem = playerentity.getProjectile(heldItem);
            if (!ammoItem.isEmpty() || ammoExists) {
                if (ammoItem.isEmpty()) {
                    ammoItem = new ItemStack(this.dartType.get());
                }
                boolean shouldNotPickupAmmo = playerentity.getAbilities().instabuild || (ammoItem.getItem() instanceof DartItem && ((DartItem) ammoItem.getItem()).isInfinite(heldItem));
                if (!worldIn.isClientSide) {
                    DartItem dartItem = (DartItem) (ammoItem.getItem() instanceof DartItem ? ammoItem.getItem() : this.dartType.get());
                    AbstractDartEntity abstractDartEntity = dartItem.createDart(worldIn, playerentity);
                    abstractDartEntity.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F, 1.0F, 1.0F);
                    abstractDartEntity.setNoGravity(true);

                    int powerModifier = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, heldItem);
                    if (powerModifier > 0) {
                        abstractDartEntity.setBaseDamage(abstractDartEntity.getBaseDamage() + powerModifier * 0.5D + 0.5D);
                    }

                    int punchModifier = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, heldItem);
                    if (punchModifier > 0) {
                        abstractDartEntity.setKnockback(punchModifier);
                    }

                    if (shouldNotPickupAmmo || playerentity.getAbilities().instabuild) {
                        abstractDartEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    worldIn.addFreshEntity(abstractDartEntity);
                }
                worldIn.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), AetherSoundEvents.ITEM_DART_SHOOTER_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (worldIn.getRandom().nextFloat() * 0.4F + 0.8F));
                if (!shouldNotPickupAmmo && !playerentity.getAbilities().instabuild) {
                    ammoItem.shrink(1);
                    if (ammoItem.isEmpty()) {
                        playerentity.getInventory().removeItem(ammoItem);
                    }
                }
                playerentity.awardStat(Stats.ITEM_USED.get(this));
            }
        }
        return heldItem;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 4;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand hand) {
        ItemStack heldItem = playerIn.getItemInHand(hand);
        boolean hasAmmo = !playerIn.getProjectile(heldItem).isEmpty();

        if (playerIn.getAbilities().instabuild || hasAmmo) {
            playerIn.startUsingItem(hand);
            return InteractionResultHolder.consume(heldItem);
        } else {
            return InteractionResultHolder.fail(heldItem);
        }
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return (stack) -> stack.getItem() == this.dartType.get();
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return ImmutableSet.of(Enchantments.POWER_ARROWS, Enchantments.PUNCH_ARROWS).contains(enchantment);
    }
}