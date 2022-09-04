package com.gildedgames.aether.block.dungeon;

import com.gildedgames.aether.client.particle.AetherParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Subclass of TreasureRoomBlock that's used for doors that are opened and closed by the boss.
 * Use this for blocks that are operated on even when the boss wins.
 */
public class InvisibleBlock extends TreasureRoomBlock {
    public InvisibleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.gameMode != null && minecraft.gameMode.getPlayerMode() == GameType.CREATIVE && minecraft.player != null && minecraft.level != null) {
            ItemStack itemstack = minecraft.player.getMainHandItem();
            Item item = itemstack.getItem();
            if (item instanceof BlockItem blockItem) {
                if (blockItem.getBlock() == this && state.getValue(INVISIBLE)) {
                    minecraft.level.addParticle(AetherParticleTypes.BOSS_DOORWAY_BLOCK.get(), (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
