package com.gildedgames.aether.common.item.combat;

import com.gildedgames.aether.common.entity.projectile.combat.AbstractDartEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class DartItem extends Item
{
    protected final Supplier<EntityType<?>> dartEntity;

    public DartItem(Supplier<EntityType<?>> dartEntity, Properties properties) {
        super(properties);
        this.dartEntity = dartEntity;
    }

    public AbstractDartEntity createDart(World world, LivingEntity shooter) {
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

    public AbstractDartEntity createDart(World world) {
        Entity entity = this.dartEntity.get().create(world);
        if (entity instanceof AbstractDartEntity) {
            return (AbstractDartEntity) entity;
        } else {
            return null;
        }
    }

    public boolean isInfinite(ItemStack dartShooter) {
        int enchant = net.minecraft.enchantment.EnchantmentHelper.getItemEnchantmentLevel(net.minecraft.enchantment.Enchantments.INFINITY_ARROWS, dartShooter);
        return enchant > 0 && this.getClass() == DartItem.class;
    }
}
