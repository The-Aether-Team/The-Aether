package com.gildedgames.aether.core.util;

import com.gildedgames.aether.Aether;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Optional;

public class BlockStateRecipeUtil {
    public static void writeBlock(FriendlyByteBuf buf, BlockState state) {
        if (state.isAir()) {
            buf.writeBoolean(false);
        } else {
            buf.writeBoolean(true);
            buf.writeVarInt(Block.getId(state));
        }
    }

    public static BlockState readBlock(FriendlyByteBuf buf) {
        if (!buf.readBoolean()) {
            return Blocks.AIR.defaultBlockState();
        } else {
            int i = buf.readVarInt();
            return Block.stateById(i);
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

    public static BlockState blockStateFromJson(JsonObject json, Block block) {
        BlockState blockstate = block.defaultBlockState();
        StateDefinition<Block, BlockState> stateDefinition = block.getStateDefinition();
        JsonArray jsonArray = GsonHelper.getAsJsonArray(json, "properties");
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement jsonElement = jsonArray.get(i);
            if (jsonElement.isJsonObject()) {
                for (String propertyName : jsonElement.getAsJsonObject().keySet()) {
                    Property<?> property = stateDefinition.getProperty(propertyName);
                    if (property != null) {
                        blockstate = setValueHelper(blockstate, property, propertyName, jsonElement.getAsJsonObject(), json);
                    }
                }
            }
        }
        return blockstate;
    }

    private static <S extends StateHolder<?, S>, T extends Comparable<T>> S setValueHelper(S stateHolder, Property<T> property, String propertyName, JsonObject propertiesObject, JsonObject blockStateObject) {
        Optional<T> optional = property.getValue(GsonHelper.getAsString(propertiesObject, propertyName));
        if (optional.isPresent()) {
            return stateHolder.setValue(property, optional.get());
        } else {
            Aether.LOGGER.warn("Unable to read property: {} with value: {} for blockstate: {}", propertyName, GsonHelper.getAsString(propertiesObject, propertyName), blockStateObject.toString());
            return stateHolder;
        }
    }
}
