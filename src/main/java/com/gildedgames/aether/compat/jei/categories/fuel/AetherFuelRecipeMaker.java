package com.gildedgames.aether.compat.jei.categories.fuel;

import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.blockentity.AltarBlockEntity;
import com.gildedgames.aether.blockentity.FreezerBlockEntity;
import com.gildedgames.aether.blockentity.IncubatorBlockEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class AetherFuelRecipeMaker {

    private AetherFuelRecipeMaker() {
    }

    public static List<AetherFuelRecipe> getFuelRecipes() {
        List<AetherFuelRecipe> fuelRecipes = new ArrayList<>();

        AltarBlockEntity.getEnchantingMap().forEach((item, burnTime) ->
                fuelRecipes.add(new AetherFuelRecipe(List.of(new ItemStack(item)), burnTime, AetherBlocks.ALTAR.get())));
        FreezerBlockEntity.getFreezingMap().forEach((item, burnTime) ->
                fuelRecipes.add(new AetherFuelRecipe(List.of(new ItemStack(item)), burnTime, AetherBlocks.FREEZER.get())));
        IncubatorBlockEntity.getIncubatingMap().forEach((item, burnTime) ->
                fuelRecipes.add(new AetherFuelRecipe(List.of(new ItemStack(item)), burnTime, AetherBlocks.INCUBATOR.get())));

        return fuelRecipes;
    }
}
