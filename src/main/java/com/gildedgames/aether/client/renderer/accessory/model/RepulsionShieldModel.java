package com.gildedgames.aether.client.renderer.accessory.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;

public class RepulsionShieldModel extends BipedModel<LivingEntity>
{
    public RepulsionShieldModel() {
        super(1.1F);
        this.texWidth = 64;
        this.texHeight = 32;
    }
}
