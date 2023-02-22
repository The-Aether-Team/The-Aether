package com.gildedgames.aether.item.food;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class AetherFoods {
    public static final FoodProperties BLUE_BERRY = new FoodProperties.Builder().fast().nutrition(2).saturationMod(0.3F).build();
    public static final FoodProperties ENCHANTED_BERRY = new FoodProperties.Builder().fast().nutrition(6).saturationMod(0.8F).build();
    public static final FoodProperties WHITE_APPLE = new FoodProperties.Builder().alwaysEat().fast().nutrition(0).build();
    public static final FoodProperties GUMMY_SWET = new FoodProperties.Builder().fast().nutrition(20).saturationMod(0.9F).build();
    public static final FoodProperties HEALING_STONE = new FoodProperties.Builder().alwaysEat().nutrition(0).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 610, 0), 1.0F).build();
    public static final FoodProperties CANDY_CANE = new FoodProperties.Builder().fast().nutrition(2).saturationMod(0.2F).build();
    public static final FoodProperties GINGERBREAD_MAN = new FoodProperties.Builder().fast().nutrition(2).saturationMod(0.2F).build();
}
