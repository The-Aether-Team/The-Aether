package com.aetherteam.aether.block.dispenser;

import com.aetherteam.aether.entity.miscellaneous.SkyrootBoat;
import com.aetherteam.aether.entity.miscellaneous.SkyrootChestBoat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

/**
 * [CODE COPY] - {@link BoatDispenseItemBehavior}
 */
public class DispenseSkyrootBoatBehavior extends DefaultDispenseItemBehavior {
    private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
    private final boolean isChestBoat;

    public DispenseSkyrootBoatBehavior() {
        this(false);
    }

    public DispenseSkyrootBoatBehavior(boolean isChestBoat) {
        this.isChestBoat = isChestBoat;
    }

    public ItemStack execute(BlockSource source, ItemStack stack) {
        Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
        Level level = source.getLevel();
        double width = 0.5625 + EntityType.BOAT.getWidth() / 2.0;
        double x = source.x() + direction.getStepX() * width;
        double y = source.y() + direction.getStepY() * 1.125F;
        double z = source.z() + direction.getStepZ() * width;
        BlockPos blockpos = source.getPos().relative(direction);
        Boat boat = (this.isChestBoat ? new SkyrootChestBoat(level, width, x, y) : new SkyrootBoat(level, width, x, y));
        boat.setYRot(direction.toYRot());
//        double yOffset; TODO: PORT
//        if (boat.canBoatInFluid(level.getFluidState(blockpos))) {
//            yOffset = 1.0D;
//        } else {
//            if (!level.getBlockState(blockpos).isAir() || !boat.canBoatInFluid(level.getFluidState(blockpos.below()))) {
//                return this.defaultDispenseItemBehavior.dispense(source, stack);
//            }
//            yOffset = 0.0D;
//        }
//        boat.setPos(x, y + yOffset, z);
        level.addFreshEntity(boat);
        stack.shrink(1);
        return stack;
    }

    protected void playSound(BlockSource source) {
        source.getLevel().levelEvent(1000, source.getPos(), 0);
    }
}
