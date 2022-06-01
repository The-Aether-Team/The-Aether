package com.gildedgames.aether.common.item.miscellaneous.bucket;

import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import java.util.Map;

public class SkyrootSolidBucketItem extends SolidBucketItem {
    public SkyrootSolidBucketItem(Block block, SoundEvent placeSound, Properties properties) {
        super(block, placeSound, properties);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        InteractionResult interactionresult = super.useOn(context);
        Player player = context.getPlayer();
        if (interactionresult.consumesAction() && player != null && !player.isCreative()) {
            InteractionHand interactionhand = context.getHand();
            player.setItemInHand(interactionhand, AetherItems.SKYROOT_BUCKET.get().getDefaultInstance());
        }
        return interactionresult;
    }

    public void fillItemCategory(@Nonnull CreativeModeTab category, @Nonnull NonNullList<ItemStack> items) {
        if (this.allowdedIn(category)) {
            items.add(new ItemStack(this));
        }
    }

    public void registerBlocks(@Nonnull Map<Block, Item> blockToItemMap, @Nonnull Item item) { }
}
