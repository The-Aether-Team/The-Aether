package com.aether.entity.projectile;

import com.aether.registry.AetherEntityTypes;
import com.aether.registry.AetherItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GoldenDartEntity extends AbstractDartEntity
{
    public GoldenDartEntity(EntityType<? extends GoldenDartEntity> type, World worldIn) {
        super(type, worldIn);
        this.setDamage(4.0D);
    }

    public GoldenDartEntity construct(World worldIn, double x, double y, double z)
    {
        this.setDamage(4.0D);
        this.setPosition(x, y, z);
        return new GoldenDartEntity(AetherEntityTypes.GOLDEN_DART.get(), worldIn);
    }

    public GoldenDartEntity construct(World worldIn, LivingEntity shooter)
    {
        this.setDamage(4.0D);
        this.setShooter(shooter);
        return new GoldenDartEntity(AetherEntityTypes.GOLDEN_DART.get(), worldIn);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(AetherItems.GOLDEN_DART.get());
    }
}
