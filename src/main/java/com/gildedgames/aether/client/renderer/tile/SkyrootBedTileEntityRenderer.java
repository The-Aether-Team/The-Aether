package com.gildedgames.aether.client.renderer.tile;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.block.utility.SkyrootBedBlock;
import com.gildedgames.aether.common.entity.tile.SkyrootBedTileEntity;
import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SkyrootBedTileEntityRenderer extends TileEntityRenderer<SkyrootBedTileEntity>
{
    private final ModelRenderer HEAD;
    private final ModelRenderer FOOT;
    private final ModelRenderer[] LEGS = new ModelRenderer[4];

    public SkyrootBedTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
        this.HEAD = new ModelRenderer(64, 64, 0, 0);
        this.HEAD.addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F, 0.0F);
        this.FOOT = new ModelRenderer(64, 64, 0, 22);
        this.FOOT.addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F, 0.0F);
        this.LEGS[0] = new ModelRenderer(64, 64, 50, 0);
        this.LEGS[1] = new ModelRenderer(64, 64, 50, 6);
        this.LEGS[2] = new ModelRenderer(64, 64, 50, 12);
        this.LEGS[3] = new ModelRenderer(64, 64, 50, 18);
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

    @OnlyIn(Dist.CLIENT)
    public void render(SkyrootBedTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        World world = tileEntityIn.getLevel();
        if (world != null) {
            BlockState blockstate = tileEntityIn.getBlockState();
            TileEntityMerger.ICallbackWrapper<? extends SkyrootBedTileEntity> icallbackwrapper = TileEntityMerger.combineWithNeigbour(AetherTileEntityTypes.SKYROOT_BED.get(), SkyrootBedBlock::getBlockType, SkyrootBedBlock::getConnectedDirection, ChestBlock.FACING, blockstate, world, tileEntityIn.getBlockPos(), (p_228846_0_, p_228846_1_) -> false);
            int i = icallbackwrapper.apply(new DualBrightnessCallback<>()).get(combinedLightIn);
            this.renderModel(matrixStackIn, bufferIn, blockstate.getValue(SkyrootBedBlock.PART) == BedPart.HEAD, blockstate.getValue(BedBlock.FACING), i, combinedOverlayIn, false);
        } else {
            this.renderModel(matrixStackIn, bufferIn, true, Direction.SOUTH, combinedLightIn, combinedOverlayIn, false);
            this.renderModel(matrixStackIn, bufferIn, false, Direction.SOUTH, combinedLightIn, combinedOverlayIn, true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void renderModel(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, boolean isHead, Direction direction, int combinedLightIn, int combinedOverlayIn, boolean isFoot) {
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
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.entitySolid(new ResourceLocation(Aether.MODID, "textures/entity/bed/skyroot_bed.png")));
        this.HEAD.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        this.FOOT.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        this.LEGS[0].render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        this.LEGS[1].render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        this.LEGS[2].render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        this.LEGS[3].render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        matrixStackIn.popPose();
    }
}
