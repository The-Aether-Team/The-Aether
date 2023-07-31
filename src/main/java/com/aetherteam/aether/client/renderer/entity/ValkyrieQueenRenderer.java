package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.ValkyrieWingsLayer;
import com.aetherteam.aether.client.renderer.entity.model.ValkyrieModel;
import com.aetherteam.aether.client.renderer.entity.model.ValkyrieWingsModel;
import com.aetherteam.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ValkyrieQueenRenderer extends MobRenderer<ValkyrieQueen, ValkyrieModel<ValkyrieQueen>> {
    private static final ResourceLocation VALKYRIE_QUEEN_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/valkyrie_queen/valkyrie_queen.png");

    public ValkyrieQueenRenderer(EntityRendererProvider.Context context) {
        super(context, new ValkyrieModel<>(context.bakeLayer(AetherModelLayers.VALKYRIE_QUEEN)), 0.3F);
        this.addLayer(new ValkyrieWingsLayer<>(this, VALKYRIE_QUEEN_TEXTURE, new ValkyrieWingsModel<>(context.bakeLayer(AetherModelLayers.VALKYRIE_QUEEN_WINGS))));
    }

    @Override
    public ResourceLocation getTextureLocation(ValkyrieQueen valkyrie) {
        return VALKYRIE_QUEEN_TEXTURE;
    }
}
