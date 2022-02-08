package com.gildedgames.aether.common.entity.projectile;

import com.gildedgames.aether.common.entity.projectile.dart.AbstractDart;
import com.gildedgames.aether.common.registry.AetherEffects;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class PoisonNeedle extends AbstractDart
{
    public PoisonNeedle(EntityType<? extends PoisonNeedle> type, Level worldIn) {
        super(type, worldIn);
        this.setBaseDamage(1.0D);
    }

    public PoisonNeedle(Level worldIn, LivingEntity shooter) {
        super(AetherEntityTypes.POISON_NEEDLE.get(), shooter, worldIn);
        this.setBaseDamage(1.0D);
    }

    @Override
    protected void doPostHurtEffects(@Nonnull LivingEntity living) {
        super.doPostHurtEffects(living);
        if (!this.level.isClientSide) {
            living.addEffect(new MobEffectInstance(AetherEffects.INEBRIATION.get(), 500, 0, false, false));
        }
    }

    @Nonnull
    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }
}
