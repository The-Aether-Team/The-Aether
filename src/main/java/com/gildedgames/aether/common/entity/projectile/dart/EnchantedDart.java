package com.gildedgames.aether.common.entity.projectile.dart;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class EnchantedDart extends AbstractDart
{
    public EnchantedDart(EntityType<? extends EnchantedDart> type, Level worldIn) {
        super(type, worldIn);
        this.setBaseDamage(6.0D);
    }

    public EnchantedDart(Level worldIn) {
        super(AetherEntityTypes.ENCHANTED_DART.get(), worldIn);
        this.setBaseDamage(6.0D);
    }

    @Nonnull
    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(AetherItems.ENCHANTED_DART.get());
    }
}
