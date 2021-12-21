package com.gildedgames.aether.client.renderer.tile;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.block.utility.SkyrootBedBlock;
import com.gildedgames.aether.common.entity.tile.SkyrootBedTileEntity;
import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.Level;

public class SkyrootBedRenderer implements BlockEntityRenderer<SkyrootBedTileEntity>
{
    private final ModelPart headRoot;
    private final ModelPart footRoot;

    public SkyrootBedRenderer(BlockEntityRendererProvider.Context context) {
        headRoot = context.bakeLayer(ModelLayers.BED_HEAD);
        footRoot = context.bakeLayer(ModelLayers.BED_FOOT);
    }

    public void render(SkyrootBedTileEntity bed, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        Level level = bed.getLevel();
        if (level != null) {
            BlockState blockstate = bed.getBlockState();
            DoubleBlockCombiner.NeighborCombineResult<? extends BedBlockEntity> neighborcombineresult = DoubleBlockCombiner.combineWithNeigbour(BlockEntityType.BED, BedBlock::getBlockType, BedBlock::getConnectedDirection, ChestBlock.FACING, blockstate, level, bed.getBlockPos(), (p_112202_, p_112203_) -> {
                return false;
            });
            int i = neighborcombineresult.<Int2IntFunction>apply(new BrightnessCombiner<>()).get(combinedLightIn);
            this.renderPiece(poseStack, buffer, blockstate.getValue(BedBlock.PART) == BedPart.HEAD ? this.headRoot : this.footRoot, blockstate.getValue(BedBlock.FACING), i, combinedOverlayIn, false);
        } else {
            this.renderPiece(poseStack, buffer, this.headRoot, Direction.SOUTH, combinedLightIn, combinedOverlayIn, false);
            this.renderPiece(poseStack, buffer, this.footRoot, Direction.SOUTH, combinedLightIn, combinedOverlayIn, true);
        }

    }

    private void renderPiece(PoseStack poseStack, MultiBufferSource buffer, ModelPart model, Direction direction, int p_173547_, int p_173548_, boolean p_173549_) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.5625D, p_173549_ ? -1.0D : 0.0D);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        poseStack.translate(0.5D, 0.5D, 0.5D);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F + direction.toYRot()));
        poseStack.translate(-0.5D, -0.5D, -0.5D);
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entitySolid(new ResourceLocation(Aether.MODID, "textures/entity/tiles/bed/skyroot_bed.png")));
        model.render(poseStack, vertexconsumer, p_173547_, p_173548_);
        poseStack.popPose();
    }
}
