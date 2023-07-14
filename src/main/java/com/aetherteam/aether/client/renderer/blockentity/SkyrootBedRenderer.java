package com.aetherteam.aether.client.renderer.blockentity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.utility.SkyrootBedBlock;
import com.aetherteam.aether.blockentity.AetherBlockEntityTypes;
import com.aetherteam.aether.blockentity.SkyrootBedBlockEntity;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

import javax.annotation.Nonnull;

public class SkyrootBedRenderer implements BlockEntityRenderer<SkyrootBedBlockEntity>
{
    private final ModelPart headRoot;
    private final ModelPart footRoot;

    public SkyrootBedRenderer(BlockEntityRendererProvider.Context context) {
        this.headRoot = context.bakeLayer(AetherModelLayers.SKYROOT_BED_HEAD);
        this.footRoot = context.bakeLayer(AetherModelLayers.SKYROOT_BED_FOOT);
    }

    public void render(SkyrootBedBlockEntity bed, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        Level level = bed.getLevel();
        if (level != null) {
            BlockState blockstate = bed.getBlockState();
            DoubleBlockCombiner.NeighborCombineResult<? extends SkyrootBedBlockEntity> combineResult = DoubleBlockCombiner.combineWithNeigbour(AetherBlockEntityTypes.SKYROOT_BED.get(), SkyrootBedBlock::getBlockType, SkyrootBedBlock::getConnectedDirection, ChestBlock.FACING, blockstate, level, bed.getBlockPos(), (p_112202_, p_112203_) -> false);
            int i = combineResult.apply(new BrightnessCombiner<>()).get(combinedLight);
            this.renderPiece(poseStack, buffer, blockstate.getValue(SkyrootBedBlock.PART) == BedPart.HEAD ? this.headRoot : this.footRoot, blockstate.getValue(SkyrootBedBlock.FACING), i, combinedOverlay, false);
        } else {
            this.renderPiece(poseStack, buffer, this.headRoot, Direction.SOUTH, combinedLight, combinedOverlay, false);
            this.renderPiece(poseStack, buffer, this.footRoot, Direction.SOUTH, combinedLight, combinedOverlay, true);
        }
    }

    private void renderPiece(PoseStack poseStack, MultiBufferSource buffer, ModelPart model, Direction direction, int packedLight, int packedOverlay, boolean foot) {
        poseStack.pushPose();
        poseStack.translate(0.0, 0.5625, foot ? -1.0 : 0.0);
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F + direction.toYRot()));
        poseStack.translate(-0.5, -0.5, -0.5);
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entitySolid(new ResourceLocation(Aether.MODID, "textures/entity/tiles/bed/skyroot_bed.png")));
        model.render(poseStack, vertexconsumer, packedLight, packedOverlay);
        poseStack.popPose();
    }
}
