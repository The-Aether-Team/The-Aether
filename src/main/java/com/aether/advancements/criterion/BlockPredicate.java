package com.aether.advancements.criterion;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.conditions.BlockStateProperty;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockPredicate {
	public static final BlockPredicate ANY = new BlockPredicate();
	@Nullable
	private final Tag<Block> tag;
	@Nullable
	private final Block block;
	private final Map<String, String> stringProperties;
	private final Map<IProperty<?>, Object> objectProperties;
	private final Predicate<BlockState> predicate;
	private final NBTPredicate nbt;

	private BlockPredicate() {
		this.tag = null;
		this.block = null;
		this.stringProperties = null;
		this.objectProperties = null;
		this.predicate = (_unused) -> true;
		this.nbt = NBTPredicate.ANY;
	}

	public BlockPredicate(@Nullable Tag<Block> tag, @Nullable Block block, Map<String, String> stringProperties, Map<IProperty<?>, Object> objectProperties, NBTPredicate nbt) {
		this.tag = tag;
		this.block = block;
		this.stringProperties = (stringProperties == null)? Collections.emptyMap() : stringProperties;
		this.objectProperties = (objectProperties == null)? Collections.emptyMap() : objectProperties;
		if (block != null) {
			this.predicate = BlockStateProperty.buildPredicate(block, objectProperties);
		}
		else if (tag != null) {
			this.predicate = BlockPredicate.buildPredicate(tag, stringProperties);
		}
		else {
			this.predicate = BlockPredicate.buildPredicate(stringProperties);
		}
		this.nbt = nbt;
	}

	private static Predicate<BlockState> buildPredicate(Tag<Block> tag, Map<String, String> map) {
		if (map.isEmpty()) {
			return (state) -> state.getBlock().isIn(tag);
		}

		Map<String, IProperty<?>> properties = Maps.newHashMap();
		boolean first = true;
		loop:
		for (Block block : tag.getAllElements()) {
			if (first) {
				first = false;
				for (IProperty<?> property : block.getStateContainer().getProperties()) {
					properties.put(property.getName(), property);
				}
				if (properties.isEmpty()) {
					break loop;
				}
			}
			else {
				for (IProperty<?> property : block.getStateContainer().getProperties()) {
					IProperty<?> existing = properties.get(property.getName());
					if (existing != null && existing != property) {
						properties.remove(property.getName());
						if (properties.isEmpty()) {
							break loop;
						}
					}
				}
			}
		}

		Map<IProperty<?>, Object> actualValues = Maps.newHashMap();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			IProperty<?> iproperty = properties.get(entry.getKey());
			if (iproperty == null) {
				throw new JsonSyntaxException(
					"Block tag #" + tag.getId() + " does not have property '" + entry.getKey() + "'");
			}
			else {
				Object value = iproperty.parseValue(entry.getValue()).orElseThrow(() -> {
					return new JsonSyntaxException("Block tag #" + tag.getId() + " property '" + entry.getKey()
						+ "' does not have value '" + entry.getValue() + "'");
				});
				actualValues.put(iproperty, value);
			}
		}

		if (actualValues.size() == 1) {
			Entry<IProperty<?>, Object> entry1 = actualValues.entrySet().iterator().next();
			IProperty<?> iproperty1 = entry1.getKey();
			Object object1 = entry1.getValue();
			return (state) -> {
				return state.getBlock().isIn(tag) && object1.equals(state.get(iproperty1));
			};
		}
		else {
			Predicate<BlockState> predicate = (state) -> {
				return state.getBlock().isIn(tag);
			};

			for (Entry<IProperty<?>, Object> entry : actualValues.entrySet()) {
				IProperty<?> iproperty = entry.getKey();
				Object object = entry.getValue();
				predicate = predicate.and((state) -> {
					return object.equals(state.get(iproperty));
				});
			}

			return predicate;
		}
	}

	private static Predicate<BlockState> buildPredicate(Map<String, String> map) {
		return (state) -> {
			for (IProperty<?> property : state.getProperties()) {
				String stateValue = map.get(property.getName());
				if (stateValue != null) {
					Object value;
					try {
						value = property.parseValue(stateValue);
					} catch (IllegalArgumentException | ClassCastException e) {
						return false;
					}
					if (!Objects.equal(value, state.get(property))) {
						return false;
					}
				}
			}
			return true;
		};
	}
	
	public boolean test(BlockState state, World world, BlockPos pos) {
		if (this == ANY) {
			return true;
		}
		if (block != null && block != state.getBlock()) {
			return false;
		}
		if (tag != null && !state.getBlock().isIn(tag)) {
			return false;
		}
		if (nbt != NBTPredicate.ANY) {
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity == null || !nbt.test(tileentity.getTileData())) {
				return false;
			}
		}
		if (!predicate.test(state)) {
			return false;
		}
		return true;
	}

	public JsonElement serialize() {
		if (this == ANY) {
			return JsonNull.INSTANCE;
		}
		else {
			JsonObject jsonobject = new JsonObject();
			if (block != null) {
				jsonobject.addProperty("block", block.getRegistryName().toString());
			}

			if (tag != null) {
				jsonobject.addProperty("tag", tag.getId().toString());
			}
			
			if (!objectProperties.isEmpty()) {
				objectProperties.entrySet().forEach((entry) -> {
					Object value = entry.getValue();
					if (value instanceof IStringSerializable) {
						value = ((IStringSerializable) value).getName();
					}
					if (value == null || value instanceof Number) {
						jsonobject.addProperty(entry.getKey().getName(), (Number) value);
					}
					else if (value instanceof Boolean) {
						jsonobject.addProperty(entry.getKey().getName(), (Boolean) value);
					}
					else if (value instanceof String) {
						jsonobject.addProperty(entry.getKey().getName(), (String) value);
					}
					else {
						jsonobject.addProperty(entry.getKey().getName(), String.valueOf(value));
					}
				});
			}
			else if (!stringProperties.isEmpty()) {
				stringProperties.entrySet().forEach((entry) -> {
					jsonobject.addProperty(entry.getKey(), entry.getValue());
				});
			}

			jsonobject.add("nbt", nbt.serialize());

			return jsonobject;
		}
	}

	public static BlockPredicate deserialize(@Nullable JsonElement element) {
		if (element != null && !element.isJsonNull()) {
			JsonObject jsonobject = JSONUtils.getJsonObject(element, "block");
			NBTPredicate nbt = NBTPredicate.deserialize(jsonobject.get("nbt"));
			final Block block = jsonobject.has("block")? getBlock(JSONUtils.getString(jsonobject, "block")) : null;

			Tag<Block> tag = null;
			if (jsonobject.has("tag")) {
				ResourceLocation resourcelocation1 = new ResourceLocation(JSONUtils.getString(jsonobject, "tag"));
				tag = BlockTags.getCollection().get(resourcelocation1);
				if (tag == null) {
					throw new JsonSyntaxException("Unknown block tag '" + resourcelocation1 + "'");
				}
			}

//			Map<Object, Object> map = Maps.newHashMap();
			Map<IProperty<?>, Object> objectProperties;
			Map<String, String> stringProperties;
			if (jsonobject.has("state")) {
				JsonObject jsonobject1 = JSONUtils.getJsonObject(jsonobject, "state");
				if (block != null) {
					StateContainer<Block, BlockState> statecontainer = block.getStateContainer();
					objectProperties = Maps.newHashMap();
					stringProperties = null;
					jsonobject1.entrySet().forEach((entry) -> {
						String key = entry.getKey();
						IProperty<?> iproperty = statecontainer.getProperty(key);
						if (iproperty == null) {
							throw new JsonSyntaxException(
								"Block " + block.getRegistryName() + " does not have property '" + key + "'");
						}
						else {
							String value = JSONUtils.getString(entry.getValue(), "value");
							Object object = iproperty.parseValue(value).orElseThrow(() -> {
								return new JsonSyntaxException("Block " + block.getRegistryName() + " property '" + key
									+ "' does not have value '" + value + "'");
							});
							objectProperties.put(iproperty, object);
						}
					});
				}
				else {
					stringProperties = Maps.newHashMap();
					objectProperties = null;
					jsonobject1.entrySet().forEach((entry) -> {
						String key = entry.getKey();
						String value = JSONUtils.getString(entry.getValue(), "value");
						stringProperties.put(key, value);
					});
				}
			} else {
				stringProperties = null;
				objectProperties = null;
			}

			return new BlockPredicate(tag, block, stringProperties, objectProperties, nbt);
		}
		else {
			return ANY;
		}
	}

	private static Block getBlock(String name) {
		ResourceLocation resourcelocation = new ResourceLocation(name);
		Block block = ForgeRegistries.BLOCKS.getValue(resourcelocation);
		if (block == null) {
			throw new JsonSyntaxException("Unknown block id '" + resourcelocation + "'");
		}
		return block;
	}

}
