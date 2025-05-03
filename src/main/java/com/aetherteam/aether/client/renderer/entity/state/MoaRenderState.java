package com.aetherteam.aether.client.renderer.entity.state;

import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.data.resources.registries.AetherMoaTypes;
import net.minecraft.client.renderer.entity.state.SaddleableRenderState;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class MoaRenderState extends BirdRenderState implements SaddleableRenderState {
    public boolean renderLegs;
    public boolean sitting;
    public boolean saddle;
    public ResourceKey<MoaType> type = AetherMoaTypes.BLUE;
    public ResourceLocation location;
    public ResourceLocation saddleLocation;

    public UUID lastRider;
    public UUID rider;
    public UUID moaUUID;

    @Override
    public boolean isSaddled() {
        return saddle;
    }
}
