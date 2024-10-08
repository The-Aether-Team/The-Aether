package com.aetherteam.aether.entity.projectile.dart;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class GoldenDart extends AbstractDart {
    public GoldenDart(EntityType<? extends GoldenDart> type, Level level) {
        super(type, level);
        this.setBaseDamage(0.5);
    }

    public GoldenDart(Level level, LivingEntity shooter, @Nullable ItemStack firedFromWeapon) {
        super(AetherEntityTypes.GOLDEN_DART.get(), level, shooter, AetherItems.GOLDEN_DART, firedFromWeapon);
        this.setBaseDamage(0.5);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(AetherItems.GOLDEN_DART.get());
    }
}
