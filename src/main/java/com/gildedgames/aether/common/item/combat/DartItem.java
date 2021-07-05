package com.gildedgames.aether.common.item.combat;

import com.gildedgames.aether.common.entity.projectile.combat.AbstractDartEntity;
import com.gildedgames.aether.common.entity.projectile.combat.EnchantedDartEntity;
import com.gildedgames.aether.common.entity.projectile.combat.GoldenDartEntity;
import com.gildedgames.aether.common.entity.projectile.combat.PoisonDartEntity;
import com.gildedgames.aether.common.registry.AetherItems;
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
            return new EnchantedDartEntity(world, shooter);
        }
        else if(item == AetherItems.POISON_DART.get()) {
            return new PoisonDartEntity(world, shooter);
        }
        else {
            return new GoldenDartEntity(world, shooter);
        }
    }
}
