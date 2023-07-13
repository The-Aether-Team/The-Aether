package com.aetherteam.aether.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class BlockStateRecipeUtil {
    /**
     * Executes an mcfunction.
     * @param level The {@link Level} to execute in.
     * @param pos The {@link BlockPos} to execute at.
     * @param function The {@link net.minecraft.commands.CommandFunction.CacheableFunction} to execute.
     */
    public static void executeFunction(Level level, BlockPos pos, CommandFunction.CacheableFunction function) {
        if (level instanceof ServerLevel serverLevel && function != null) {
            MinecraftServer minecraftServer = serverLevel.getServer();
            function.get(minecraftServer.getFunctions()).ifPresent(command -> {
                CommandSourceStack context = minecraftServer.getFunctions().getGameLoopSender()
                        .withPosition(Vec3.atBottomCenterOf(pos))
                        .withLevel(serverLevel);
                minecraftServer.getFunctions().execute(command, context);
            });
        }
    }

    // Buffer write methods.
    /**
     * Writes a {@link BlockPropertyPair} to the networking buffer.
     * @param buffer The networking {@link FriendlyByteBuf}.
     * @param pair The {@link BlockPropertyPair}.
     */
    public static void writePair(FriendlyByteBuf buffer, BlockPropertyPair pair) {
        ResourceLocation blockLocation = ForgeRegistries.BLOCKS.getKey(pair.block());
        if ((pair.block().defaultBlockState().isAir() && pair.properties().isEmpty()) || blockLocation == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            buffer.writeUtf(blockLocation.toString());
            CompoundTag tag = new CompoundTag();
            for (Map.Entry<Property<?>, Comparable<?>> entry : pair.properties().entrySet()) {
                Property<?> property = entry.getKey();
                tag.putString(property.getName(), getName(property, entry.getValue()));
            }
            buffer.writeNbt(tag);
        }
    }

    /**
     * Writes a {@link Biome} {@link ResourceKey} to the networking buffer.
     * @param buffer The networking {@link FriendlyByteBuf}.
     * @param biomeKey The {@link Biome} {@link ResourceKey}.
     */
    public static void writeBiomeKey(FriendlyByteBuf buffer, ResourceKey<Biome> biomeKey) {
        if (biomeKey == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            buffer.writeResourceLocation(biomeKey.location());
        }
    }

    /**
     * Writes a {@link Biome} {@link TagKey} to the networking buffer.
     * @param buffer The networking {@link FriendlyByteBuf}.
     * @param biomeTag The {@link Biome} {@link TagKey}.
     */
    public static void writeBiomeTag(FriendlyByteBuf buffer, TagKey<Biome> biomeTag) {
        if (biomeTag == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            buffer.writeResourceLocation(biomeTag.location());
        }
    }

    // Buffer read methods.
    /**
     * Reads a {@link BlockPropertyPair} from the networking buffer.<br><br>
     * Warning for "unchecked" is suppressed because casting within this method works fine.
     * @param buffer The networking {@link FriendlyByteBuf}.
     * @return The {@link BlockPropertyPair}.
     */
    @SuppressWarnings("unchecked")
    public static BlockPropertyPair readPair(FriendlyByteBuf buffer) {
        if (!buffer.readBoolean()) {
            return BlockPropertyPair.of(Blocks.AIR, new HashMap<>());
        } else {
            String blockString = buffer.readUtf();
            ResourceLocation blockLocation = new ResourceLocation(blockString);
            Block block = ForgeRegistries.BLOCKS.getValue(blockLocation);
            if (block == null) {
                throw new JsonSyntaxException("Unknown block '" + blockLocation + "'");
            }

            Map<Property<?>, Comparable<?>> properties = new HashMap<>();
            CompoundTag tag = buffer.readNbt();

            if (tag != null) {
                for (String propertyName : tag.getAllKeys()) {
                    Property<?> property = block.getStateDefinition().getProperty(propertyName);
                    if (property != null) {
                        Optional<Comparable<?>> comparable = (Optional<Comparable<?>>) property.getValue(propertyName);
                        comparable.ifPresent(value -> properties.put(property, value));
                    }
                }
            }

            return BlockPropertyPair.of(block, properties);
        }
    }

    /**
     * Reads a {@link Biome} {@link ResourceKey} from the networking buffer.
     * @param buffer The networking {@link FriendlyByteBuf}.
     * @return The {@link Biome} {@link ResourceKey}.
     */
    public static ResourceKey<Biome> readBiomeKey(FriendlyByteBuf buffer) {
        if (!buffer.readBoolean()) {
            return null;
        } else {
            ResourceLocation biomeLocation = buffer.readResourceLocation();
            return ResourceKey.create(Registries.BIOME, biomeLocation);
        }
    }

    /**
     * Reads a {@link Biome} {@link TagKey} from the networking buffer.
     * @param buffer The networking {@link FriendlyByteBuf}.
     * @return The {@link Biome} {@link TagKey}.
     */
    public static TagKey<Biome> readBiomeTag(FriendlyByteBuf buffer) {
        if (!buffer.readBoolean()) {
            return null;
        } else {
            ResourceLocation tagLocation = buffer.readResourceLocation();
            return TagKey.create(Registries.BIOME, tagLocation);
        }
    }

    /**
     * Reads a {@link net.minecraft.commands.CommandFunction.CacheableFunction} from the networking buffer.
     * @param buffer The networking {@link FriendlyByteBuf}.
     * @return The {@link net.minecraft.commands.CommandFunction.CacheableFunction}.
     */
    public static CommandFunction.CacheableFunction readFunction(FriendlyByteBuf buffer) {
        String functionString = buffer.readUtf();
        ResourceLocation functionLocation = functionString.isEmpty() ? null : new ResourceLocation(functionString);
        return functionLocation == null ? CommandFunction.CacheableFunction.NONE : new CommandFunction.CacheableFunction(functionLocation);
    }

    // JSON write methods.
    /**
     * Adds a {@link Biome} {@link ResourceKey} to a {@link JsonObject}.
     * @param json The {@link JsonObject}.
     * @param biomeKey The {@link Biome} {@link ResourceKey}.
     */
    public static void biomeKeyToJson(JsonObject json, ResourceKey<Biome> biomeKey) {
        if (biomeKey != null) {
            ResourceLocation biomeLocation = biomeKey.location();
            json.addProperty("biome", biomeLocation.toString());
        }
    }

    /**
     * Adds a {@link Biome} {@link TagKey} to a {@link JsonObject}.
     * @param json The {@link JsonObject}.
     * @param biomeTag The {@link Biome} {@link TagKey}.
     */
    public static void biomeTagToJson(JsonObject json, TagKey<Biome> biomeTag) {
        if (biomeTag != null) {
            ResourceLocation tagLocation = biomeTag.location();
            json.addProperty("biome", "#" + tagLocation);
        }
    }

    // JSON read methods.
    /**
     * Reads a {@link BlockPropertyPair} from a {@link JsonObject}.
     * @param json The {@link JsonObject}.
     * @return The {@link BlockPropertyPair}.
     */
    public static BlockPropertyPair pairFromJson(JsonObject json) {
        Block block;
        Map<Property<?>, Comparable<?>> properties = Map.of();
        if (json.has("block")) {
            block = BlockStateRecipeUtil.blockFromJson(json);
            if (json.has("properties")) {
                if (json.get("properties").isJsonObject()) {
                    properties = BlockStateRecipeUtil.propertiesFromJson(json, block);
                } else {
                    throw new JsonSyntaxException("Expected properties to be object");
                }
            }
        } else {
            throw new JsonSyntaxException("Missing block in result");
        }
        return BlockPropertyPair.of(block, properties);
    }

    /**
     * Reads a {@link Block} from a {@link JsonObject}.
     * @param json The {@link JsonObject}.
     * @return The {@link Block}.
     */
    public static Block blockFromJson(JsonObject json) {
        String blockName = GsonHelper.getAsString(json, "block");
        ResourceLocation blockLocation = new ResourceLocation(blockName);
        Block block = ForgeRegistries.BLOCKS.getValue(blockLocation);
        if (block == null) {
            throw new JsonSyntaxException("Unknown block '" + blockLocation + "'");
        }
        if (block.defaultBlockState().isAir()) {
            throw new JsonSyntaxException("Invalid block: " + blockLocation);
        } else {
            return block;
        }
    }

    /**
     * Reads a {@link Map} of {@link Property Properties} and {@link Comparable}s (representing block properties) from a {@link JsonObject}.<br><br>
     * Warning for "unchecked" is suppressed because casting within this method works fine.
     * @param json The {@link JsonObject}.
     * @param block The {@link Block} that the properties are paired with.
     * @return Block properties, as a {@link Map} of {@link Property Properties} and {@link Comparable}s.
     */
    @SuppressWarnings("unchecked")
    public static Map<Property<?>, Comparable<?>> propertiesFromJson(JsonObject json, Block block) {
        Map<Property<?>, Comparable<?>> properties = new HashMap<>();
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

    /**
     * Reads a {@link Biome} {@link ResourceKey} or {@link TagKey} from a {@link JsonObject}.
     * @param json The {@link JsonObject}.
     * @return A {@link Pair} containing either a {@link Biome} {@link ResourceKey} or {@link TagKey} and a null value.
     */
    public static Pair<ResourceKey<Biome>, TagKey<Biome>> biomeRecipeDataFromJson(JsonObject json) {
        ResourceKey<Biome> biomeKey = null;
        TagKey<Biome> biomeTag = null;
        if (json.has("biome")) {
            String biomeName = GsonHelper.getAsString(json, "biome");
            if (biomeName.startsWith("#")) {
                biomeTag = biomeTagFromJson(json);
            } else {
                biomeKey = biomeKeyFromJson(json);
            }
        }
        return Pair.of(biomeKey, biomeTag);
    }

    /**
     * Reads a {@link Biome} {@link ResourceKey} from a {@link JsonObject}.
     * @param json The {@link JsonObject}.
     * @return The {@link Biome} {@link ResourceKey}.
     */
    public static ResourceKey<Biome> biomeKeyFromJson(JsonObject json) {
        String biomeName = GsonHelper.getAsString(json, "biome");
        String[] nameWithId = biomeName.split(":");
        return ResourceKey.create(Registries.BIOME, (nameWithId.length > 1) ? new ResourceLocation(nameWithId[0], nameWithId[1]) : new ResourceLocation(biomeName));
    }

    /**
     * Reads a {@link Biome} {@link TagKey} from a {@link JsonObject}.
     * @param json The {@link JsonObject}.
     * @return The {@link Biome} {@link TagKey}.
     */
    public static TagKey<Biome> biomeTagFromJson(JsonObject json) {
        String biomeName = GsonHelper.getAsString(json, "biome").replace("#", "");
        String[] nameWithId = biomeName.split(":");
        return TagKey.create(Registries.BIOME, (nameWithId.length > 1) ? new ResourceLocation(nameWithId[0], nameWithId[1]) : new ResourceLocation(biomeName));
    }

    // Extra methods.
    /**
     * Sets a property to a {@link BlockState} from a property map entry.<br><br>
     * Warning for "unchecked" is suppressed because casting within this method works fine.
     * @param properties The property map entry, as a {@link Map.Entry} of a {@link Property} and {@link Comparable}.
     * @param state The {@link BlockState}.
     * @return The {@link BlockState} with the applied property.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>, V extends T> BlockState setHelper(Map.Entry<Property<?>, Comparable<?>> properties, BlockState state) {
        return state.setValue((Property<T>) properties.getKey(), (V) properties.getValue());
    }

    /**
     * Gets the name of a block property.<br><br>
     * Warning for "unchecked" is suppressed because casting within this method works fine.
     * @param property The block {@link Property}.
     * @param value The property value, as a {@link Comparable}.
     * @return The name as a {@link String}.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> String getName(Property<T> property, Comparable<?> value) {
        return property.getName((T) value);
    }
}
