package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.ISlotType;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.common.inventory.CurioStacksHandler;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mixin(SmithingScreen.class)
public class SmithingScreenMixin {
    @Shadow
    @Nullable
    private ArmorStand armorStandPreview;

    @SuppressWarnings("deprecated")
    @Inject(at = @At("HEAD"), method = "updateArmorStandPreview(Lnet/minecraft/world/item/ItemStack;)V", cancellable = true)
    private void updateArmorStandPreview(ItemStack stack, CallbackInfo ci) {
        if (this.armorStandPreview != null) {
            String identifier = AetherConfig.COMMON.use_curios_menu.get() ? "hands" : "aether_gloves";
            LazyOptional<ICuriosItemHandler> lazyHandler = CuriosApi.getCuriosInventory(this.armorStandPreview);
            if (lazyHandler.isPresent() && lazyHandler.resolve().isPresent()) {
                ICuriosItemHandler handler = lazyHandler.resolve().get();
                ISlotType slot = CuriosApi.getEntitySlots(this.armorStandPreview.getType()).get(identifier);
                handler.setCurios(new HashMap<>(Map.of(identifier, new CurioStacksHandler(handler, slot.getIdentifier(), slot.getSize(), slot.useNativeGui(), slot.hasCosmetic(), slot.canToggleRendering(), slot.getDropRule()))));
                Optional<ICurioStacksHandler> stacksHandler = handler.getStacksHandler(identifier);
                if (stacksHandler.isPresent()) {
                    IDynamicStackHandler stackHandler = stacksHandler.get().getCosmeticStacks();
                    if (0 < stackHandler.getSlots()) {
                        stackHandler.setStackInSlot(0, ItemStack.EMPTY);
                        if (!stack.isEmpty()) {
                            Item item = stack.getItem();
                            if (item instanceof GlovesItem) {
                                stackHandler.setStackInSlot(0, stack.copy());
                                ci.cancel();
                            }
                        }
                    }
                }
            }
        }
    }
}
