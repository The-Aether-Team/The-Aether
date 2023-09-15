package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(SmithingScreen.class)
public class SmithingScreenMixin {
    @Shadow
    @Nullable
    private ArmorStand armorStandPreview;

    @Inject(at = @At("HEAD"), method = "updateArmorStandPreview(Lnet/minecraft/world/item/ItemStack;)V", cancellable = true)
    private void updateArmorStandPreview(ItemStack stack, CallbackInfo ci) {
        if (this.armorStandPreview != null) {
            String identifier = AetherConfig.COMMON.use_curios_menu.get() ? "hands" : "aether_gloves";
            AetherMixinHooks.setItemByIdentifier(this.armorStandPreview, ItemStack.EMPTY, identifier);
            if (!stack.isEmpty()) {
                ItemStack itemStack = stack.copy();
                Item item = stack.getItem();
                if (item instanceof GlovesItem) {
                    AetherMixinHooks.setItemByIdentifier(this.armorStandPreview, itemStack, identifier);
                    ci.cancel();
                }
            }
        }
    }
}
