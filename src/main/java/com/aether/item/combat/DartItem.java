package com.aether.item.combat;

import com.aether.entity.projectile.AbstractDartEntity;
import com.aether.entity.projectile.EnchantedDartEntity;
import com.aether.entity.projectile.GoldenDartEntity;
import com.aether.entity.projectile.PoisonDartEntity;
import com.aether.registry.AetherEntityTypes;
import com.aether.registry.AetherItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DartItem extends Item
{
    public DartItem(Properties properties) {
        super(properties);
    }

    public AbstractDartEntity createDart(World world, ItemStack stack, LivingEntity shooter) {
        Item item = stack.getItem();
        if(item == AetherItems.ENCHANTED_DART.get()) {
            return new EnchantedDartEntity(AetherEntityTypes.ENCHANTED_DART.get(), world).construct(world, shooter);
        }
        else if(item == AetherItems.POISON_DART.get()) {
            return new PoisonDartEntity(AetherEntityTypes.POISON_DART.get(), world).construct(world, shooter);
        }
        else {
            return new GoldenDartEntity(AetherEntityTypes.GOLDEN_DART.get(), world).construct(world, shooter);
        }
    }
}
