package com.aetherteam.aether.block.natural;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
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
	 * [CODE COPY] - {@link net.minecraft.world.level.block.SweetBerryBushBlock#entityInside(BlockState, Level, BlockPos, Entity)}.<br><br>
	 * Modified to remove damage behavior.<br><br>
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (AetherConfig.SERVER.berry_bush_consistency.get()) {
			if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
				entity.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
			}
		}
	}

	/**
	 * Allows Berry Bushes to be harvested by right-clicking if the {@link AetherConfig.Server#berry_bush_consistency} config is enabled.<br><br>
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 * @param state The {@link BlockState} of the block.
	 * @param level The {@link Level} the block is in.
	 * @param pos The {@link BlockPos} of the block.
	 * @param player The {@link Player} interacting with the block.
	 * @param hand The {@link InteractionHand}.
	 * @param hit The {@link BlockHitResult} when using.
	 * @return The {@link InteractionResult} for using this block.
	 */
	@SuppressWarnings("deprecation")
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (AetherConfig.SERVER.berry_bush_consistency.get()) {
			Block.dropResources(state, level, pos, null, player, ItemStack.EMPTY);
			level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.getRandom().nextFloat() * 0.4F);
			level.setBlock(pos, AetherBlocks.BERRY_BUSH_STEM.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, state.getValue(AetherBlockStateProperties.DOUBLE_DROPS)), 1 | 2);
			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
			return InteractionResult.sidedSuccess(level.isClientSide());
		} else {
			return super.use(state, level, pos, player, hand, hit);
		}
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
			if (AetherConfig.SERVER.berry_bush_consistency.get()) { // Destroy stem too if config is enabled.
				level.destroyBlock(pos, true, player);
			}
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
		if (AetherConfig.SERVER.berry_bush_consistency.get()) { // Destroy stem too if config is enabled.
			level.destroyBlock(pos, true);
		}
	}

	/**
	 * [CODE COPY] - {@link net.minecraft.world.level.block.state.BlockBehaviour#getCollisionShape(BlockState, BlockGetter, BlockPos, CollisionContext)}.<br><br>
	 * Modified to have a try/catch with a config check.<br><br>
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		try {
			return AetherConfig.SERVER.berry_bush_consistency.get() ? Shapes.empty() : state.getShape(level, pos);
		} catch (IllegalStateException ignored) { }
		return state.getShape(level, pos);
	}
}
