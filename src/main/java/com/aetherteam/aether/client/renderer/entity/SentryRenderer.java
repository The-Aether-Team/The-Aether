package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.SentryGlowLayer;
import com.aetherteam.aether.client.renderer.entity.state.SentryRenderState;
import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SentryRenderer extends MobRenderer<Sentry, SentryRenderState, SlimeModel> {
    private static final ResourceLocation SENTRY_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/sentry/sentry.png");
    private static final ResourceLocation SENTRY_LIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/sentry/sentry_lit.png");

    public SentryRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel(context.bakeLayer(AetherModelLayers.SENTRY)), 0.3F);
        this.addLayer(new SentryGlowLayer(this));
    }

    @Override
    public SentryRenderState createRenderState() {
        return new SentryRenderState();
    }

    @Override
    public void extractRenderState(Sentry entity, SentryRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.awake = entity.isAwake();
    }

    /**
     * Scales the Sentry according to its size.
     *
     * @param sentry       The {@link Sentry} entity.
     * @param poseStack    The rendering {@link PoseStack}.
     */
    @Override
    protected void scale(SentryRenderState sentry, PoseStack poseStack) {
        float f = 0.879F;
        poseStack.scale(f, f, f);
        float f1 = sentry.scale + 1.0F;
        float f2 = 0.0F;
        float f3 = 1.0F / (f2 + 1.0F);
        poseStack.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    @Override
    public ResourceLocation getTextureLocation(SentryRenderState sentry) {
        return sentry.awake ? SENTRY_LIT_TEXTURE : SENTRY_TEXTURE;
    }
}
