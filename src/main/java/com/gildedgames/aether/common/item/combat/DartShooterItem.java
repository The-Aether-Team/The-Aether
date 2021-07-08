package com.gildedgames.aether.common.item.combat;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.projectile.combat.AbstractDartEntity;
import com.google.common.collect.ImmutableSet;
import net.minecraft.enchantment.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class DartShooterItem extends ShootableItem implements IVanishable
{
    protected final Supplier<Item> dartType;

    public DartShooterItem(Supplier<Item> dartType, Properties builder) {
        super(builder);
        this.dartType = dartType;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack heldItem, World worldIn, LivingEntity livingEntity) {
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity) livingEntity;
            boolean ammoExists = playerentity.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, heldItem) > 0;
            ItemStack ammoItem = playerentity.getProjectile(heldItem);
            if (!ammoItem.isEmpty() || ammoExists) {
                if (ammoItem.isEmpty()) {
                    ammoItem = new ItemStack(this.dartType.get());
                }
                boolean shouldNotPickupAmmo = playerentity.abilities.instabuild || (ammoItem.getItem() instanceof DartItem && ((DartItem) ammoItem.getItem()).isInfinite(heldItem));
                if (!worldIn.isClientSide) {
                    DartItem dartItem = (DartItem) (ammoItem.getItem() instanceof DartItem ? ammoItem.getItem() : this.dartType.get());
                    AbstractDartEntity abstractDartEntity = dartItem.createDart(worldIn, playerentity);
                    abstractDartEntity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, 1.0F, 1.0F);
                    abstractDartEntity.setNoGravity(true);

                    int powerModifier = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, heldItem);
                    if (powerModifier > 0) {
                        abstractDartEntity.setBaseDamage(abstractDartEntity.getBaseDamage() + powerModifier * 0.5D + 0.5D);
                    }

                    int punchModifier = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, heldItem);
                    if (punchModifier > 0) {
                        abstractDartEntity.setKnockback(punchModifier);
                    }

                    if (shouldNotPickupAmmo || playerentity.abilities.instabuild) {
                        abstractDartEntity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                    }

                    worldIn.addFreshEntity(abstractDartEntity);
                }
                worldIn.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), AetherSoundEvents.ITEM_DART_SHOOTER_SHOOT.get(), SoundCategory.PLAYERS, 1.0F, 1.0F / (worldIn.getRandom().nextFloat() * 0.4F + 0.8F));
                if (!shouldNotPickupAmmo && !playerentity.abilities.instabuild) {
                    ammoItem.shrink(1);
                    if (ammoItem.isEmpty()) {
                        playerentity.inventory.removeItem(ammoItem);
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
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand hand) {
        ItemStack heldItem = playerIn.getItemInHand(hand);
        boolean hasAmmo = !playerIn.getProjectile(heldItem).isEmpty();

        if (playerIn.abilities.instabuild || hasAmmo) {
            playerIn.startUsingItem(hand);
            return ActionResult.consume(heldItem);
        } else {
            return ActionResult.fail(heldItem);
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