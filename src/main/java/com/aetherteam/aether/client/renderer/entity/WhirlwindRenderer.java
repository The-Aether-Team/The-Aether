package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.entity.monster.AbstractWhirlwind;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class WhirlwindRenderer extends EntityRenderer<AbstractWhirlwind, EntityRenderState> {
    public WhirlwindRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }
}
