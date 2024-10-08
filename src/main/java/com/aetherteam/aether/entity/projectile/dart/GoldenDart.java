package com.aetherteam.aether.entity.projectile.dart;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GoldenDart extends AbstractDart {
    public GoldenDart(EntityType<? extends GoldenDart> type, Level level) {
        super(type, level, AetherItems.GOLDEN_DART);
        this.setBaseDamage(0.5);
    }

    public GoldenDart(Level level) {
        super(AetherEntityTypes.GOLDEN_DART.get(), level, AetherItems.GOLDEN_DART);
        this.setBaseDamage(0.5);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(AetherItems.GOLDEN_DART.get());
    }
}
