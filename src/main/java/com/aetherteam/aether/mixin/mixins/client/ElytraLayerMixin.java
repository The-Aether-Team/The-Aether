package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.item.accessories.cape.CapeItem;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
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
    /**
     * Used to change the elytra texture on an armor stand based on the equipped cape.
     *
     * @param stack  The elytra {@link ItemStack}.
     * @param entity The entity wearing the elytra.
     * @param cir    The {@link ResourceLocation} {@link CallbackInfoReturnable} used for the method's return value.
     */
    @Inject(at = @At("HEAD"), method = "getElytraTexture(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true, remap = false)
    private void getElytraTexture(ItemStack stack, T entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (entity instanceof ArmorStand armorStand) {
            String identifier = CapeItem.getIdentifierStatic();
            Optional<ICuriosItemHandler> lazyHandler = CuriosApi.getCuriosInventory(armorStand);
            if (lazyHandler.isPresent()) {
                ICuriosItemHandler handler = lazyHandler.get();
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
