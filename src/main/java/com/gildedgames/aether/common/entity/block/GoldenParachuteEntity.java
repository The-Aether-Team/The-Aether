package com.gildedgames.aether.common.entity.block;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class GoldenParachuteEntity extends ParachuteEntity
{
    public GoldenParachuteEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    public GoldenParachuteEntity(World world) {
        this(AetherEntityTypes.GOLDEN_PARACHUTE.get(), world);
    }
}
