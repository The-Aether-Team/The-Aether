package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.SunSpiritModel;
import com.aetherteam.aether.client.renderer.entity.state.SunSpiritRenderState;
import com.aetherteam.aether.entity.monster.dungeon.boss.SunSpirit;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class SunSpiritRenderer extends MobRenderer<SunSpirit, SunSpiritRenderState, SunSpiritModel<SunSpiritRenderState>> {
    private static final ResourceLocation SUN_SPIRIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/sun_spirit/sun_spirit.png");
    private static final ResourceLocation FROZEN_SPIRIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/sun_spirit/frozen_sun_spirit.png");

    public SunSpiritRenderer(EntityRendererProvider.Context context) {
        super(context, new SunSpiritModel<>(context.bakeLayer(AetherModelLayers.SUN_SPIRIT)), 0.8F);
    }

    @Override
    protected void scale(SunSpiritRenderState pLivingEntity, PoseStack pMatrixStack) {
        pMatrixStack.scale(2.25F, 2.25F, 2.25F);
        pMatrixStack.translate(0, 0.85, 0);
    }


    @Override
    protected int getBlockLightLevel(SunSpirit entity, BlockPos pos) {
        return 15;
    }

    @Override
    public SunSpiritRenderState createRenderState() {
        return new SunSpiritRenderState();
    }

    @Override
    public void extractRenderState(SunSpirit entity, SunSpiritRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.isFrozen = entity.isFrozen();
    }

    @Override
    protected int getSkyLightLevel(SunSpirit sunSpirit, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(SunSpiritRenderState sunSpirit) {
        return sunSpirit.isFrozen ? FROZEN_SPIRIT_TEXTURE : SUN_SPIRIT_TEXTURE;
    }
}
