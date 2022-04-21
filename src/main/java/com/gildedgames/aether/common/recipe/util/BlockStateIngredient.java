package com.gildedgames.aether.common.recipe.util;

import com.gildedgames.aether.core.util.BlockStateRecipeUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.*;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BlockStateIngredient implements Predicate<BlockState> {
    public static final BlockStateIngredient EMPTY = new BlockStateIngredient(Stream.empty());
    private final BlockStateIngredient.Value[] values;
    @Nullable
    private BlockState[] blockStates;

    protected BlockStateIngredient(Stream<? extends BlockStateIngredient.Value> values) {
        this.values = values.toArray(Value[]::new);
    }

    private void dissolve() {
        if (this.blockStates == null) {
            this.blockStates = Arrays.stream(this.values).flatMap((values) -> values.getStates().stream()).distinct().toArray(BlockState[]::new);
        }
    }

    @Override
    public boolean test(@Nullable BlockState state) {
        if (state == null) {
            return false;
        } else {
            this.dissolve();
            if (this.blockStates.length == 0) {
                return state.isAir();
            } else {
                for (BlockState blockState : this.blockStates) {
                    if (blockState.is(state.getBlock())) {
                        return true;
                    }
                }
                return false;
            }
        }
    }

    public boolean isEmpty() {
        return this.values.length == 0 && (this.blockStates == null || this.blockStates.length == 0);
    }

    //TODO: of() blockstates

    public static BlockStateIngredient of() {
        return EMPTY;
    }

    public static BlockStateIngredient of(Block... blocks) {
        return ofBlock(Arrays.stream(blocks));
    }

    public static BlockStateIngredient ofBlock(Stream<Block> blocks) {
        return fromValues(blocks.filter((block) -> !block.defaultBlockState().isAir()).map(BlockStateIngredient.BlockValue::new));
    }

    public static BlockStateIngredient of(TagKey<Block> tag) {
        return fromValues(Stream.of(new BlockStateIngredient.TagValue(tag)));
    }

    public final void toNetwork(FriendlyByteBuf buf) {
        this.dissolve();
        buf.writeCollection(Arrays.asList(this.blockStates), BlockStateRecipeUtil::writeBlockState);
    }

    public static BlockStateIngredient fromNetwork(FriendlyByteBuf buf) {
        var size = buf.readVarInt();
        BlockState blockState = BlockStateRecipeUtil.readBlockState(buf);
        return fromValues(Stream.generate(() -> new BlockStateIngredient.StateValue(blockState.getBlock(), blockState.getValues())).limit(size));
    }

    public static BlockStateIngredient fromValues(Stream<? extends BlockStateIngredient.Value> stream) {
        BlockStateIngredient ingredient = new BlockStateIngredient(stream);
        return ingredient.values.length == 0 ? EMPTY : ingredient;
    }

    public JsonElement toJson() {
        if (this.values.length == 1) {
            return this.values[0].serialize();
        } else {
            JsonArray jsonArray = new JsonArray();
            for (BlockStateIngredient.Value value : this.values) {
                jsonArray.add(value.serialize());
            }
            return jsonArray;
        }
    }

    public static BlockStateIngredient fromJson(@Nullable JsonElement json) {
        if (json != null && !json.isJsonNull()) {
            if (json.isJsonObject()) {
                return fromValues(Stream.of(valueFromJson(json.getAsJsonObject())));
            } else if (json.isJsonArray()) {
                JsonArray jsonArray = json.getAsJsonArray();
                if (jsonArray.size() == 0) {
                    throw new JsonSyntaxException("Block array cannot be empty, at least one item must be defined");
                } else {
                    return fromValues(StreamSupport.stream(jsonArray.spliterator(), false).map((p_151264_) -> valueFromJson(GsonHelper.convertToJsonObject(p_151264_, "block"))));
                }
            } else {
                throw new JsonSyntaxException("Expected block to be object or array of objects");
            }
        } else {
            throw new JsonSyntaxException("Block cannot be null");
        }
    }

    public static BlockStateIngredient.Value valueFromJson(JsonObject json) {
        if (json.has("block") && json.has("tag")) {
            throw new JsonParseException("An ingredient entry is either a tag or a block, not both");
        } else if (json.has("block")) {
            Block block = BlockStateRecipeUtil.blockFromJson(json);
            if (json.has("properties")) {
                Map<Property<?>, Comparable<?>> properties = BlockStateRecipeUtil.propertiesFromJson(json, block);
                return new BlockStateIngredient.StateValue(block, properties);
            } else {
                return new BlockStateIngredient.BlockValue(block);
            }
        } else if (json.has("tag")) {
            ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(json, "tag"));
            TagKey<Block> tagKey = TagKey.create(Registry.BLOCK_REGISTRY, resourcelocation);
            return new BlockStateIngredient.TagValue(tagKey);
        } else {
            throw new JsonParseException("An ingredient entry needs either a tag or a block");
        }
    }

    public static class StateValue implements BlockStateIngredient.Value { //in practice, the usage of properties will probably have to be like checking if the input has the property value or something to match it which might be doable in test().
        private final Block block;
        private final Map<Property<?>, Comparable<?>> properties;

        public StateValue(Block block, Map<Property<?>, Comparable<?>> properties) {
            this.block = block;
            this.properties = properties;
        }

        public StateValue put(Property<?> property, Comparable<?> value) {
            this.properties.put(property, value);
            return this;
        }

        public Collection<BlockState> getStates() {
            BlockState blockState = this.block.defaultBlockState();
            for (Map.Entry<Property<?>, Comparable<?>> entry : this.properties.entrySet()) {
                BlockStateRecipeUtil.putHelper(this.properties, entry.getKey(), entry.getValue());
            }
            return Collections.singleton(blockState);
        }

        @Override
        public Map<Property<?>, Comparable<?>> getProperties() {
            return this.properties;
        }

        public JsonObject serialize() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("block", Registry.BLOCK.getKey(this.block).toString());
            JsonObject jsonObject1 = new JsonObject();
            if (!this.properties.isEmpty()) {
                for (Map.Entry<Property<?>, Comparable<?>> entry : this.properties.entrySet()) {
                    Property<?> property = entry.getKey();
                    jsonObject1.addProperty(property.getName(), BlockStateRecipeUtil.getName(property, entry.getValue())); //TODO: verify.
                }
            }
            jsonObject.add("properties", jsonObject1);
            return jsonObject;
        }
    }

    public static class BlockValue implements BlockStateIngredient.Value { //TODO: verify
        private final Block block;

        public BlockValue(Block block) {
            this.block = block;
        }

        public Collection<BlockState> getStates() {
            List<BlockState> list = Lists.newArrayList();
            for (BlockState blockState : Block.BLOCK_STATE_REGISTRY) {
                if (blockState.is(this.block)) {
                    list.add(blockState);
                }
            }
            return list;
        }

        @Override
        public Map<Property<?>, Comparable<?>> getProperties() {
            return Map.of();
        }

        public JsonObject serialize() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("block", Registry.BLOCK.getKey(this.block).toString());
            return jsonObject;
        }
    }

    public static class TagValue implements BlockStateIngredient.Value {
        private final TagKey<Block> tag;

        public TagValue(TagKey<Block> tag) {
            this.tag = tag;
        }

        public Collection<BlockState> getStates() {
            List<BlockState> list = Lists.newArrayList();
            for (Holder<Block> blockHolder : Registry.BLOCK.getTagOrEmpty(this.tag)) {
                for (BlockState blockState : Block.BLOCK_STATE_REGISTRY) {
                    if (blockState.is(blockHolder.value())) {
                        list.add(blockState);
                    }
                }
            }
            return list;
        }

        @Override
        public Map<Property<?>, Comparable<?>> getProperties() {
            return Map.of();
        }

        public JsonObject serialize() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("tag", this.tag.location().toString());
            return jsonobject;
        }
    }

    public interface Value {
        Collection<BlockState> getStates();
        Map<Property<?>, Comparable<?>> getProperties();

        JsonObject serialize();
    }
}
