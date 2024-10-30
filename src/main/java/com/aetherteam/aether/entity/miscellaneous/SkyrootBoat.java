package com.aetherteam.aether.entity.miscellaneous;

import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;

public class SkyrootBoat extends Boat {
    public static final Boat.Type SKYROOT = Boat.Type.valueOf("AETHER_SKYROOT");

    public SkyrootBoat(EntityType<? extends SkyrootBoat> type, Level level) {
        super(type, level);
        this.setVariant(SKYROOT);
    }

    public SkyrootBoat(Level level, double x, double y, double z) {
        this(AetherEntityTypes.SKYROOT_BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }
}
