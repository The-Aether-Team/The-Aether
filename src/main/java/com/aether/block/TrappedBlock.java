package com.aether.block;

import java.util.Random;
import java.util.function.Supplier;

import com.aether.util.AetherSoundEvents;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TrappedBlock extends Block {
	
	private static final Random rand = new Random();

	private final Supplier<EntityType<?>> entityTypeSupplier;
	private final Supplier<? extends BlockState> untrappedVariantSupplier;
	
	public TrappedBlock(Supplier<EntityType<?>> entityTypeSupplier, Supplier<? extends BlockState> untrappedVariantSupplier, Properties properties) {
		super(properties);
		this.entityTypeSupplier = entityTypeSupplier;
		this.untrappedVariantSupplier = untrappedVariantSupplier;
	}
	
	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof PlayerEntity) {
			world.setBlockState(pos, untrappedVariantSupplier.get());
			
			if (!world.isRemote) {
				EntityType<?> entityType = entityTypeSupplier.get();
				Entity entity = entityType.create(world);
				entity.setPositionAndRotation(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, rand.nextFloat() * 360.0F, 0.0F);
				world.addEntity(entity);
			}
			
			world.playSound(null, pos, AetherSoundEvents.BLOCK_DUNGEON_TRAP_TRIGGER, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
		}
	}

}
