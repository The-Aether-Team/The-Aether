package com.aetherteam.aether.client.renderer.accessory;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface FirstPersonRendering {
    <M extends LivingEntity> void renderOnFirstPerson(HumanoidArm arm, ItemStack stack, LivingEntity livingEntity, PoseStack matrices, EntityModel<M> model, MultiBufferSource multiBufferSource, int light);
}
