package com.aetherteam.aether.block.dispenser;

import com.aetherteam.aether.entity.projectile.dart.AbstractDart;
import com.aetherteam.aether.item.combat.DartItem;
import net.minecraft.Util;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class DispenseDartBehavior extends DefaultDispenseItemBehavior {
    protected final Supplier<? extends Item> dartItem;
    private final ProjectileItem.DispenseConfig dispenseConfig;

    public DispenseDartBehavior(Supplier<? extends Item> dartItem) {
        this.dartItem = dartItem;
    }

    /**
     * Dispenses the {@link AbstractDart} projectile and shrinks the {@link DartItem} stack.
     *
     * @param blockSource The {@link BlockSource} for the dispenser.
     * @param stack       The {@link ItemStack} in the dispenser.
     * @return A modified version of the {@link ItemStack} in the dispenser.
     */
    @Override
    public ItemStack execute(BlockSource blockSource, ItemStack stack) {
        Projectile projectile = this.getProjectile(blockSource.level(), DispenserBlock.getDispensePosition(blockSource), stack);
        if (projectile != null) {
            AetherDispenseBehaviors.spawnProjectile(blockSource, projectile, this.dispenseConfig.power(), this.getUncertainty());
            stack.shrink(1);
        }
        return stack;
    }

    /**
     * Sets up the {@link AbstractDart} projectile to be dispensed using the {@link DartItem} in the dispenser.
     *
     * @param level    The {@link Level} the dispenser is in.
     * @param position The {@link Position} to dispense at.
     * @param stack    The {@link ItemStack} in the dispenser.
     * @return The {@link Projectile} to dispense.
     */
    @Nullable
    @Override
    protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
        Item item = this.dartItem.get();
        if (item instanceof DartItem dartItem) {
            AbstractDart abstractDart = dartItem.createDart(level);
            if (abstractDart != null) {
                return Util.make(abstractDart, (dart) -> {
                    dart.setPos(position.x(), position.y(), position.z());
                    dart.pickup = AbstractArrow.Pickup.ALLOWED;
                    dart.setNoGravity(true);
                });
            }
        }
        return null;
    }

    @Override
    protected float getUncertainty() {
        return 3.0F;
    }
}
