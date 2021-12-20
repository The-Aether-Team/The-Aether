package com.gildedgames.aether.common.entity.projectile.dart;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GoldenDartEntity extends AbstractDartEntity
{
    public GoldenDartEntity(EntityType<? extends GoldenDartEntity> type, Level worldIn) {
        super(type, worldIn);
        this.setBaseDamage(4.0D);
    }

    public GoldenDartEntity(Level worldIn) {
        super(AetherEntityTypes.GOLDEN_DART.get(), worldIn);
        this.setBaseDamage(4.0D);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(AetherItems.GOLDEN_DART.get());
    }
}
