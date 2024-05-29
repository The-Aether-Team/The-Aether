package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.event.hooks.EntityHooks;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
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
     * @param original Whether an item could have been taken before.
     * @param stack The {@link ItemStack}.
     * @return Whether this {@link Mob} can take from a curios or aether slot, otherwise whether it could have before.
     */
    @ModifyReturnValue(at = @At(value = "RETURN"), method = "canTakeItem(Lnet/minecraft/world/item/ItemStack;)Z")
    private boolean canTakeItem(boolean original, @Local(ordinal = 0, argsOnly = true) ItemStack stack) {
        Mob mob = (Mob) (Object) this;
        if (EntityHooks.canMobSpawnWithAccessories(mob)) {
            String identifier = AetherMixinHooks.getIdentifierForItem(mob, stack);
            if (!identifier.isEmpty()) {
                ItemStack accessory = AetherMixinHooks.getItemByIdentifier(mob, identifier);
                if (accessory.isEmpty()) return true;
            }
        }
        return original;
    }

    /**
     * Handles equipping accessories for {@link Mob}s.
     *
     * @param original The {@link ItemStack} returned by the target method.
     * @param stack The {@link ItemStack} provided to the target method.
     */
    @ModifyReturnValue(at = @At(value = "RETURN"), method = "equipItemIfPossible(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;")
    private ItemStack equipItemIfPossible(ItemStack original, @Local(ordinal = 0, argsOnly = true) ItemStack stack) {
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
                return stack;
            }
        }
        return original;
    }
}
