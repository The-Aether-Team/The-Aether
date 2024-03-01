package com.aetherteam.aether.integration.rei;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.blockentity.AltarBlockEntity;
import com.aetherteam.aether.blockentity.FreezerBlockEntity;
import com.aetherteam.aether.blockentity.IncubatorBlockEntity;
import com.aetherteam.aether.integration.rei.categories.ban.BlockBanRecipeCategory;
import com.aetherteam.aether.integration.rei.categories.ban.ItemBanRecipeCategory;
import com.aetherteam.aether.integration.rei.categories.ban.PlacementBanRecipeDisplay;
import com.aetherteam.aether.integration.rei.categories.block.AetherBlockStateRecipeCategory;
import com.aetherteam.aether.integration.rei.categories.block.BiomeParameterRecipeCategory;
import com.aetherteam.aether.integration.rei.categories.item.AetherCookingRecipeCategory;
import com.aetherteam.aether.integration.rei.categories.item.AetherCookingRecipeDisplay;
import com.aetherteam.aether.inventory.menu.AltarMenu;
import com.aetherteam.aether.inventory.menu.FreezerMenu;
import com.aetherteam.aether.inventory.menu.IncubatorMenu;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.recipes.ban.BlockBanRecipe;
import com.aetherteam.aether.recipe.recipes.ban.ItemBanRecipe;
import com.aetherteam.aether.recipe.recipes.block.*;
import com.aetherteam.aether.recipe.recipes.item.AltarRepairRecipe;
import com.aetherteam.aether.recipe.recipes.item.EnchantingRecipe;
import com.aetherteam.aether.recipe.recipes.item.FreezingRecipe;
import com.aetherteam.aether.recipe.recipes.item.IncubationRecipe;
import com.aetherteam.nitrogen.integration.rei.categories.fuel.AbstractFuelCategory;
import com.aetherteam.nitrogen.integration.rei.displays.BlockStateRecipeDisplay;
import com.aetherteam.nitrogen.integration.rei.displays.FuelDisplay;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.client.registry.transfer.simple.SimpleTransferHandler;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

@REIPluginClient
public class AetherREIClientPlugin implements REIClientPlugin {
    public static final ResourceLocation ALTAR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/menu/altar.png");

    public static List<FuelRecipe> getFuelRecipes() {
        List<FuelRecipe> fuelRecipes = new ArrayList<>();
        AltarBlockEntity.getEnchantingMap().forEach((item, burnTime) -> fuelRecipes.add(new FuelRecipe(List.of(new ItemStack(item)), burnTime, AetherBlocks.ALTAR.get())));
        FreezerBlockEntity.getFreezingMap().forEach((item, burnTime) -> fuelRecipes.add(new FuelRecipe(List.of(new ItemStack(item)), burnTime, AetherBlocks.FREEZER.get())));
        IncubatorBlockEntity.getIncubatingMap().forEach((item, burnTime) -> fuelRecipes.add(new FuelRecipe(List.of(new ItemStack(item)), burnTime, AetherBlocks.INCUBATOR.get())));
        return fuelRecipes;
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(ItemBanRecipe.class, AetherRecipeTypes.ITEM_PLACEMENT_BAN.get(), recipe -> PlacementBanRecipeDisplay.ofItem(recipe.value()));
        registry.registerRecipeFiller(BlockBanRecipe.class, AetherRecipeTypes.BLOCK_PLACEMENT_BAN.get(), recipe -> PlacementBanRecipeDisplay.ofBlock(recipe.value()));

        registry.registerRecipeFiller(AccessoryFreezableRecipe.class, AetherRecipeTypes.ACCESSORY_FREEZABLE.get(), recipe -> new BlockStateRecipeDisplay<>(AetherREIServerPlugin.ACCESSORY_FREEZABLE, recipe.value()));
        registry.registerRecipeFiller(AmbrosiumRecipe.class, AetherRecipeTypes.AMBROSIUM_ENCHANTING.get(), recipe -> new BlockStateRecipeDisplay<>(AetherREIServerPlugin.AMBROSIUM_ENCHANTING, recipe.value()));
        registry.registerRecipeFiller(IcestoneFreezableRecipe.class, AetherRecipeTypes.ICESTONE_FREEZABLE.get(), recipe -> new BlockStateRecipeDisplay<>(AetherREIServerPlugin.ICESTONE_FREEZABLE, recipe.value()));

        registry.registerRecipeFiller(PlacementConversionRecipe.class, AetherRecipeTypes.PLACEMENT_CONVERSION.get(), recipe -> new BlockStateRecipeDisplay<>(AetherREIServerPlugin.PLACEMENT_CONVERSION, recipe.value()));
        registry.registerRecipeFiller(SwetBallRecipe.class, AetherRecipeTypes.SWET_BALL_CONVERSION.get(), recipe -> new BlockStateRecipeDisplay<>(AetherREIServerPlugin.SWET_BALL_CONVERSION, recipe.value()));

        // Fuel
        for (var fuelRecipe : getFuelRecipes()) {
            registry.add(new FuelDisplay(AetherREIServerPlugin.AETHER_FUEL, fuelRecipe.inputItems(), fuelRecipe.burnTime(), fuelRecipe.usageBlock()));
        }

        for (var recipe : (List<?>) registry.getRecipeManager().getAllRecipesFor(AetherRecipeTypes.ENCHANTING.get())) {
            if(recipe instanceof EnchantingRecipe enchanting){
                registry.add(AetherCookingRecipeDisplay.of(AetherREIServerPlugin.ALTAR_ENCHANTING, enchanting));
            } else if(recipe instanceof AltarRepairRecipe repair){
                registry.add(AetherCookingRecipeDisplay.of(AetherREIServerPlugin.ALTAR_REPAIR, repair));
            }
        }

        registry.registerFiller((o) -> o instanceof RecipeHolder<? extends Recipe<?>> holder && holder.value() instanceof EnchantingRecipe, recipe -> AetherCookingRecipeDisplay.of(AetherREIServerPlugin.ALTAR_ENCHANTING, ((RecipeHolder<EnchantingRecipe>) recipe).value()));
        registry.registerFiller((o) -> o instanceof RecipeHolder<? extends Recipe<?>> holder && holder.value() instanceof AltarRepairRecipe, recipe -> AetherCookingRecipeDisplay.of(AetherREIServerPlugin.ALTAR_REPAIR, ((RecipeHolder<AltarRepairRecipe>) recipe).value()));
        registry.registerRecipeFiller(FreezingRecipe.class, AetherRecipeTypes.FREEZING.get(), recipe -> AetherCookingRecipeDisplay.of(AetherREIServerPlugin.FREEZING, recipe.value()));
        registry.registerRecipeFiller(IncubationRecipe.class, AetherRecipeTypes.INCUBATION.get(), recipe -> AetherCookingRecipeDisplay.of(AetherREIServerPlugin.INCUBATING, recipe.value()));
    }

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new BlockBanRecipeCategory());
        registry.add(new ItemBanRecipeCategory());

        registry.add(AetherBlockStateRecipeCategory.accessoryFreezable());
        registry.add(AetherBlockStateRecipeCategory.ambrosium());
        registry.add(AetherBlockStateRecipeCategory.icestoneFreezable());

        registry.add(BiomeParameterRecipeCategory.placementConversion());
        registry.add(BiomeParameterRecipeCategory.swetBall());

        registry.add(new AbstractFuelCategory(AetherREIServerPlugin.AETHER_FUEL, ALTAR_TEXTURE) {
            @Override
            public Component getTitle() {
                return Component.translatable("gui." + Aether.MODID + ".jei.fuel");
            }
        });

        registry.add(AetherCookingRecipeCategory.altarRepair());
        registry.add(AetherCookingRecipeCategory.altarEnchanting());
        registry.add(AetherCookingRecipeCategory.freezing());
        registry.add(AetherCookingRecipeCategory.incubating());

        registry.addWorkstations(AetherREIServerPlugin.AETHER_FUEL, EntryStacks.of(AetherBlocks.FREEZER.get()), EntryStacks.of(AetherBlocks.ALTAR.get()), EntryStacks.of(AetherBlocks.INCUBATOR.get()));

        registry.addWorkstations(AetherREIServerPlugin.FREEZING, EntryStacks.of(AetherBlocks.FREEZER.get()));
        registry.addWorkstations(AetherREIServerPlugin.ALTAR_REPAIR, EntryStacks.of(AetherBlocks.ALTAR.get()));
        registry.addWorkstations(AetherREIServerPlugin.ALTAR_ENCHANTING, EntryStacks.of(AetherBlocks.ALTAR.get()));
        registry.addWorkstations(AetherREIServerPlugin.INCUBATING, EntryStacks.of(AetherBlocks.INCUBATOR.get()));
    }

    @Override
    public void registerTransferHandlers(TransferHandlerRegistry registry) {
        registry.register(SimpleTransferHandler.create(AltarMenu.class, AetherREIServerPlugin.ALTAR_ENCHANTING, new SimpleTransferHandler.IntRange(0, 1)));
        registry.register(SimpleTransferHandler.create(AltarMenu.class, AetherREIServerPlugin.ALTAR_REPAIR, new SimpleTransferHandler.IntRange(0, 1)));
        registry.register(SimpleTransferHandler.create(FreezerMenu.class, AetherREIServerPlugin.FREEZING, new SimpleTransferHandler.IntRange(0, 1)));
        registry.register(SimpleTransferHandler.create(IncubatorMenu.class, AetherREIServerPlugin.INCUBATING, new SimpleTransferHandler.IntRange(0, 1)));
    }
}
