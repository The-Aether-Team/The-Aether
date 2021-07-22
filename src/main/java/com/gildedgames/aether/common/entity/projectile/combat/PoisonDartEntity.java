package com.gildedgames.aether.common.entity.projectile.combat;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class PoisonDartEntity extends AbstractDartEntity
{
    public PoisonDartEntity(EntityType<? extends PoisonDartEntity> type, World worldIn) {
        super(type, worldIn);
        this.setBaseDamage(0.5D);
    }

    public PoisonDartEntity(World worldIn) {
        super(AetherEntityTypes.POISON_DART.get(), worldIn);
        this.setBaseDamage(0.5D);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        if (!this.level.isClientSide) {
            living.addEffect(new EffectInstance(AetherEffects.INEBRIATION.get(), 500, 0, false, false));
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(AetherItems.POISON_DART.get());
    }
}
