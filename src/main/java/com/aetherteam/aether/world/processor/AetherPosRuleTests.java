package com.aetherteam.aether.world.processor;

import com.aetherteam.aether.Aether;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTestType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AetherPosRuleTests {
    public static final DeferredRegister<PosRuleTestType<?>> POS_RULE_TESTS = DeferredRegister.create(Registry.POS_RULE_TEST_REGISTRY, Aether.MODID);

    public static final RegistryObject<PosRuleTestType<BorderBoxPosTest>> BORDER_BOX = POS_RULE_TESTS.register("border_box", () -> () -> BorderBoxPosTest.CODEC);
}
