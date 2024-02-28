package com.aetherteam.aether.data.providers;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.builder.*;
import com.aetherteam.nitrogen.data.providers.NitrogenRecipeProvider;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.builder.BlockStateRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.common.Tags;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class AetherRecipeProvider extends NitrogenRecipeProvider {
    public AetherRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String id) {
        super(output, lookupProvider, id);
    }

    protected ShapedRecipeBuilder fence(Supplier<? extends Block> fence, Supplier<? extends Block> material) {
        return this.fence(fence, material, Ingredient.of(AetherTags.Items.SKYROOT_STICKS));
    }

    protected ShapedRecipeBuilder fenceGate(Supplier<? extends Block> fenceGate, Supplier<? extends Block> material) {
        return this.fenceGate(fenceGate, material, Ingredient.of(AetherTags.Items.SKYROOT_STICKS));
    }

    protected ShapedRecipeBuilder makePickaxeWithTag(Supplier<? extends Item> pickaxe, TagKey<Item> material, String has) {
        return this.makePickaxeWithTag(pickaxe, material, Ingredient.of(AetherTags.Items.SKYROOT_STICKS), has);
    }

    protected ShapedRecipeBuilder makePickaxeWithBlock(Supplier<? extends Item> pickaxe, Supplier<? extends Block> material) {
        return this.makePickaxeWithBlock(pickaxe, material, Ingredient.of(AetherTags.Items.SKYROOT_STICKS));
    }

    protected ShapedRecipeBuilder makeAxeWithTag(Supplier<? extends Item> axe, TagKey<Item> material, String has) {
        return this.makeAxeWithTag(axe, material, Ingredient.of(AetherTags.Items.SKYROOT_STICKS), has);
    }

    protected ShapedRecipeBuilder makeAxeWithBlock(Supplier<? extends Item> axe, Supplier<? extends Block> material) {
        return this.makeAxeWithBlock(axe, material, Ingredient.of(AetherTags.Items.SKYROOT_STICKS));
    }

    protected ShapedRecipeBuilder makeShovelWithTag(Supplier<? extends Item> shovel, TagKey<Item> material, String has) {
        return this.makeShovelWithTag(shovel, material, Ingredient.of(AetherTags.Items.SKYROOT_STICKS), has);
    }

    protected ShapedRecipeBuilder makeShovelWithBlock(Supplier<? extends Item> shovel, Supplier<? extends Block> material) {
        return this.makeShovelWithBlock(shovel, material, Ingredient.of(AetherTags.Items.SKYROOT_STICKS));
    }

    protected ShapedRecipeBuilder makeHoeWithTag(Supplier<? extends Item> hoe, TagKey<Item> material, String has) {
        return this.makeHoeWithTag(hoe, material, Ingredient.of(AetherTags.Items.SKYROOT_STICKS), has);
    }

    protected ShapedRecipeBuilder makeHoeWithBlock(Supplier<? extends Item> hoe, Supplier<? extends Block> material) {
        return this.makeHoeWithBlock(hoe, material, Ingredient.of(AetherTags.Items.SKYROOT_STICKS));
    }

    protected ShapedRecipeBuilder makeSwordWithTag(Supplier<? extends Item> sword, TagKey<Item> material, String has) {
        return this.makeSwordWithTag(sword, material, Ingredient.of(AetherTags.Items.SKYROOT_STICKS), has);
    }

    protected ShapedRecipeBuilder makeSwordWithBlock(Supplier<? extends Item> sword, Supplier<? extends Block> material) {
        return this.makeSwordWithBlock(sword, material, Ingredient.of(AetherTags.Items.SKYROOT_STICKS));
    }

    protected ShapedRecipeBuilder makePickaxe(Supplier<? extends Item> pickaxe, Supplier<? extends Item> material) {
        return this.makePickaxe(pickaxe, material, Ingredient.of(AetherTags.Items.SKYROOT_STICKS));
    }

    protected ShapedRecipeBuilder makeAxe(Supplier<? extends Item> axe, Supplier<? extends Item> material) {
        return this.makeAxe(axe, material, Ingredient.of(AetherTags.Items.SKYROOT_STICKS));
    }

    protected ShapedRecipeBuilder makeShovel(Supplier<? extends Item> shovel, Supplier<? extends Item> material) {
        return this.makeShovel(shovel, material, Ingredient.of(AetherTags.Items.SKYROOT_STICKS));
    }

    protected ShapedRecipeBuilder makeHoe(Supplier<? extends Item> hoe, Supplier<? extends Item> material) {
        return this.makeHoe(hoe, material, Ingredient.of(AetherTags.Items.SKYROOT_STICKS));
    }

    protected ShapedRecipeBuilder makeSword(Supplier<? extends Item> sword, Supplier<? extends Item> material) {
        return this.makeSword(sword, material, Ingredient.of(AetherTags.Items.SKYROOT_STICKS));
    }

    protected ShapedRecipeBuilder makePendant(Supplier<? extends Item> pendant, Item material) {
        return this.makePendant(pendant, material, Ingredient.of(Tags.Items.STRING));
    }

    protected ShapedRecipeBuilder makeCape(Supplier<? extends Item> cape, Item material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, cape.get())
                .define('#', material)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(material), has(material));
    }

    protected AltarRepairBuilder repairingRecipe(RecipeCategory category, ItemLike item, int duration) {
        return AltarRepairBuilder.repair(Ingredient.of(new ItemStack(item, 1)), category, duration, AetherRecipeSerializers.REPAIRING.get())
                .unlockedBy(getHasName(item), has(item));
    }

    protected AetherCookingRecipeBuilder enchantingRecipe(RecipeCategory category, ItemLike result, ItemLike ingredient, float experience, int duration) {
        return AetherCookingRecipeBuilder.generic(Ingredient.of(new ItemStack(ingredient, 1)), category, result, experience, duration, AetherRecipeSerializers.ENCHANTING.get())
                .unlockedBy(getHasName(ingredient), has(ingredient));
    }

    protected AetherCookingRecipeBuilder enchantingRecipe(RecipeCategory category, ItemLike result, TagKey<Item> ingredient, float experience, int duration, String unlockName) {
        return AetherCookingRecipeBuilder.generic(Ingredient.of(ingredient), category, result, experience, duration, AetherRecipeSerializers.ENCHANTING.get())
                .unlockedBy("has_" + unlockName, has(ingredient));
    }

    protected AetherCookingRecipeBuilder hiddenEnchantingRecipe(RecipeCategory category, ItemLike result, ItemLike ingredient, float experience, int duration) {
        return AetherCookingRecipeBuilder.generic(Ingredient.of(ingredient), category, result, experience, duration, AetherRecipeSerializers.ENCHANTING.get())
                .unlockedBy(getHasName(result), has(result));
    }

    protected AetherCookingRecipeBuilder freezingRecipe(RecipeCategory category, ItemLike result, ItemLike ingredient, float experience, int duration) {
        return AetherCookingRecipeBuilder.generic(Ingredient.of(ingredient), category, result, experience, duration, AetherRecipeSerializers.FREEZING.get())
                .unlockedBy(getHasName(ingredient), has(ingredient));
    }

    protected AetherCookingRecipeBuilder freezingRecipeWithTag(RecipeCategory category, ItemLike result, TagKey<Item> ingredient, float experience, int duration, String unlockName) {
        return AetherCookingRecipeBuilder.generic(Ingredient.of(ingredient), category, result, experience, duration, AetherRecipeSerializers.FREEZING.get())
                .unlockedBy("has_" + unlockName, has(ingredient));
    }

    protected AetherCookingRecipeBuilder freezingRecipeWithUnlockTag(RecipeCategory category, ItemLike result, ItemLike ingredient, TagKey<Item> unlock, float experience, int duration, String unlockName) {
        return AetherCookingRecipeBuilder.generic(Ingredient.of(ingredient), category, result, experience, duration, AetherRecipeSerializers.FREEZING.get())
                .unlockedBy("has_" + unlockName, has(unlock));
    }

    protected AetherCookingRecipeBuilder hiddenFreezingRecipe(RecipeCategory category, ItemLike result, ItemLike ingredient, float experience, int duration) {
        return AetherCookingRecipeBuilder.generic(Ingredient.of(ingredient), category, result, experience, duration, AetherRecipeSerializers.FREEZING.get())
                .unlockedBy(getHasName(result), has(result));
    }

    protected IncubationBuilder moaIncubationRecipe(EntityType<?> entity, Supplier<? extends MoaType> moaType, ItemLike ingredient) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("IsBaby", true);
        tag.putString("MoaType", moaType.get().toString());
        tag.putBoolean("Hungry", true);
        tag.putBoolean("PlayerGrown", true);
        return IncubationBuilder.incubation(Ingredient.of(ingredient), entity, tag, 1000, AetherRecipeSerializers.INCUBATION.get())
                .unlockedBy(getHasName(ingredient), has(ingredient));
    }

    protected BlockStateRecipeBuilder ambrosiumEnchanting(Block result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, AetherRecipeSerializers.AMBROSIUM_ENCHANTING.get());
    }

    protected BlockStateRecipeBuilder swetBallConversion(Block result, Block ingredient) {
        return BiomeParameterRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, AetherRecipeSerializers.SWET_BALL_CONVERSION.get());
    }

    protected BlockStateRecipeBuilder swetBallConversionTag(Block result, Block ingredient, TagKey<Biome> tagKey) {
        return BiomeParameterRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, tagKey, AetherRecipeSerializers.SWET_BALL_CONVERSION.get());
    }

    protected BlockStateRecipeBuilder icestoneFreezable(Block result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, AetherRecipeSerializers.ICESTONE_FREEZABLE.get());
    }

    protected BlockStateRecipeBuilder accessoryFreezable(Block result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(this.pair(ingredient, Optional.of(Map.of(BlockStateProperties.LEVEL, 0)))), result, AetherRecipeSerializers.ACCESSORY_FREEZABLE.get());
    }

    protected BlockStateRecipeBuilder convertPlacement(Block result, Block ingredient, TagKey<Biome> biome) {
        return BiomeParameterRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, biome, AetherRecipeSerializers.PLACEMENT_CONVERSION.get());
    }

    protected BlockStateRecipeBuilder convertPlacementWithProperties(Block result, Map<Property<?>, Comparable<?>> resultProperties, Block ingredient, Map<Property<?>, Comparable<?>> ingredientProperties, TagKey<Biome> biome) {
        return BiomeParameterRecipeBuilder.recipe(BlockStateIngredient.of(this.pair(ingredient, Optional.of(ingredientProperties))), result, resultProperties, biome, AetherRecipeSerializers.PLACEMENT_CONVERSION.get());
    }

    protected PlacementBanBuilder banItemPlacement(ItemLike ingredient, TagKey<Biome> biome) {
        return ItemBanBuilder.recipe(Ingredient.of(ingredient), Optional.empty(), biome, AetherRecipeSerializers.ITEM_PLACEMENT_BAN.get());
    }

    protected PlacementBanBuilder banItemPlacementWithBypass(ItemLike ingredient, TagKey<Block> bypass, TagKey<Biome> biome) {
        return ItemBanBuilder.recipe(Ingredient.of(ingredient), Optional.of(BlockStateIngredient.of(bypass)), biome, AetherRecipeSerializers.ITEM_PLACEMENT_BAN.get());
    }

    protected PlacementBanBuilder banBlockPlacement(Block ingredient, TagKey<Biome> biome) {
        return BlockBanBuilder.recipe(BlockStateIngredient.of(ingredient), Optional.empty(), biome, AetherRecipeSerializers.BLOCK_PLACEMENT_BAN.get());
    }

    protected PlacementBanBuilder banBlockPlacementWithBypass(Block ingredient, TagKey<Block> bypass, TagKey<Biome> biome) {
        return BlockBanBuilder.recipe(BlockStateIngredient.of(ingredient), Optional.of(BlockStateIngredient.of(bypass)), biome, AetherRecipeSerializers.BLOCK_PLACEMENT_BAN.get());
    }
}
