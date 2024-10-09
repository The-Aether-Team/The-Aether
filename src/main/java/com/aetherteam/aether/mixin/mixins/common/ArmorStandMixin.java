package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.item.accessories.SlotIdentifierHolder;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ArmorStand.class)
public class ArmorStandMixin {
    /**
     * Allows {@link ArmorStand}s to accept accessories from {@link net.minecraft.world.entity.EntitySelector.MobCanWearArmorEntitySelector}.
     *
     * @param original Whether an item could have been taken before.
     * @param stack The {@link ItemStack}.
     * @return Whether {@link ArmorStand} can take from a curios or aether slot, otherwise whether it could have before.
     */
    @ModifyReturnValue(at = @At(value = "RETURN"), method = "canTakeItem(Lnet/minecraft/world/item/ItemStack;)Z")
    private boolean canTakeItem(boolean original, @Local(ordinal = 0, argsOnly = true) ItemStack stack) {
        ArmorStand armorStand = (ArmorStand) (Object) this;
        SlotTypeReference identifier = null;
        if (stack.getItem() instanceof SlotIdentifierHolder slotIdentifierHolder)
            identifier = slotIdentifierHolder.getIdentifier();

        if (identifier != null) {
            ItemStack accessory = AetherMixinHooks.getItemByIdentifier(armorStand, identifier);
            if (accessory.isEmpty()) return true;
        }
        return original;
    }
}
