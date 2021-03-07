package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.passive.AerwhaleEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class BaseAerwhaleModel extends EntityModel<AerwhaleEntity> {

	@Override
	public void setRotationAngles(AerwhaleEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

}
