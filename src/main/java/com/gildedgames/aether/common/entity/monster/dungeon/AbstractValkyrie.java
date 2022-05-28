package com.gildedgames.aether.common.entity.monster.dungeon;

import com.gildedgames.aether.common.entity.NotGrounded;
import com.gildedgames.aether.common.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.common.event.events.ValkyrieTeleportEvent;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.ExplosionParticlePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;

import javax.annotation.Nonnull;

/**
 * Abstract class that holds common code for Valkyrie and ValkyrieQueen, both of which are children of this class.
 */
public abstract class AbstractValkyrie extends Monster implements NotGrounded {
    private static final EntityDataAccessor<Boolean> DATA_ENTITY_ON_GROUND_ID = SynchedEntityData.defineId(AbstractValkyrie.class, EntityDataSerializers.BOOLEAN);
    /** Increments every tick to decide when the valkyries are ready to teleport. */
    protected int teleportTimer;

    public AbstractValkyrie(EntityType<? extends AbstractValkyrie> type, Level level) {
        super(type, level);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ENTITY_ON_GROUND_ID, true);
    }

    /**
     * Increments the teleport timer.
     */
    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.teleportTimer++;
    }

    @Override
    protected void jumpFromGround() {
        super.jumpFromGround();
        this.setEntityOnGround(false);
    }

    /**
     * Valkyries don't take fall damage.
     */
    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, @Nonnull DamageSource pSource) {
        return false;
    }

    /**
     * Spawns explosion particles.
     */
    public void spawnExplosionParticles() {
        for (int i = 0; i < 5; i++) {
            AetherPacketHandler.sendToAll(new ExplosionParticlePacket(this.getId()));
        }
    }

    /**
     * Teleports near a target outside of a specified radius. Returns false if it fails.
     * @param rad - An int equal to the length of the target radius from the target.
     */
    protected boolean teleportAroundTarget(Entity target, int rad) {
        Vec2 targetVec = new Vec2((this.random.nextFloat() - 0.5F), (this.random.nextFloat() - 0.5F)).normalized();
        double x = target.getX() + targetVec.x * rad;
        double y = target.getY();
        double z = target.getZ() + targetVec.y * rad;
        return this.teleport(x, y, z);
    }

    /**
     * Teleports to the specified position. Returns false if it fails.
     */
    protected boolean teleport(double pX, double pY, double pZ) {
        /*BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(pX, pY, pZ);

        while(blockpos$mutableblockpos.getY() > this.level.getMinBuildHeight() && !this.level.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMotion()) {
            blockpos$mutableblockpos.move(Direction.DOWN);
        }

        BlockState blockstate = this.level.getBlockState(blockpos$mutableblockpos);
        boolean flag = blockstate.is(AetherTags.Blocks.VALKYRIE_TELEPORTABLE_ON);*/
        //TODO: Lock teleporting to tagged blocks.
        ValkyrieTeleportEvent event = AetherEventDispatch.onValkyrieTeleport(this, pX, pY, pZ);
        if (event.isCanceled()) return false;
        boolean flag = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), false);
        if (flag) {
            this.spawnExplosionParticles();
        }
        return flag;
    }


    @Override
    public boolean isEntityOnGround() {
        return this.entityData.get(DATA_ENTITY_ON_GROUND_ID);
    }

    @Override
    public void setEntityOnGround(boolean onGround) {
        this.entityData.set(DATA_ENTITY_ON_GROUND_ID, onGround);
    }

    /**
     * Goal that allows the mob to teleport to a random spot near the target to confuse them.
     */
    public static class ValkyrieTeleportGoal extends Goal {
        private final AbstractValkyrie valkyrie;
        public ValkyrieTeleportGoal(AbstractValkyrie mob) {
            this.valkyrie = mob;
        }

        @Override
        public boolean canUse() {
            return this.valkyrie.getTarget() != null && this.valkyrie.teleportTimer >= 450;
        }

        @Override
        public void start() {
            if (this.valkyrie.teleportAroundTarget(valkyrie.getTarget(), 7)) {
                this.valkyrie.teleportTimer = this.valkyrie.random.nextInt(40);
            } else {
                this.valkyrie.teleportTimer -= 20;
            }
        }
    }
}
