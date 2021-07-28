package com.gildedgames.aether.common.item.combat.loot;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.projectile.weapon.HammerProjectileEntity;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class HammerOfNotchItem extends SwordItem
{
    public HammerOfNotchItem() {
        super(ItemTier.IRON, 3, -2.4F, new Item.Properties().rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand hand) {
        ItemStack heldItem = playerIn.getItemInHand(hand);
        if (!worldIn.isClientSide) {
            if (!playerIn.abilities.instabuild) {
                playerIn.getCooldowns().addCooldown(this, 200);
                heldItem.hurtAndBreak(1, playerIn, (p) -> p.broadcastBreakEvent(hand));
            }
            HammerProjectileEntity hammerProjectile = new HammerProjectileEntity(playerIn, worldIn);
            hammerProjectile.shoot(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.5F, 1.0F);
            worldIn.addFreshEntity(hammerProjectile);
        }
        worldIn.playLocalSound(playerIn.getX(), playerIn.getY(), playerIn.getZ(), AetherSoundEvents.ITEM_HAMMER_OF_NOTCH_SHOOT.get(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F), false);
        playerIn.awardStat(Stats.ITEM_USED.get(this));
        return ActionResult.success(heldItem);
    }
}
