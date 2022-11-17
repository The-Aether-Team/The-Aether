package com.gildedgames.aether.block.natural;

import com.gildedgames.aether.block.AetherBlockStateProperties;
import com.gildedgames.aether.block.AetherBlocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.Nullable;

public class BerryBushBlock extends AetherBushBlock {
	public BerryBushBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}

	@Override
	public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pBlockEntity, ItemStack pTool) {
		super.playerDestroy(pLevel, pPlayer, pPos, pState, pBlockEntity, pTool);
		if (pTool.getEnchantmentLevel(Enchantments.SILK_TOUCH) <= 0) {
			pLevel.setBlock(pPos, AetherBlocks.BERRY_BUSH_STEM.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, pState.getValue(AetherBlockStateProperties.DOUBLE_DROPS)), 3);
		}
	}

	@Override
	public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
		super.onBlockExploded(state, world, pos, explosion);
		world.setBlock(pos, AetherBlocks.BERRY_BUSH_STEM.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, state.getValue(AetherBlockStateProperties.DOUBLE_DROPS)), 3);
	}
}
