package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.monster.Zephyr;

import net.minecraft.client.model.EntityModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class BaseZephyrModel extends EntityModel<Zephyr>
{
    @Override
	public void setupAnim(Zephyr entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}
}