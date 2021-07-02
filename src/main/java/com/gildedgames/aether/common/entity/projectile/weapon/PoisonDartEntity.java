package com.gildedgames.aether.common.entity.projectile.weapon;

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
        this.setBaseDamage(0.0D);
    }

    public PoisonDartEntity(World worldIn, double x, double y, double z) {
        super(AetherEntityTypes.POISON_DART.get(), x, y, z, worldIn);
        this.setBaseDamage(0.0D);
    }

    public PoisonDartEntity(World worldIn, LivingEntity shooter) {
        super(AetherEntityTypes.POISON_DART.get(), shooter, worldIn);
        this.setBaseDamage(0.0D);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);

        if (!level.isClientSide) {
            living.addEffect(new EffectInstance(AetherEffects.INEBRIATION.get(), 500, 0, false, false));
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(AetherItems.POISON_DART.get());
    }
}
