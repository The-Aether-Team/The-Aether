package com.gildedgames.aether.integration.crafttweaker.actions;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.gildedgames.aether.common.block.natural.IcestoneBlock;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;

public class RemoveIcestoneFreezableAction implements IRuntimeAction
{
    private final Fluid fluid;
    private final Block block;

    public RemoveIcestoneFreezableAction(Fluid fluid, Block block) {
        this.fluid = fluid;
        this.block = block;
    }

    @Override
    public void apply() {
        IcestoneBlock.removeFreezableFluid(() -> this.fluid, () -> this.block);
    }

    @Override
    public String describe() {
        return "Making fluid " + this.fluid.getRegistryName() + " no longer freezable by Icestone into " + this.block.getRegistryName();
    }
}
