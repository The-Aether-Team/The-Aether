package com.aetherteam.aether.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.item.ItemStack;

public class LightningKnifeRenderState extends EntityRenderState {
    public float xRot;
    public float yRot;
    public ItemStack stack = ItemStack.EMPTY;
    public int id;
}
