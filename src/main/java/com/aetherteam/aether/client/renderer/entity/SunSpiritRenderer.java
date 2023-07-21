package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.SunSpiritModel;
import com.aetherteam.aether.entity.monster.dungeon.boss.SunSpirit;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class SunSpiritRenderer extends MobRenderer<SunSpirit, SunSpiritModel<SunSpirit>> {
    private static final ResourceLocation SUN_SPIRIT_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sun_spirit/sun_spirit.png");
    private static final ResourceLocation FROZEN_SPIRIT_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sun_spirit/frozen_sun_spirit.png");

    public SunSpiritRenderer(EntityRendererProvider.Context context) {
        super(context, new SunSpiritModel<>(context.bakeLayer(AetherModelLayers.SUN_SPIRIT)), 0.8F);
    }

    @Override
    protected void scale(SunSpirit pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
        pMatrixStack.scale(2.25F, 2.25F, 2.25F);
        pMatrixStack.translate(0, 0.85, 0);
    }

    @Override
   
    public ResourceLocation getTextureLocation(SunSpirit sunSpirit) {
        return sunSpirit.isFrozen() ? FROZEN_SPIRIT_TEXTURE : SUN_SPIRIT_TEXTURE;
    }

    @Override
    protected int getBlockLightLevel(SunSpirit sunSpirit, BlockPos pos) {
        return 15;
    }

    @Override
    protected int getSkyLightLevel(SunSpirit sunSpirit, BlockPos pos) {
        return 15;
    }
}
