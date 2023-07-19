package com.aetherteam.aether.data.providers;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.builder.*;
import com.aetherteam.nitrogen.data.providers.NitrogenRecipeProvider;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.builder.BlockStateRecipeBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
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
import net.minecraftforge.common.Tags;

import java.util.Map;
import java.util.function.Supplier;

public abstract class AetherRecipeProvider extends NitrogenRecipeProvider {
    public AetherRecipeProvider(PackOutput output, String id) {
        super(output, id);
    }

    protected ShapedRecipeBuilder fence(Supplier<? extends Block> fence, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, fence.get(), 3)
                .group("wooden_fence")
                .define('M', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("M/M")
                .pattern("M/M")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected ShapedRecipeBuilder fenceGate(Supplier<? extends Block> fenceGate, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, fenceGate.get())
                .group("wooden_fence_gate")
                .define('M', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("/M/")
                .pattern("/M/")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected ShapedRecipeBuilder makePickaxeWithTag(Supplier<? extends Item> pickaxe, TagKey<Item> material, String has) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe.get())
                .define('#', material)
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("###")
                .pattern(" / ")
                .pattern(" / ")
                .unlockedBy(has, has(material));
    }

    protected ShapedRecipeBuilder makePickaxeWithBlock(Supplier<? extends Item> pickaxe, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("###")
                .pattern(" / ")
                .pattern(" / ")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected ShapedRecipeBuilder makeAxeWithTag(Supplier<? extends Item> axe, TagKey<Item> material, String has) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe.get())
                .define('#', material)
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("##")
                .pattern("#/")
                .pattern(" /")
                .unlockedBy(has, has(material));
    }

    protected ShapedRecipeBuilder makeAxeWithBlock(Supplier<? extends Item> axe, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("##")
                .pattern("#/")
                .pattern(" /")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected ShapedRecipeBuilder makeShovelWithTag(Supplier<? extends Item> shovel, TagKey<Item> material, String has) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel.get())
                .define('#', material)
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("#")
                .pattern("/")
                .pattern("/")
                .unlockedBy(has, has(material));
    }

    protected ShapedRecipeBuilder makeShovelWithBlock(Supplier<? extends Item> shovel, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("#")
                .pattern("/")
                .pattern("/")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected ShapedRecipeBuilder makeHoeWithTag(Supplier<? extends Item> hoe, TagKey<Item> material, String has) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe.get())
                .define('#', material)
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("##")
                .pattern(" /")
                .pattern(" /")
                .unlockedBy(has, has(material));
    }

    protected ShapedRecipeBuilder makeHoeWithBlock(Supplier<? extends Item> hoe, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("##")
                .pattern(" /")
                .pattern(" /")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected ShapedRecipeBuilder makeSwordWithTag(Supplier<? extends Item> sword, TagKey<Item> material, String has) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword.get())
                .define('#', material)
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("#")
                .pattern("#")
                .pattern("/")
                .unlockedBy(has, has(material));
    }

    protected ShapedRecipeBuilder makeSwordWithBlock(Supplier<? extends Item> sword, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("#")
                .pattern("#")
                .pattern("/")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected ShapedRecipeBuilder makePickaxe(Supplier<? extends Item> pickaxe, Supplier<? extends Item> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("###")
                .pattern(" / ")
                .pattern(" / ")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected ShapedRecipeBuilder makeAxe(Supplier<? extends Item> axe, Supplier<? extends Item> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("##")
                .pattern("#/")
                .pattern(" /")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected ShapedRecipeBuilder makeShovel(Supplier<? extends Item> shovel, Supplier<? extends Item> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("#")
                .pattern("/")
                .pattern("/")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected ShapedRecipeBuilder makeHoe(Supplier<? extends Item> hoe, Supplier<? extends Item> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("##")
                .pattern(" /")
                .pattern(" /")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected ShapedRecipeBuilder makeSword(Supplier<? extends Item> sword, Supplier<? extends Item> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("#")
                .pattern("#")
                .pattern("/")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected ShapedRecipeBuilder makeHelmetWithBlock(Supplier<? extends Item> helmet, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet.get())
                .define('#', material.get())
                .pattern("###")
                .pattern("# #")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected ShapedRecipeBuilder makeChestplateWithBlock(Supplier<? extends Item> chestplate, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate.get())
                .define('#', material.get())
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected ShapedRecipeBuilder makeLeggingsWithBlock(Supplier<? extends Item> leggings, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings.get())
                .define('#', material.get())
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected ShapedRecipeBuilder makeBootsWithBlock(Supplier<? extends Item> boots, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots.get())
                .define('#', material.get())
                .pattern("# #")
                .pattern("# #")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected ShapedRecipeBuilder makeGlovesWithBlock(Supplier<? extends Item> gloves, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, gloves.get())
                .define('#', material.get())
                .pattern("# #")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected ShapedRecipeBuilder makePendant(Supplier<? extends Item> pendant, Item material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, pendant.get())
                .define('S', Tags.Items.STRING)
                .define('#', material)
                .pattern("SSS")
                .pattern("S S")
                .pattern(" # ")
                .unlockedBy(getHasName(material), has(material));
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
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(this.pair(ingredient, Map.of(BlockStateProperties.LEVEL, 0))), result, AetherRecipeSerializers.ACCESSORY_FREEZABLE.get());
    }

    protected BlockStateRecipeBuilder convertPlacement(Block result, Block ingredient, TagKey<Biome> biome) {
        return BiomeParameterRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, biome, AetherRecipeSerializers.PLACEMENT_CONVERSION.get());
    }

    protected BlockStateRecipeBuilder convertPlacementWithProperties(Block result, Map<Property<?>, Comparable<?>> resultProperties, Block ingredient, Map<Property<?>, Comparable<?>> ingredientProperties, TagKey<Biome> biome) {
        return BiomeParameterRecipeBuilder.recipe(BlockStateIngredient.of(this.pair(ingredient, ingredientProperties)), result, resultProperties, biome, AetherRecipeSerializers.PLACEMENT_CONVERSION.get());
    }

    protected PlacementBanBuilder banItemPlacement(ItemLike ingredient, TagKey<Biome> biome) {
        return ItemBanBuilder.recipe(Ingredient.of(ingredient), BlockStateIngredient.EMPTY, biome, AetherRecipeSerializers.ITEM_PLACEMENT_BAN.get());
    }

    protected PlacementBanBuilder banItemPlacementWithBypass(ItemLike ingredient, TagKey<Block> bypass, TagKey<Biome> biome) {
        return ItemBanBuilder.recipe(Ingredient.of(ingredient), BlockStateIngredient.of(bypass), biome, AetherRecipeSerializers.ITEM_PLACEMENT_BAN.get());
    }

    protected PlacementBanBuilder banBlockPlacement(Block ingredient, TagKey<Biome> biome) {
        return BlockBanBuilder.recipe(BlockStateIngredient.of(ingredient), BlockStateIngredient.EMPTY, biome, AetherRecipeSerializers.BLOCK_PLACEMENT_BAN.get());
    }

    protected PlacementBanBuilder banBlockPlacementWithBypass(Block ingredient, TagKey<Block> bypass, TagKey<Biome> biome) {
        return BlockBanBuilder.recipe(BlockStateIngredient.of(ingredient), BlockStateIngredient.of(bypass), biome, AetherRecipeSerializers.BLOCK_PLACEMENT_BAN.get());
    }
}
