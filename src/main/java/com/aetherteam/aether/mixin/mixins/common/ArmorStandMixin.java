package com.aetherteam.aether.mixin.mixins.common;

import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ArmorStand.class)
public class ArmorStandMixin { //todo figure out equip code changes
//    /**
//     * Allows {@link ArmorStand}s to accept accessories from {@link net.minecraft.world.entity.EntitySelector.MobCanWearArmorEntitySelector}.
//     *
//     * @param original Whether an item could have been taken before.
//     * @param stack The {@link ItemStack}.
//     * @return Whether {@link ArmorStand} can take from an accessory slot, otherwise whether it could have before.
//     */
//    @ModifyReturnValue(at = @At(value = "RETURN"), method = "canTakeItem(Lnet/minecraft/world/item/ItemStack;)Z")
//    private boolean canTakeItem(boolean original, @Local(ordinal = 0, argsOnly = true) ItemStack stack) {
//        ArmorStand armorStand = (ArmorStand) (Object) this;
//        SlotTypeReference identifier = null;
//        if (stack.getItem() instanceof SlotIdentifierHolder slotIdentifierHolder)
//            identifier = slotIdentifierHolder.getIdentifier();
//
//        if (identifier != null) {
//            ItemStack accessory = AetherMixinHooks.getItemByIdentifier(armorStand, identifier);
//            if (accessory.isEmpty()) return true;
//        }
//        return original;
//    }
}
