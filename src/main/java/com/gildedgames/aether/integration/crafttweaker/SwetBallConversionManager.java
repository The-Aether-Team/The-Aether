package com.gildedgames.aether.integration.crafttweaker;

//import com.blamejared.crafttweaker.api.CraftTweakerAPI;
//import com.blamejared.crafttweaker.api.annotations.ZenRegister;
//import com.gildedgames.aether.integration.crafttweaker.actions.conversion.AddBiomeConversionAction;
//import com.gildedgames.aether.integration.crafttweaker.actions.conversion.AddDefaultConversionAction;
//import com.gildedgames.aether.integration.crafttweaker.actions.conversion.RemoveBiomeConversionAction;
//import com.gildedgames.aether.integration.crafttweaker.actions.conversion.RemoveDefaultConversionAction;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.resources.ResourceLocation;
//import org.openzen.zencode.java.ZenCodeType;
//
//@ZenRegister
//@ZenCodeType.Name("mods.aether.SwetBallConversion")
//public class SwetBallConversionManager
//{
//    @ZenCodeType.Method
//    public static void addDefaultConversion(Block oldBlock, BlockState newBlock) {
//        CraftTweakerAPI.apply(new AddDefaultConversionAction(oldBlock, newBlock));
//    }
//
//    @ZenCodeType.Method
//    public static void removeDefaultConversion(Block oldBlock, BlockState newBlock) {
//        CraftTweakerAPI.apply(new RemoveDefaultConversionAction(oldBlock, newBlock));
//    }
//
//    @ZenCodeType.Method
//    public static void addBiomeConversion(ResourceLocation biome, Block oldBlock, BlockState newBlock) {
//        CraftTweakerAPI.apply(new AddBiomeConversionAction(biome, oldBlock, newBlock));
//    }
//
//    @ZenCodeType.Method
//    public static void removeBiomeConversion(ResourceLocation biome, Block oldBlock, BlockState newBlock) {
//        CraftTweakerAPI.apply(new RemoveBiomeConversionAction(biome, oldBlock, newBlock));
//    }
//}
