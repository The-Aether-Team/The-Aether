package com.gildedgames.aether.common.entity.pathfinding;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class FallPathNavigator extends GroundPathNavigator {
	public FallPathNavigator(MobEntity p_i45875_1_, World p_i45875_2_) {
		super(p_i45875_1_, p_i45875_2_);
	}

	@Override
	protected PathFinder createPathFinder(int p_179679_1_) {
		this.nodeEvaluator = new WalkAndSwimNodeProcessor();
		this.nodeEvaluator.setCanPassDoors(true);
		return new PathFinder(this.nodeEvaluator, p_179679_1_);
	}

	@Override
	protected boolean canUpdatePath() {
		return this.canFloat() && this.isInLiquid() || !this.mob.isPassenger();
	}

	@Override
	protected Vector3d getTempMobPos() {
		return new Vector3d(this.mob.getX(), (double) this.getSurfaceY(), this.mob.getZ());
	}

	private int getSurfaceY() {
		if (this.mob.isInWater() && this.canFloat()) {
			int i = MathHelper.floor(this.mob.getY());
			Block block = this.level.getBlockState(new BlockPos(this.mob.getX(), (double) i, this.mob.getZ())).getBlock();
			int j = 0;

			while (block == Blocks.WATER) {
				++i;
				block = this.level.getBlockState(new BlockPos(this.mob.getX(), (double) i, this.mob.getZ())).getBlock();
				++j;
				if (j > 16) {
					return MathHelper.floor(this.mob.getY());
				}
			}

			return i;
		} else {
			return MathHelper.floor(this.mob.getY() + 0.5D);
		}
	}

	@Override
	public Path createPath(Entity p_75494_1_, int p_75494_2_) {
		return this.createPath(p_75494_1_.blockPosition(), p_75494_2_);
	}

	@Override
	public void tick() {
		++this.tick;
		if (this.hasDelayedRecomputation) {
			this.recomputePath();
		}

		if (!this.isDone()) {
			if (this.canUpdatePath()) {
				this.followThePath();
			} else if (this.path != null && !this.path.isDone()) {
				Vector3d vector3d = this.path.getNextEntityPos(this.mob);
				if (MathHelper.floor(this.mob.getX()) == MathHelper.floor(vector3d.x) && MathHelper.floor(this.mob.getY()) == MathHelper.floor(vector3d.y) && MathHelper.floor(this.mob.getZ()) == MathHelper.floor(vector3d.z)) {
					this.path.advance();
				}
			}

			DebugPacketSender.sendPathFindingPacket(this.level, this.mob, this.path, this.maxDistanceToWaypoint);
			if (!this.isDone()) {
				Vector3d vector3d1 = this.path.getNextEntityPos(this.mob);
				BlockPos blockpos = new BlockPos(vector3d1);
				this.mob.getMoveControl().setWantedPosition(vector3d1.x, WalkNodeProcessor.getFloorLevel(this.level, blockpos), vector3d1.z, this.speedModifier);
			}
		}
	}
}
