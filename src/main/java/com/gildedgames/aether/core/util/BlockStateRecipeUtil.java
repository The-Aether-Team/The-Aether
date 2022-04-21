package com.gildedgames.aether.core.util;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Map;
import java.util.Optional;

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
            for (String propertyName : tag.getAllKeys()) {
                Property<?> property = stateDefinition.getProperty(propertyName);
                if (property != null) {
                    Optional<Comparable<?>> comparable = (Optional<Comparable<?>>) property.getValue(propertyName);
                    comparable.ifPresent(value -> properties.put(property, value));
                }
            }
            return properties;
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

    public static Map<Property<?>, Comparable<?>> propertiesFromJson(JsonObject json, Block block) { //todo
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

    public static <T extends Comparable<T>> String getName(Property<T> pProperty, Comparable<?> pValue) {
        return pProperty.getName((T) pValue);
    }
}
