package com.aether.item;

import java.util.Random;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHolystoneToolItem {
	
	default boolean shouldDropAmbrosium(Random rand) {
		return rand.nextInt(100) <= 5;
	}

	default void spawnAmbrosiumDrops(World world, BlockPos pos) {
		if (!world.isRemote && this.shouldDropAmbrosium(world.rand)) {
			ItemEntity itementity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ());
			itementity.setItem(new ItemStack(AetherItems.AMBROSIUM_SHARD));
			world.addEntity(itementity);
		}
	}
	
}
