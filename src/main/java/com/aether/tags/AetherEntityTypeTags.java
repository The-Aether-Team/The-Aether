package com.aether.tags;

import com.aether.Aether;

import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class AetherEntityTypeTags {
	
	public static final Tag<EntityType<?>> PIGS = tag("pigs"); 
	public static final Tag<EntityType<?>> NO_SKYROOT_DOUBLE_DROPS = tag("no_skyroot_double_drops");
	
	private static Tag<EntityType<?>> tag(String name) {
		return EntityTypeTags.getCollection().getOrCreate(new ResourceLocation(Aether.MODID, name));
	}
	
}
