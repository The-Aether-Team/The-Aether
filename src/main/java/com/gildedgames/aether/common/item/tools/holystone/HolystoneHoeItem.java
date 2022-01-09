package com.gildedgames.aether.common.item.tools.holystone;

import com.gildedgames.aether.common.item.tools.abilities.IHolystoneToolItem;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.HoeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class HolystoneHoeItem extends HoeItem implements IHolystoneToolItem
{
    public HolystoneHoeItem() {
        super(AetherItemTiers.HOLYSTONE, -1, -2.0F, new Item.Properties().tab(AetherItemGroups.AETHER_TOOLS));
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        spawnAmbrosiumDrops(worldIn, pos);
        return super.mineBlock(stack, worldIn, state, pos, entityLiving);
    }
}
