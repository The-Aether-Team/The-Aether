package com.gildedgames.aether.common.entity.monster.dungeon;

import com.gildedgames.aether.common.entity.NotGrounded;
import com.gildedgames.aether.common.entity.ai.goal.target.MostDamageTargetGoal;
import com.gildedgames.aether.common.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.common.event.events.ValkyrieTeleportEvent;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.ExplosionParticlePacket;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.Vec2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Abstract class that holds common code for Valkyrie and ValkyrieQueen, both of which are children of this class.
 */
public abstract class AbstractValkyrie extends Monster implements NotGrounded {
    private static final EntityDataAccessor<Boolean> DATA_ENTITY_ON_GROUND_ID = SynchedEntityData.defineId(AbstractValkyrie.class, EntityDataSerializers.BOOLEAN);
    /** Increments every tick to decide when the valkyries are ready to teleport. */
    protected int teleportTimer;
    /** Goal for targeting in groups of entities */
    MostDamageTargetGoal mostDamageTargetGoal;

    public AbstractValkyrie(EntityType<? extends AbstractValkyrie> type, Level level) {
        super(type, level);
        this.moveControl = new ValkyrieMoveControl(this);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(1, new ValkyrieTeleportGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 0.65, true));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.5));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F, 8.0F));
        this.mostDamageTargetGoal = new MostDamageTargetGoal(this);
        this.targetSelector.addGoal(1, this.mostDamageTargetGoal);
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    @Nonnull
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.5);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ENTITY_ON_GROUND_ID, true);
    }

    @Override
    @Nonnull
    protected PathNavigation createNavigation(@Nonnull Level level) {
        return new ValkyriePathNavigation(this, level);
    }

    /**
     * Handles some movement logic for the valkyrie.
     */
    @Override
    public void tick() {
        super.tick();
        if (this.isOnGround()) {
            this.setEntityOnGround(true);
        }
        if (!this.onGround) {
            double fallSpeed;
            AttributeInstance gravity = this.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
            if (gravity != null) {
                fallSpeed = Math.max(gravity.getValue() * -0.625, -0.275);
            } else {
                fallSpeed = -0.275;
            }
            this.setDeltaMovement(this.getDeltaMovement().add(0, 0.055, 0));
            if (this.getDeltaMovement().y < fallSpeed) {
                this.setDeltaMovement(this.getDeltaMovement().x, fallSpeed, this.getDeltaMovement().z);
                this.setEntityOnGround(false);
            }
        }
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
     * The valkyrie will be provoked to attack the player if attacked.
     */
    @Override
    public boolean hurt(@Nonnull DamageSource source, float pDamageAmount) {
        boolean result = super.hurt(source, pDamageAmount);
        if (!this.level.isClientSide && result && source.getEntity() instanceof LivingEntity living) {
            this.mostDamageTargetGoal.addAggro(living, pDamageAmount);
        }
        return result;
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
     */
    protected boolean teleportAroundTarget(Entity target) {
        Vec2 targetVec = new Vec2((this.random.nextFloat() - 0.5F), (this.random.nextFloat() - 0.5F)).normalized();
        double x = target.getX() + targetVec.x * 7;
        double y = target.getY();
        double z = target.getZ() + targetVec.y * 7;
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

    /**
     * Sends a message to the player who interacted with the valkyrie.
     */
    protected void chatItUp(Player player, Component message) {
        player.sendMessage(message, player.getUUID());
    }

    @Override
    public boolean isEntityOnGround() {
        return this.entityData.get(DATA_ENTITY_ON_GROUND_ID);
    }

    @Override
    public void setEntityOnGround(boolean onGround) {
        this.entityData.set(DATA_ENTITY_ON_GROUND_ID, onGround);
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
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
            if (this.valkyrie.teleportAroundTarget(valkyrie.getTarget())) {
                this.valkyrie.teleportTimer = this.valkyrie.random.nextInt(40);
            } else {
                this.valkyrie.teleportTimer -= 20;
            }
        }
    }

    /**
     * When the valkyrie is in the air, it will keep advancing toward the player.
     */
    public static class ValkyrieMoveControl extends MoveControl {
        public ValkyrieMoveControl(Mob pMob) {
            super(pMob);
        }

        @Override
        public void tick() {
            super.tick();
            if (!this.mob.isOnGround()) {
                this.mob.setZza(1.5F);
            }
        }
    }

    /**
     * Allows the valkyrie to update paths when in the sky.
     */
    public static class ValkyriePathNavigation extends GroundPathNavigation {
        public ValkyriePathNavigation(Mob mob, Level level) {
            super(mob, level);
        }

        @Override
        @Nonnull
        protected PathFinder createPathFinder(int maxVisitedNodes) {
            this.nodeEvaluator = new ValkyrieNodeEvaluator();
            this.nodeEvaluator.setCanPassDoors(true);
            return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
        }

        @Override
        protected boolean canUpdatePath() {
            return true;
        }
    }

    /**
     * Allows the valkyrie to establish a path through the air while descending.
     */
    public static class ValkyrieNodeEvaluator extends FlyNodeEvaluator {

        /**
         * Returns a mapped point or creates and adds one
         */
        @Override
        @Nullable
        protected Node getNode(int pX, int pY, int pZ) {
            Node node = null;
            BlockPathTypes blockpathtypes = this.getCachedBlockPathType(pX, pY, pZ);
            float f = this.mob.getPathfindingMalus(blockpathtypes);
            if (f >= 0.0F) {
                node = this.nodes.computeIfAbsent(Node.createHash(pX, pY, pZ), (p_77332_) -> new Node(pX, pY, pZ));
                node.type = blockpathtypes;
                node.costMalus = Math.max(node.costMalus, f);
            }
            return node;
        }
    }
}
