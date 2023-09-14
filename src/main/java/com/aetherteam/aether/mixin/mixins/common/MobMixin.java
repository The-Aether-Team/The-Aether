package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.capability.accessory.MobAccessory;
import com.aetherteam.aether.event.hooks.EntityHooks;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public class MobMixin {
    @Inject(at = @At(value = "HEAD"), method = "canTakeItem(Lnet/minecraft/world/item/ItemStack;)Z", cancellable = true)
    private void canTakeItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Mob mob = (Mob) (Object) this;
        if (EntityHooks.canMobSpawnWithAccessories(mob)) {
            String identifier = AetherMixinHooks.getIdentifierForItem(mob, stack);
            if (!identifier.isEmpty()) {
                ItemStack accessory = AetherMixinHooks.getItemByIdentifier(mob, identifier);
                if (accessory.isEmpty() && mob.canPickUpLoot()) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "equipItemIfPossible(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;", cancellable = true)
    private void equipItemIfPossible(ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        Mob mob = (Mob) (Object) this;
        MobAccessory.get(mob).ifPresent((accessoryMob) -> {
            String identifier = AetherMixinHooks.getIdentifierForItem(accessoryMob.getMob(), stack);
            if (!identifier.isEmpty()) {
                ItemStack accessory = AetherMixinHooks.getItemByIdentifier(accessoryMob.getMob(), identifier);
                boolean flag = AetherMixinHooks.canReplaceCurrentAccessory(accessoryMob.getMob(), stack, accessory);
                if (flag && accessoryMob.getMob().canHoldItem(stack)) {
                    double dropChance = accessoryMob.getEquipmentDropChance(identifier);
                    if (!accessory.isEmpty() && Math.max(accessoryMob.getMob().getRandom().nextFloat() - 0.1F, 0.0F) < dropChance) {
                        mob.spawnAtLocation(accessory);
                    }
                    AetherMixinHooks.setItemByIdentifier(mob, stack, identifier);
                    accessoryMob.setGuaranteedDrop(identifier);
                    mob.setPersistenceRequired();
                    cir.setReturnValue(stack);
                }
            }
        });
    }
}
