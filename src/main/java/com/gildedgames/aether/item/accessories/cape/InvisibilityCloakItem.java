package com.gildedgames.aether.item.accessories.cape;

import com.gildedgames.aether.item.accessories.AccessoryItem;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import top.theillusivec4.curios.api.SlotContext;

/**
 * Additional invisibility behavior is handled with {@link com.gildedgames.aether.client.event.listeners.abilities.AccessoryAbilityClientListener#onRenderPlayer(RenderPlayerEvent.Pre)}
 * and {@link com.gildedgames.aether.client.event.listeners.abilities.AccessoryAbilityClientListener#onRenderArm(RenderArmEvent)}.<br><br>
 * The wearer is also hidden from other entities' targeting by {@link com.gildedgames.aether.event.listeners.abilities.AccessoryAbilityListener#onTargetSet(LivingEvent.LivingVisibilityEvent)}.
 */
public class InvisibilityCloakItem extends AccessoryItem {
    public InvisibilityCloakItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        slotContext.entity().setInvisible(true);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        slotContext.entity().setInvisible(false);
    }
}
