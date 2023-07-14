package com.aetherteam.aether.integration.jei;

import com.aetherteam.aether.Aether;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.common.platform.IPlatformRenderHelper;
import mezz.jei.common.platform.Services;
import mezz.jei.common.util.ErrorUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlockStateRenderer implements IIngredientRenderer<ItemStack> {
    private final BlockPropertyPair[] pairs;

    public BlockStateRenderer(BlockPropertyPair... pairs) {
        this.pairs = pairs;
    }

    @Override
    public void render(PoseStack poseStack, @Nullable ItemStack ingredient) {
        Minecraft minecraft = Minecraft.getInstance();
        BlockRenderDispatcher blockRenderDispatcher = minecraft.getBlockRenderer();

        BlockPropertyPair pair = this.getMatchingPair(ingredient);

        if (pair.block() != null && pair.properties() != null) {
            BlockState blockState = pair.block().defaultBlockState();
            for (Map.Entry<Property<?>, Comparable<?>> propertyEntry : pair.properties().entrySet()) {
                blockState = BlockStateRecipeUtil.setHelper(propertyEntry, blockState);
            }

            poseStack.pushPose();

            poseStack.translate(15, 12.33, 5);
            poseStack.mulPose(Axis.XP.rotationDegrees(-30F));
            poseStack.mulPose(Axis.YP.rotationDegrees(45F));
            poseStack.scale(-9.9F, -9.9F, -9.9F);

            RenderSystem.setupGui3DDiffuseLighting((new Vector3f(0.4F, 0.0F, 1.0F)).normalize(), (new Vector3f(-0.4F, 1.0F, -0.2F)).normalize());

            ModelBlockRenderer modelBlockRenderer = blockRenderDispatcher.getModelRenderer();
            MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
            BakedModel model = blockRenderDispatcher.getBlockModel(blockState);
            RenderType renderType = model.getRenderTypes(blockState, minecraft.level.getRandom(), ModelData.EMPTY).asList().get(0);
            modelBlockRenderer.tesselateBlock(new FakeWorld(blockState), model, blockState, BlockPos.ZERO, poseStack, bufferSource.getBuffer(Sheets.translucentCullBlockSheet()), false, minecraft.level.getRandom(), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);
            bufferSource.endBatch();

            Lighting.setupFor3DItems();

            poseStack.popPose();
        }
    }

    @Override
    public List<Component> getTooltip(ItemStack ingredient, TooltipFlag tooltipFlag) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        try {
            BlockPropertyPair pair = this.getMatchingPair(ingredient);
            Block block = pair.block();
            Map<Property<?>, Comparable<?>> properties = pair.properties();

            List<Component> list = Lists.newArrayList();
            MutableComponent mutablecomponent = Component.empty().append(block.getName()).withStyle(ingredient.getRarity().getStyleModifier());
            list.add(mutablecomponent);
            if (tooltipFlag.isAdvanced()) {
                list.add(Component.literal(BuiltInRegistries.BLOCK.getKey(block).toString()).withStyle(ChatFormatting.DARK_GRAY));
            }
            if (player != null && !ingredient.getItem().isEnabled(player.getLevel().enabledFeatures())) {
                list.add(Component.translatable("item.disabled").withStyle(ChatFormatting.RED));
            }
            if (!properties.isEmpty()) {
                list.add(Component.translatable("gui.aether.jei.properties.tooltip").withStyle(ChatFormatting.GRAY));
                for (Map.Entry<Property<?>, Comparable<?>> entry : properties.entrySet()) {
                    list.add(Component.literal(entry.getKey().getName() + ": " + entry.getValue().toString()).withStyle(ChatFormatting.DARK_GRAY));
                }
            }
            return list;
        } catch (RuntimeException | LinkageError e) {
            String itemStackInfo = ErrorUtil.getItemStackInfo(ingredient);
            Aether.LOGGER.error("Failed to get tooltip: {}", itemStackInfo, e);
            List<Component> list = new ArrayList<>();
            MutableComponent crash = Component.translatable("jei.tooltip.error.crash");
            list.add(crash.withStyle(ChatFormatting.RED));
            return list;
        }
    }

    @Override
    public Font getFontRenderer(Minecraft minecraft, ItemStack ingredient) {
        IPlatformRenderHelper renderHelper = Services.PLATFORM.getRenderHelper();
        return renderHelper.getFontRenderer(minecraft, ingredient);
    }

    @Override
    public int getWidth() {
        return 16;
    }

    @Override
    public int getHeight() {
        return 16;
    }

    private BlockPropertyPair getMatchingPair(ItemStack ingredient) {
        Map<Block, Map<Property<?>, Comparable<?>>> pairsMap = Stream.of(this.pairs).collect(Collectors.toMap(BlockPropertyPair::block, BlockPropertyPair::properties));
        Block block = null;
        Map<Property<?>, Comparable<?>> propertiesMap = null;
        for (Map.Entry<Block, Map<Property<?>, Comparable<?>>> entry : pairsMap.entrySet()) {
            ItemStack stack = entry.getKey().getCloneItemStack(Minecraft.getInstance().level, BlockPos.ZERO, entry.getKey().defaultBlockState());
            stack = stack.isEmpty() ? new ItemStack(Blocks.STONE) : stack;
            if (stack.getItem() == ingredient.getItem()) {
                block = entry.getKey();
                propertiesMap = entry.getValue();
            }
        }
        return BlockPropertyPair.of(block, propertiesMap);
    }

    private static class FakeWorld implements BlockAndTintGetter {
        private final BlockState blockState;

        public FakeWorld(BlockState blockState) {
            this.blockState = blockState;
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
                return this.blockState;
            } else {
                return Blocks.AIR.defaultBlockState();
            }
        }

        @Override
        public FluidState getFluidState(BlockPos pos) {
            return Fluids.EMPTY.defaultFluidState();
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
