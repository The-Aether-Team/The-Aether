package com.gildedgames.aether.common.registry;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class AetherFoods
{
    public static final Food BLUE_BERRY = (new Food.Builder()).fast().nutrition(2).saturationMod(0.1F).build();
    public static final Food ENCHANTED_BERRY = (new Food.Builder()).fast().nutrition(8).saturationMod(0.3F).build();
    public static final Food WHITE_APPLE = (new Food.Builder()).alwaysEat().fast().nutrition(0).build();
    public static final Food GUMMY_SWET = (new Food.Builder()).fast().nutrition(20).saturationMod(1.2F).build();
    public static final Food HEALING_STONE = (new Food.Builder()).alwaysEat().nutrition(0).effect(() -> new EffectInstance(Effects.REGENERATION, 610, 0), 1.0F).build();
    public static final Food CANDY_CANE = (new Food.Builder()).fast().nutrition(2).saturationMod(0.1F).build();
    public static final Food GINGERBREAD_MAN = (new Food.Builder()).fast().nutrition(2).saturationMod(0.1F).build();
}
