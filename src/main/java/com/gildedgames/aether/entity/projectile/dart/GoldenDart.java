package com.gildedgames.aether.entity.projectile.dart;

import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.item.AetherItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class GoldenDart extends AbstractDart {
    public GoldenDart(EntityType<? extends GoldenDart> type, Level level) {
        super(type, level);
        this.setBaseDamage(4.0);
    }

    public GoldenDart(Level level) {
        super(AetherEntityTypes.GOLDEN_DART.get(), level);
        this.setBaseDamage(4.0);
    }

    @Nonnull
    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(AetherItems.GOLDEN_DART.get());
    }
}
