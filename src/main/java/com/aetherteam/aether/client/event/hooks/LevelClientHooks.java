package com.aetherteam.aether.client.event.hooks;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LevelClientHooks {
    private static final TextureAtlasSprite LOCK = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(Aether.MODID, "block/dungeon/lock"));
    private static final TextureAtlasSprite EXCLAMATION = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(Aether.MODID, "block/dungeon/exclamation"));
    private static final TextureAtlasSprite DOOR = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(Aether.MODID, "block/dungeon/door"));
    private static final TextureAtlasSprite TREASURE = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(Aether.MODID, "block/dungeon/treasure"));
    private static final HashMap<Integer, List<BlockPos>> positionsForTypes = new HashMap<>();

    /**
     * [CODE COPY] - {@link ClientLevel#animateTick(int, int, int)}.
     * Checks to set up positions and render overlays for dungeon blocks from whatever block item the player is holding.
     * @see com.aetherteam.aether.client.event.listeners.LevelClientListener#onRenderLevelLast(RenderLevelStageEvent)
     */
    public static void renderDungeonBlockOverlays(RenderLevelStageEvent.Stage stage, PoseStack poseStack, Camera camera, Frustum frustum, Minecraft minecraft) {
        if (stage == RenderLevelStageEvent.Stage.AFTER_PARTICLES && minecraft.level != null) {
            LocalPlayer player = minecraft.player;
            ClientLevel level = minecraft.level;
            RenderBuffers renderBuffers = minecraft.renderBuffers();
            int range = 32; // Range for how far the overlays can be rendered at.
            if (player != null && player.isCreative()) {
                BlockPos playerPos = player.blockPosition();
                ItemStack stack = player.getMainHandItem();
                int type = idForItem(stack); // Get an ID for the currently held dungeon block item.
                if (type != -1) {
                    updatePositions(playerPos, level, stack, range, type, false); // Check to add overlays to the map.
                }
                for (int i = 0; i < positionsForTypes.size(); i++) {
                    renderOverlays(level, poseStack, renderBuffers, camera, frustum, i); // Render any overlays at positions in the map.
                    updatePositions(playerPos, level, stack, range, i, true); // Check to remove overlays from the map.
                }
            }
        }
    }

    /**
     * [CODE COPY] - {@link ClientLevel#animateTick(int, int, int)}.
     * [CODE COPY] - {@link ClientLevel#doAnimateTick(int, int, int, int, RandomSource, Block, BlockPos.MutableBlockPos)}.
     * Based on code from methods in {@link ClientLevel} for Barrier particle rendering.
     * Modified to store positions for where different types of dungeon block overlays should render.
     */
    private static void updatePositions(BlockPos playerPos, ClientLevel level, ItemStack stack, int range, int type, boolean depopulate) {
        // Initial setup of the different IDs in the map.
        positionsForTypes.putIfAbsent(0, new ArrayList<>());
        positionsForTypes.putIfAbsent(1, new ArrayList<>());
        positionsForTypes.putIfAbsent(2, new ArrayList<>());
        positionsForTypes.putIfAbsent(3, new ArrayList<>());
        // Loop and select random positions to check and see whether an overlay should be rendered there.
        for (int c = 0; c < 667; ++c) {
            int x = playerPos.getX() + level.getRandom().nextInt(range) - level.getRandom().nextInt(range);
            int y = playerPos.getY() + level.getRandom().nextInt(range) - level.getRandom().nextInt(range);
            int z = playerPos.getZ() + level.getRandom().nextInt(range) - level.getRandom().nextInt(range);
            if (!depopulate) { // For checking to add overlays to the world.
                BlockPos pos = new BlockPos(x, y, z);
                if (stack.is(level.getBlockState(pos).getBlock().asItem())) { // Add an overlay if the corresponding dungeon block item is held.
                    if (!positionsForTypes.get(type).contains(pos)) {
                        positionsForTypes.get(type).add(pos);
                    }
                }
            } else { // For checking to remove overlays from the world.
                List<BlockPos> positions = positionsForTypes.get(type);
                if (positions.size() > 0 && level.getRandom().nextInt(100) == 0) {
                    BlockPos pos = positions.get(level.getRandom().nextInt(positions.size()));
                    if (!stack.is(level.getBlockState(pos).getBlock().asItem())) { // Remove an overlay if the corresponding dungeon block item is not held.
                        positions.remove(pos);
                        positionsForTypes.put(type, positions);
                    }
                }
            }
        }
    }

    /**
     * Renders all the overlays for block positions as stored in {@link LevelClientHooks#positionsForTypes}.
     * @param level The {@link ClientLevel} to render in.
     * @param poseStack The rendering {@link PoseStack}.
     * @param renderBuffers The rendering {@link RenderBuffers}.
     * @param camera The {@link Camera} viewing the overlays.
     * @param frustum The camera's {@link Frustum}, for checking things in view of the camera.
     * @param type The {@link Integer} type ID for an overlay.
     */
    private static void renderOverlays(ClientLevel level, PoseStack poseStack, RenderBuffers renderBuffers, Camera camera, Frustum frustum, int type) {
        for (BlockPos blockPos : positionsForTypes.get(type)) {
            if (frustum.isVisible(new AABB(blockPos)) && level.getBlockState(blockPos).getRenderShape() != RenderShape.INVISIBLE) {
                drawSurfaces(renderBuffers.bufferSource(), poseStack.last().pose(), poseStack.last().normal(), blockPos, camera,
                        (float) (blockPos.getX() - camera.getPosition().x()) - 0.001F,
                        (float) (blockPos.getZ() - camera.getPosition().z()) - 0.001F,
                        (float) (blockPos.getX() - camera.getPosition().x()) + 1.001F,
                        (float) (blockPos.getZ() - camera.getPosition().z()) + 1.001F,
                        (float) (blockPos.getY() - camera.getPosition().y()) - 0.001F,
                        (float) (blockPos.getY() - camera.getPosition().y()) + 1.001F,
                        type);
            }
        }
        renderBuffers.bufferSource().endBatch();
    }

    private static void drawSurfaces(MultiBufferSource buffer, Matrix4f matrix, Matrix3f normal, BlockPos blockPos, Camera camera, float startX, float startZ, float endX, float endZ, float botY, float topY, int type) {
        VertexConsumer builder = buffer.getBuffer(RenderType.cutout());
        TextureAtlasSprite sprite = spriteForId(type);

        if (sprite != null) {
            float minU = sprite.getU1();
            float maxU = sprite.getU0();
            float minV = sprite.getV1();
            float maxV = sprite.getV0();

            // Renders an overlay on the bottom face of a block if the camera is below it, i.e. the camera can see the block face.
            if (camera.getPosition().y() < blockPos.getY() + botY) {
                buildVertex(builder, matrix, normal, startX, botY, startZ, minU, minV, 0, -1, 0);
                buildVertex(builder, matrix, normal, endX, botY, startZ, maxU, minV, 0, -1, 0);
                buildVertex(builder, matrix, normal, endX, botY, endZ, maxU, maxV, 0, -1, 0);
                buildVertex(builder, matrix, normal, startX, botY, endZ, minU, maxV, 0, -1, 0);
            }

            // Renders an overlay on the top face of a block if the camera is above it, i.e. the camera can see the block face.
            if (camera.getPosition().y() > blockPos.getY() + topY) {
                buildVertex(builder, matrix, normal, endX, topY, startZ, minU, minV, 0, 1, 0);
                buildVertex(builder, matrix, normal, startX, topY, startZ, maxU, minV, 0, 1, 0);
                buildVertex(builder, matrix, normal, startX, topY, endZ, maxU, maxV, 0, 1, 0);
                buildVertex(builder, matrix, normal, endX, topY, endZ, minU, maxV, 0, 1, 0);
            }

            // Renders an overlay on the north face of a block if the camera's z-coordinate is less than the block's z-coordinate, i.e. the camera can see the block face.
            if (camera.getPosition().z() < blockPos.getZ() + startZ) {
                buildVertex(builder, matrix, normal, startX, botY, startZ, minU, minV, 0, 0, -1);
                buildVertex(builder, matrix, normal, startX, topY, startZ, minU, maxV, 0, 0, -1);
                buildVertex(builder, matrix, normal, endX, topY, startZ, maxU, maxV, 0, 0, -1);
                buildVertex(builder, matrix, normal, endX, botY, startZ, maxU, minV, 0, 0, -1);
            }

            // Renders an overlay on the south face of a block if the camera's z-coordinate is greater than the block's z-coordinate, i.e. the camera can see the block face.
            if (camera.getPosition().z() > blockPos.getZ() + endZ) {
                buildVertex(builder, matrix, normal, endX, botY, endZ, minU, minV, 0, 0, 1);
                buildVertex(builder, matrix, normal, endX, topY, endZ, minU, maxV, 0, 0, 1);
                buildVertex(builder, matrix, normal, startX, topY, endZ, maxU, maxV, 0, 0, 1);
                buildVertex(builder, matrix, normal, startX, botY, endZ, maxU, minV, 0, 0, 1);
            }

            // Renders an overlay on the west face of a block if the camera's x-coordinate is less than the block's x-coordinate, i.e. the camera can see the block face.
            if (camera.getPosition().x() < blockPos.getX() + startX) {
                buildVertex(builder, matrix, normal, startX, botY, endZ, minU, minV, -1, 0, 0);
                buildVertex(builder, matrix, normal, startX, topY, endZ, minU, maxV, -1, 0, 0);
                buildVertex(builder, matrix, normal, startX, topY, startZ, maxU, maxV, -1, 0, 0);
                buildVertex(builder, matrix, normal, startX, botY, startZ, maxU, minV, -1, 0, 0);
            }

            // Renders an overlay on the east face of a block if the camera's x-coordinate is greater than the block's x-coordinate, i.e. the camera can see the block face.
            if (camera.getPosition().x() > blockPos.getX() + endX) {
                buildVertex(builder, matrix, normal, endX, botY, startZ, minU, minV, 1, 0, 0);
                buildVertex(builder, matrix, normal, endX, topY, startZ, minU, maxV, 1, 0, 0);
                buildVertex(builder, matrix, normal, endX, topY, endZ, maxU, maxV, 1, 0, 0);
                buildVertex(builder, matrix, normal, endX, botY, endZ, maxU, minV, 1, 0, 0);
            }
        }
    }

    private static void buildVertex(VertexConsumer builder, Matrix4f matrix, Matrix3f normal, float x, float y, float z, float u, float v, float normalX, float normalY, float normalZ) {
        builder.vertex(matrix, x, y, z).color(0xFF, 0xFF, 0xFF, 0xAA).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, normalX, normalY, normalZ).endVertex();
    }

    /**
     * Returns a dungeon block overlay sprite for an ID.
     * @param id The {@link Integer} ID.
     * @return The {@link TextureAtlasSprite} for the overlay.
     */
    private static TextureAtlasSprite spriteForId(int id) {
        switch(id) {
            case 0 -> {
                return LOCK;
            }
            case 1 -> {
                return EXCLAMATION;
            }
            case 2 -> {
                return DOOR;
            }
            case 3 -> {
                return TREASURE;
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Returns an ID to a dungeon block item.
     * @param stack The {@link ItemStack} for the dungeon block.
     * @return The corresponding {@link Integer} ID.
     */
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
