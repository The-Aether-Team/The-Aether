package com.gildedgames.aether.client.renderer.tile;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.block.utility.SkyrootBedBlock;
import com.gildedgames.aether.common.entity.tile.SkyrootBedTileEntity;
import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.Level;

public class SkyrootBedTileEntityRenderer extends BlockEntityRenderer<SkyrootBedTileEntity>
{
    private final ModelPart HEAD;
    private final ModelPart FOOT;
    private final ModelPart[] LEGS = new ModelPart[4];

    public SkyrootBedTileEntityRenderer(BlockEntityRenderDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
        this.HEAD = new ModelPart(64, 64, 0, 0);
        this.HEAD.addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F, 0.0F);
        this.FOOT = new ModelPart(64, 64, 0, 22);
        this.FOOT.addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F, 0.0F);
        this.LEGS[0] = new ModelPart(64, 64, 50, 0);
        this.LEGS[1] = new ModelPart(64, 64, 50, 6);
        this.LEGS[2] = new ModelPart(64, 64, 50, 12);
        this.LEGS[3] = new ModelPart(64, 64, 50, 18);
        this.LEGS[0].addBox(0.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F);
        this.LEGS[1].addBox(0.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F);
        this.LEGS[2].addBox(-16.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F);
        this.LEGS[3].addBox(-16.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F);
        this.LEGS[0].xRot = ((float)Math.PI / 2F);
        this.LEGS[1].xRot = ((float)Math.PI / 2F);
        this.LEGS[2].xRot = ((float)Math.PI / 2F);
        this.LEGS[3].xRot = ((float)Math.PI / 2F);
        this.LEGS[0].zRot = 0.0F;
        this.LEGS[1].zRot = ((float)Math.PI / 2F);
        this.LEGS[2].zRot = ((float)Math.PI * 1.5F);
        this.LEGS[3].zRot = (float)Math.PI;
    }

    public void render(SkyrootBedTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Level world = tileEntityIn.getLevel();
        if (world != null) {
            BlockState blockstate = tileEntityIn.getBlockState();
            DoubleBlockCombiner.NeighborCombineResult<? extends SkyrootBedTileEntity> icallbackwrapper = DoubleBlockCombiner.combineWithNeigbour(AetherTileEntityTypes.SKYROOT_BED.get(), SkyrootBedBlock::getBlockType, SkyrootBedBlock::getConnectedDirection, ChestBlock.FACING, blockstate, world, tileEntityIn.getBlockPos(), (p_228846_0_, p_228846_1_) -> false);
            int i = icallbackwrapper.apply(new BrightnessCombiner<>()).get(combinedLightIn);
            this.renderModel(matrixStackIn, bufferIn, blockstate.getValue(SkyrootBedBlock.PART) == BedPart.HEAD, blockstate.getValue(BedBlock.FACING), i, combinedOverlayIn, false);
        } else {
            this.renderModel(matrixStackIn, bufferIn, true, Direction.SOUTH, combinedLightIn, combinedOverlayIn, false);
            this.renderModel(matrixStackIn, bufferIn, false, Direction.SOUTH, combinedLightIn, combinedOverlayIn, true);
        }
    }

    private void renderModel(PoseStack matrixStackIn, MultiBufferSource bufferIn, boolean isHead, Direction direction, int combinedLightIn, int combinedOverlayIn, boolean isFoot) {
        this.HEAD.visible = isHead;
        this.FOOT.visible = !isHead;
        this.LEGS[0].visible = !isHead;
        this.LEGS[1].visible = isHead;
        this.LEGS[2].visible = !isHead;
        this.LEGS[3].visible = isHead;
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.0D, 0.5625D, isFoot ? -1.0D : 0.0D);
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        matrixStackIn.translate(0.5D, 0.5D, 0.5D);
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(180.0F + direction.toYRot()));
        matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entitySolid(new ResourceLocation(Aether.MODID, "textures/entity/tiles/bed/skyroot_bed.png")));
        this.HEAD.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        this.FOOT.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        this.LEGS[0].render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        this.LEGS[1].render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        this.LEGS[2].render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        this.LEGS[3].render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        matrixStackIn.popPose();
    }
}
