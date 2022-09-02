package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.SunSpiritModel;
import com.gildedgames.aether.entity.monster.dungeon.SunSpirit;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class SunSpiritRenderer extends MobRenderer<SunSpirit, SunSpiritModel<SunSpirit>> {
    private static final ResourceLocation SUN_SPIRIT_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sun_spirit/sun_spirit.png");
    private static final ResourceLocation FROZEN_SPIRIT_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sun_spirit/frozen_sun_spirit.png");

    public SunSpiritRenderer(EntityRendererProvider.Context context) {
        super(context, new SunSpiritModel<>(context.bakeLayer(AetherModelLayers.SUN_SPIRIT)), 0.8F);
    }

    @Override
    protected void scale(@Nonnull SunSpirit pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
        pMatrixStack.scale(2.25F, 2.25F, 2.25F);
    }

    @Override
    @Nonnull
    public ResourceLocation getTextureLocation(SunSpirit sunSpirit) {
        return sunSpirit.isFrozen() ? FROZEN_SPIRIT_TEXTURE : SUN_SPIRIT_TEXTURE;
    }
}
