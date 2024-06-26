package com.aetherteam.aether.item.accessories;

import com.aetherteam.aether.block.dispenser.AetherDispenseBehaviors;
import com.aetherteam.aether.client.AetherSoundEvents;
import io.github.fabricators_of_create.porting_lib.enchant.CustomEnchantingBehaviorItem;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.SoundEventData;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.function.Supplier;

public class AccessoryItem extends Item implements Accessory, Vanishable, CustomEnchantingBehaviorItem {
    private final Supplier<? extends SoundEvent> soundEventSupplier;
    public AccessoryItem(Properties properties) {
        this(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_GENERIC, properties);
    }

    public AccessoryItem(Supplier<? extends SoundEvent> soundEventSupplier, Properties properties) {
        super(properties);
        AccessoriesAPI.registerAccessory(this, this);
        this.soundEventSupplier = soundEventSupplier;
        DispenserBlock.registerBehavior(this, AetherDispenseBehaviors.DISPENSE_ACCESSORY_BEHAVIOR); // Behavior to allow accessories to be equipped from a Dispenser.
    }

    @Override
    public SoundEventData getEquipSound(ItemStack stack, SlotReference slot) {
        return new SoundEventData(this.soundEventSupplier.get(), 1.0F, 1.0F);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return CustomEnchantingBehaviorItem.super.canApplyAtEnchantingTable(stack, enchantment) || enchantment == Enchantments.BINDING_CURSE;
    }
}