package com.aetherteam.aether.item.accessories.cape;

import dev.emi.trinkets.api.SlotReference;
import io.github.fabricators_of_create.porting_lib.attributes.PortingLibAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

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
     * @param stack The Trinket {@link ItemStack}.
     * @param slotContext The {@link SlotReference} of the Trinket.
     * @param livingEntity The {@link LivingEntity} of the Trinket.
     */
    @Override
    public void tick(ItemStack stack, SlotReference slotContext, LivingEntity livingEntity) {
        AttributeInstance stepHeight = livingEntity.getAttribute(PortingLibAttributes.STEP_HEIGHT_ADDITION);
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
     * @param stack The {@link ItemStack} of the Trinket.
     * @param slotContext The {@link SlotReference} of the Trinket.
     * @param livingEntity The {@link LivingEntity} of the Trinket.
     */
    @Override
    public void onUnequip(ItemStack stack, SlotReference slotContext, LivingEntity livingEntity) {
        AttributeInstance stepHeight = livingEntity.getAttribute(PortingLibAttributes.STEP_HEIGHT_ADDITION);
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
