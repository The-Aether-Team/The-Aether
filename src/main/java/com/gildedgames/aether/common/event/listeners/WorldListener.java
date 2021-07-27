package com.gildedgames.aether.common.event.listeners;

import com.gildedgames.aether.common.block.miscellaneous.AetherPortalBlock;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class WorldListener
{
//    @SubscribeEvent
//    public static void onWorldLoad(WorldEvent.Load event) {
//        for (Item item : AetherTags.Items.AETHER_PORTAL_ACTIVATION_ITEMS.getValues()) {
//            DispenserBlock.registerBehavior(item, new DefaultDispenseItemBehavior()
//            {
//                private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
//
//                @Override
//                protected ItemStack execute(IBlockSource source, ItemStack stack) {
//                    World world = source.getLevel();
//                    BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
//                    if (AetherPortalBlock.fillPortalBlocksWithoutContext(world, blockpos, stack)) {
//                        return stack;
//                    } else {
//                        return this.defaultDispenseItemBehavior.dispense(source, stack);
//                    }
//                }
//            });
//        }
//    }
}
