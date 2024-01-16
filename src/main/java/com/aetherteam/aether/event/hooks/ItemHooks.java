package com.aetherteam.aether.event.hooks;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemHooks {
    /**
     * Adds a tooltip to loot items in the creative inventory, indicating what dungeon(s) they can be found in.
     * @param components The old {@link List} of {@link Component}s for the item.
     * @param stack The loot {@link ItemStack}.
     * @param flag The {@link TooltipFlag} for what type of tooltip this is.
     * @see com.aetherteam.aether.event.listeners.ItemListener#onTooltipAdd(ItemStack, TooltipFlag, List)
     */
    public static void addDungeonTooltips(List<Component> components, ItemStack stack, TooltipFlag flag) {
        if (flag.isCreative()) {
            int position = components.size();
            Component itemName = stack.getItem().getName(stack);
            for (int i = 0; i < position; i++) {
                Component component = components.get(i);
                if (component.getString().equals(itemName.getString())) {
                    position = i + 1;
                    break;
                }
            }
            if (stack.is(AetherTags.Items.BRONZE_DUNGEON_LOOT)) {
                components.add(position, AetherItems.BRONZE_DUNGEON_TOOLTIP);
            }
            if (stack.is(AetherTags.Items.SILVER_DUNGEON_LOOT)) {
                components.add(position, AetherItems.SILVER_DUNGEON_TOOLTIP);
            }
            if (stack.is(AetherTags.Items.GOLD_DUNGEON_LOOT)) {
                components.add(position, AetherItems.GOLD_DUNGEON_TOOLTIP);
            }
        }
    }
}
