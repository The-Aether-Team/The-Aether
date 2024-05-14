package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.item.accessories.cape.CapeItem;
import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import com.aetherteam.aether.item.accessories.miscellaneous.ShieldOfRepulsionItem;
import com.aetherteam.aether.item.accessories.pendant.PendantItem;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
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
        String identifier = "";
        if (stack.getItem() instanceof GlovesItem) {
            identifier = AetherConfig.COMMON.use_curios_menu.get() ? "hands" : "aether_gloves";
        } else if (stack.getItem() instanceof PendantItem) {
            identifier = AetherConfig.COMMON.use_curios_menu.get() ? "necklace" : "aether_pendant";
        } else if (stack.getItem() instanceof CapeItem) {
            identifier = AetherConfig.COMMON.use_curios_menu.get() ? "back" : "aether_cape";
        } else if (stack.getItem() instanceof ShieldOfRepulsionItem) {
            identifier = AetherConfig.COMMON.use_curios_menu.get() ? "body" : "aether_shield";
        }

        if (!identifier.isEmpty()) {
            ItemStack accessory = AetherMixinHooks.getItemByIdentifier(armorStand, identifier);
            if (accessory.isEmpty()) return true;
        }
        return original;
    }
}
