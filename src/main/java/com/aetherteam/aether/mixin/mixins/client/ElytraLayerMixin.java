package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

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

            Player player = Minecraft.getInstance().player;
            SlotEntryReference result = EquipmentUtil.getCape(armorStand);
            if (result != null && AetherMixinHooks.isCapeVisible(player)) {
                ResourceLocation texture = AetherMixinHooks.getCapeTexture(result.stack());
                if (texture != null)
                    return texture;
            }


//            String identifier = CapeItem.getIdentifierStatic();
//            Optional<ICuriosItemHandler> lazyHandler = CuriosApi.getCuriosInventory(armorStand);
//            if (lazyHandler.isPresent()) {
//                ICuriosItemHandler handler = lazyHandler.get();
//                Optional<ICurioStacksHandler> stacksHandler = handler.getStacksHandler(identifier);
//                if (stacksHandler.isPresent()) {
//                    IDynamicStackHandler stackHandler = stacksHandler.get().getCosmeticStacks();
//                    if (0 < stackHandler.getSlots()) {
//                        ItemStack itemStack = stackHandler.getStackInSlot(0);
//                        ResourceLocation texture = AetherMixinHooks.getCapeTexture(itemStack);
//                        if (texture != null)
//                            return texture;
//                    }
//                }
//            }
        }
        return original;
    }
}
