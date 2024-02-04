package com.aetherteam.aether.world.treedecorator;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AetherTreeDecoratorTypes {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(BuiltInRegistries.TREE_DECORATOR_TYPE, Aether.MODID);

    public static final Supplier<TreeDecoratorType<HolidayTreeDecorator>> HOLIDAY_TREE_DECORATOR = TREE_DECORATORS.register("holiday_tree_decorator", () -> new TreeDecoratorType<>(HolidayTreeDecorator.CODEC));
}
