package com.aetherteam.aether.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.SaddleableRenderState;

public class PhygRenderState extends WingEntityRenderState implements SaddleableRenderState {
    public boolean saddle;

    @Override
    public boolean isSaddled() {
        return saddle;
    }
}
