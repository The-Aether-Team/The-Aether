package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.projectile.dart.AbstractDart;
import com.aetherteam.aether.entity.projectile.dart.EnchantedDart;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class EnchantedDartItem extends DartItem {
    public EnchantedDartItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractDart createDart(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack firedFromWeapon) {
        return new EnchantedDart(level, shooter, ammo, firedFromWeapon);
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        EnchantedDart dart = new EnchantedDart(AetherEntityTypes.ENCHANTED_DART.get(), level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1), null);
        dart.pickup = AbstractArrow.Pickup.ALLOWED;
        return dart;
    }
}
