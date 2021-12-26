package com.gildedgames.aether.common.item.tools.abilities;

import java.util.Random;

import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface IHolystoneToolItem
{
	default boolean shouldDropAmbrosium(Random rand) {
		return rand.nextInt(100) <= 5;
	}

	default void spawnAmbrosiumDrops(Level world, BlockPos pos) {
		if (!world.isClientSide && this.shouldDropAmbrosium(world.random)) {
			ItemEntity itementity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(AetherItems.AMBROSIUM_SHARD.get()));
			world.addFreshEntity(itementity);
		}
	}
}
