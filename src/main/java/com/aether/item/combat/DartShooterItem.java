package com.aether.item.combat;

import com.aether.entity.projectile.AbstractDartEntity;
import com.aether.registry.AetherSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class DartShooterItem extends Item
{
    protected final Supplier<Item> ammoType;
    public DartShooterItem(Supplier<Item> ammo, Properties builder) {
        super(builder);
        this.ammoType = ammo;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        boolean flag = playerIn.abilities.isCreativeMode;
        ItemStack heldItem = playerIn.getHeldItem(handIn);
        ItemStack ammo = this.findAmmo(playerIn);
        if(ammo.isEmpty() && !flag) {
            return ActionResult.resultFail(heldItem);
        }

        if (!worldIn.isRemote) {
            DartItem dartItem;
            if(flag && ammo.isEmpty()) {
                dartItem = (DartItem) ammoType.get();
            }
            else {
                dartItem = (DartItem) ammo.getItem();
            }
            AbstractDartEntity dart = dartItem.createDart(worldIn, ammo, playerIn);
            dart.setNoGravity(true);
            dart.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.0F, 1.0F);
            if (flag) {
                dart.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
            }
            else {
                dart.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
            }
            worldIn.addEntity(dart);
        }
        worldIn.playSound(playerIn, playerIn.getPosition(), AetherSoundEvents.ENTITY_DART_SHOOTER_SHOOT.get(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
        if (!flag) {
            ammo.shrink(1);
            if (ammo.isEmpty()) {
                playerIn.inventory.deleteStack(ammo);
            }
        }
        return ActionResult.resultConsume(heldItem);
    }

    private ItemStack findAmmo(PlayerEntity player) {
        IInventory inv = player.inventory;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
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
