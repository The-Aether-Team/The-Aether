package com.gildedgames.aether.entity.ai.navigator;

import com.gildedgames.aether.entity.monster.dungeon.boss.Slider;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

//public class SlideNavigation extends PathNavigation {
//    public SlideNavigation(Mob p_26515_, Level p_26516_) {
//        super(p_26515_, p_26516_);
//    }
//
//    @Override
//    protected PathFinder createPathFinder(int p_26531_) {
//        return null;
//    }
//
//
//    protected Vec3 getTempMobPos() {
//        return new Vec3(this.mob.getX(), this.mob.getY(), this.mob.getZ());
//    }
//
//    @Override
//    protected boolean canUpdatePath() {
//        return true;
//    }
//
//    @Nullable
//    public Path createPath(Entity p_26534_, int p_26535_) {
//        return this.createPath(ImmutableSet.of(p_26534_.blockPosition()), 2, true, false);
//    }
//
//    @Nullable
//    protected Path createPath(Set<BlockPos> pTargets, int pRegionOffset, boolean pOffsetUpward, int pAccuracy, float pRange) {
//        return this.createPath(pTargets, 2, true, false);
//    }
//
//
//    @Override
//    public void tick() {
//        ++this.tick;
//        if (this.hasDelayedRecomputation) {
//            this.recomputePath();
//        }
//
//        if (!this.isDone()) {
//            if (this.path != null && !this.path.isDone()) {
//                Vec3 vec3 = this.getTempMobPos();
//                Vec3 vec31 = this.path.getNode(this.path.getNextNodeIndex()).asVec3();
//
//                if (vec3.distanceTo(vec31) < this.mob.getDimensions(null).width) {
//                    this.path.advance();
//
//                    if (this.mob.getMoveControl() instanceof Slider.SliderMoveControl slideControl) {
//                        slideControl.recharge();
//                    }
//                }
//            }
//
//            if (!this.isDone()) {
//                Vec3 vec32 = this.path.getNextEntityPos(this.mob);
//
//                this.mob.getMoveControl().setWantedPosition(vec32.x, vec32.y, vec32.z, this.speedModifier);
//            }
//        }
//    }
//
//    protected Node createNode(BlockPos pos) {
//        Node node = new Node(pos.getX(), pos.getY(), pos.getZ());
//        node.closed = false;
//        node.cameFrom = null;
//        node.heapIdx = -1;
//
//        return node;
//    }
//
//    protected Direction getDirections(BlockPos first, BlockPos second) {
//        return Direction.fromNormal(second.subtract(first));
//    }
//
//    @Nullable
//    protected Path createPath(Set<BlockPos> targets, int steps, boolean yFirst, boolean zFirst) {
//        BlockPos last = this.mob.blockPosition();
//        List<Node> nodes = Lists.newArrayList();
//
//        nodes.add(createNode(last));
//
//        for (BlockPos pos : targets) {
//            if (yFirst) {
//                last = new BlockPos(last.getX(), pos.getY(), last.getZ());
//                nodes.add(createNode(last));
//            }
//
//            if (zFirst) {
//                last = new BlockPos(last.getX(), last.getY(), pos.getZ());
//                nodes.add(createNode(last));
//            }
//
//            last = new BlockPos(pos.getX(), last.getY(), last.getZ());
//            nodes.add(createNode(last));
//
//            if (!zFirst) {
//                last = new BlockPos(last.getX(), last.getY(), pos.getZ());
//                nodes.add(createNode(last));
//            }
//
//            if (!yFirst) {
//                last = new BlockPos(last.getX(), pos.getY(), last.getZ());
//                nodes.add(createNode(last));
//            }
//        }
//
//        return new Path(nodes, last, false);
//    }
//}
