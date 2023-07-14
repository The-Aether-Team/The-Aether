package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.ZephyrTransparencyLayer;
import com.aetherteam.aether.client.renderer.entity.model.ClassicZephyrModel;
import com.aetherteam.aether.client.renderer.entity.model.ZephyrModel;
import com.aetherteam.aether.entity.monster.Zephyr;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;

public class ZephyrRenderer extends MultiModelRenderer<Zephyr, EntityModel<Zephyr>, ZephyrModel, ClassicZephyrModel> {
    private static final ResourceLocation ZEPHYR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/zephyr/zephyr.png");
    private static final ResourceLocation ZEPHYR_CLASSIC_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/zephyr/zephyr_classic.png");

    private final ZephyrModel defaultModel;
    private final ClassicZephyrModel oldModel;

    public ZephyrRenderer(EntityRendererProvider.Context context) {
        super(context, new ZephyrModel(context.bakeLayer(AetherModelLayers.ZEPHYR)), 0.5F);
        this.addLayer(new ZephyrTransparencyLayer(this, new ZephyrModel(context.getModelSet().bakeLayer(AetherModelLayers.ZEPHYR_TRANSPARENCY))));
        this.defaultModel = new ZephyrModel(context.bakeLayer(AetherModelLayers.ZEPHYR));
        this.oldModel = new ClassicZephyrModel(context.bakeLayer(AetherModelLayers.ZEPHYR_CLASSIC));
    }

    @Override
    protected void scale(Zephyr zephyr, @Nonnull PoseStack poseStack, float partialTickTime) {
        float f = Mth.lerp(partialTickTime, zephyr.scale, zephyr.scale + zephyr.scaleAdd);
        float f1 = f / 40.0F;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }
        f1 = 1.0F / ((float) Math.pow(f1, 5) * 2.0F + 1.0F);
        float f2 = (8.0F + f1) / 2.0F;
        float f3 = (8.0F + 1.0F / f1) / 2.0F;

        poseStack.scale(f3, f2, f3);
        poseStack.translate(0.0, 0.5, 0.0);

        if (this.getModel() instanceof ClassicZephyrModel) {
            poseStack.scale(0.8F, 0.8F, 0.8F);
            poseStack.translate(0.0, -0.1, 0.0);
        }
    }

    @Override
    protected float getBob(@Nonnull Zephyr zephyr, float partialTicks) {
        return Mth.lerp(partialTicks, zephyr.tailRot, zephyr.tailRot + zephyr.tailRotAdd);
    }

    @Override
    public ZephyrModel getDefaultModel() {
        return this.defaultModel;
    }

    @Override
    public ClassicZephyrModel getOldModel() {
        return this.oldModel;
    }

    @Override
    public ResourceLocation getDefaultTexture() {
        return ZEPHYR_TEXTURE;
    }

    @Override
    public ResourceLocation getOldTexture() {
        return ZEPHYR_CLASSIC_TEXTURE;
    }
}
