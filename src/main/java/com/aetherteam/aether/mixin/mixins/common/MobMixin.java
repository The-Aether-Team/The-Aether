package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.attachment.AetherDataAttachments;
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
    /**
     * Allows {@link Mob}s to accept accessories from {@link net.minecraft.world.entity.EntitySelector.MobCanWearArmorEntitySelector}.
     *
     * @param stack The {@link ItemStack}.
     * @param cir   The {@link Boolean} {@link CallbackInfoReturnable} used for the method's return value.
     */
    @Inject(at = @At(value = "HEAD"), method = "canTakeItem(Lnet/minecraft/world/item/ItemStack;)Z", cancellable = true)
    private void canTakeItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Mob mob = (Mob) (Object) this;
        if (EntityHooks.canMobSpawnWithAccessories(mob)) {
            String identifier = AetherMixinHooks.getIdentifierForItem(mob, stack);
            if (!identifier.isEmpty()) {
                ItemStack accessory = AetherMixinHooks.getItemByIdentifier(mob, identifier);
                if (accessory.isEmpty()) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

    /**
     * Handles equipping accessories for {@link Mob}s.
     *
     * @param stack The {@link ItemStack}.
     * @param cir   The {@link Boolean} {@link CallbackInfoReturnable} used for the method's return value.
     */
    @Inject(at = @At(value = "HEAD"), method = "equipItemIfPossible(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;", cancellable = true)
    private void equipItemIfPossible(ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        Mob mob = (Mob) (Object) this;
        var data = mob.getData(AetherDataAttachments.MOB_ACCESSORY);
        String identifier = AetherMixinHooks.getIdentifierForItem(mob, stack);
        if (!identifier.isEmpty()) {
            ItemStack accessory = AetherMixinHooks.getItemByIdentifier(mob, identifier);
            boolean flag = AetherMixinHooks.canReplaceCurrentAccessory(mob, stack, accessory);
            if (flag && mob.canHoldItem(stack)) {
                double dropChance = data.getEquipmentDropChance(identifier);
                if (!accessory.isEmpty() && Math.max(mob.getRandom().nextFloat() - 0.1F, 0.0F) < dropChance) {
                    mob.spawnAtLocation(accessory);
                }
                AetherMixinHooks.setItemByIdentifier(mob, stack, identifier);
                data.setGuaranteedDrop(identifier);
                mob.setPersistenceRequired();
                cir.setReturnValue(stack);
            }
        }
    }
}
