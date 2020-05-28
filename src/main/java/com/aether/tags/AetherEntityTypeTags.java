package com.aether.tags;

import com.aether.Aether;

import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class AetherEntityTypeTags {
	
	public static final Tag<EntityType<?>> PIGS = tag("pigs"); 
	
	private static Tag<EntityType<?>> tag(String name) {
		return EntityTypeTags.getCollection().getOrCreate(new ResourceLocation(Aether.MODID, name));
	}
	
}
