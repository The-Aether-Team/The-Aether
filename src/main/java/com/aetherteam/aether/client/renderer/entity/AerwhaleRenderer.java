package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.AerwhaleModel;
import com.aetherteam.aether.client.renderer.entity.model.ClassicAerwhaleModel;
import com.aetherteam.aether.client.renderer.entity.state.AerwhaleRenderState;
import com.aetherteam.aether.entity.passive.Aerwhale;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AerwhaleRenderer extends MultiModelRenderer<Aerwhale, AerwhaleRenderState, EntityModel<AerwhaleRenderState>, AerwhaleModel, ClassicAerwhaleModel> {
    private static final ResourceLocation AERWHALE_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/aerwhale/aerwhale.png");
    private static final ResourceLocation AERWHALE_CLASSIC_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/aerwhale/aerwhale_classic.png");
    private final AerwhaleModel defaultModel;
    private final ClassicAerwhaleModel oldModel;

    public AerwhaleRenderer(EntityRendererProvider.Context context) {
        super(context, new AerwhaleModel(context.bakeLayer(AetherModelLayers.AERWHALE)), 0.5F);
        this.defaultModel = new AerwhaleModel(context.bakeLayer(AetherModelLayers.AERWHALE));
        this.oldModel = new ClassicAerwhaleModel(context.bakeLayer(AetherModelLayers.AERWHALE_CLASSIC));
    }

    @Override
    public AerwhaleRenderState createRenderState() {
        return new AerwhaleRenderState();
    }

    @Override
    public void extractRenderState(Aerwhale entity, AerwhaleRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.xRotData = Mth.lerp(partialTick, entity.getXRotOData(), entity.getXRotData());
    }

    @Override
    protected void scale(AerwhaleRenderState aerwhale, PoseStack poseStack) {
        poseStack.translate(0.0, -0.5, 0.0);
        poseStack.scale(2.0F, 2.0F, 2.0F);
    }

    /**
     * Rotates the Aerwhale from data values stored in the entity.
     *
     * @param aerwhale     The {@link Aerwhale} entity.
     * @param poseStack    The rendering {@link PoseStack}.
     * @param bob          The {@link Float} for the entity's animation bob.
     */
    @Override
    protected void setupRotations(AerwhaleRenderState aerwhale, PoseStack poseStack, float bob, float yBodyRotation) {
        super.setupRotations(aerwhale, poseStack, bob, yBodyRotation);
        poseStack.mulPose(Axis.XP.rotationDegrees(aerwhale.xRotData));
    }

    @Override
    public AerwhaleModel getDefaultModel() {
        return this.defaultModel;
    }

    @Override
    public ClassicAerwhaleModel getOldModel() {
        return this.oldModel;
    }

    @Override
    public ResourceLocation getDefaultTexture() {
        return AERWHALE_TEXTURE;
    }

    @Override
    public ResourceLocation getOldTexture() {
        return AERWHALE_CLASSIC_TEXTURE;
    }
}

