package com.gildedgames.aether.common.entity.projectile;

import com.gildedgames.aether.common.entity.projectile.dart.AbstractDartEntity;
import com.gildedgames.aether.common.registry.AetherEffects;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;

public class PoisonNeedleEntity extends AbstractDartEntity
{
    public PoisonNeedleEntity(EntityType<? extends PoisonNeedleEntity> type, Level worldIn) {
        super(type, worldIn);
        this.setBaseDamage(1.0D);
    }

    public PoisonNeedleEntity(Level worldIn, LivingEntity shooter) {
        super(AetherEntityTypes.POISON_NEEDLE.get(), shooter, worldIn);
        this.setBaseDamage(1.0D);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        if (!this.level.isClientSide) {
            living.addEffect(new MobEffectInstance(AetherEffects.INEBRIATION.get(), 500, 0, false, false));
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }
}
