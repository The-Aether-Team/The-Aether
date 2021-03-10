package com.gildedgames.aether.common.entity.projectile;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GoldenDartEntity extends AbstractDartEntity
{
    public GoldenDartEntity(EntityType<? extends GoldenDartEntity> type, World worldIn) {
        super(type, worldIn);
        this.setBaseDamage(4.0D);
    }

    public GoldenDartEntity(World worldIn, double x, double y, double z) {
        super(AetherEntityTypes.GOLDEN_DART.get(), x, y, z, worldIn);
        this.setBaseDamage(4.0D);
    }

    public GoldenDartEntity(World worldIn, LivingEntity shooter) {
        super(AetherEntityTypes.GOLDEN_DART.get(), shooter, worldIn);
        this.setBaseDamage(4.0D);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(AetherItems.GOLDEN_DART.get());
    }
}
