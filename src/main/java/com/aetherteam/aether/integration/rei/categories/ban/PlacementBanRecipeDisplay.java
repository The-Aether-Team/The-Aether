package com.aetherteam.aether.integration.rei.categories.ban;

import com.aetherteam.aether.integration.rei.AetherREIServerPlugin;
import com.aetherteam.aether.recipe.recipes.ban.AbstractPlacementBanRecipe;
import com.aetherteam.aether.recipe.recipes.ban.BlockBanRecipe;
import com.aetherteam.aether.recipe.recipes.ban.ItemBanRecipe;
import com.aetherteam.nitrogen.integration.rei.REIUtils;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.mojang.datafixers.util.Either;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlacementBanRecipeDisplay<R extends AbstractPlacementBanRecipe<?, ?, ?>> extends BasicDisplay {
    private final CategoryIdentifier<?> categoryIdentifier;

    private final Either<ResourceKey<Biome>, TagKey<Biome>> biome;
    private final Optional<BlockStateIngredient> bypassBlock;
    private final Optional<BlockStateIngredient> blockStateIngredient;

    protected PlacementBanRecipeDisplay(CategoryIdentifier<? extends PlacementBanRecipeDisplay<R>> categoryIdentifier, List<EntryIngredient> inputs, Either<ResourceKey<Biome>, TagKey<Biome>> biome, Optional<BlockStateIngredient> bypassBlock, Optional<BlockStateIngredient> blockStateIngredient, Optional<ResourceLocation> location) {
        super(inputs, List.of(), location);
        this.categoryIdentifier = categoryIdentifier;
        this.biome = biome;
        this.bypassBlock = bypassBlock;
        this.blockStateIngredient = blockStateIngredient;
    }

    protected PlacementBanRecipeDisplay(R recipe, CategoryIdentifier<? extends PlacementBanRecipeDisplay<R>> categoryIdentifier, List<EntryIngredient> inputs) {
        this(categoryIdentifier, inputs, recipe.getBiome(), recipe.getBypassBlock(), Optional.ofNullable((recipe.getIngredient() instanceof BlockStateIngredient ingredient) ? ingredient : null), Optional.empty());
    }

    public static PlacementBanRecipeDisplay<ItemBanRecipe> ofItem(ItemBanRecipe recipe){
        List<EntryIngredient> list = new ArrayList<>();

        list.add(EntryIngredients.ofIngredient(recipe.getIngredient()));

        if (recipe.getBypassBlock().isPresent() && !recipe.getBypassBlock().get().isEmpty()) {
            list.addAll(REIUtils.toIngredientList(recipe.getBypassBlock().get().getPairs()));
        }

        return new PlacementBanRecipeDisplay<>(recipe, AetherREIServerPlugin.ITEM_PLACEMENT_BAN, list);
    }

    public static PlacementBanRecipeDisplay<BlockBanRecipe> ofBlock(BlockBanRecipe recipe) {
        List<EntryIngredient> list = new ArrayList<>(REIUtils.toIngredientList(recipe.getIngredient().getPairs()));

        if (recipe.getBypassBlock().isPresent() && !recipe.getBypassBlock().get().isEmpty()) {
            list.addAll(REIUtils.toIngredientList(recipe.getBypassBlock().get().getPairs()));
        }

        return new PlacementBanRecipeDisplay<>(recipe, AetherREIServerPlugin.BLOCK_PLACEMENT_BAN, list);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return this.categoryIdentifier;
    }

    public Either<ResourceKey<Biome>, TagKey<Biome>> getBiome() {
        return this.biome;
    }

    public Optional<BlockStateIngredient> getBypassBlock() {
        return this.bypassBlock;
    }

    @Nullable
    public BlockStateIngredient getBlockStateIngredient() {
        return this.blockStateIngredient.orElse(null);
    }
}
