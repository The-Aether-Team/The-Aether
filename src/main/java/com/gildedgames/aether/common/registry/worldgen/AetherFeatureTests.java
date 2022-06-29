package com.gildedgames.aether.common.registry.worldgen;

import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class AetherFeatureTests {
    public static final RuleTest HOLYSTONE = new TagMatchTest(AetherTags.Blocks.HOLYSTONE);
}
