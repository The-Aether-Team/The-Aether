package com.aetherteam.aether.item.tools.gravitite;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.GravititeTool;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.context.UseOnContext;

public class GravititePickaxeItem extends PickaxeItem implements GravititeTool {
    public GravititePickaxeItem() {
        super(AetherItemTiers.GRAVITITE, 1, -2.8F, new Item.Properties());
    }

    /**
     * Interaction code to prioritize the ability at {@link GravititeTool#floatBlock(UseOnContext)} over the normal tool interactions.
     *
     * @param context The {@link UseOnContext} of the tool interaction.
     * @return The {@link InteractionResult} of the interaction, which returns the result of {@link PickaxeItem#useOn(UseOnContext)} if the ability fails and a {@link InteractionResult#sidedSuccess(boolean)} if it doesn't.
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
