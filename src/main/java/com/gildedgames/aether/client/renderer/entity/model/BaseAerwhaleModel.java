package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.passive.AerwhaleEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;

public abstract class BaseAerwhaleModel extends EntityModel<AerwhaleEntity> {

	@Override
	public void setupAnim(AerwhaleEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

}
