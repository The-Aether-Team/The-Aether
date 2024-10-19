package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.impl.ExpandedSimpleContainer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ElytraLayer.class)
public class ElytraLayerMixin<T extends LivingEntity> {
    /**
     * Used to change the elytra texture on an armor stand based on the equipped cape.
     *
     * @param poseStack     The current matrix stack {@link PoseStack}.
     * @param buffer        The buffer source.
     * @param packedLight   The packed light.
     * @param entity     The entity wearing the elytra.
     * @return
     */
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;armorCutoutNoCull(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"), method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V")
    private RenderType getElytraTexture(ResourceLocation location, Operation<RenderType> original, PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity) {
        if (entity instanceof ArmorStand armorStand) {
            String identifier = AetherConfig.COMMON.use_curios_menu.get() ? "back" : "aether_cape";
            AccessoriesCapability handler = armorStand.accessoriesCapability();
            if (handler != null) {
                AccessoriesContainer stacksHandler = handler.getContainers().get(identifier);
                if (stacksHandler != null) {
                    ExpandedSimpleContainer stackHandler = stacksHandler.getCosmeticAccessories();
                    if (0 < stackHandler.getContainerSize()) {
                        ItemStack itemStack = stackHandler.getItem(0);
                        ResourceLocation texture = AetherMixinHooks.getCapeTexture(itemStack);
                        if (texture != null) {
                            return original.call(texture);
                        }
                    }
                }
            }
        }
        return original.call(location);
    }
}
