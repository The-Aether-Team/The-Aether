package com.aetherteam.aether.entity.projectile.dart;

import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class PoisonDart extends AbstractDart {
    public PoisonDart(EntityType<? extends PoisonDart> type, Level level) {
        super(type, level);
        this.setBaseDamage(0.25);
    }

    public PoisonDart(Level level, LivingEntity shooter, @Nullable ItemStack firedFromWeapon) {
        super(AetherEntityTypes.POISON_DART.get(), level, shooter, AetherItems.POISON_DART, firedFromWeapon);
        this.setBaseDamage(0.25);
    }

    /**
     * Applies the Inebriation effect to an entity after being hurt.
     *
     * @param living The {@link LivingEntity} to affect.
     */
    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        living.addEffect(new MobEffectInstance(AetherEffects.INEBRIATION, 200, 0));
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(AetherItems.POISON_DART.get());
    }
}
