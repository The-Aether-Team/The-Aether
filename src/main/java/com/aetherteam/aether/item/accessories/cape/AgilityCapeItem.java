package com.aetherteam.aether.item.accessories.cape;

import com.aetherteam.aether.Aether;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AgilityCapeItem extends CapeItem {
    /**
     * The unique identifier for the item's step height modifier.
     */
    private static final ResourceLocation STEP_HEIGHT_ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "agility_cape_step_height");

    public AgilityCapeItem(String capeLocation, Properties properties) {
        super(capeLocation, properties);
    }

    @Override
    public void tick(ItemStack stack, Level level, LivingEntity entity, InteractionHand hand) {
        AttributeInstance stepHeight = entity.getAttribute(Attributes.STEP_HEIGHT);
        if (stepHeight != null) {
            if (!stepHeight.hasModifier(this.getStepHeightModifier().id()) && !entity.isShiftKeyDown()) {
                stepHeight.addTransientModifier(this.getStepHeightModifier());
            }
            if (entity.isShiftKeyDown()) {
                stepHeight.removeModifier(this.getStepHeightModifier().id());
            }
        }
    }

    @Override
    public void onUnequip(ItemStack stack, Level level, LivingEntity entity, InteractionHand hand) {
        AttributeInstance stepHeight = entity.getAttribute(Attributes.STEP_HEIGHT);
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
