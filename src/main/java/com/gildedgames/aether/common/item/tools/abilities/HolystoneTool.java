package com.gildedgames.aether.common.item.tools.abilities;

import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface HolystoneTool {
    static void dropAmbrosium(Player player, Level level, BlockPos pos, ItemStack stack) {
        if (stack.is(AetherTags.Items.HOLYSTONE_TOOLS)) {
            if (!level.isClientSide && player.random.nextInt(100) <= 5) {
                ItemEntity itementity = new ItemEntity(level, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, new ItemStack(AetherItems.AMBROSIUM_SHARD.get()));
                level.addFreshEntity(itementity);
            }
        }
    }
}
