package com.aetherteam.aether.item.accessories.gloves;

import io.github.fabricators_of_create.porting_lib.item.PiglinsNeutralItem;
import io.wispforest.accessories.api.events.extra.PiglinNeutralInducer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;

public class GoldGlovesItem extends GlovesItem implements PiglinsNeutralItem, PiglinNeutralInducer {
    public GoldGlovesItem(double punchDamage, Properties properties) {
        super(ArmorMaterials.GOLD, punchDamage, "gold_gloves", () -> SoundEvents.ARMOR_EQUIP_GOLD, properties);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    @Override
    public TriState makesPiglinsNeutral(ItemStack stack, SlotReference reference) {
        return TriState.of(makesPiglinsNeutral(stack, reference.entity()));
    }
}
