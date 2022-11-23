package com.gildedgames.aether.entity.ai.navigator;

import com.gildedgames.aether.Aether;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class AxisAlignedPathNavigation extends PathNavigation { // TODO: Set up slider path navigation
    public AxisAlignedPathNavigation(Mob pMob, Level pLevel) {
        super(pMob, pLevel);
    }

    @Override
    public void tick() {
        super.tick();
        /*if (this.path != null && this.path.targetNodes != null) {
            this.path.targetNodes.forEach(node -> Aether.LOGGER.info(String.format("Node: %d %d %d", node.x, node.y, node.z)));
        }*/
    }

    @Override
    protected PathFinder createPathFinder(int pMaxVisitedNodes) {
        this.nodeEvaluator = new AxisAlignedNodeEvaluator();
        return new PathFinder(this.nodeEvaluator, pMaxVisitedNodes);
    }

    @Override
    protected Vec3 getTempMobPos() {
        return this.mob.getBoundingBox().getCenter();
    }

    @Override
    protected boolean canUpdatePath() {
        return true;
    }


}
