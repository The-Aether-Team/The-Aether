package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.layers.ZephyrTransparencyLayer;
import com.gildedgames.aether.client.renderer.entity.model.BaseZephyrModel;
import com.gildedgames.aether.client.renderer.entity.model.OldZephyrModel;
import com.gildedgames.aether.client.renderer.entity.model.ZephyrModel;
import com.gildedgames.aether.common.entity.monster.Zephyr;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZephyrRenderer extends MobRenderer<Zephyr, BaseZephyrModel>
{
    private static final ResourceLocation ZEPHYR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/zephyr/zephyr_main.png");
    private static final ResourceLocation OLD_ZEPHYR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/zephyr/zephyr_old.png");

    private final ZephyrModel regularModel;
    private final OldZephyrModel oldModel;
    private final ZephyrTransparencyLayer transparencyLayer;

    public ZephyrRenderer(EntityRendererProvider.Context renderer) {
        super(renderer, new ZephyrModel(renderer.bakeLayer(AetherModelLayers.ZEPHYR)), 0.5F);
        addLayer(this.transparencyLayer = new ZephyrTransparencyLayer(this, renderer.getModelSet()));
        this.regularModel = (ZephyrModel) this.model;
        this.oldModel = new OldZephyrModel(renderer.bakeLayer(AetherModelLayers.ZEPHYR_CLASSIC));
    }

    @Override
    protected void scale(Zephyr zephyr, PoseStack matrixStackIn, float partialTickTime) {
        float f1 = ((float) zephyr.getAttackCharge() + partialTickTime) / 20.0F;
        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        f1 = 1.0F / (f1 * f1 * f1 * f1 * f1 * 2.0F + 1.0F);

        float f2 = (8.0F + f1) / 2.0F;
        float f3 = (8.0F + 1.0F / f1) / 2.0F;
        matrixStackIn.scale(f3, f2, f3);
        matrixStackIn.translate(0, 0.5, 0);

        // TODO: Re-enable this when the config is put back in.
//      if (AetherConfig.visual_options.legacy_models) { 
//          matrixStackIn.scale(0.8F, 0.8F, 0.8F);
//          matrixStackIn.translate(0, -0.1, 0);
//      }
    }

    @Override    // TODO: Configurable old zephyr model
    public BaseZephyrModel getModel() {
        return regularModel;
    }

    @Override   // TODO: Configurable old zephyr texture
    public ResourceLocation getTextureLocation(Zephyr entity) {
        return ZEPHYR_TEXTURE;
    }
}
