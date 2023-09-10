package com.aetherteam.aether.item.miscellaneous.bucket;

import com.aetherteam.aether.item.AetherItems;
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

import java.util.Map;

public class SkyrootSolidBucketItem extends SolidBucketItem {
    public SkyrootSolidBucketItem(Block block, SoundEvent placeSound, Properties properties) {
        super(block, placeSound, properties);
    }

    /**
     * Sets the bucket after usage to a Skyroot Bucket. Otherwise behavior is the same as {@link SolidBucketItem}.
     * @param context The {@link UseOnContext} of the usage interaction.
     * @return The super {@link InteractionResult}.
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult interactionResult = super.useOn(context);
        Player player = context.getPlayer();
        if (interactionResult.consumesAction() && player != null && !player.isCreative()) {
            InteractionHand interactionHand = context.getHand();
            player.setItemInHand(interactionHand, new ItemStack(AetherItems.SKYROOT_BUCKET.get()));
        }
        return interactionResult;
    }

    /**
     * Normally, anything extending {@link net.minecraft.world.item.BlockItem} calls {@link Block#fillItemCategory(CreativeModeTab, NonNullList)} for this,
     * meaning in the creative tab it'll fill whatever item corresponds to the block in the registry, and not this item itself. So, we override that and instead perform the
     * default behavior for adding an item to a creative tab (as seen in {@link Item#fillItemCategory(CreativeModeTab, NonNullList)}) so that it doesn't conflict with vanilla solid bucket items (like the Powder Snow Bucket).
     * @param category The {@link CreativeModeTab} the item is trying to be added to.
     * @param items The {@link NonNullList} for all the items that have been added to the tabs.
     */
    @Override
    public void fillItemCategory(CreativeModeTab category, NonNullList<ItemStack> items) {
        if (this.allowedIn(category)) {
            items.add(new ItemStack(this));
        }
    }

    /**
     * This is used to pair a {@link net.minecraft.world.item.BlockItem} to a block, which we don't want to do because we don't want to pair our solid bucket items in place of vanilla's.
     * @param blockToItemMap The {@link Map} pairing {@link Block}s to {@link Item}s.
     * @param item The {@link Item} to register.
     */
    @Override
    public void registerBlocks(Map<Block, Item> blockToItemMap, Item item) { }

    /**
     * We don't register to the map, so we also don't allow removing from it. See {@link SkyrootSolidBucketItem#registerBlocks(Map, Item)}.
     * @param blockToItemMap The {@link Map} pairing {@link Block}s to {@link Item}s.
     * @param item The {@link Item} to register.
     */
    @Override
    public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item item) { }
}
