package com.gildedgames.aether.common.block.utility;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.tile.SkyrootBedTileEntity;
import com.gildedgames.aether.common.registry.AetherDimensions;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.*;

public class SkyrootBedBlock extends BedBlock
{
    public SkyrootBedBlock(AbstractBlock.Properties properties) {
        super(DyeColor.WHITE, properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(PART, BedPart.FOOT).with(OCCUPIED, Boolean.FALSE));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.CONSUME;
        } else {
            if (state.get(PART) != BedPart.HEAD) {
                pos = pos.offset(state.get(HORIZONTAL_FACING));
                state = worldIn.getBlockState(pos);
                if (!state.isIn(this)) {
                    return ActionResultType.CONSUME;
                }
            }

            if (!doesBedWork(worldIn)) {
                worldIn.removeBlock(pos, false);
                BlockPos blockpos = pos.offset(state.get(HORIZONTAL_FACING).getOpposite());
                if (worldIn.getBlockState(blockpos).isIn(this)) {
                    worldIn.removeBlock(blockpos, false);
                }
                worldIn.createExplosion(null, DamageSource.func_233546_a_(), null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 5.0F, true, Explosion.Mode.DESTROY);
                return ActionResultType.SUCCESS;
            } else if (state.get(OCCUPIED)) {
                player.sendStatusMessage(new TranslationTextComponent("block.minecraft.bed.occupied"), true);
                return ActionResultType.SUCCESS;
            } else {
                Aether.LOGGER.info("went to bed");
                player.trySleep(pos).ifLeft((result) -> {
                    if (result != null) {
                        player.sendStatusMessage(result.getMessage(), true);
                    }
                });
                return ActionResultType.SUCCESS;
            }
        }
    }

    public static boolean doesBedWork(World world) {
        return world.getDimensionKey() == AetherDimensions.AETHER_WORLD;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new SkyrootBedTileEntity();
    }
}

