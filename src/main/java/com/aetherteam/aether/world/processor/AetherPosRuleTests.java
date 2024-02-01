package com.aetherteam.aether.world.processor;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTestType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryObject;

public class AetherPosRuleTests {
    public static final DeferredRegister<PosRuleTestType<?>> POS_RULE_TESTS = DeferredRegister.create(BuiltInRegistries.POS_RULE_TEST.key(), Aether.MODID);

    public static final RegistryObject<PosRuleTestType<BorderBoxPosTest>> BORDER_BOX = POS_RULE_TESTS.register("border_box", () -> () -> BorderBoxPosTest.CODEC);
}
