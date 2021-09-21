package com.gildedgames.aether.common.block.dungeon;

import java.util.Random;
import java.util.function.Supplier;

import com.gildedgames.aether.client.registry.AetherSoundEvents;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TrappedBlock extends Block
{
	private final Supplier<EntityType<?>> entityTypeSupplier;
	private final Supplier<? extends BlockState> untrappedVariantSupplier;
	
	public TrappedBlock(Supplier<EntityType<?>> entityTypeSupplier, Supplier<? extends BlockState> untrappedVariantSupplier, Properties properties) {
		super(properties);
		this.entityTypeSupplier = entityTypeSupplier;
		this.untrappedVariantSupplier = untrappedVariantSupplier;
	}
	
	@Override
	public void stepOn(World world, BlockPos pos, Entity entityIn) {
		Random random = new Random();
		if (entityIn instanceof PlayerEntity) {
			world.setBlockAndUpdate(pos, untrappedVariantSupplier.get());
			if (!world.isClientSide) {
				EntityType<?> entityType = entityTypeSupplier.get();
				Entity entity = entityType.create(world);
				entity.absMoveTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, random.nextFloat() * 360.0F, 0.0F);
				if (entity instanceof MobEntity) {
					((MobEntity)entity).finalizeSpawn((ServerWorld)world, world.getCurrentDifficultyAt(entity.blockPosition()), SpawnReason.TRIGGERED, (ILivingEntityData)null, (CompoundNBT)null);
				}
				world.addFreshEntity(entity);
			}
			world.playSound(null, pos, AetherSoundEvents.BLOCK_DUNGEON_TRAP_TRIGGER.get(), SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
		}
	}
}
