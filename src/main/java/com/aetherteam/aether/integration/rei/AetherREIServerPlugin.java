package com.aetherteam.aether.integration.rei;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.integration.rei.categories.ban.PlacementBanRecipeDisplay;
import com.aetherteam.aether.integration.rei.categories.item.AetherCookingRecipeDisplay;
import com.aetherteam.aether.recipe.recipes.ban.BlockBanRecipe;
import com.aetherteam.aether.recipe.recipes.ban.ItemBanRecipe;
import com.aetherteam.aether.recipe.recipes.block.*;
import com.aetherteam.aether.recipe.recipes.item.AltarRepairRecipe;
import com.aetherteam.aether.recipe.recipes.item.EnchantingRecipe;
import com.aetherteam.aether.recipe.recipes.item.FreezingRecipe;
import com.aetherteam.aether.recipe.recipes.item.IncubationRecipe;
import com.aetherteam.nitrogen.integration.rei.displays.BlockStateRecipeDisplay;
import com.aetherteam.nitrogen.integration.rei.displays.FuelDisplay;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.forge.REIPluginCommon;
import net.minecraft.resources.ResourceLocation;

@REIPluginCommon
public class AetherREIServerPlugin implements REIServerPlugin {
    public static final CategoryIdentifier<PlacementBanRecipeDisplay<BlockBanRecipe>> BLOCK_PLACEMENT_BAN = CategoryIdentifier.of(new ResourceLocation(Aether.MODID, "block_placement_ban"));
    public static final CategoryIdentifier<PlacementBanRecipeDisplay<ItemBanRecipe>> ITEM_PLACEMENT_BAN = CategoryIdentifier.of(new ResourceLocation(Aether.MODID, "item_placement_ban"));

    public static final CategoryIdentifier<BlockStateRecipeDisplay<AccessoryFreezableRecipe>> ACCESSORY_FREEZABLE = CategoryIdentifier.of(new ResourceLocation(Aether.MODID, "accessory_freezable"));
    public static final CategoryIdentifier<BlockStateRecipeDisplay<AmbrosiumRecipe>> AMBROSIUM_ENCHANTING = CategoryIdentifier.of(new ResourceLocation(Aether.MODID, "ambrosium_enchanting"));
    public static final CategoryIdentifier<BlockStateRecipeDisplay<IcestoneFreezableRecipe>> ICESTONE_FREEZABLE = CategoryIdentifier.of(new ResourceLocation(Aether.MODID, "icestone_freezable"));

    public static final CategoryIdentifier<BlockStateRecipeDisplay<PlacementConversionRecipe>> PLACEMENT_CONVERSION = CategoryIdentifier.of(new ResourceLocation(Aether.MODID, "placement_conversion"));
    public static final CategoryIdentifier<BlockStateRecipeDisplay<SwetBallRecipe>> SWET_BALL_CONVERSION = CategoryIdentifier.of(new ResourceLocation(Aether.MODID, "swet_ball_conversion"));

    public static final CategoryIdentifier<FuelDisplay> AETHER_FUEL = CategoryIdentifier.of(new ResourceLocation(Aether.MODID, "fuel"));

    public static final CategoryIdentifier<AetherCookingRecipeDisplay<AltarRepairRecipe>> ALTAR_REPAIR = CategoryIdentifier.of(new ResourceLocation(Aether.MODID, "repairing"));
    public static final CategoryIdentifier<AetherCookingRecipeDisplay<EnchantingRecipe>> ALTAR_ENCHANTING = CategoryIdentifier.of(new ResourceLocation(Aether.MODID, "enchanting"));
    public static final CategoryIdentifier<AetherCookingRecipeDisplay<FreezingRecipe>> FREEZING = CategoryIdentifier.of(new ResourceLocation(Aether.MODID, "freezing"));
    public static final CategoryIdentifier<AetherCookingRecipeDisplay<IncubationRecipe>> INCUBATING = CategoryIdentifier.of(new ResourceLocation(Aether.MODID, "incubating"));

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(ALTAR_REPAIR, AetherCookingRecipeDisplay.serializer(ALTAR_REPAIR));
        registry.register(ALTAR_ENCHANTING, AetherCookingRecipeDisplay.serializer(ALTAR_ENCHANTING));
        registry.register(FREEZING, AetherCookingRecipeDisplay.serializer(FREEZING));
        registry.register(INCUBATING, AetherCookingRecipeDisplay.serializer(INCUBATING));
    }
}
