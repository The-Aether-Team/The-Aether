package com.aetherteam.aether.item.accessories.cape;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForgeMod;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class AgilityCapeItem extends CapeItem {
    /**
     * The unique identifier for the item's step height modifier.
     */
    private static final UUID STEP_HEIGHT_UUID = UUID.fromString("FC022E1C-E2D5-4A0B-9562-55C75FE53A1E");

    public AgilityCapeItem(String capeLocation, Properties properties) {
        super(capeLocation, properties);
    }

    /**
     * Applies a step height modifier to the wearer as long as they aren't holding shift. If they are, the modifier is removed until they stop holding shift.
     * @param slotContext The {@link SlotContext} of the Curio.
     * @param stack The Curio {@link ItemStack}.
     */
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        AttributeInstance stepHeight = livingEntity.getAttribute(NeoForgeMod.STEP_HEIGHT_ADDITION.get());
        if (stepHeight != null) {
            if (!stepHeight.hasModifier(this.getStepHeightModifier()) && !livingEntity.isShiftKeyDown()) {
                stepHeight.addTransientModifier(this.getStepHeightModifier());
            }
            if (livingEntity.isShiftKeyDown()) {
                stepHeight.removeModifier(this.getStepHeightModifier());
            }
        }
    }

    /**
     * Removes the step height modifier when the Agility Cape is unequipped.
     * @param slotContext The {@link SlotContext} of the Curio.
     * @param newStack The new {@link ItemStack} in the slot.
     * @param stack The {@link ItemStack} of the Curio.
     */
    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        AttributeInstance stepHeight = livingEntity.getAttribute(NeoForgeMod.STEP_HEIGHT_ADDITION.get());
        if (stepHeight != null) {
            if (stepHeight.hasModifier(this.getStepHeightModifier())) {
                stepHeight.removeModifier(this.getStepHeightModifier());
            }
        }
    }

    /**
     * @return The step height {@link AttributeModifier}. The default step height is 0.5, so this is an additional 0.5 to give the wearer a full block of step height.
     */
    public AttributeModifier getStepHeightModifier() {
        return new AttributeModifier(STEP_HEIGHT_UUID, "Step height increase", 0.5, AttributeModifier.Operation.ADDITION);
    }
}
