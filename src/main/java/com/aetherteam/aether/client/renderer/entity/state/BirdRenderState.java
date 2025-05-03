package com.aetherteam.aether.client.renderer.entity.state;

import com.aetherteam.aether.entity.WingedBird;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;

public class BirdRenderState extends LivingEntityRenderState {
    public boolean isEntityOnGround;
    public float wingRotation;

    public float setupWingsAnimation(WingedBird bipedBird, float partialTicks) {
        float rotVal = Mth.lerp(partialTicks, bipedBird.getPrevWingRotation(), bipedBird.getWingRotation());
        float destVal = Mth.lerp(partialTicks, bipedBird.getPrevWingDestPos(), bipedBird.getWingDestPos());
        return (Mth.sin(rotVal * 0.225F) + 1.0F) * destVal;
    }
}
