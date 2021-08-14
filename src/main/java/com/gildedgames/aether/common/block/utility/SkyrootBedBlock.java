package com.gildedgames.aether.common.block.utility;

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
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, BedPart.FOOT).setValue(OCCUPIED, Boolean.FALSE));
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isClientSide) {
            return ActionResultType.CONSUME;
        } else {
            if (state.getValue(PART) != BedPart.HEAD) {
                pos = pos.relative(state.getValue(FACING));
                state = worldIn.getBlockState(pos);
                if (!state.is(this)) {
                    return ActionResultType.CONSUME;
                }
            }

            if (!doesBedWork(worldIn)) {
                worldIn.removeBlock(pos, false);
                BlockPos blockpos = pos.relative(state.getValue(FACING).getOpposite());
                if (worldIn.getBlockState(blockpos).is(this)) {
                    worldIn.removeBlock(blockpos, false);
                }
                worldIn.explode(null, DamageSource.badRespawnPointExplosion(), null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 5.0F, true, Explosion.Mode.DESTROY);
                return ActionResultType.SUCCESS;
            } else if (state.getValue(OCCUPIED)) {
                player.displayClientMessage(new TranslationTextComponent("block.minecraft.bed.occupied"), true);
                return ActionResultType.SUCCESS;
            } else {
                player.startSleepInBed(pos).ifLeft((result) -> {
                    if (result != null) {
                        player.displayClientMessage(result.getMessage(), true);
                    }
                });
                return ActionResultType.SUCCESS;
            }
        }
    }

    public static boolean doesBedWork(World world) {
        return world.dimension() == AetherDimensions.AETHER_WORLD;
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new SkyrootBedTileEntity();
    }
}

