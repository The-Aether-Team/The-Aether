package com.aether.entity.projectile;

import com.aether.registry.AetherEntityTypes;
import com.aether.registry.AetherItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EnchantedDartEntity extends AbstractDartEntity {
    public EnchantedDartEntity(EntityType<? extends EnchantedDartEntity> type, World worldIn) {
        super(type, worldIn);
        this.setDamage(6.0D);
    }

    public EnchantedDartEntity construct(World worldIn, double x, double y, double z)
    {
        this.setDamage(6.0D);
        this.setPosition(x, y, z);
        return new EnchantedDartEntity(AetherEntityTypes.ENCHANTED_DART.get(), worldIn);
    }

    public EnchantedDartEntity construct(World worldIn, LivingEntity shooter)
    {
        this.setDamage(6.0D);
        this.setShooter(shooter);
        return new EnchantedDartEntity(AetherEntityTypes.ENCHANTED_DART.get(), worldIn);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(AetherItems.ENCHANTED_DART.get());
    }
}
