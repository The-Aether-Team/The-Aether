package com.aether.tags;

import com.aether.Aether;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class AetherItemTags {

	public static final ITag.INamedTag<Item> BANNED_IN_AETHER = tag("banned_in_aether");
	public static final ITag.INamedTag<Item> GOLDEN_AMBER_HARVESTERS = tag("golden_amber_harvesters");
	public static final ITag.INamedTag<Item> GRAVITITE_TOOLS = tag("gravitite_tools");
	public static final ITag.INamedTag<Item> SKYROOT_TOOLS = tag("skyroot_tools");
	public static final ITag.INamedTag<Item> VALKYRIE_TOOLS = tag("valkyrie_tools");
	public static final ITag.INamedTag<Item> ZANITE_TOOLS = tag("zanite_tools");
	public static final ITag.INamedTag<Item> NO_SKYROOT_DOUBLE_DROPS = tag("no_skyroot_double_drops");
	public static final ITag.INamedTag<Item> DUNGEON_KEYS = tag("dungeon_keys");

	private static ITag.INamedTag<Item> tag(String name) {
		return ItemTags.makeWrapperTag(new ResourceLocation(Aether.MODID, name).toString());
	}
	
}
