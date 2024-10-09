package com.aetherteam.aether.client.renderer.accessory;

import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.accessory.model.PendantModel;
import com.aetherteam.aether.item.accessories.pendant.PendantItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.client.SimpleAccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class PendantRenderer implements SimpleAccessoryRenderer {
    private final PendantModel pendant;

    public PendantRenderer() {
        this.pendant = new PendantModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.PENDANT));
    }

    @Override
    public <M extends LivingEntity> void render(ItemStack stack, SlotReference reference, PoseStack poseStack, EntityModel<M> entityModel, MultiBufferSource multiBufferSource, int packedLight, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        PendantItem pendantItem = (PendantItem) stack.getItem();
        this.align(stack, reference, this.pendant, poseStack);
        VertexConsumer consumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(pendantItem.getPendantTexture()), false);
        this.pendant.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public <M extends LivingEntity> void align(ItemStack stack, SlotReference reference, EntityModel<M> model, PoseStack poseStack) {
        if (model instanceof HumanoidModel<? extends LivingEntity> humanoidModel) {
            AccessoryRenderer.followBodyRotations(reference.entity(), (HumanoidModel<LivingEntity>) humanoidModel);
        }
    }
}
