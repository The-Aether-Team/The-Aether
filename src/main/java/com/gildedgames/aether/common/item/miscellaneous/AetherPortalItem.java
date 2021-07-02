package com.gildedgames.aether.common.item.miscellaneous;

import com.gildedgames.aether.common.block.miscellaneous.AetherPortalBlock;
import com.gildedgames.aether.common.registry.AetherBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AetherPortalItem extends Item
{
    public AetherPortalItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        if (player != null) {
            if (createPortalFrame(context.getLevel(), context.getClickedPos().relative(context.getClickedFace()), player.getDirection().getAxis())) {
                player.swing(context.getHand());
                if (!player.isCreative()) {
                    context.getItemInHand().shrink(1);
                }
                return ActionResultType.CONSUME;
            }
        }
        return ActionResultType.FAIL;
    }

    private boolean createPortalFrame(World world, BlockPos pos, Direction.Axis axis) {
        for (int h = -1; h < 3; h++) {
            for (int v = pos.getY(); v < pos.getY() + 5; v++) {
                BlockPos truePos = axis == Direction.Axis.X ? new BlockPos(pos.getX(), v, pos.getZ() + h) : new BlockPos(pos.getX() + h, v, pos.getZ());
                if (!world.getBlockState(truePos).isAir()) {
                    return false;
                }
            }
        }

        for (int h = -1; h < 3; h++) {
            for (int v = pos.getY(); v < pos.getY() + 5; v++) {
                BlockPos truePos = axis == Direction.Axis.X ? new BlockPos(pos.getX(), v, pos.getZ() + h) : new BlockPos(pos.getX() + h, v, pos.getZ());
                world.setBlockAndUpdate(truePos, Blocks.GLOWSTONE.defaultBlockState());
            }
        }

        for (int h = 0; h < 2; h++) {
            for (int v = pos.getY() + 1; v < pos.getY() + 4; v++) {
                BlockPos truePos = axis == Direction.Axis.X ? new BlockPos(pos.getX(), v, pos.getZ() + h) : new BlockPos(pos.getX() + h, v, pos.getZ());
                Direction.Axis trueAxis = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
                world.setBlock(truePos, AetherBlocks.AETHER_PORTAL.get().defaultBlockState().setValue(AetherPortalBlock.AXIS, trueAxis), 18);
            }
        }

        return true;
    }
}
