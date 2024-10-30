package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.combat.abilities.weapon.ZaniteWeapon;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

public class ZaniteSwordItem extends SwordItem implements ZaniteWeapon {
    public ZaniteSwordItem() {
        super(AetherItemTiers.ZANITE, new Item.Properties().attributes(SwordItem.createAttributes(AetherItemTiers.ZANITE, 3.0F, -2.4F)));
    }

    /**
     *
     * @see Aether#eventSetup(IEventBus)
     * @see ZaniteWeapon#increaseDamage(ItemAttributeModifiers, ItemStack)
     */
    public static void onModifyAttributes(ItemAttributeModifierEvent event) {
        ItemAttributeModifiers modifiers = event.getDefaultModifiers();
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof ZaniteWeapon zaniteWeapon) {
            ItemAttributeModifiers.Entry attributeEntry = zaniteWeapon.increaseDamage(modifiers, itemStack);
            event.replaceModifier(attributeEntry.attribute(), attributeEntry.modifier(), attributeEntry.slot());
        }
    }
}
