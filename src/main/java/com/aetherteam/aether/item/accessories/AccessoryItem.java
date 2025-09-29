package com.aetherteam.aether.item.accessories;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.integration.AccessoryUtil;
import com.aetherteam.aether.inventory.container.AccessoryContainer;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

//todo possibly convert to a data component when the first iteration of this is done and functional
public class AccessoryItem extends Item {
    private final AccessoryContainer.SlotType slotType;
    private final Holder<SoundEvent> equipSound;

    public AccessoryItem(Properties properties, AccessoryContainer.SlotType slotType) {
        this(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_GENERIC, properties, slotType);
    }

    public AccessoryItem(Holder<SoundEvent> equipSound, Properties properties, AccessoryContainer.SlotType slotType) {
        super(properties);
        this.slotType = slotType;
        this.equipSound = equipSound;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return AccessoryUtil.equip(player, player.getItemInHand(hand), this.getSlotType());
    }

    public void tick(ItemStack stack, Level level, LivingEntity entity, InteractionHand hand) { //todo behavior

    }

    public void onEquip(ItemStack stack, Level level, LivingEntity entity, InteractionHand hand) {  //todo behavior

    }

    public void onUnequip(ItemStack stack, Level level, LivingEntity entity, InteractionHand hand) {  //todo behavior

    }

    public AccessoryContainer.SlotType getSlotType() {
        return this.slotType;
    }

    @Nullable
    public Holder<SoundEvent> getEquipSound() {  //todo behavior
        return this.equipSound;
    }

    public Map<Holder<Attribute>, AttributeModifier> getAttributes(ItemStack stack) {  //todo behavior
        return new HashMap<>();
    }
}
