package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.ValkyrieWingsLayer;
import com.aetherteam.aether.client.renderer.entity.model.ValkyrieModel;
import com.aetherteam.aether.client.renderer.entity.model.ValkyrieWingsModel;
import com.aetherteam.aether.client.renderer.entity.state.ValkyrieRenderState;
import com.aetherteam.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ValkyrieQueenRenderer extends MobRenderer<ValkyrieQueen, ValkyrieRenderState, ValkyrieModel<ValkyrieRenderState>> {
    private static final ResourceLocation VALKYRIE_QUEEN_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/valkyrie_queen/valkyrie_queen.png");

    public ValkyrieQueenRenderer(EntityRendererProvider.Context context) {
        super(context, new ValkyrieModel(context.bakeLayer(AetherModelLayers.VALKYRIE_QUEEN)), 0.3F);
        this.addLayer(new ValkyrieWingsLayer(this, VALKYRIE_QUEEN_TEXTURE, new ValkyrieWingsModel<>(context.bakeLayer(AetherModelLayers.VALKYRIE_QUEEN_WINGS))));
    }

    @Override
    public ValkyrieRenderState createRenderState() {
        return new ValkyrieRenderState();
    }

    @Override
    public void extractRenderState(ValkyrieQueen entity, ValkyrieRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.onGround = entity.isEntityOnGround();
    }

    @Override
    public ResourceLocation getTextureLocation(ValkyrieRenderState valkyrie) {
        return VALKYRIE_QUEEN_TEXTURE;
    }
}
