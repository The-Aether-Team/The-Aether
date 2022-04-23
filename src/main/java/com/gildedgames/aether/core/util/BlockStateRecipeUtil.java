package com.gildedgames.aether.core.util;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.*;

public class BlockStateRecipeUtil {
    public static void writeBlock(FriendlyByteBuf buf, Block block) {
        if (block.defaultBlockState().isAir()) {
            buf.writeBoolean(false);
        } else {
            buf.writeBoolean(true);
            buf.writeVarInt(Registry.BLOCK.getId(block));
        }
    }

    public static void writeProperties(FriendlyByteBuf buf, Map<Property<?>, Comparable<?>> properties) {
        if (properties.isEmpty()) {
            buf.writeBoolean(false);
        } else {
            buf.writeBoolean(true);
            CompoundTag tag = new CompoundTag();
            for (Map.Entry<Property<?>, Comparable<?>> entry : properties.entrySet()) {
                Property<?> property = entry.getKey();
                tag.putString(property.getName(), getName(property, entry.getValue()));
            }
            buf.writeNbt(tag);
        }
    }

    public static void writeBiomeKey(FriendlyByteBuf buf, ResourceKey<Biome> biomeKey) {
        if (biomeKey == null) {
            buf.writeBoolean(false);
        } else {
            buf.writeBoolean(true);
            buf.writeResourceLocation(biomeKey.location());
        }
    }

    public static void writeBiomeTag(FriendlyByteBuf buf, TagKey<Biome> biomeTag) {
        if (biomeTag == null) {
            buf.writeBoolean(false);
        } else {
            buf.writeBoolean(true);
            buf.writeResourceLocation(biomeTag.location());
        }
    }

    public static Block readBlock(FriendlyByteBuf buf) {
        if (!buf.readBoolean()) {
            return Blocks.AIR;
        } else {
            int i = buf.readVarInt();
            return Registry.BLOCK.byId(i);
        }
    }

    public static Map<Property<?>, Comparable<?>> readProperties(FriendlyByteBuf buf, Block block)  {
        if (!buf.readBoolean()) {
            return Maps.newHashMap();
        } else {
            Map<Property<?>, Comparable<?>> properties = Maps.newHashMap();
            StateDefinition<Block, BlockState> stateDefinition = block.getStateDefinition();
            CompoundTag tag = buf.readNbt();
            if (tag != null) {
                for (String propertyName : tag.getAllKeys()) {
                    Property<?> property = stateDefinition.getProperty(propertyName);
                    if (property != null) {
                        Optional<Comparable<?>> comparable = (Optional<Comparable<?>>) property.getValue(propertyName);
                        comparable.ifPresent(value -> properties.put(property, value));
                    }
                }
            }
            return properties;
        }
    }

    public static ResourceKey<Biome> readBiomeKey(FriendlyByteBuf buf) {
        if (!buf.readBoolean()) {
            return null;
        } else {
            ResourceLocation biomeLocation = buf.readResourceLocation();
            return ResourceKey.create(Registry.BIOME_REGISTRY, biomeLocation);
        }
    }

    public static TagKey<Biome> readBiomeTag(FriendlyByteBuf buf) {
        if (!buf.readBoolean()) {
            return null;
        } else {
            ResourceLocation tagLocation = buf.readResourceLocation();
            return TagKey.create(Registry.BIOME_REGISTRY, tagLocation);
        }
    }

    public static Block blockFromJson(JsonObject json) {
        String blockName = GsonHelper.getAsString(json, "block");
        Block block = Registry.BLOCK.getOptional(new ResourceLocation(blockName)).orElseThrow(() -> new JsonSyntaxException("Unknown block '" + blockName + "'"));
        if (block.defaultBlockState().isAir()) {
            throw new JsonSyntaxException("Invalid block: " + blockName);
        } else {
            return block;
        }
    }

    public static Map<Property<?>, Comparable<?>> propertiesFromJson(JsonObject json, Block block) {
        Map<Property<?>, Comparable<?>> properties = Maps.newHashMap();
        StateDefinition<Block, BlockState> stateDefinition = block.getStateDefinition();
        JsonObject propertyObject = GsonHelper.getAsJsonObject(json, "properties");
        for (String propertyName : propertyObject.keySet()) {
            Property<?> property = stateDefinition.getProperty(propertyName);
            String valueName = GsonHelper.getAsString(propertyObject, propertyName);
            if (property != null) {
                Optional<Comparable<?>> comparable = (Optional<Comparable<?>>) property.getValue(valueName);
                comparable.ifPresent(value -> properties.put(property, value));
            }
        }
        return properties;
    }

    public static ResourceKey<Biome> biomeKeyFromJson(JsonObject json) {
        String biomeName = GsonHelper.getAsString(json, "biome");
        String[] nameWithId = biomeName.split(":");
        return ResourceKey.create(Registry.BIOME_REGISTRY, (nameWithId.length > 1) ? new ResourceLocation(nameWithId[0], nameWithId[1]) : new ResourceLocation(biomeName));
    }

    public static TagKey<Biome> biomeTagFromJson(JsonObject json) {
        String biomeName = GsonHelper.getAsString(json, "biome").replace("#", "");
        String[] nameWithId = biomeName.split(":");
        return TagKey.create(Registry.BIOME_REGISTRY, (nameWithId.length > 1) ? new ResourceLocation(nameWithId[0], nameWithId[1]) : new ResourceLocation(biomeName));
    }

    public static void biomeKeyToJson(JsonObject json, ResourceKey<Biome> biomeKey) {
        if (biomeKey != null) {
            ResourceLocation biomeLocation = biomeKey.location();
            json.addProperty("biome", biomeLocation.toString());
        }
    }

    public static void biomeTagToJson(JsonObject json, TagKey<Biome> biomeTag) {
        if (biomeTag != null) {
            ResourceLocation tagLocation = biomeTag.location();
            json.addProperty("biome", "#" + tagLocation);
        }
    }

    public static <T extends Comparable<T>, V extends T> BlockState setHelper(Map.Entry<Property<?>, Comparable<?>> properties, BlockState state) {
        return state.setValue((Property<T>) properties.getKey(), (V) properties.getValue());
    }

    public static <T extends Comparable<T>> String getName(Property<T> pProperty, Comparable<?> pValue) {
        return pProperty.getName((T) pValue);
    }
}
