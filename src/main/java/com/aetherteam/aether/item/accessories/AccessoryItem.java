package com.aetherteam.aether.item.accessories;

import com.aetherteam.aether.block.dispenser.AetherDispenseBehaviors;
import com.aetherteam.aether.client.AetherSoundEvents;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import io.github.fabricators_of_create.porting_lib.enchant.CustomEnchantingBehaviorItem;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.function.Supplier;

public class AccessoryItem extends TrinketItem implements Vanishable, CustomEnchantingBehaviorItem {
    private final Supplier<? extends SoundEvent> soundEventSupplier;
    public AccessoryItem(Properties properties) {
        this(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_GENERIC, properties);
    }

    public AccessoryItem(Supplier<? extends SoundEvent> soundEventSupplier, Properties properties) {
        super(properties);
        this.soundEventSupplier = soundEventSupplier;
        DispenserBlock.registerBehavior(this, AetherDispenseBehaviors.DISPENSE_ACCESSORY_BEHAVIOR); // Behavior to allow accessories to be equipped from a Dispenser.
    }

    public SoundInfo getEquipSound(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return new SoundInfo(this.soundEventSupplier.get(), 1.0F, 1.0F);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        SoundInfo soundInfo = getEquipSound(stack, slot, entity);
        entity.level().playSound(null, entity.blockPosition(), soundInfo.soundEvent(),
                entity.getSoundSource(), soundInfo.volume(), soundInfo.pitch());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return CustomEnchantingBehaviorItem.super.canApplyAtEnchantingTable(stack, enchantment) || enchantment == Enchantments.BINDING_CURSE;
    }

    public record SoundInfo(SoundEvent soundEvent, float volume, float pitch) {}
}