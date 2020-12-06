package com.aether.tags;

import com.aether.Aether;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class AetherEntityTypeTags {
	
	public static final ITag.INamedTag<EntityType<?>> PIGS = tag("pigs");
	public static final ITag.INamedTag<EntityType<?>> NO_SKYROOT_DOUBLE_DROPS = tag("no_skyroot_double_drops");

	private static ITag.INamedTag<EntityType<?>> tag(String name) {
		return EntityTypeTags.getTagById(new ResourceLocation(Aether.MODID, name).toString());
	}
	
}
