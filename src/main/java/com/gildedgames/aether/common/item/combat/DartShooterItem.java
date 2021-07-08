package com.gildedgames.aether.common.item.combat;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.projectile.combat.AbstractDartEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
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
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand hand) {
        ItemStack heldItem = playerIn.getItemInHand(hand);
        ItemStack ammoItem = playerIn.getProjectile(heldItem);
        boolean ammoExists = !ammoItem.isEmpty();
        if (playerIn.abilities.instabuild || ammoExists) {
            if (!ammoExists) {
                ammoItem = new ItemStack(this.dartType.get());
            }
            boolean shouldNotPickupAmmo = playerIn.abilities.instabuild || (ammoItem.getItem() instanceof DartItem && ((DartItem) ammoItem.getItem()).isInfinite(heldItem));
            if (!worldIn.isClientSide) {
                DartItem dartItem = (DartItem) (ammoItem.getItem() instanceof DartItem ? ammoItem.getItem() : this.dartType.get());
                AbstractDartEntity abstractDartEntity = dartItem.createDart(worldIn, playerIn);
                abstractDartEntity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.0F, 1.0F);
                abstractDartEntity.setNoGravity(true);

                int powerModifier = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, heldItem);
                if (powerModifier > 0 && abstractDartEntity.getBaseDamage() > 0.0D) {
                    abstractDartEntity.setBaseDamage(abstractDartEntity.getBaseDamage() + powerModifier * 0.5D + 0.5D);
                }

                int punchModifier = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, heldItem);
                if (punchModifier > 0) {
                    abstractDartEntity.setKnockback(punchModifier);
                }

                heldItem.hurtAndBreak(1, playerIn, (p_220009_1_) -> p_220009_1_.broadcastBreakEvent(playerIn.getUsedItemHand()));
                if (shouldNotPickupAmmo || playerIn.abilities.instabuild) {
                    abstractDartEntity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                }

                worldIn.addFreshEntity(abstractDartEntity);
            }
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), AetherSoundEvents.ITEM_DART_SHOOTER_SHOOT.get(), SoundCategory.PLAYERS, 1.0F, 1.0F / (worldIn.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!shouldNotPickupAmmo && !playerIn.abilities.instabuild) {
                ammoItem.shrink(1);
                if (!ammoExists) {
                    playerIn.inventory.removeItem(ammoItem);
                }
            }
            playerIn.awardStat(Stats.ITEM_USED.get(this));
            return ActionResult.consume(heldItem);
        } else {
            return ActionResult.fail(heldItem);
        }
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return (p_220003_0_) -> p_220003_0_.getItem() == this.dartType.get();
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }
}