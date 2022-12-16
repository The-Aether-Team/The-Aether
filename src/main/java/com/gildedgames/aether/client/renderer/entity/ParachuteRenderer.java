package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.entity.miscellaneous.Parachute;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.data.ModelData;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ParachuteRenderer extends EntityRenderer<Parachute> {
    private final Supplier<Block> parachuteBlock;

    public ParachuteRenderer(EntityRendererProvider.Context context, Supplier<Block> parachuteBlock) {
        super(context);
        this.parachuteBlock = parachuteBlock;
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(@Nonnull Parachute parachute, float entityYaw, float partialTicks, PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        Entity passenger = parachute.getControllingPassenger();
        if (passenger != null) {
            if (passenger instanceof Player player) {
                poseStack.mulPose(Axis.YP.rotationDegrees(-Mth.lerp(partialTicks, player.yHeadRotO, player.yHeadRot)));
            } else {
                poseStack.mulPose(Axis.YP.rotationDegrees(-Mth.lerp(partialTicks, passenger.yRotO, passenger.getYRot())));
            }
        }
        poseStack.translate(-0.5, 0.0, -0.5);
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(this.parachuteBlock.get().defaultBlockState(), poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.translucent());
        poseStack.popPose();
        super.render(parachute, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull Parachute parachute) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
