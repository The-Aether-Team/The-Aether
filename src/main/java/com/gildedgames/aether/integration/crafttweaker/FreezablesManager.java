package com.gildedgames.aether.integration.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.gildedgames.aether.integration.crafttweaker.actions.AddIcestoneFreezableAction;
import com.gildedgames.aether.integration.crafttweaker.actions.RemoveIcestoneFreezableAction;
import net.minecraft.block.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.aether.FreezableBlocks")
public class FreezablesManager
{
    @ZenCodeType.Method
    public static void addIcestoneFreezable(IFluidStack fluidBlock, BlockState frozenBlock) {
        CraftTweakerAPI.apply(new AddIcestoneFreezableAction(fluidBlock.getFluid(), frozenBlock.getBlock()));
    }

    @ZenCodeType.Method
    public static void removeIcestoneFreezable(IFluidStack fluidBlock, BlockState frozenBlock) {
        CraftTweakerAPI.apply(new RemoveIcestoneFreezableAction(fluidBlock.getFluid(), frozenBlock.getBlock()));
    }
}
