package com.aetherteam.aether.integration.jei;

import com.aetherteam.aether.Aether;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Axis;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.ingredients.IIngredientTypeWithSubtypes;
import mezz.jei.common.platform.IPlatformFluidHelperInternal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.List;

public class FluidStateRenderer<T> implements IIngredientRenderer<T> {
    private final IPlatformFluidHelperInternal<T> fluidHelper;

    public FluidStateRenderer(IPlatformFluidHelperInternal<T> fluidHelper) {
        this.fluidHelper = fluidHelper;
    }

    @Override
    public void render(PoseStack poseStack, T ingredient) {
        Minecraft minecraft = Minecraft.getInstance();
        BlockRenderDispatcher blockRenderDispatcher = minecraft.getBlockRenderer();

        poseStack.pushPose();

        poseStack.translate(15, 12.33, 0);
        poseStack.mulPose(Axis.XP.rotationDegrees(-30F));
        poseStack.mulPose(Axis.YP.rotationDegrees(45F));
        poseStack.scale(-9.9F, -9.9F, -9.9F);

        IIngredientTypeWithSubtypes<Fluid, T> type = this.fluidHelper.getFluidIngredientType();
        Fluid fluidType = type.getBase(ingredient);
        FluidState fluidState = fluidType.defaultFluidState();
        RenderType renderType = ItemBlockRenderTypes.getRenderLayer(fluidState);
        PoseStack worldStack = RenderSystem.getModelViewStack();

        renderType.setupRenderState();
        worldStack.pushPose();
        worldStack.mulPoseMatrix(poseStack.last().pose());
        RenderSystem.applyModelViewMatrix();

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder builder = tesselator.getBuilder();
        builder.begin(renderType.mode(), renderType.format());
        blockRenderDispatcher.renderLiquid(BlockPos.ZERO, new FakeWorld(fluidState), builder, fluidState.createLegacyBlock(), fluidState);
        if (builder.building()) {
            tesselator.end();
        }

        renderType.clearRenderState();
        worldStack.popPose();
        RenderSystem.applyModelViewMatrix();

        poseStack.popPose();
    }

    @Override
    public List<Component> getTooltip(T ingredient, TooltipFlag tooltipFlag) {
        try {
            return this.fluidHelper.getTooltip(ingredient, tooltipFlag);
        } catch (RuntimeException | LinkageError e) {
            Component displayName = this.fluidHelper.getDisplayName(ingredient);
            Aether.LOGGER.error("Failed to get tooltip for fluid: " + displayName, e);
            return new ArrayList<>();
        }
    }

    private static class FakeWorld implements BlockAndTintGetter {
        private final FluidState fluidState;

        public FakeWorld(FluidState fluidState) {
            this.fluidState = fluidState;
        }

        @Override
        public float getShade(Direction direction, boolean bl) {
            return 1.0F;
        }

        @Override
        public LevelLightEngine getLightEngine() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getBrightness(LightLayer lightLayer, BlockPos pos) {
            return 15;
        }

        @Override
        public int getRawBrightness(BlockPos pos, int i) {
            return 15;
        }

        @Override
        public int getBlockTint(BlockPos pos, ColorResolver colorResolver) {
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null) {
                Holder<Biome> biome = Minecraft.getInstance().level.getBiome(pos);
                return colorResolver.getColor(biome.value(), 0, 0);
            } else {
                return -1;
            }
        }

        @Override
        public BlockEntity getBlockEntity(BlockPos pos) {
            return null;
        }

        @Override
        public BlockState getBlockState(BlockPos pos) {
            if (pos.equals(BlockPos.ZERO)) {
                return this.fluidState.createLegacyBlock();
            } else {
                return Blocks.AIR.defaultBlockState();
            }
        }

        @Override
        public FluidState getFluidState(BlockPos pos) {
            if (pos.equals(BlockPos.ZERO)) {
                return this.fluidState;
            } else {
                return Fluids.EMPTY.defaultFluidState();
            }
        }

        @Override
        public int getHeight() {
            return 0;
        }

        @Override
        public int getMinBuildHeight() {
            return 0;
        }
    }
}
