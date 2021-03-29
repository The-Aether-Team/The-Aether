package com.gildedgames.aether.integration.crafttweaker.actions;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.gildedgames.aether.common.item.accessories.abilities.IIceAccessory;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;

public class AddIceAccessoryFreezableAction implements IRuntimeAction
{
    private final Fluid fluid;
    private final Block block;

    public AddIceAccessoryFreezableAction(Fluid fluid, Block block) {
        this.fluid = fluid;
        this.block = block;
    }

    @Override
    public void apply() {
        IIceAccessory.registerFreezableFluid(() -> this.fluid, () -> this.block);
    }

    @Override
    public String describe() {
        return "Making fluid " + this.fluid.getRegistryName() + " freezable by ice accessories into " + this.block.getRegistryName();
    }
}
