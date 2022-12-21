//package com.gildedgames.aether.integration.jei.categories.fuel;
//
//import com.google.common.base.Preconditions;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.block.Block;
//
//import java.util.Collection;
//import java.util.List;
//
//public class AetherFuelRecipe {
//    private final List<ItemStack> inputs;
//    private final int burnTime;
//    private final Block usage;
//
//    public AetherFuelRecipe(Collection<ItemStack> input, int burnTime, Block usage) {
//        Preconditions.checkArgument(burnTime > 0, "burn time must be greater than 0");
//        this.inputs = List.copyOf(input);
//        this.burnTime = burnTime;
//        this.usage = usage;
//    }
//
//    public List<ItemStack> getInput() {
//        return inputs;
//    }
//
//    public int getBurnTime() {
//        return burnTime;
//    }
//
//    public Block getUsage() {
//        return usage;
//    }
//}
