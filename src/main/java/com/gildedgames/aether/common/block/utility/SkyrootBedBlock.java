package com.gildedgames.aether.common.block.utility;

import com.gildedgames.aether.common.block.entity.SkyrootBedBlockEntity;
import com.gildedgames.aether.common.registry.AetherDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;

public class SkyrootBedBlock extends BedBlock
{
    public SkyrootBedBlock(Properties properties) {
        super(DyeColor.WHITE, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, BedPart.FOOT).setValue(OCCUPIED, Boolean.FALSE));
    }

    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new SkyrootBedBlockEntity(pos, state);
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, Level worldIn, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        if (worldIn.isClientSide()) {
            return InteractionResult.CONSUME;
        } else {
            if (state.getValue(PART) != BedPart.HEAD) {
                pos = pos.relative(state.getValue(FACING));
                state = worldIn.getBlockState(pos);
                if (!state.is(this)) {
                    return InteractionResult.CONSUME;
                }
            }

            if (!canSetSpawn(worldIn)) {
                worldIn.removeBlock(pos, false);
                BlockPos blockpos = pos.relative(state.getValue(FACING).getOpposite());
                if (worldIn.getBlockState(blockpos).is(this)) {
                    worldIn.removeBlock(blockpos, false);
                }
                worldIn.explode(null, DamageSource.badRespawnPointExplosion(), null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 5.0F, true, Explosion.BlockInteraction.DESTROY);
                return InteractionResult.SUCCESS;
            } else if (state.getValue(OCCUPIED)) {
                player.displayClientMessage(new TranslatableComponent("block.minecraft.bed.occupied"), true);
                return InteractionResult.SUCCESS;
            } else {
                player.startSleepInBed(pos).ifLeft((result) -> {
                    if (result != null && result.getMessage() != null) {
                        player.displayClientMessage(result.getMessage(), true);
                    }
                });
                return InteractionResult.SUCCESS;
            }
        }
    }

    public static boolean canSetSpawn(Level world) {
        return world.dimension() == AetherDimensions.AETHER_WORLD;
    }
}

