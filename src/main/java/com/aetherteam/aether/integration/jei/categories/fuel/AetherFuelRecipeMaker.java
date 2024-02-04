package com.aetherteam.aether.integration.jei.categories.fuel;

//import com.aetherteam.aether.block.AetherBlocks;
//import com.aetherteam.aether.blockentity.AltarBlockEntity;
//import com.aetherteam.aether.blockentity.FreezerBlockEntity;
//import com.aetherteam.aether.blockentity.IncubatorBlockEntity;
//import com.aetherteam.nitrogen.integration.jei.categories.fuel.FuelRecipe;
//import net.minecraft.world.item.ItemStack;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public final class AetherFuelRecipeMaker {
//    private AetherFuelRecipeMaker() { }
//
//    public static List<FuelRecipe> getFuelRecipes() {
//        List<FuelRecipe> fuelRecipes = new ArrayList<>();
//        AltarBlockEntity.getEnchantingMap().forEach((item, burnTime) -> fuelRecipes.add(new FuelRecipe(List.of(new ItemStack(item)), burnTime, AetherBlocks.ALTAR.get())));
//        FreezerBlockEntity.getFreezingMap().forEach((item, burnTime) -> fuelRecipes.add(new FuelRecipe(List.of(new ItemStack(item)), burnTime, AetherBlocks.FREEZER.get())));
//        IncubatorBlockEntity.getIncubatingMap().forEach((item, burnTime) -> fuelRecipes.add(new FuelRecipe(List.of(new ItemStack(item)), burnTime, AetherBlocks.INCUBATOR.get())));
//        return fuelRecipes;
//    }
//}
