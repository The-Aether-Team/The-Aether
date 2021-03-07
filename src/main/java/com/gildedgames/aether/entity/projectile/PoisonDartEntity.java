package com.gildedgames.aether.entity.projectile;

import com.gildedgames.aether.registry.AetherEntityTypes;
import com.gildedgames.aether.registry.AetherItems;
import com.gildedgames.aether.registry.AetherPotionEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class PoisonDartEntity extends AbstractDartEntity
{
    public PoisonDartEntity(EntityType<? extends PoisonDartEntity> type, World worldIn) {
        super(type, worldIn);
        this.setDamage(0.0D);
    }

    public PoisonDartEntity(World worldIn, double x, double y, double z) {
        super(AetherEntityTypes.POISON_DART.get(), x, y, z, worldIn);
        this.setDamage(0.0D);
    }

    public PoisonDartEntity(World worldIn, LivingEntity shooter) {
        super(AetherEntityTypes.POISON_DART.get(), shooter, worldIn);
        this.setDamage(0.0D);
    }

    @Override
    protected void arrowHit(LivingEntity living) {
        super.arrowHit(living);

        if (!world.isRemote) {
            living.addPotionEffect(new EffectInstance(AetherPotionEffects.INEBRIATION.get(), 500, 0, false, false));
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(AetherItems.POISON_DART.get());
    }
}
