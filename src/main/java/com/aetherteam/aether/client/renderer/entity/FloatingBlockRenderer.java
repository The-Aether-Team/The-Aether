package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.entity.block.FloatingBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.FallingBlockRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class FloatingBlockRenderer extends EntityRenderer<FloatingBlockEntity, FallingBlockRenderState> {
    private final BlockRenderDispatcher dispatcher;

    public FloatingBlockRenderer(EntityRendererProvider.Context p_174112_) {
        super(p_174112_);
        this.shadowRadius = 0.5F;
        this.dispatcher = p_174112_.getBlockRenderDispatcher();
    }

    public boolean shouldRender(FloatingBlockEntity p_362415_, Frustum p_364047_, double p_362218_, double p_363427_, double p_361722_) {
        return !super.shouldRender(p_362415_, p_364047_, p_362218_, p_363427_, p_361722_)
            ? false
            : p_362415_.getBlockState() != p_362415_.level().getBlockState(p_362415_.blockPosition());
    }

    public void render(FallingBlockRenderState p_361300_, PoseStack p_114637_, MultiBufferSource p_114638_, int p_114639_) {
        BlockState blockstate = p_361300_.blockState;
        if (blockstate.getRenderShape() == RenderShape.MODEL) {
            p_114637_.pushPose();
            p_114637_.translate(-0.5, 0.0, -0.5);
            var model = this.dispatcher.getBlockModel(blockstate);
            for (var renderType : model.getRenderTypes(blockstate, RandomSource.create(blockstate.getSeed(p_361300_.startBlockPos)), net.neoforged.neoforge.client.model.data.ModelData.EMPTY))
                this.dispatcher
                    .getModelRenderer()
                    .tesselateBlock(
                        p_361300_,
                        this.dispatcher.getBlockModel(blockstate),
                        blockstate,
                        p_361300_.blockPos,
                        p_114637_,
                        p_114638_.getBuffer(net.neoforged.neoforge.client.RenderTypeHelper.getMovingBlockRenderType(renderType)),
                        false,
                        RandomSource.create(),
                        blockstate.getSeed(p_361300_.startBlockPos),
                        OverlayTexture.NO_OVERLAY,
                        net.neoforged.neoforge.client.model.data.ModelData.EMPTY,
                        renderType
                    );
            p_114637_.popPose();
            super.render(p_361300_, p_114637_, p_114638_, p_114639_);
        }
    }

    public FallingBlockRenderState createRenderState() {
        return new FallingBlockRenderState();
    }

    public void extractRenderState(FloatingBlockEntity p_364559_, FallingBlockRenderState p_360509_, float p_361019_) {
        super.extractRenderState(p_364559_, p_360509_, p_361019_);
        BlockPos blockpos = BlockPos.containing(p_364559_.getX(), p_364559_.getBoundingBox().maxY, p_364559_.getZ());
        p_360509_.startBlockPos = p_364559_.getStartPos();
        p_360509_.blockPos = blockpos;
        p_360509_.blockState = p_364559_.getBlockState();
        p_360509_.biome = p_364559_.level().getBiome(blockpos);
        p_360509_.level = p_364559_.level();
    }
}
