package com.aetherteam.aether.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.phys.Vec3;

public class AerbunnyRenderState extends LivingEntityRenderState {
    public float puffiness;

    public Vec3 deltaMovement = new Vec3(0, 0, 0);
    public boolean onGround = true;
}
