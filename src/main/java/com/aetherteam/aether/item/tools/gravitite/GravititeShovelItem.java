package com.aetherteam.aether.item.tools.gravitite;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.GravititeTool;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.UseOnContext;

public class GravititeShovelItem extends ShovelItem implements GravititeTool {
    public GravititeShovelItem() {
        super(AetherItemTiers.GRAVITITE, new Item.Properties().attributes(ShovelItem.createAttributes(AetherItemTiers.GRAVITITE, 1.5F, -3.0F)));
    }

    /**
     * Interaction code to prioritize the ability at {@link GravititeTool#floatBlock(UseOnContext)} over the normal tool interactions.
     *
     * @param context The {@link UseOnContext} of the tool interaction.
     * @return The {@link InteractionResult} of the interaction, which returns the result of {@link ShovelItem#useOn(UseOnContext)} if the ability fails and a {@link InteractionResult#sidedSuccess(boolean)} if it doesn't.
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!this.floatBlock(context)) {
            return super.useOn(context);
        } else {
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide());
        }
    }
}
