package com.gildedgames.aether.integration.crafttweaker.actions.freezable;

//import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
//import com.gildedgames.aether.common.block.natural.IcestoneBlock;
//import com.gildedgames.aether.common.block.util.IIcestoneBlock;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.material.Fluid;
//
//public class RemoveIcestoneFreezableAction implements IRuntimeAction
//{
//    private final Fluid fluid;
//    private final Block block;
//
//    public RemoveIcestoneFreezableAction(Fluid fluid, Block block) {
//        this.fluid = fluid;
//        this.block = block;
//    }
//
//    @Override
//    public void apply() {
//        IIcestoneBlock.removeFreezableFluid(() -> this.fluid, () -> this.block);
//    }
//
//    @Override
//    public String describe() {
//        return "Making fluid " + this.fluid.getRegistryName() + " no longer freezable by Icestone into " + this.block.getRegistryName();
//    }
//}
