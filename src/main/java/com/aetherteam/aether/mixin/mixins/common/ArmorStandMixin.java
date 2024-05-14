package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.item.accessories.SlotIdentifierHolder;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStand.class)
public class ArmorStandMixin {
    /**
     * Allows {@link ArmorStand}s to accept accessories from {@link net.minecraft.world.entity.EntitySelector.MobCanWearArmorEntitySelector}.
     *
     * @param stack The {@link ItemStack}.
     * @param cir   The {@link Boolean} {@link CallbackInfoReturnable} used for the method's return value.
     */
    @Inject(at = @At(value = "HEAD"), method = "canTakeItem(Lnet/minecraft/world/item/ItemStack;)Z", cancellable = true)
    private void canTakeItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        ArmorStand armorStand = (ArmorStand) (Object) this;
        String identifier = "";
        if (stack.getItem() instanceof SlotIdentifierHolder slotIdentifierHolder)
            identifier = slotIdentifierHolder.getIdentifier();

        if (!identifier.isEmpty()) {
            ItemStack accessory = AetherMixinHooks.getItemByIdentifier(armorStand, identifier);
            if (accessory.isEmpty()) {
                cir.setReturnValue(true);
            }
        }
    }
}
