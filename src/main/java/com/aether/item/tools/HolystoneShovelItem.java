package com.aether.item.tools;

import com.aether.item.tools.abilities.IHolystoneToolItem;
import com.aether.registry.AetherItemGroups;
import com.aether.registry.AetherItemTier;
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
		super(AetherItemTier.HOLYSTONE, 1.5F, -3.0F, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS));
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		spawnAmbrosiumDrops(worldIn, pos);
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}
}
