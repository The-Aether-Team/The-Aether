package com.gildedgames.aether.common.item.combat;

import com.gildedgames.aether.common.entity.projectile.dart.AbstractDartEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

import net.minecraft.world.item.Item.Properties;

public class DartItem extends Item
{
    protected final Supplier<EntityType<?>> dartEntity;

    public DartItem(Supplier<EntityType<?>> dartEntity, Properties properties) {
        super(properties);
        this.dartEntity = dartEntity;
    }

    public AbstractDartEntity createDart(Level world, LivingEntity shooter) {
        Entity entity = this.dartEntity.get().create(world);
        if (entity instanceof AbstractDartEntity) {
            AbstractDartEntity dartEntity = (AbstractDartEntity) entity;
            dartEntity.setPos(shooter.getX(), shooter.getEyeY() - 0.1D, shooter.getZ());
            dartEntity.setOwner(shooter);
            return dartEntity;
        } else {
            return null;
        }
    }

    public AbstractDartEntity createDart(Level world) {
        Entity entity = this.dartEntity.get().create(world);
        if (entity instanceof AbstractDartEntity) {
            return (AbstractDartEntity) entity;
        } else {
            return null;
        }
    }

    public boolean isInfinite(ItemStack dartShooter) {
        int enchant = net.minecraft.world.item.enchantment.EnchantmentHelper.getItemEnchantmentLevel(net.minecraft.world.item.enchantment.Enchantments.INFINITY_ARROWS, dartShooter);
        return enchant > 0 && this.getClass() == DartItem.class;
    }
}
