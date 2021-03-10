package com.gildedgames.aether.common.entity.block;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class ColdParachuteEntity extends ParachuteEntity
{
    public ColdParachuteEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    public ColdParachuteEntity(World world) {
        this(AetherEntityTypes.COLD_PARACHUTE.get(), world);
    }
}
