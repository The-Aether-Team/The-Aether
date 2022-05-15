package com.gildedgames.aether.common.item.accessories.cape;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class AgilityCapeItem extends CapeItem {
    UUID STEP_HEIGHT_UUID = UUID.fromString("FC022E1C-E2D5-4A0B-9562-55C75FE53A1E");

    public AgilityCapeItem(String capeLocation, Properties properties) {
        super(capeLocation, properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        AttributeInstance stepHeight = livingEntity.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
        if (stepHeight != null) {
            if (!stepHeight.hasModifier(this.getStepHeightModifier()) && !livingEntity.isCrouching()) {
                stepHeight.addTransientModifier(this.getStepHeightModifier());
            }
            if (livingEntity.isCrouching()) {
                stepHeight.removeModifier(this.getStepHeightModifier());
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.getWearer();
        AttributeInstance stepHeight = livingEntity.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
        if (stepHeight != null) {
            if (stepHeight.hasModifier(this.getStepHeightModifier())) {
                stepHeight.removeModifier(this.getStepHeightModifier());
            }
        }
    }

    public AttributeModifier getStepHeightModifier() {
        return new AttributeModifier(STEP_HEIGHT_UUID, "Step height increase", 1.0F, AttributeModifier.Operation.ADDITION);
    }
}
