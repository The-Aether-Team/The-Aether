package com.gildedgames.aether.common.item.combat.loot;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.projectile.weapon.HammerProjectileEntity;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.SwordItem;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

public class HammerOfNotchItem extends SwordItem
{
    public HammerOfNotchItem() {
        super(Tiers.IRON, 3, -2.4F, new Item.Properties().rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand hand) {
        ItemStack heldItem = playerIn.getItemInHand(hand);
        if (!worldIn.isClientSide) {
            if (!playerIn.getAbilities().instabuild) {
                playerIn.getCooldowns().addCooldown(this, 200);
                heldItem.hurtAndBreak(1, playerIn, (p) -> p.broadcastBreakEvent(hand));
            }
            HammerProjectileEntity hammerProjectile = new HammerProjectileEntity(playerIn, worldIn);
            hammerProjectile.shoot(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, 1.5F, 1.0F);
            if (heldItem.getHoverName().getString().equalsIgnoreCase("hammer of jeb")) {
                hammerProjectile.setIsJeb(true);
            }
            worldIn.addFreshEntity(hammerProjectile);
        }
        worldIn.playLocalSound(playerIn.getX(), playerIn.getY(), playerIn.getZ(), AetherSoundEvents.ITEM_HAMMER_OF_NOTCH_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (playerIn.getRandom().nextFloat() * 0.4F + 0.8F), false);
        playerIn.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.success(heldItem);
    }
}
