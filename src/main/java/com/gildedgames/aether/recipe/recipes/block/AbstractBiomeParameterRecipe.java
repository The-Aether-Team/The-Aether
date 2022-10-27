package com.gildedgames.aether.recipe.recipes.block;

import com.gildedgames.aether.recipe.BlockPropertyPair;
import com.gildedgames.aether.recipe.BlockStateIngredient;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public abstract class AbstractBiomeParameterRecipe extends AbstractBlockStateRecipe  {
    private final ResourceKey<Biome> biomeKey;
    private final TagKey<Biome> biomeTag;

    public AbstractBiomeParameterRecipe(RecipeType<?> type, ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient ingredient, BlockPropertyPair result, CommandFunction.CacheableFunction mcfunction) {
        super(type, id, ingredient, result, mcfunction);
        this.biomeKey = biomeKey;
        this.biomeTag = biomeTag;
    }

    @Override
    public boolean matches(Level level, BlockPos pos, BlockState state) {
        if (this.biomeKey != null) {
            return level.getBiome(pos).is(this.biomeKey) && super.matches(level, pos, state);
        } else if (this.biomeTag != null) {
            return level.getBiome(pos).is(this.biomeTag) && super.matches(level, pos, state);
        } else {
            return super.matches(level, pos, state);
        }
    }

    @Nullable
    public ResourceKey<Biome> getBiomeKey() {
        return this.biomeKey;
    }

    @Nullable
    public TagKey<Biome> getBiomeTag() {
        return this.biomeTag;
    }
}
