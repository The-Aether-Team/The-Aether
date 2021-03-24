package com.gildedgames.aether.common.entity.projectile;

import com.gildedgames.aether.common.registry.AetherEffects;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class PoisonNeedleEntity extends AbstractDartEntity {
    public PoisonNeedleEntity(EntityType<? extends PoisonNeedleEntity> type, World worldIn) {
        super(type, worldIn);
        this.setBaseDamage(1.0D);
        this.setNoGravity(false);
    }

    public PoisonNeedleEntity(World worldIn, double x, double y, double z) {
        super(AetherEntityTypes.POISON_NEEDLE.get(), x, y, z, worldIn);
        this.setBaseDamage(1.0D);
        this.setNoGravity(false);
    }

    public PoisonNeedleEntity(World worldIn, LivingEntity shooter) {
        super(AetherEntityTypes.POISON_NEEDLE.get(), shooter, worldIn);
        this.setBaseDamage(1.0D);
        this.setNoGravity(false);
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
        return ItemStack.EMPTY;
    }
}
