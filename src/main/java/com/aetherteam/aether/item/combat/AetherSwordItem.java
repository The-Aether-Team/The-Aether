package com.aetherteam.aether.item.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AetherSwordItem extends SwordItem {
    private final List<Component> dungeonTooltips = new ArrayList<>();

    public AetherSwordItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    /**
     * When in a creative tab, this adds a tooltip to an item indicating what dungeon it can be found in.
     * @param stack The {@link ItemStack} with the tooltip.
     * @param level The {@link Level} the item is rendered in.
     * @param components A {@link List} of {@link Component}s making up this item's tooltip.
     * @param flag A {@link TooltipFlag} for the tooltip type.
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);
        if (flag.isCreative()) {
            components.addAll(this.dungeonTooltips);
        }
    }

    public AetherSwordItem addDungeonTooltip(Component dungeonTooltip) {
        this.dungeonTooltips.add(dungeonTooltip);
        return this;
    }
}
