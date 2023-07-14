package com.aetherteam.aether.data.providers;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.builder.*;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.builder.BlockStateRecipeBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
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
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AetherRecipeProvider extends RecipeProvider {
    private static String ID;

    public AetherRecipeProvider(PackOutput output, String id) {
        super(output);
        ID = id;
    }

    protected static ResourceLocation name(String name) {
        return new ResourceLocation(ID, name);
    }

    protected static void oreBlockStorageRecipesRecipesWithCustomUnpacking(Consumer<FinishedRecipe> consumer, RecipeCategory itemCategory, ItemLike item, RecipeCategory blockCategory, ItemLike block, String itemRecipeName, String itemGroup) {
        ShapelessRecipeBuilder.shapeless(itemCategory, item, 9).requires(block).group(itemGroup).unlockedBy(getHasName(block), has(block)).save(consumer, name(itemRecipeName));
        ShapedRecipeBuilder.shaped(blockCategory, block).define('#', item).pattern("###").pattern("###").pattern("###").unlockedBy(getHasName(item), has(item)).save(consumer, name(getSimpleRecipeName(block)));
    }

    protected static ShapedRecipeBuilder fence(Supplier<? extends Block> fence, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, fence.get(), 3)
                .group("wooden_fence")
                .define('M', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("M/M")
                .pattern("M/M")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder fenceGate(Supplier<? extends Block> fenceGate, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, fenceGate.get())
                .group("wooden_fence_gate")
                .define('M', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("/M/")
                .pattern("/M/")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static RecipeBuilder stairs(Supplier<? extends Block> stairs, Supplier<? extends Block> material) {
        return stairBuilder(stairs.get(), Ingredient.of(material.get())).unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makePickaxeWithTag(Supplier<? extends Item> pickaxe, TagKey<Item> material, String has) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe.get())
                .define('#', material)
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("###")
                .pattern(" / ")
                .pattern(" / ")
                .unlockedBy(has, has(material));
    }

    protected static ShapedRecipeBuilder makePickaxeWithBlock(Supplier<? extends Item> pickaxe, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("###")
                .pattern(" / ")
                .pattern(" / ")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeAxeWithTag(Supplier<? extends Item> axe, TagKey<Item> material, String has) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe.get())
                .define('#', material)
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("##")
                .pattern("#/")
                .pattern(" /")
                .unlockedBy(has, has(material));
    }

    protected static ShapedRecipeBuilder makeAxeWithBlock(Supplier<? extends Item> axe, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("##")
                .pattern("#/")
                .pattern(" /")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeShovelWithTag(Supplier<? extends Item> shovel, TagKey<Item> material, String has) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel.get())
                .define('#', material)
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("#")
                .pattern("/")
                .pattern("/")
                .unlockedBy(has, has(material));
    }

    protected static ShapedRecipeBuilder makeShovelWithBlock(Supplier<? extends Item> shovel, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("#")
                .pattern("/")
                .pattern("/")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeHoeWithTag(Supplier<? extends Item> hoe, TagKey<Item> material, String has) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe.get())
                .define('#', material)
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("##")
                .pattern(" /")
                .pattern(" /")
                .unlockedBy(has, has(material));
    }

    protected static ShapedRecipeBuilder makeHoeWithBlock(Supplier<? extends Item> hoe, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("##")
                .pattern(" /")
                .pattern(" /")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeSwordWithTag(Supplier<? extends Item> sword, TagKey<Item> material, String has) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword.get())
                .define('#', material)
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("#")
                .pattern("#")
                .pattern("/")
                .unlockedBy(has, has(material));
    }

    protected static ShapedRecipeBuilder makeSwordWithBlock(Supplier<? extends Item> sword, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("#")
                .pattern("#")
                .pattern("/")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makePickaxe(Supplier<? extends Item> pickaxe, Supplier<? extends Item> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("###")
                .pattern(" / ")
                .pattern(" / ")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeAxe(Supplier<? extends Item> axe, Supplier<? extends Item> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("##")
                .pattern("#/")
                .pattern(" /")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeShovel(Supplier<? extends Item> shovel, Supplier<? extends Item> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("#")
                .pattern("/")
                .pattern("/")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeHoe(Supplier<? extends Item> hoe, Supplier<? extends Item> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("##")
                .pattern(" /")
                .pattern(" /")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeSword(Supplier<? extends Item> sword, Supplier<? extends Item> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword.get())
                .define('#', material.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("#")
                .pattern("#")
                .pattern("/")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeHelmetWithBlock(Supplier<? extends Item> helmet, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet.get())
                .define('#', material.get())
                .pattern("###")
                .pattern("# #")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeChestplateWithBlock(Supplier<? extends Item> chestplate, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate.get())
                .define('#', material.get())
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeLeggingsWithBlock(Supplier<? extends Item> leggings, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings.get())
                .define('#', material.get())
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeBootsWithBlock(Supplier<? extends Item> boots, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots.get())
                .define('#', material.get())
                .pattern("# #")
                .pattern("# #")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeGlovesWithBlock(Supplier<? extends Item> gloves, Supplier<? extends Block> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, gloves.get())
                .define('#', material.get())
                .pattern("# #")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeHelmet(Supplier<? extends Item> helmet, Supplier<? extends Item> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet.get())
                .define('#', material.get())
                .pattern("###")
                .pattern("# #")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeChestplate(Supplier<? extends Item> chestplate, Supplier<? extends Item> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate.get())
                .define('#', material.get())
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeLeggings(Supplier<? extends Item> leggings, Supplier<? extends Item> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings.get())
                .define('#', material.get())
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeBoots(Supplier<? extends Item> boots, Supplier<? extends Item> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots.get())
                .define('#', material.get())
                .pattern("# #")
                .pattern("# #")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeGloves(Supplier<? extends Item> gloves, Supplier<? extends Item> material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, gloves.get())
                .define('#', material.get())
                .pattern("# #")
                .unlockedBy(getHasName(material.get()), has(material.get()));
    }

    protected static ShapedRecipeBuilder makeGlovesWithTag(Supplier<? extends Item> gloves, TagKey<Item> materialTag, String unlockName) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, gloves.get())
                .define('#', materialTag)
                .pattern("# #")
                .unlockedBy("has_" + unlockName, has(materialTag));
    }

    protected static ShapedRecipeBuilder makeRing(Supplier<? extends Item> ring, Item material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ring.get())
                .define('#', material)
                .pattern(" # ")
                .pattern("# #")
                .pattern(" # ")
                .unlockedBy(getHasName(material), has(material));
    }

    protected static ShapedRecipeBuilder makePendant(Supplier<? extends Item> pendant, Item material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, pendant.get())
                .define('S', Tags.Items.STRING)
                .define('#', material)
                .pattern("SSS")
                .pattern("S S")
                .pattern(" # ")
                .unlockedBy(getHasName(material), has(material));
    }

    protected static ShapedRecipeBuilder makeCape(Supplier<? extends Item> cape, Item material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, cape.get())
                .define('#', material)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(material), has(material));
    }

    protected static SimpleCookingRecipeBuilder smeltingOreRecipe(ItemLike result, ItemLike ingredient, float experience) {
        return SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, 200)
                .unlockedBy(getHasName(ingredient), has(ingredient));
    }

    protected static SimpleCookingRecipeBuilder blastingOreRecipe(ItemLike result, ItemLike ingredient, float experience) {
        return SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, 100)
                .unlockedBy(getHasName(ingredient), has(ingredient));
    }

    protected static void stonecuttingRecipe(Consumer<FinishedRecipe> consumer, RecipeCategory category, ItemLike item, ItemLike ingredient) {
        stonecuttingRecipe(consumer, category, item, ingredient, 1);
    }

    protected static void stonecuttingRecipe(Consumer<FinishedRecipe> consumer, RecipeCategory category, ItemLike item, ItemLike ingredient, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), category, item, count).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, name(getConversionRecipeName(item, ingredient) + "_stonecutting"));
    }

    protected static AltarRepairBuilder repairingRecipe(RecipeCategory category, ItemLike item, int duration) {
        return AltarRepairBuilder.repair(Ingredient.of(new ItemStack(item, 1)), category, duration, AetherRecipeSerializers.REPAIRING.get())
                .unlockedBy(getHasName(item), has(item));
    }

    protected static AetherCookingRecipeBuilder enchantingRecipe(RecipeCategory category, ItemLike result, ItemLike ingredient, float experience, int duration) {
        return AetherCookingRecipeBuilder.generic(Ingredient.of(new ItemStack(ingredient, 1)), category, result, experience, duration, AetherRecipeSerializers.ENCHANTING.get())
                .unlockedBy(getHasName(ingredient), has(ingredient));
    }

    protected static AetherCookingRecipeBuilder enchantingRecipe(RecipeCategory category, ItemLike result, TagKey<Item> ingredient, float experience, int duration, String unlockName) {
        return AetherCookingRecipeBuilder.generic(Ingredient.of(ingredient), category, result, experience, duration, AetherRecipeSerializers.ENCHANTING.get())
                .unlockedBy("has_" + unlockName, has(ingredient));
    }

    protected static AetherCookingRecipeBuilder hiddenEnchantingRecipe(RecipeCategory category, ItemLike result, ItemLike ingredient, float experience, int duration) {
        return AetherCookingRecipeBuilder.generic(Ingredient.of(ingredient), category, result, experience, duration, AetherRecipeSerializers.ENCHANTING.get())
                .unlockedBy(getHasName(result), has(result));
    }

    protected static AetherCookingRecipeBuilder freezingRecipe(RecipeCategory category, ItemLike result, ItemLike ingredient, float experience, int duration) {
        return AetherCookingRecipeBuilder.generic(Ingredient.of(ingredient), category, result, experience, duration, AetherRecipeSerializers.FREEZING.get())
                .unlockedBy(getHasName(ingredient), has(ingredient));
    }

    protected static AetherCookingRecipeBuilder freezingRecipeWithTag(RecipeCategory category, ItemLike result, TagKey<Item> ingredient, float experience, int duration, String unlockName) {
        return AetherCookingRecipeBuilder.generic(Ingredient.of(ingredient), category, result, experience, duration, AetherRecipeSerializers.FREEZING.get())
                .unlockedBy("has_" + unlockName, has(ingredient));
    }

    protected static AetherCookingRecipeBuilder freezingRecipeWithUnlockTag(RecipeCategory category, ItemLike result, ItemLike ingredient, TagKey<Item> unlock, float experience, int duration, String unlockName) {
        return AetherCookingRecipeBuilder.generic(Ingredient.of(ingredient), category, result, experience, duration, AetherRecipeSerializers.FREEZING.get())
                .unlockedBy("has_" + unlockName, has(unlock));
    }

    protected static AetherCookingRecipeBuilder hiddenFreezingRecipe(RecipeCategory category, ItemLike result, ItemLike ingredient, float experience, int duration) {
        return AetherCookingRecipeBuilder.generic(Ingredient.of(ingredient), category, result, experience, duration, AetherRecipeSerializers.FREEZING.get())
                .unlockedBy(getHasName(result), has(result));
    }

    protected static IncubationBuilder moaIncubationRecipe(EntityType<?> entity, Supplier<? extends MoaType> moaType, ItemLike ingredient) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("IsBaby", true);
        tag.putString("MoaType", moaType.get().toString());
        tag.putBoolean("Hungry", true);
        tag.putBoolean("PlayerGrown", true);
        return IncubationBuilder.incubation(Ingredient.of(ingredient), entity, tag, 1000, AetherRecipeSerializers.INCUBATION.get())
                .unlockedBy(getHasName(ingredient), has(ingredient));
    }

    protected static BlockStateRecipeBuilder ambrosiumEnchanting(Block result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, AetherRecipeSerializers.AMBROSIUM_ENCHANTING.get());
    }

    protected static BlockStateRecipeBuilder swetBallConversion(Block result, Block ingredient) {
        return BiomeParameterRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, AetherRecipeSerializers.SWET_BALL_CONVERSION.get());
    }

    protected static BlockStateRecipeBuilder swetBallConversionTag(Block result, Block ingredient, TagKey<Biome> tagKey) {
        return BiomeParameterRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, tagKey, AetherRecipeSerializers.SWET_BALL_CONVERSION.get());
    }

    protected static BlockStateRecipeBuilder icestoneFreezable(Block result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, AetherRecipeSerializers.ICESTONE_FREEZABLE.get());
    }

    protected static BlockStateRecipeBuilder accessoryFreezable(Block result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(pair(ingredient, Map.of(BlockStateProperties.LEVEL, 0))), result, AetherRecipeSerializers.ACCESSORY_FREEZABLE.get());
    }

    protected static BlockStateRecipeBuilder convertPlacement(Block result, Block ingredient, TagKey<Biome> biome) {
        return BiomeParameterRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, biome, AetherRecipeSerializers.PLACEMENT_CONVERSION.get());
    }

    protected static BlockStateRecipeBuilder convertPlacementWithProperties(Block result, Map<Property<?>, Comparable<?>> resultProperties, Block ingredient, Map<Property<?>, Comparable<?>> ingredientProperties, TagKey<Biome> biome) {
        return BiomeParameterRecipeBuilder.recipe(BlockStateIngredient.of(pair(ingredient, ingredientProperties)), result, resultProperties, biome, AetherRecipeSerializers.PLACEMENT_CONVERSION.get());
    }

    protected static PlacementBanBuilder banItemPlacement(ItemLike ingredient, TagKey<Biome> biome) {
        return ItemBanBuilder.recipe(Ingredient.of(ingredient), BlockStateIngredient.EMPTY, biome, AetherRecipeSerializers.ITEM_PLACEMENT_BAN.get());
    }

    protected static PlacementBanBuilder banItemPlacementWithBypass(ItemLike ingredient, TagKey<Block> bypass, TagKey<Biome> biome) {
        return ItemBanBuilder.recipe(Ingredient.of(ingredient), BlockStateIngredient.of(bypass), biome, AetherRecipeSerializers.ITEM_PLACEMENT_BAN.get());
    }

    protected static PlacementBanBuilder banBlockPlacement(Block ingredient, TagKey<Biome> biome) {
        return BlockBanBuilder.recipe(BlockStateIngredient.of(ingredient), BlockStateIngredient.EMPTY, biome, AetherRecipeSerializers.BLOCK_PLACEMENT_BAN.get());
    }

    protected static PlacementBanBuilder banBlockPlacementWithBypass(Block ingredient, TagKey<Block> bypass, TagKey<Biome> biome) {
        return BlockBanBuilder.recipe(BlockStateIngredient.of(ingredient), BlockStateIngredient.of(bypass), biome, AetherRecipeSerializers.BLOCK_PLACEMENT_BAN.get());
    }

    protected static BlockPropertyPair pair(Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties) {
        return BlockPropertyPair.of(resultBlock, resultProperties);
    }
}
