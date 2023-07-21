package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.AetherConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public abstract class MultiModelRenderer<T extends Mob, M extends EntityModel<T>, N extends M, O extends M> extends MobRenderer<T, M> {
    public MultiModelRenderer(EntityRendererProvider.Context context, N defaultModel, float shadowRadius) {
        super(context, defaultModel, shadowRadius);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.model = this.getModel();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

   
    @Override
    public M getModel() {
        return AetherConfig.CLIENT.legacy_models.get() ? this.getOldModel() : this.getDefaultModel();
    }

    public abstract N getDefaultModel();

    public abstract O getOldModel();

   
    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return AetherConfig.CLIENT.legacy_models.get() ? this.getOldTexture() : this.getDefaultTexture();
    }

    public abstract ResourceLocation getDefaultTexture();

    public abstract ResourceLocation getOldTexture();
}
