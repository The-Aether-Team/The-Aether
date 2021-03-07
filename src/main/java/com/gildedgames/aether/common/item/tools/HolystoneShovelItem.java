package com.gildedgames.aether.common.item.tools;

import com.gildedgames.aether.common.item.tools.abilities.IHolystoneToolItem;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HolystoneShovelItem extends ShovelItem implements IHolystoneToolItem
{
	public HolystoneShovelItem() {
		super(AetherItemTiers.HOLYSTONE, 1.5F, -3.0F, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS));
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		spawnAmbrosiumDrops(worldIn, pos);
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}
}
