package com.gildedgames.aether.client.event.hooks;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.api.WorldDisplayHelper;
import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.client.AetherRenderTypes;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelDataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LevelClientHooks {
    public static void renderMenuWithWorld(RenderLevelStageEvent.Stage stage, Minecraft minecraft) {
        if (stage == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            if (AetherConfig.CLIENT.enable_world_preview.get()) {
                if (WorldDisplayHelper.loadedSummary != null) {
                    if (minecraft.screen == null) {
                        setupMenu(minecraft);
                    } else {
                        LocalPlayer player = minecraft.player;
                        if (player != null) {
                            player.setXRot(0);
                            player.setYRot(player.getYRot() + 0.02F);
                        }
                        if (minecraft.screen instanceof PauseScreen) {
                            setupMenu(minecraft);
                        }
                    }
                }
            } else {
                WorldDisplayHelper.loadedLevel = null;
                WorldDisplayHelper.loadedSummary = null;
            }
        }
    }

    public static void setupMenu(Minecraft minecraft) {
        WorldDisplayHelper.setupLevelForDisplay();
        minecraft.forceSetScreen(GuiHooks.getMenu());
    }

    private static final HashMap<Integer, List<BlockPos>> positionsForTypes = new HashMap<>();

    public static void renderDungeonBlockOverlays(RenderLevelStageEvent.Stage stage, PoseStack poseStack, Camera camera, Minecraft minecraft) {
        if (stage == RenderLevelStageEvent.Stage.AFTER_PARTICLES && minecraft.level != null) {
            LocalPlayer player = minecraft.player;
            ClientLevel level = minecraft.level;
            ModelDataManager modelDataManager = level.getModelDataManager();
            RenderBuffers renderBuffers = minecraft.renderBuffers();
            Vec3 vec3 = camera.getPosition();
            double vecX = vec3.x();
            double vecY = vec3.y();
            double vecZ = vec3.z();
            int range = 32;
            if (player != null && player.isCreative()) {
                BlockPos playerPos = player.blockPosition();
                ItemStack stack = player.getMainHandItem();
                int type = idForItem(stack);
                if (type != -1) {
                    updatePositions(playerPos, level, stack, range, type, false);
                }
                for (int i = 0; i < positionsForTypes.size(); i++) {
                    renderOverlays(poseStack, modelDataManager, renderBuffers, level, vecX, vecY, vecZ, minecraft, i);
                    updatePositions(playerPos, level, stack, range, i, true);
                }
            }
        }
    }

    private static void updatePositions(BlockPos playerPos, ClientLevel level, ItemStack stack, int range, int type, boolean depopulate) {
        positionsForTypes.putIfAbsent(0, new ArrayList<>());
        positionsForTypes.putIfAbsent(1, new ArrayList<>());
        positionsForTypes.putIfAbsent(2, new ArrayList<>());
        positionsForTypes.putIfAbsent(3, new ArrayList<>());
        for (int c = 0; c < 667; ++c) {
            int x = playerPos.getX() + level.random.nextInt(range) - level.random.nextInt(range);
            int y = playerPos.getY() + level.random.nextInt(range) - level.random.nextInt(range);
            int z = playerPos.getZ() + level.random.nextInt(range) - level.random.nextInt(range);
            if (!depopulate) {
                BlockPos pos = new BlockPos(x, y, z);
                if (stack.is(level.getBlockState(pos).getBlock().asItem())) {
                    positionsForTypes.get(type).add(pos);
                }
            } else {
                List<BlockPos> positions = positionsForTypes.get(type);
                if (positions.size() > 0 && level.random.nextInt(750) == 0) {
                    BlockPos pos = positions.get(level.random.nextInt(positions.size()));
                    if (!stack.is(level.getBlockState(pos).getBlock().asItem())) {
                        positions.remove(pos);
                        positionsForTypes.put(type, positions);
                    }
                }
            }
        }
    }

    private static void renderOverlays(PoseStack poseStack, ModelDataManager modelDataManager, RenderBuffers renderBuffers, ClientLevel level, double vecX, double vecY, double vecZ, Minecraft minecraft, int type) {
        for (BlockPos blockPos : positionsForTypes.get(type)) {
            poseStack.pushPose();
            poseStack.translate(blockPos.getX() - vecX, blockPos.getY() - vecY, blockPos.getZ() - vecZ);
            PoseStack.Pose lastPose = poseStack.last();
            VertexConsumer vertexConsumer = new SheetedDecalTextureGenerator(renderBuffers.bufferSource().getBuffer(AetherRenderTypes.dungeonBlockOverlayType(type)), lastPose.pose(), lastPose.normal());
            if (modelDataManager != null) {
                ModelData modelData = modelDataManager.getAt(blockPos);
                minecraft.getBlockRenderer().renderBreakingTexture(level.getBlockState(blockPos), blockPos, level, poseStack, vertexConsumer, modelData == null ? ModelData.EMPTY : modelData);
            }
            poseStack.popPose();
        }
        renderBuffers.bufferSource().endBatch();
    }

    private static int idForItem(ItemStack stack) {
        if (stack.is(AetherTags.Items.LOCKED_DUNGEON_BLOCKS)) {
            return 0;
        } else if (stack.is(AetherTags.Items.TRAPPED_DUNGEON_BLOCKS)) {
            return 1;
        } else if (stack.is(AetherTags.Items.BOSS_DOORWAY_DUNGEON_BLOCKS)) {
            return 2;
        } else if (stack.is(AetherTags.Items.TREASURE_DOORWAY_DUNGEON_BLOCKS)) {
            return 3;
        } else {
            return -1;
        }
    }
}
