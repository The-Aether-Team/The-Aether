package com.gildedgames.aether.integration.crafttweaker.actions.conversion;

//import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
//import com.gildedgames.aether.common.item.materials.util.ISwetBallConversion;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.state.BlockState;
//
//public class RemoveDefaultConversionAction implements IRuntimeAction
//{
//    private final Block block;
//    private final BlockState blockState;
//
//    public RemoveDefaultConversionAction(Block block, BlockState blockState) {
//        this.block = block;
//        this.blockState = blockState;
//    }
//
//    @Override
//    public void apply() {
//        ISwetBallConversion.removeDefaultConversion(() -> this.block, () -> this.blockState);
//    }
//
//    @Override
//    public String describe() {
//        return "Making block " + this.block.getRegistryName() + " no longer convertable with Swet Balls into " + this.blockState.getBlock().getRegistryName();
//    }
//}
