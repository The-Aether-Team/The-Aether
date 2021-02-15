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

    public EnchantedDartEntity(World worldIn, double x, double y, double z) {
        super(AetherEntityTypes.ENCHANTED_DART, x, y, z, worldIn);
        this.setDamage(6.0D);
    }

    public EnchantedDartEntity(World worldIn, LivingEntity shooter) {
        super(AetherEntityTypes.ENCHANTED_DART, shooter, worldIn);
        this.setDamage(6.0D);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(AetherItems.ENCHANTED_DART.get());
    }
}
