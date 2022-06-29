package com.gildedgames.aether.world.treedecorator;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.world.treedecorator.HolidayTreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherTreeDecoratorTypes {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, Aether.MODID);

    public static final RegistryObject<TreeDecoratorType<HolidayTreeDecorator>> HOLIDAY_TREE_DECORATOR = TREE_DECORATORS.register("holiday_tree_decorator", () -> new TreeDecoratorType<>(HolidayTreeDecorator.CODEC));
}
