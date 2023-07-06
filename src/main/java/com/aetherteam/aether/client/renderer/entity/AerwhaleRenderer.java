package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.AerwhaleModel;
import com.aetherteam.aether.client.renderer.entity.model.ClassicAerwhaleModel;
import com.aetherteam.aether.entity.passive.Aerwhale;
import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;

public class AerwhaleRenderer extends MultiModelRenderer<Aerwhale, EntityModel<Aerwhale>, AerwhaleModel, ClassicAerwhaleModel> {
    private static final ResourceLocation AERWHALE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/aerwhale/aerwhale.png");
    private static final ResourceLocation AERWHALE_CLASSIC_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/aerwhale/aerwhale_classic.png");
    private final AerwhaleModel defaultModel;
    private final ClassicAerwhaleModel oldModel;
    
    public AerwhaleRenderer(EntityRendererProvider.Context context) {
        super(context, new AerwhaleModel(context.bakeLayer(AetherModelLayers.AERWHALE)), 0.5F);
        this.defaultModel = new AerwhaleModel(context.bakeLayer(AetherModelLayers.AERWHALE));
        this.oldModel = new ClassicAerwhaleModel(context.bakeLayer(AetherModelLayers.AERWHALE_CLASSIC));
    }

    @Override
    protected void scale(@Nonnull Aerwhale aerwhale, PoseStack poseStack, float partialTickTime) {
        poseStack.translate(0.0, -0.5, 0.0);
        poseStack.scale(2.0F, 2.0F, 2.0F);
    }

    @Override
    protected void setupRotations(@Nonnull Aerwhale aerwhale, @Nonnull PoseStack poseStack, float ageInTicks, float pRotationYaw, float partialTicks) {
        super.setupRotations(aerwhale, poseStack, ageInTicks, pRotationYaw, partialTicks);
        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(partialTicks, aerwhale.getXRotOData(), aerwhale.getXRotData())));
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

