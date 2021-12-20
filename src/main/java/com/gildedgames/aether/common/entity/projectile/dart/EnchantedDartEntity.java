package com.gildedgames.aether.common.entity.projectile.dart;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EnchantedDartEntity extends AbstractDartEntity
{
    public EnchantedDartEntity(EntityType<? extends EnchantedDartEntity> type, Level worldIn) {
        super(type, worldIn);
        this.setBaseDamage(6.0D);
    }

    public EnchantedDartEntity(Level worldIn) {
        super(AetherEntityTypes.ENCHANTED_DART.get(), worldIn);
        this.setBaseDamage(6.0D);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(AetherItems.ENCHANTED_DART.get());
    }
}
