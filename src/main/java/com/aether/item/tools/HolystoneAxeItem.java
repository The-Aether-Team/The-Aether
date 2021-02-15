package com.aether.item.tools;

import com.aether.item.tools.abilities.IHolystoneToolItem;
import com.aether.registry.AetherItemGroups;
import com.aether.registry.AetherItemTier;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HolystoneAxeItem extends AxeItem implements IHolystoneToolItem
{
	public HolystoneAxeItem() {
		super(AetherItemTier.HOLYSTONE, 8.0F, -3.2F, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS));
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		spawnAmbrosiumDrops(worldIn, pos);
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}
}
