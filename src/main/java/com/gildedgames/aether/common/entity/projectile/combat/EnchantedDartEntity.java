package com.gildedgames.aether.common.entity.projectile.combat;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EnchantedDartEntity extends AbstractDartEntity
{
    public EnchantedDartEntity(EntityType<? extends EnchantedDartEntity> type, World worldIn) {
        super(type, worldIn);
        this.setBaseDamage(6.0D);
    }

    public EnchantedDartEntity(World worldIn, double x, double y, double z) {
        super(AetherEntityTypes.ENCHANTED_DART.get(), x, y, z, worldIn);
        this.setBaseDamage(6.0D);
    }

    public EnchantedDartEntity(World worldIn, LivingEntity shooter) {
        super(AetherEntityTypes.ENCHANTED_DART.get(), shooter, worldIn);
        this.setBaseDamage(6.0D);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(AetherItems.ENCHANTED_DART.get());
    }
}
