package com.aetherteam.aether.entity.ai.navigator;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class AxisAlignedPathNavigation extends PathNavigation {
    public AxisAlignedPathNavigation(Mob pMob, Level pLevel) {
        super(pMob, pLevel);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Nullable
    @Override
    protected Path createPath(Set<BlockPos> pTargets, int pRegionOffset, boolean pOffsetUpward, int pAccuracy, float pFollowRange) {
        Path path = super.createPath(pTargets, pRegionOffset, pOffsetUpward, pAccuracy, pFollowRange);
        if (path != null) {
            path.advance();
        }
        return path;
    }

    @Override
    protected void trimPath() {
        super.trimPath();
    }

    @Override
    protected PathFinder createPathFinder(int pMaxVisitedNodes) {
        this.nodeEvaluator = new AxisAlignedNodeEvaluator();
        return new PathFinder(this.nodeEvaluator, pMaxVisitedNodes);
    }

    @Override
    protected Vec3 getTempMobPos() {
        return this.mob.position();
    }

    @Override
    protected boolean canUpdatePath() {
        return true;
    }
}
