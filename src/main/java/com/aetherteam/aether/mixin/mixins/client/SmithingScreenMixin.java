package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.data.SlotTypeLoader;
import io.wispforest.accessories.impl.ExpandedSimpleContainer;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.jetbrains.annotations.Nullable;

@Mixin(SmithingScreen.class)
public class SmithingScreenMixin {
    @Shadow
    @Nullable
    private ArmorStand armorStandPreview;

    /**
     * Renders gloves on the armor stand in the smithing screen when applying armor trims to them.
     * @param stack The {@link ItemStack} to try to render on the armor stand.
     * @param ci The {@link CallbackInfo} for the void method return.
     */
    @Inject(at = @At("HEAD"), method = "updateArmorStandPreview(Lnet/minecraft/world/item/ItemStack;)V", cancellable = true)
    private void updateArmorStandPreview(ItemStack stack, CallbackInfo ci) {
        if (this.armorStandPreview != null) {
            String identifier = AetherConfig.COMMON.use_curios_menu.get() ? "hands" : "aether_gloves";
            AccessoriesCapability handler = this.armorStandPreview.accessoriesCapability();
            if (handler != null) {
                AccessoriesContainer container = handler.getContainer(SlotTypeLoader.getSlotType(this.armorStandPreview, identifier));
                if (container != null) {
                    ExpandedSimpleContainer accessories = container.getAccessories();
                    accessories.setItem(0, ItemStack.EMPTY);
                    for (EquipmentSlot slot : EquipmentSlot.values()) {
                        this.armorStandPreview.setItemSlot(slot, ItemStack.EMPTY);
                    }
                    if (!stack.isEmpty()) {
                        ItemStack itemStack = stack.copy();
                        Item item = stack.getItem();
                        if (item instanceof GlovesItem) {
                            accessories.setItem(0, itemStack);
                            ci.cancel();
                        }
                    }
                }
            }
        }
    }
}
