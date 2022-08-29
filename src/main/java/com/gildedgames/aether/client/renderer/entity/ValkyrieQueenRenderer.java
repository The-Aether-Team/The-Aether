package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.layers.ValkyrieWingsLayer;
import com.gildedgames.aether.client.renderer.entity.model.ValkyrieModel;
import com.gildedgames.aether.client.renderer.entity.model.ValkyrieWingsModel;
import com.gildedgames.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class ValkyrieQueenRenderer extends MobRenderer<ValkyrieQueen, ValkyrieModel<ValkyrieQueen>> {
    private static final ResourceLocation VALKYRIE_QUEEN_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/valkyrie_queen/valkyrie_queen.png");

    public ValkyrieQueenRenderer(EntityRendererProvider.Context context) {
        super(context, new ValkyrieModel<>(context.bakeLayer(AetherModelLayers.VALKYRIE_QUEEN)), 0.3F);
        this.addLayer(new ValkyrieWingsLayer<>(this, new ValkyrieWingsModel<>(context.bakeLayer(AetherModelLayers.VALKYRIE_QUEEN_WINGS))));
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull ValkyrieQueen valkyrie) {
        return VALKYRIE_QUEEN_TEXTURE;
    }
}
