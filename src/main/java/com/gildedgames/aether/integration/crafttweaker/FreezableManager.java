package com.gildedgames.aether.integration.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.gildedgames.aether.integration.crafttweaker.actions.freezable.AddIceAccessoryFreezableAction;
import com.gildedgames.aether.integration.crafttweaker.actions.freezable.AddIcestoneFreezableAction;
import com.gildedgames.aether.integration.crafttweaker.actions.freezable.RemoveIceAccessoryFreezableAction;
import com.gildedgames.aether.integration.crafttweaker.actions.freezable.RemoveIcestoneFreezableAction;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.aether.Freezable")
public class FreezableManager
{
    @ZenCodeType.Method
    public static void addIcestoneFreezable(IFluidStack fluidBlock, BlockState frozenBlock) {
        CraftTweakerAPI.apply(new AddIcestoneFreezableAction(fluidBlock.getFluid(), frozenBlock.getBlock()));
    }

    @ZenCodeType.Method
    public static void removeIcestoneFreezable(IFluidStack fluidBlock, BlockState frozenBlock) {
        CraftTweakerAPI.apply(new RemoveIcestoneFreezableAction(fluidBlock.getFluid(), frozenBlock.getBlock()));
    }

    @ZenCodeType.Method
    public static void addIceAccessoryFreezable(IFluidStack fluidBlock, BlockState frozenBlock) {
        CraftTweakerAPI.apply(new AddIceAccessoryFreezableAction(fluidBlock.getFluid(), frozenBlock.getBlock()));
    }

    @ZenCodeType.Method
    public static void removeIceAccessoryFreezable(IFluidStack fluidBlock, BlockState frozenBlock) {
        CraftTweakerAPI.apply(new RemoveIceAccessoryFreezableAction(fluidBlock.getFluid(), frozenBlock.getBlock()));
    }
}
