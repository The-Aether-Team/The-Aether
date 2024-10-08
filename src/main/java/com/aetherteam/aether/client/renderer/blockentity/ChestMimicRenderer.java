package com.aetherteam.aether.client.renderer.blockentity;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.dungeon.ChestMimicBlock;
import com.aetherteam.aether.blockentity.ChestMimicBlockEntity;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.neoforged.fml.ModList;
import noobanidus.mods.lootr.neoforge.config.ConfigManager;

import java.util.Calendar;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.blockentity.ChestRenderer}.<br><br>
 * Stripped down to only use what is necessary.
 */
public class ChestMimicRenderer implements BlockEntityRenderer<ChestMimicBlockEntity> {
    private static final Material LOOTR_MATERIAL = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.fromNamespaceAndPath("lootr", "chest"));
    private final ModelPart lid;
    private final ModelPart bottom;
    private final ModelPart lock;
    private boolean xmasTextures = false;

    public ChestMimicRenderer(BlockEntityRendererProvider.Context context) {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26) {
            this.xmasTextures = true;
        }
        ModelPart root = context.bakeLayer(AetherModelLayers.CHEST_MIMIC);
        this.bottom = root.getChild("bottom");
        this.lid = root.getChild("lid");
        this.lock = root.getChild("lock");
    }

    @Override
    public void render(ChestMimicBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockState blockState = blockEntity.getLevel() != null ? blockEntity.getBlockState() : AetherBlocks.CHEST_MIMIC.get().defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
        if (blockState.getBlock() instanceof ChestMimicBlock) {
            poseStack.pushPose();
            float f = blockState.getValue(ChestBlock.FACING).toYRot();
            poseStack.translate(0.5, 0.5, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(-f));
            poseStack.translate(-0.5, -0.5, -0.5);
            Material material = this.getMaterial(blockEntity);
            VertexConsumer vertexconsumer = material.buffer(buffer, RenderType::entityCutout);
            this.render(poseStack, vertexconsumer, this.lid, this.lock, this.bottom, packedLight, packedOverlay);
            poseStack.popPose();
        }
    }

    private void render(PoseStack poseStack, VertexConsumer consumer, ModelPart chestLid, ModelPart chestLatch, ModelPart chestBottom, int packedLight, int packedOverlay) {
        chestLid.render(poseStack, consumer, packedLight, packedOverlay);
        chestLatch.render(poseStack, consumer, packedLight, packedOverlay);
        chestBottom.render(poseStack, consumer, packedLight, packedOverlay);
    }

    private Material getMaterial(ChestMimicBlockEntity blockEntity) {
		if (ModList.get().isLoaded("lootr")) {
			if (!ConfigManager.isVanillaTextures()) {
				return LOOTR_MATERIAL;
			}
		}
        return Sheets.chooseMaterial(blockEntity, ChestType.SINGLE, this.xmasTextures);
    }
}
