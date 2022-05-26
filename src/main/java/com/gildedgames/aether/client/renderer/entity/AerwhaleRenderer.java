package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.AerwhaleModel;
import com.gildedgames.aether.client.renderer.entity.model.ClassicAerwhaleModel;
import com.gildedgames.aether.common.entity.passive.Aerwhale;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;

import javax.annotation.Nonnull;

public class AerwhaleRenderer extends MultiModelRenderer<Aerwhale, EntityModel<Aerwhale>, AerwhaleModel, ClassicAerwhaleModel> {
    private static final ResourceLocation AERWHALE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/aerwhale/aerwhale.png");
    private static final ResourceLocation AERWHALE_CLASSIC_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/aerwhale/aerwhale_classic.png");
    private final AerwhaleModel defaultModel;
    private final ClassicAerwhaleModel oldModel;
    
    public AerwhaleRenderer(EntityRendererProvider.Context renderer) {
        super(renderer, new AerwhaleModel(renderer.bakeLayer(AetherModelLayers.AERWHALE)), 0.5F);
        this.defaultModel = new AerwhaleModel(renderer.bakeLayer(AetherModelLayers.AERWHALE));
        this.oldModel = new ClassicAerwhaleModel(renderer.bakeLayer(AetherModelLayers.AERWHALE_CLASSIC));
    }

    @Override
    protected void scale(@Nonnull Aerwhale aerwhale, PoseStack pMatrixStack, float partialTickTime) {
        pMatrixStack.translate(0.0, -0.5, 0.0);
        pMatrixStack.scale(2.0F, 2.0F, 2.0F);
    }

    @Override
    protected void setupRotations(@Nonnull Aerwhale pEntityLiving, @Nonnull PoseStack pMatrixStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pMatrixStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(pEntityLiving.getXRot()));
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

