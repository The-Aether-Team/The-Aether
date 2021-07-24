package com.gildedgames.aether.common.entity.pathfinding;

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

	protected PathFinder createPathFinder(int p_179679_1_) {
		this.nodeEvaluator = new WalkAndSwimNodeProcessor();
		this.nodeEvaluator.setCanPassDoors(true);
		return new PathFinder(this.nodeEvaluator, p_179679_1_);
	}

	protected boolean canUpdatePath() {
		return this.canFloat() && this.isInLiquid() || !this.mob.isPassenger();
	}

	public boolean isStableDestination(BlockPos p_188555_1_) {
		return !this.level.getBlockState(p_188555_1_.below()).isAir();
	}

	protected Vector3d getTempMobPos() {
		return this.mob.position();
	}

	public Path createPath(Entity p_75494_1_, int p_75494_2_) {
		return this.createPath(p_75494_1_.blockPosition(), p_75494_2_);
	}

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
				this.mob.getMoveControl().setWantedPosition(vector3d1.x, this.level.getBlockState(blockpos.below()).isAir() ? vector3d1.y : WalkNodeProcessor.getFloorLevel(this.level, blockpos), vector3d1.z, this.speedModifier);
			}
		}
	}
}
