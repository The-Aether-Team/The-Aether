package com.aether.entity.projectile;

import com.aether.entity.AetherEntityTypes;
import com.aether.item.AetherItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GoldenDartEntity extends AbstractDartEntity {
    public GoldenDartEntity(EntityType<? extends GoldenDartEntity> type, World worldIn) {
        super(type, worldIn);
        this.setDamage(4.0D);
    }

    public GoldenDartEntity(World worldIn, double x, double y, double z) {
        super(AetherEntityTypes.GOLDEN_DART, x, y, z, worldIn);
        this.setDamage(4.0D);
    }

    public GoldenDartEntity(World worldIn, LivingEntity shooter) {
        super(AetherEntityTypes.GOLDEN_DART, shooter, worldIn);
        this.setDamage(4.0D);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(AetherItems.GOLDEN_DART);
    }
}
