package com.aetherteam.aether.world.processor;

import com.aetherteam.aether.Aether;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTestType;

public class AetherPosRuleTests {
    public static final LazyRegistrar<PosRuleTestType<?>> POS_RULE_TESTS = LazyRegistrar.create(BuiltInRegistries.POS_RULE_TEST.key(), Aether.MODID);

    public static final RegistryObject<PosRuleTestType<BorderBoxPosTest>> BORDER_BOX = POS_RULE_TESTS.register("border_box", () -> () -> BorderBoxPosTest.CODEC);
}
