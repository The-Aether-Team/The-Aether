package com.aetherteam.aether.item.food;

import net.minecraft.world.food.FoodProperties;

public class AetherFoods {
    public static final FoodProperties BLUE_BERRY = new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).build();
    public static final FoodProperties ENCHANTED_BERRY = new FoodProperties.Builder().nutrition(6).saturationModifier(0.8F).build();
    public static final FoodProperties WHITE_APPLE = new FoodProperties.Builder().alwaysEdible().nutrition(0).build();
    public static final FoodProperties GUMMY_SWET = new FoodProperties.Builder().nutrition(20).saturationModifier(0.9F).build();
    public static final FoodProperties HEALING_STONE = new FoodProperties.Builder().alwaysEdible().nutrition(0).build();
    public static final FoodProperties CANDY_CANE = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2F).build();
    public static final FoodProperties GINGERBREAD_MAN = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2F).build();
}
