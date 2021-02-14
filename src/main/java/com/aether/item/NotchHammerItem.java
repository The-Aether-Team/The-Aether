package com.aether.item;

import com.aether.entity.projectile.HammerProjectileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;;

public class NotchHammerItem extends SwordItem {
    public NotchHammerItem(Properties properties) {
        super(ItemTier.IRON, 2, -2.4F, properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack hammer = player.getHeldItem(hand);
        world.playSound(player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F), false);

        if(!world.isRemote) {
            HammerProjectileEntity hammerProjectile = new HammerProjectileEntity(world, player);
            hammerProjectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
            world.addEntity(hammerProjectile);
            if(!player.abilities.isCreativeMode) {
                player.getCooldownTracker().setCooldown(this, 200);
                hammer.damageItem(1, player, (p) -> p.sendBreakAnimation(hand));
            }
        }
        return ActionResult.resultSuccess(hammer);
    }
}
