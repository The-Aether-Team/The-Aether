package com.aetherteam.aether.integration.jei;

//import com.aetherteam.aether.Aether;
//import com.aetherteam.aether.block.AetherBlocks;
//import com.aetherteam.aether.integration.jei.categories.ban.BlockBanRecipeCategory;
//import com.aetherteam.aether.integration.jei.categories.ban.ItemBanRecipeCategory;
//import com.aetherteam.aether.integration.jei.categories.block.*;
//import com.aetherteam.aether.integration.jei.categories.fuel.AetherFuelCategory;
//import com.aetherteam.aether.integration.jei.categories.fuel.AetherFuelRecipeMaker;
//import com.aetherteam.aether.integration.jei.categories.item.AltarRepairRecipeCategory;
//import com.aetherteam.aether.integration.jei.categories.item.EnchantingRecipeCategory;
//import com.aetherteam.aether.integration.jei.categories.item.FreezingRecipeCategory;
//import com.aetherteam.aether.integration.jei.categories.item.IncubationRecipeCategory;
//import com.aetherteam.aether.item.AetherItems;
//import com.aetherteam.aether.recipe.AetherRecipeTypes;
//import com.aetherteam.aether.recipe.recipes.item.AltarRepairRecipe;
//import com.aetherteam.aether.recipe.recipes.item.EnchantingRecipe;
//import mezz.jei.api.IModPlugin;
//import mezz.jei.api.JeiPlugin;
//import mezz.jei.api.registration.IRecipeCatalystRegistration;
//import mezz.jei.api.registration.IRecipeCategoryRegistration;
//import mezz.jei.api.registration.IRecipeRegistration;
//import net.minecraft.client.Minecraft;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.item.crafting.RecipeManager;
//import net.minecraft.world.level.block.Blocks;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//@JeiPlugin
//public class AetherJEIPlugin implements IModPlugin {
//    @Override
//    public ResourceLocation getPluginUid() {
//        return new ResourceLocation(Aether.MODID, "jei");
//    }
//
//    @Override
//    public void registerCategories(IRecipeCategoryRegistration registration) {
//        // Item
//        registration.addRecipeCategories(new EnchantingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
//        registration.addRecipeCategories(new AltarRepairRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
//        registration.addRecipeCategories(new FreezingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
//        registration.addRecipeCategories(new IncubationRecipeCategory((registration.getJeiHelpers().getGuiHelper())));
//
//        // Fuel
//        registration.addRecipeCategories(new AetherFuelCategory(registration.getJeiHelpers().getGuiHelper()));
//
//        // Block
//        registration.addRecipeCategories(new AmbrosiumRecipeCategory(registration.getJeiHelpers().getGuiHelper(), registration.getJeiHelpers().getPlatformFluidHelper()));
//        registration.addRecipeCategories(new SwetBallRecipeCategory(registration.getJeiHelpers().getGuiHelper(), registration.getJeiHelpers().getPlatformFluidHelper()));
//        registration.addRecipeCategories(new IcestoneFreezableRecipeCategory(registration.getJeiHelpers().getGuiHelper(), registration.getJeiHelpers().getPlatformFluidHelper()));
//        registration.addRecipeCategories(new AccessoryFreezableRecipeCategory(registration.getJeiHelpers().getGuiHelper(), registration.getJeiHelpers().getPlatformFluidHelper()));
//        registration.addRecipeCategories(new PlacementConversionRecipeCategory(registration.getJeiHelpers().getGuiHelper(), registration.getJeiHelpers().getPlatformFluidHelper()));
//        registration.addRecipeCategories(new ItemBanRecipeCategory(registration.getJeiHelpers().getGuiHelper(), registration.getJeiHelpers().getPlatformFluidHelper()));
//        registration.addRecipeCategories(new BlockBanRecipeCategory(registration.getJeiHelpers().getGuiHelper(), registration.getJeiHelpers().getPlatformFluidHelper()));
//    }
//
//    @Override
//    public void registerRecipes(IRecipeRegistration registration) {
//        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
//
//        // Item
//        List<?> unfilteredRecipes = rm.getAllRecipesFor(AetherRecipeTypes.ENCHANTING.get());
//        List<EnchantingRecipe> enchantingRecipes = new ArrayList<>();
//        List<AltarRepairRecipe> repairRecipes = new ArrayList<>();
//        unfilteredRecipes.stream().filter(recipe -> recipe instanceof EnchantingRecipe).forEach(recipe -> enchantingRecipes.add((EnchantingRecipe) recipe));
//        unfilteredRecipes.stream().filter(recipe -> recipe instanceof AltarRepairRecipe).forEach(recipe -> repairRecipes.add((AltarRepairRecipe) recipe));
//        registration.addRecipes(EnchantingRecipeCategory.RECIPE_TYPE, enchantingRecipes);
//        registration.addRecipes(AltarRepairRecipeCategory.RECIPE_TYPE, repairRecipes);
//        registration.addRecipes(FreezingRecipeCategory.RECIPE_TYPE, rm.getAllRecipesFor(AetherRecipeTypes.FREEZING.get()));
//        registration.addRecipes(IncubationRecipeCategory.RECIPE_TYPE, rm.getAllRecipesFor(AetherRecipeTypes.INCUBATION.get()));
//
//        // Fuel
//        registration.addRecipes(AetherFuelCategory.RECIPE_TYPE, AetherFuelRecipeMaker.getFuelRecipes());
//
//        // Block
//        registration.addRecipes(AmbrosiumRecipeCategory.RECIPE_TYPE, rm.getAllRecipesFor(AetherRecipeTypes.AMBROSIUM_ENCHANTING.get()));
//        registration.addRecipes(SwetBallRecipeCategory.RECIPE_TYPE, rm.getAllRecipesFor(AetherRecipeTypes.SWET_BALL_CONVERSION.get()));
//        registration.addRecipes(IcestoneFreezableRecipeCategory.RECIPE_TYPE, rm.getAllRecipesFor(AetherRecipeTypes.ICESTONE_FREEZABLE.get()));
//        registration.addRecipes(AccessoryFreezableRecipeCategory.RECIPE_TYPE, rm.getAllRecipesFor(AetherRecipeTypes.ACCESSORY_FREEZABLE.get()));
//        registration.addRecipes(PlacementConversionRecipeCategory.RECIPE_TYPE, rm.getAllRecipesFor(AetherRecipeTypes.PLACEMENT_CONVERSION.get()));
//        registration.addRecipes(ItemBanRecipeCategory.RECIPE_TYPE, rm.getAllRecipesFor(AetherRecipeTypes.ITEM_PLACEMENT_BAN.get()));
//        registration.addRecipes(BlockBanRecipeCategory.RECIPE_TYPE, rm.getAllRecipesFor(AetherRecipeTypes.BLOCK_PLACEMENT_BAN.get()));
//    }
//
//    @Override
//    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
//        // Item
//        registration.addRecipeCatalyst(new ItemStack(AetherBlocks.ALTAR.get()), EnchantingRecipeCategory.RECIPE_TYPE, AltarRepairRecipeCategory.RECIPE_TYPE, AetherFuelCategory.RECIPE_TYPE);
//        registration.addRecipeCatalyst(new ItemStack(AetherBlocks.FREEZER.get()), FreezingRecipeCategory.RECIPE_TYPE, AetherFuelCategory.RECIPE_TYPE);
//        registration.addRecipeCatalyst(new ItemStack(AetherBlocks.INCUBATOR.get()), IncubationRecipeCategory.RECIPE_TYPE, AetherFuelCategory.RECIPE_TYPE);
//
//        // Block
//        registration.addRecipeCatalyst(new ItemStack(AetherItems.AMBROSIUM_SHARD.get()), AmbrosiumRecipeCategory.RECIPE_TYPE);
//        registration.addRecipeCatalyst(new ItemStack(AetherItems.SWET_BALL.get()), SwetBallRecipeCategory.RECIPE_TYPE);
//        registration.addRecipeCatalyst(new ItemStack(AetherBlocks.ICESTONE.get()), IcestoneFreezableRecipeCategory.RECIPE_TYPE);
//        registration.addRecipeCatalyst(new ItemStack(AetherBlocks.ICESTONE_SLAB.get()), IcestoneFreezableRecipeCategory.RECIPE_TYPE);
//        registration.addRecipeCatalyst(new ItemStack(AetherBlocks.ICESTONE_STAIRS.get()), IcestoneFreezableRecipeCategory.RECIPE_TYPE);
//        registration.addRecipeCatalyst(new ItemStack(AetherBlocks.ICESTONE_WALL.get()), IcestoneFreezableRecipeCategory.RECIPE_TYPE);
//        registration.addRecipeCatalyst(new ItemStack(AetherItems.ICE_RING.get()), AccessoryFreezableRecipeCategory.RECIPE_TYPE);
//        registration.addRecipeCatalyst(new ItemStack(AetherItems.ICE_PENDANT.get()), AccessoryFreezableRecipeCategory.RECIPE_TYPE);
//        registration.addRecipeCatalyst(new ItemStack(AetherItems.AETHER_PORTAL_FRAME.get()), PlacementConversionRecipeCategory.RECIPE_TYPE);
//        registration.addRecipeCatalyst(new ItemStack(Items.FLINT_AND_STEEL), ItemBanRecipeCategory.RECIPE_TYPE);
//        registration.addRecipeCatalyst(new ItemStack(Blocks.TORCH), BlockBanRecipeCategory.RECIPE_TYPE);
//    }
//}
