package com.gildedgames.aether.common.registry.worldgen;

import com.gildedgames.aether.Aether;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.OptionalLong;

public class AetherDimensions {
	// NOT for API - If you are an addon author using this field, expect this field to change without warning!
	public final static ResourceLocation AETHER_LEVEL_ID = new ResourceLocation(Aether.MODID, "the_aether");

	public static final DeferredRegister<DimensionType> DIMENSION_TYPES = DeferredRegister.create(Registry.DIMENSION_TYPE_REGISTRY, Aether.MODID);

	public static RegistryObject<DimensionType> AETHER_DIMENSION_TYPE = DIMENSION_TYPES.register("the_aether", () -> new DimensionType(OptionalLong.empty(), true, false, false, true, 1.0D, true, false, 0, 256, 256, BlockTags.INFINIBURN_OVERWORLD, new ResourceLocation(Aether.MODID, "the_aether"), 0.1F, new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0)));

//	// Dimension Type - Specifies logic that impacts an entire dimension
//	public static final ResourceKey<DimensionType> AETHER_DIMENSION_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, AetherDimensions.AETHER_LEVEL_ID); //todo reimplement?
	// Level Stem - Begins the dimension's lifecycle
	public static final ResourceKey<LevelStem> AETHER_LEVEL_STEM = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, AetherDimensions.AETHER_LEVEL_ID);
	// Level - Actual runtime dimension
	public static final ResourceKey<Level> AETHER_LEVEL = ResourceKey.create(Registry.DIMENSION_REGISTRY, AetherDimensions.AETHER_LEVEL_ID);
}