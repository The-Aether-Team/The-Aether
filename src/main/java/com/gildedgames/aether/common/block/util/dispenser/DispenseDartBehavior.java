package com.gildedgames.aether.common.block.util.dispenser;

import com.gildedgames.aether.common.entity.projectile.dart.AbstractDartEntity;
import com.gildedgames.aether.common.item.combat.DartItem;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class DispenseDartBehavior extends ProjectileDispenseBehavior
{
    protected final Supplier<Item> dartItem;

    public DispenseDartBehavior(Supplier<Item> dartItem) {
        this.dartItem = dartItem;
    }

    @Override
    public ItemStack execute(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
        World world = p_82487_1_.getLevel();
        IPosition iposition = DispenserBlock.getDispensePosition(p_82487_1_);
        Direction direction = p_82487_1_.getBlockState().getValue(DispenserBlock.FACING);
        ProjectileEntity projectileentity = this.getProjectile(world, iposition, p_82487_2_);
        projectileentity.shoot(direction.getStepX(), (float) direction.getStepY(), direction.getStepZ(), this.getPower(), this.getUncertainty());
        world.addFreshEntity(projectileentity);
        p_82487_2_.shrink(1);
        return p_82487_2_;
    }

    @Override
    protected ProjectileEntity getProjectile(World world, IPosition position, ItemStack stack) {
        Item item = this.dartItem.get();
        if (item instanceof DartItem) {
            DartItem dartItem = (DartItem) item;
            AbstractDartEntity dartEntity = dartItem.createDart(world);
            dartEntity.setPos(position.x(), position.y(), position.z());
            dartEntity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
            dartEntity.setNoGravity(true);
            return dartEntity;
        } else {
            return null;
        }
    }

    @Override
    protected float getUncertainty() {
        return 3.0F;
    }
}
