package com.aetherteam.aether.item.accessories.gloves;

import io.wispforest.accessories.api.events.extra.PiglinNeutralInducer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;

public class GoldGlovesItem extends GlovesItem implements PiglinNeutralInducer {
    public GoldGlovesItem(double punchDamage, Properties properties) {
        super(ArmorMaterials.GOLD, punchDamage, "gold_gloves", SoundEvents.ARMOR_EQUIP_GOLD, properties);
    }

    @Override
    public TriState makePiglinsNeutral(ItemStack stack, SlotReference reference) {
        return TriState.TRUE;
    }
}
