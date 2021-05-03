package com.gildedgames.aether.common.item.combat;

import com.gildedgames.aether.common.entity.projectile.AbstractDartEntity;
import com.gildedgames.aether.client.registry.AetherSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.function.Supplier;

import net.minecraft.item.Item.Properties;

public class DartShooterItem extends Item
{
    protected final Supplier<Item> ammoType;
    public DartShooterItem(Supplier<Item> ammo, Properties builder) {
        super(builder);
        this.ammoType = ammo;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        boolean flag = playerIn.abilities.instabuild;
        ItemStack heldItem = playerIn.getItemInHand(handIn);
        ItemStack ammo = this.findAmmo(playerIn);
        if(ammo.isEmpty() && !flag) {
            return ActionResult.fail(heldItem);
        }

        if (!worldIn.isClientSide) {
            DartItem dartItem;
            if(flag && ammo.isEmpty()) {
                dartItem = (DartItem) ammoType.get();
            }
            else {
                dartItem = (DartItem) ammo.getItem();
            }
            AbstractDartEntity dart = dartItem.createDart(worldIn, ammo, playerIn);
            dart.setNoGravity(true);
            dart.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.0F, 1.0F);
            if (flag) {
                dart.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
            }
            else {
                dart.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
            }
            worldIn.addFreshEntity(dart);
        }
        worldIn.playSound(playerIn, playerIn.blockPosition(), AetherSoundEvents.ITEM_DART_SHOOTER_SHOOT.get(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
        if (!flag) {
            ammo.shrink(1);
            if (ammo.isEmpty()) {
                playerIn.inventory.removeItem(ammo);
            }
        }
        return ActionResult.consume(heldItem);
    }

    private ItemStack findAmmo(PlayerEntity player) {
        IInventory inv = player.inventory;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack == ItemStack.EMPTY) {
                continue;
            }
            else {
                if(stack.getItem() == this.ammoType.get()) {
                    return stack;
                }
            }
        }
        return ItemStack.EMPTY;
    }
}
