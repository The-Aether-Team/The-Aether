package com.gildedgames.aether.common.block.util.dispenser;

import com.gildedgames.aether.common.entity.projectile.dart.AbstractDartEntity;
import com.gildedgames.aether.common.item.combat.DartItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class DispenseDartBehavior extends AbstractProjectileDispenseBehavior
{
    protected final Supplier<Item> dartItem;

    public DispenseDartBehavior(Supplier<Item> dartItem) {
        this.dartItem = dartItem;
    }

    @Override
    public ItemStack execute(BlockSource p_82487_1_, ItemStack p_82487_2_) {
        Level world = p_82487_1_.getLevel();
        Position iposition = DispenserBlock.getDispensePosition(p_82487_1_);
        Direction direction = p_82487_1_.getBlockState().getValue(DispenserBlock.FACING);
        Projectile projectileentity = this.getProjectile(world, iposition, p_82487_2_);
        projectileentity.shoot(direction.getStepX(), (float) direction.getStepY(), direction.getStepZ(), this.getPower(), this.getUncertainty());
        world.addFreshEntity(projectileentity);
        p_82487_2_.shrink(1);
        return p_82487_2_;
    }

    @Override
    protected Projectile getProjectile(Level world, Position position, ItemStack stack) {
        Item item = this.dartItem.get();
        if (item instanceof DartItem) {
            DartItem dartItem = (DartItem) item;
            AbstractDartEntity dartEntity = dartItem.createDart(world);
            dartEntity.setPos(position.x(), position.y(), position.z());
            dartEntity.pickup = AbstractArrow.Pickup.ALLOWED;
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
