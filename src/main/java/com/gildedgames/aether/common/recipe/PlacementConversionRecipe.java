package com.gildedgames.aether.common.recipe;

import com.gildedgames.aether.common.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.common.event.events.PlacementConvertEvent;
import com.gildedgames.aether.common.recipe.ingredient.BlockStateIngredient;
import com.gildedgames.aether.common.recipe.serializer.BlockStateRecipeSerializer;
import com.gildedgames.aether.common.registry.AetherRecipes;
import com.gildedgames.aether.common.recipe.util.BlockStateRecipeUtil;
import com.gildedgames.aether.core.util.LevelUtil;
import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Map;

public class PlacementConversionRecipe extends AbstractBlockStateRecipe {
    private final ResourceKey<DimensionType> dimensionTypeKey;
    private final TagKey<DimensionType> dimensionTypeTag;

    public PlacementConversionRecipe(ResourceLocation id, BlockStateIngredient ingredient, @Nullable ResourceKey<DimensionType> dimensionTypeKey, @Nullable TagKey<DimensionType> dimensionTypeTag, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties) {
        super(AetherRecipes.RecipeTypes.PLACEMENT_CONVERSION, id, ingredient, resultBlock, resultProperties);
        this.dimensionTypeKey = dimensionTypeKey;
        this.dimensionTypeTag = dimensionTypeTag;
    }

    public PlacementConversionRecipe(ResourceLocation id, BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties) {
        this(id, ingredient, null, null, resultBlock, resultProperties);
    }

    public boolean convert(Level level, BlockPos pos, BlockState oldState) {
        if (this.matches(level, pos, oldState)) {
            BlockState newState = this.getResultState(oldState);
            PlacementConvertEvent event = AetherEventDispatch.onPlacementConvert(level, pos, oldState, newState);
            if (!event.isCanceled()) {
                level.setBlockAndUpdate(pos, newState);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean matches(Level level, BlockPos pos, BlockState state) {
        if (this.dimensionTypeKey != null) {
            return level.dimensionTypeRegistration().is(this.dimensionTypeKey) && super.matches(level, pos, state);
        } else if (this.dimensionTypeTag != null) {
            return LevelUtil.inTag(level, this.dimensionTypeTag) && super.matches(level, pos, state);
        } else {
            return super.matches(level, pos, state);
        }
    }

    public ResourceKey<DimensionType> getDimensionTypeKey() {
        return this.dimensionTypeKey;
    }

    public TagKey<DimensionType> getDimensionTypeTag() {
        return this.dimensionTypeTag;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipes.PLACEMENT_CONVERSION.get();
    }

    public static class Serializer extends BlockStateRecipeSerializer<PlacementConversionRecipe> {
        public Serializer() {
            super(PlacementConversionRecipe::new);
        }

        @Nonnull
        @Override
        public PlacementConversionRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject serializedRecipe) {
            ResourceKey<DimensionType> dimensionTypeKey = null;
            TagKey<DimensionType> dimensionTypeTag = null;
            if (serializedRecipe.has("dimension_type")) {
                String dimensionTypeName = GsonHelper.getAsString(serializedRecipe, "dimension_type");
                if (dimensionTypeName.startsWith("#")) {
                    dimensionTypeTag = BlockStateRecipeUtil.dimensionTypeTagFromJson(serializedRecipe);
                } else {
                    dimensionTypeKey = BlockStateRecipeUtil.dimensionTypeKeyFromJson(serializedRecipe);
                }
            }
            PlacementConversionRecipe recipe = super.fromJson(recipeId, serializedRecipe);
            return new PlacementConversionRecipe(recipeId, recipe.getIngredient(), dimensionTypeKey, dimensionTypeTag, recipe.getResultBlock(), recipe.getResultProperties());
        }

        @Nullable
        @Override
        public PlacementConversionRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buf) {
            ResourceKey<DimensionType> dimensionTypeKey = BlockStateRecipeUtil.readDimensionTypeKey(buf);
            TagKey<DimensionType> dimensionTypeTag = BlockStateRecipeUtil.readDimensionTypeTag(buf);
            BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buf);
            Block blockResult = BlockStateRecipeUtil.readBlock(buf);
            Map<Property<?>, Comparable<?>> propertiesResult = BlockStateRecipeUtil.readProperties(buf, blockResult);
            return new PlacementConversionRecipe(recipeId, ingredient, dimensionTypeKey, dimensionTypeTag, blockResult, propertiesResult);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buf, PlacementConversionRecipe recipe) {
            BlockStateRecipeUtil.writeDimensionTypeKey(buf, recipe.getDimensionTypeKey());
            BlockStateRecipeUtil.writeDimensionTypeTag(buf, recipe.getDimensionTypeTag());
            super.toNetwork(buf, recipe);
        }
    }
}
