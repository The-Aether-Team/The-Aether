package com.aetherteam.aether.item.accessories.cape;

import com.aetherteam.aether.Aether;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class AgilityCapeItem extends CapeItem {
    /**
     * The unique identifier for the item's step height modifier.
     */
    private static final ResourceLocation STEP_HEIGHT_ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "agility_cape_step_height");

    public AgilityCapeItem(String capeLocation, Properties properties) {
        super(capeLocation, properties);
    }

    /**
     * Applies a step height modifier to the wearer as long as they aren't holding shift. If they are, the modifier is removed until they stop holding shift.
     *
     * @param stack       The accessory {@link ItemStack}.
     * @param reference   The {@link SlotReference} of the accessory.
     */
    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        LivingEntity livingEntity = reference.entity();
        AttributeInstance stepHeight = livingEntity.getAttribute(Attributes.STEP_HEIGHT);
        if (stepHeight != null) {
            if (!stepHeight.hasModifier(this.getStepHeightModifier().id()) && !livingEntity.isShiftKeyDown()) {
                stepHeight.addTransientModifier(this.getStepHeightModifier());
            }
            if (livingEntity.isShiftKeyDown()) {
                stepHeight.removeModifier(this.getStepHeightModifier().id());
            }
        }
    }

    /**
     * Removes the step height modifier when the Agility Cape is unequipped.
     *
     * @param stack       The accessory {@link ItemStack}.
     * @param reference   The {@link SlotReference} of the accessory.
     */
    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        LivingEntity livingEntity = reference.entity();
        AttributeInstance stepHeight = livingEntity.getAttribute(Attributes.STEP_HEIGHT);
        if (stepHeight != null) {
            if (stepHeight.hasModifier(this.getStepHeightModifier().id())) {
                stepHeight.removeModifier(this.getStepHeightModifier().id());
            }
        }
    }

    /**
     * @return The step height {@link AttributeModifier}. The default step height is 0.5, so this is an additional 0.5 to give the wearer a full block of step height.
     */
    public AttributeModifier getStepHeightModifier() {
        return new AttributeModifier(STEP_HEIGHT_ID, 0.5, AttributeModifier.Operation.ADD_VALUE);
    }
}
