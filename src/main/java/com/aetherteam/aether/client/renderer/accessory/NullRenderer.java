package com.aetherteam.aether.client.renderer.accessory;

import com.mojang.blaze3d.vertex.PoseStack;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class NullRenderer implements AccessoryRenderer {

    /**
     * Prevents rendering of capes as an item in default accessory slots.
     * @param stack The {@link ItemStack} for the Accessory.
     * @param slotContext The {@link SlotReference} for the Accessory.
     * @param poseStack The rendering {@link PoseStack}.
     * @param contextModel The {@link EntityModel<T>} for the renderer.
     * @param buffer The rendering {@link MultiBufferSource}.
     * @param packedLight The {@link Integer} for the packed lighting for rendering.
     * @param limbSwing The {@link Float} for the limb swing rotation.
     * @param limbSwingAmount The {@link Float} for the limb swing amount.
     * @param partialTicks The {@link Float} for the game's partial ticks.
     * @param ageInTicks The {@link Float} for the entity's age in ticks.
     * @param netHeadYaw The {@link Float} for the head yaw rotation.
     * @param headPitch The {@link Float} for the head pitch rotation.
     */
    @Override
    public <T extends LivingEntity> void render(ItemStack stack, SlotReference slotContext, PoseStack poseStack, EntityModel<T> contextModel, MultiBufferSource buffer, int packedLight, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    /**
     * Returns false always, as we render the cape as a layer.
     * @param isRendering
     * @return if the given Accessory should render or not based on the boolean provided
     */
    @Override
    public boolean shouldRender(boolean isRendering) {
        return false;
    }
}
