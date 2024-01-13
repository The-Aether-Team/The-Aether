package com.aetherteam.aether.world.treedecorator;

import com.aetherteam.aether.Aether;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class AetherTreeDecoratorTypes {
    public static final LazyRegistrar<TreeDecoratorType<?>> TREE_DECORATORS = LazyRegistrar.create(Registries.TREE_DECORATOR_TYPE, Aether.MODID);

    public static final RegistryObject<TreeDecoratorType<HolidayTreeDecorator>> HOLIDAY_TREE_DECORATOR = TREE_DECORATORS.register("holiday_tree_decorator", () -> new TreeDecoratorType<>(HolidayTreeDecorator.CODEC));
}
