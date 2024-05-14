package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.item.accessories.cape.CapeItem;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
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
     * @param original The original {@link ResourceLocation} of the texture for the elytra on this armor stand.
     * @param stack  The elytra {@link ItemStack}.
     * @param entity The {@link LivingEntity} wearing the elytra.
     * @return If the armor stand has an equipped cape, the cape texture, else returns the original texture.
     */
    @ModifyReturnValue(at = @At("RETURN"), method = "getElytraTexture(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/resources/ResourceLocation;", remap = false)
    private ResourceLocation getElytraTexture(ResourceLocation original, @Local(ordinal = 0, argsOnly = true) ItemStack stack, @Local(ordinal = 0, argsOnly = true) T entity) {
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
                        if (texture != null)
                            return texture;
                    }
                }
            }
        }
        return original;
    }
}
