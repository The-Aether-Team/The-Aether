package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Optional;

@Mixin(ElytraLayer.class)
public class ElytraLayerMixin<T extends LivingEntity> {
    @Inject(at = @At("HEAD"), method = "getElytraTexture(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true, remap = false)
    private void getElytraTexture(ItemStack stack, T entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (entity instanceof ArmorStand armorStand) {
            String identifier = AetherConfig.COMMON.use_curios_menu.get() ? "back" : "aether_cape";
            LazyOptional<ICuriosItemHandler> lazyHandler = CuriosApi.getCuriosInventory(armorStand);
            if (lazyHandler.isPresent() && lazyHandler.resolve().isPresent()) {
                ICuriosItemHandler handler = lazyHandler.resolve().get();
                Optional<ICurioStacksHandler> stacksHandler = handler.getStacksHandler(identifier);
                if (stacksHandler.isPresent()) {
                    IDynamicStackHandler stackHandler = stacksHandler.get().getCosmeticStacks();
                    if (0 < stackHandler.getSlots()) {
                        ItemStack itemStack = stackHandler.getStackInSlot(0);
                        ResourceLocation texture = AetherMixinHooks.getCapeTexture(itemStack);
                        if (texture != null) {
                            cir.setReturnValue(texture);
                        }
                    }
                }
            }
        }
    }
}
