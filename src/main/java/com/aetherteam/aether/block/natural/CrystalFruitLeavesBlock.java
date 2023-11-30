package com.aetherteam.aether.block.natural;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class CrystalFruitLeavesBlock extends LeavesWithParticlesBlock {
    public CrystalFruitLeavesBlock(Supplier<? extends ParticleOptions> particle, Properties properties) {
        super(particle, properties);
    }

    /**
     * Warning for "deprecation" is suppressed because the method is fine to override.
     * @param state The {@link BlockState} of the block.
     * @param level The {@link Level} the block is in.
     * @param pos The {@link BlockPos} of the block.
     * @param player The {@link Player} interacting with the block.
     * @param hand The {@link InteractionHand} the player interacts with.
     * @param hit The {@link BlockHitResult} of the interaction.
     * @return The {@link InteractionResult} of the interaction.
     */
    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (AetherConfig.SERVER.crystal_leaves_consistency.get()) {
            CrystalFruitLeavesBlock.dropResources(state, level, pos, null, player, ItemStack.EMPTY, true);
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.getRandom().nextFloat() * 0.4F);
            level.setBlock(pos, AetherBlocks.CRYSTAL_LEAVES.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, state.getValue(AetherBlockStateProperties.DOUBLE_DROPS)), 1 | 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
            return InteractionResult.sidedSuccess(level.isClientSide());
        } else {
            return super.use(state, level, pos, player, hand, hit);
        }
    }

    /**
     * [CODE COPY] - {@link Block#dropResources(BlockState, Level, BlockPos, BlockEntity, Entity, ItemStack, boolean)}.<br><br>
     * Modified to not drop Skyroot Sticks from leaves' loot table.
     */
    public static void dropResources(BlockState state, Level level, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack tool, boolean dropXp) {
        if (level instanceof ServerLevel serverLevel) {
            getDrops(state, serverLevel, pos, blockEntity, entity, tool).forEach((itemStack) -> {
                if (itemStack.getItem() != AetherItems.SKYROOT_STICK.get()) {
                    popResource(level, pos, itemStack);
                }
            });
            state.spawnAfterBreak(serverLevel, pos, tool, dropXp);
        }
    }
}
