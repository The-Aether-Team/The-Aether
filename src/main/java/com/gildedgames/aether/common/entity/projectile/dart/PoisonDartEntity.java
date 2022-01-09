package com.gildedgames.aether.common.entity.projectile.dart;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;

public class PoisonDartEntity extends AbstractDartEntity
{
    public PoisonDartEntity(EntityType<? extends PoisonDartEntity> type, Level worldIn) {
        super(type, worldIn);
        this.setBaseDamage(0.5D);
    }

    public PoisonDartEntity(Level worldIn) {
        super(AetherEntityTypes.POISON_DART.get(), worldIn);
        this.setBaseDamage(0.5D);
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
        return new ItemStack(AetherItems.POISON_DART.get());
    }
}
