package com.gildedgames.aether.common.item.combat.loot;

import com.gildedgames.aether.common.entity.projectile.combat.HammerProjectileEntity;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;;

public class NotchHammerItem extends SwordItem
{
    public NotchHammerItem() {
        super(ItemTier.IRON, 3, -2.4F, new Item.Properties().rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack hammer = player.getItemInHand(hand);
        world.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.GHAST_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F), false);

        if(!world.isClientSide) {
            HammerProjectileEntity hammerProjectile = new HammerProjectileEntity(world, player);
            hammerProjectile.shoot(player, player.xRot, player.yRot, 0.0F, 1.5F, 1.0F);
            world.addFreshEntity(hammerProjectile);
            if(!player.abilities.instabuild) {
                player.getCooldowns().addCooldown(this, 200);
                hammer.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            }
        }
        return ActionResult.success(hammer);
    }
}
