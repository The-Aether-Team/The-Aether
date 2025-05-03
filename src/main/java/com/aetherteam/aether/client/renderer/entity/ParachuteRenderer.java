package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.client.renderer.entity.state.ParachuteRenderState;
import com.aetherteam.aether.entity.miscellaneous.Parachute;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;

import java.util.function.Supplier;

public class ParachuteRenderer extends EntityRenderer<Parachute, ParachuteRenderState> {
    private final Supplier<? extends Block> parachuteBlock;

    public ParachuteRenderer(EntityRendererProvider.Context context, Supplier<? extends Block> parachuteBlock) {
        super(context);
        this.parachuteBlock = parachuteBlock;
        this.shadowRadius = 0.0F;
    }

    @Override
    public void extractRenderState(Parachute parachute, ParachuteRenderState reusedState, float partialTick) {
        super.extractRenderState(parachute, reusedState, partialTick);
        Entity passenger = parachute.getControllingPassenger();
        if (passenger != null) {
            if (passenger instanceof Player player) {
                reusedState.yRot = Mth.lerp(partialTick, player.yHeadRotO, player.getYHeadRot());
            } else {
                reusedState.yRot = Mth.lerp(partialTick, passenger.yRotO, passenger.getYRot());
            }
        }
    }

    @Override
    public ParachuteRenderState createRenderState() {
        return new ParachuteRenderState();
    }

    /**
     * Renders and rotates the Parachute with the player.<br><br>
     * The warning for "deprecation" is suppressed because {@link net.minecraft.client.renderer.block.BlockRenderDispatcher#renderSingleBlock(BlockState, PoseStack, MultiBufferSource, int, int, ModelData, RenderType)} is fine to use.
     *
     * @param parachute    The {@link Parachute} entity.
     * @param poseStack    The rendering {@link PoseStack}.
     * @param buffer       The rendering {@link MultiBufferSource}.
     * @param packedLight  The {@link Integer} for the packed lighting for rendering.
     */
    @Override
    @SuppressWarnings("deprecation")
    public void render(ParachuteRenderState parachute, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-parachute.yRot));

        poseStack.translate(-0.5, 0.0, -0.5);
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(this.parachuteBlock.get().defaultBlockState(), poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(parachute, poseStack, buffer, packedLight);
    }

    public ResourceLocation getTextureLocation(ParachuteRenderState parachute) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
