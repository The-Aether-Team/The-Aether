package com.gildedgames.aether.common.entity.ai.navigator;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class FallPathNavigator extends GroundPathNavigator
{
    private BlockPos pathToPosition;

    public FallPathNavigator(MobEntity mobEntity, World world) {
        super(mobEntity, world);
    }

    @Override
    public Path createPath(BlockPos pos, int length) {
        this.pathToPosition = pos;
        return super.createPath(pos, length);
    }

    @Override
    public Path createPath(Entity entity, int length) {
        this.pathToPosition = entity.blockPosition();
        return super.createPath(entity, length);
    }

    @Override
    public boolean moveTo(Entity entity, double speed) {
        Path path = this.createPath(entity, 0);
        if (path != null) {
            return this.moveTo(path, speed);
        } else {
            this.pathToPosition = entity.blockPosition();
            this.speedModifier = speed;
            return true;
        }
    }

    @Override
    public void tick() {
        if (!this.isDone()) {
            ++this.tick;
            if (this.hasDelayedRecomputation) {
                this.recomputePath();
            }
            if (!this.isDone()) {
                if (this.canUpdatePath()) {
                    this.followThePath();
                } else if (this.path != null && !this.path.isDone()) {
                    Vector3d vector3d = this.getTempMobPos();
                    if (MathHelper.floor(this.mob.getX()) == MathHelper.floor(vector3d.x) && MathHelper.floor(this.mob.getY()) == MathHelper.floor(vector3d.y) && MathHelper.floor(this.mob.getZ()) == MathHelper.floor(vector3d.z)) {
                        this.path.advance();
                    }
                }
                DebugPacketSender.sendPathFindingPacket(this.level, this.mob, this.path, this.maxDistanceToWaypoint);
                if (this.path != null && !this.isDone()) {
                    Vector3d vector3d2 = this.path.getNextEntityPos(this.mob);
                    BlockPos blockpos = new BlockPos(vector3d2);
                    this.mob.getMoveControl().setWantedPosition(vector3d2.x, this.level.getBlockState(blockpos.below()).isAir() ? vector3d2.y : WalkNodeProcessor.getFloorLevel(this.level, blockpos), vector3d2.z, this.speedModifier);
                }
            }
        } else {
            if (this.pathToPosition != null) {
                if (!this.pathToPosition.closerThan(this.mob.position(), Math.max(this.mob.getBbWidth(), 1.0D)) && (!(this.mob.getY() > (double)this.pathToPosition.getY()) || !(new BlockPos(this.pathToPosition.getX(), this.mob.getY(), this.pathToPosition.getZ())).closerThan(this.mob.position(), Math.max(this.mob.getBbWidth(), 1.0D)))) {
                    this.mob.getMoveControl().setWantedPosition(this.pathToPosition.getX(), this.pathToPosition.getY(), this.pathToPosition.getZ(), this.speedModifier);
                } else {
                    this.pathToPosition = null;
                }
            }
        }
    }
}
