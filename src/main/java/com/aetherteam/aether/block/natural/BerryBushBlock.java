package com.aetherteam.aether.block.natural;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

public class BerryBushBlock extends AetherBushBlock {
	public BerryBushBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}

	/**
	 * Sets a Berry Bush Stem in place of a destroyed Berry Bush, if Silk Touch wasn't used on the Berry Bush.
	 * @param level The {@link Level} the block is in.
	 * @param player The {@link Player} destroying the block.
	 * @param pos The {@link BlockPos} of the block.
	 * @param state The {@link BlockState} of the block.
	 * @param blockEntity The {@link BlockEntity} that the block has.
	 * @param tool The {@link ItemStack} of the tool used to destroy the block.
	 */
	@Override
	public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
		super.playerDestroy(level, player, pos, state, blockEntity, tool);
		if (tool.getEnchantmentLevel(Enchantments.SILK_TOUCH) <= 0) {
			level.setBlock(pos, AetherBlocks.BERRY_BUSH_STEM.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, state.getValue(AetherBlockStateProperties.DOUBLE_DROPS)), 1 | 2);
		}
	}

	/**
	 * Sets a Berry Bush stem in place of a Berry Bush when exploded.
	 * @param state The {@link BlockState} of the block.
	 * @param level The {@link Level} the block is in.
	 * @param pos The {@link BlockPos} of the block.
	 * @param explosion The {@link Explosion} affecting the block.
	 */
	@Override
	public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
		super.onBlockExploded(state, level, pos, explosion);
		level.setBlock(pos, AetherBlocks.BERRY_BUSH_STEM.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, state.getValue(AetherBlockStateProperties.DOUBLE_DROPS)), 1 | 2);
	}
}
