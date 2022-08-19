package com.gildedgames.aether.entity.projectile.crystal;

import com.gildedgames.aether.entity.AetherEntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/**
 * A damaging projectile shot by the sun spirit. It floats around the room for 15 seconds.
 */
public class FireCrystal extends AbstractCrystal {

    /**
     * Used for registering the entity. Use the other constructor to provide more context.
     */
    public FireCrystal(EntityType<? extends FireCrystal> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * @param shooter - The entity that created this projectile
     */
    public FireCrystal(Level level, Entity shooter) {
        this(AetherEntityTypes.FIRE_CRYSTAL.get(), level);
        this.setOwner(shooter);
        this.setPos(shooter.getX(), shooter.getY(), shooter.getZ());
    }


    /**
     * This is needed to make the crystal vulnerable to player attacks.
     */
    @Override
    public boolean isPickable() {
        return true;
    }
}
