package com.gildedgames.aether.integration.crafttweaker.actions.freezable;

//import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
//import com.gildedgames.aether.common.block.natural.IcestoneBlock;
//import com.gildedgames.aether.common.block.util.IIcestoneBlock;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.material.Fluid;
//
//public class AddIcestoneFreezableAction implements IRuntimeAction
//{
//    private final Fluid fluid;
//    private final Block block;
//
//    public AddIcestoneFreezableAction(Fluid fluid, Block block) {
//        this.fluid = fluid;
//        this.block = block;
//    }
//
//    @Override
//    public void apply() {
//        IIcestoneBlock.registerFreezableFluid(() -> this.fluid, () -> this.block);
//    }
//
//    @Override
//    public String describe() {
//        return "Making fluid " + this.fluid.getRegistryName() + " freezable by Icestone into " + this.block.getRegistryName();
//    }
//}
