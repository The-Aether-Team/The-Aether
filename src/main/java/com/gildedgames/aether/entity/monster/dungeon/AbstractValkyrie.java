package com.gildedgames.aether.entity.monster.dungeon;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.entity.NotGrounded;
import com.gildedgames.aether.entity.ai.goal.target.MostDamageTargetGoal;
import com.gildedgames.aether.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.event.events.ValkyrieTeleportEvent;
import com.gildedgames.aether.mixin.mixins.common.accessor.FlyNodeEvaluatorAccessor;
import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.client.ExplosionParticlePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;

/**
 * Abstract class that holds common code for Valkyrie and ValkyrieQueen, both of which are children of this class.
 */
public abstract class AbstractValkyrie extends Monster implements NotGrounded {
    private static final EntityDataAccessor<Boolean> DATA_ENTITY_ON_GROUND_ID = SynchedEntityData.defineId(AbstractValkyrie.class, EntityDataSerializers.BOOLEAN);

    private double lastMotionY;

    /** Goal for targeting in groups of entities */
    MostDamageTargetGoal mostDamageTargetGoal;

    public AbstractValkyrie(EntityType<? extends AbstractValkyrie> type, Level level) {
        super(type, level);
        this.moveControl = new ValkyrieMoveControl(this);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(1, new ValkyrieTeleportGoal(this));
        this.goalSelector.addGoal(3, new LungeGoal(this, 0.65));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 0.65, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.5));
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
//        return new ValkyriePathNavigation(this, level);
        return super.createNavigation(level);
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

        double motionY = this.getDeltaMovement().y;

        if (!this.onGround && Math.abs(motionY - this.lastMotionY) > 0.07D && Math.abs(motionY - this.lastMotionY) < 0.09D) {
            this.setDeltaMovement(this.getDeltaMovement().add(0, 0.055, 0));
        }
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        this.lastMotionY = this.getDeltaMovement().y;
        this.flyingSpeed = this.getSpeed() * 0.21600002F;
        super.travel(pTravelVector);
    }

    /**
     * Increments the teleport timer.
     */
    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
    }

    @Override
    protected void jumpFromGround() {
        super.jumpFromGround();
        this.setEntityOnGround(false);
    }

    /*@Override
    protected float getJumpPower() {
        return 0.6F * this.getBlockJumpFactor();
    }*/

    /*@Override
    public void knockback(double pStrength, double pX, double pZ) {
        net.minecraftforge.event.entity.living.LivingKnockBackEvent event = net.minecraftforge.common.ForgeHooks.onLivingKnockBack(this, (float) pStrength, pX, pZ);
        if(event.isCanceled()) return;
        pStrength = event.getStrength();
        pX = event.getRatioX();
        pZ = event.getRatioZ();
        pStrength *= 1.0D - this.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
        if (!(pStrength <= 0.0D)) {
            this.hasImpulse = true;
            Vec3 vec3 = this.getDeltaMovement();
            Vec3 vec31 = (new Vec3(pX, 0.0D, pZ)).normalize().scale(pStrength);
            this.setDeltaMovement(vec3.x / 2.0D - vec31.x, this.onGround ? 0.4D : vec3.y, vec3.z / 2.0D - vec31.z);
        }
    }*/

    /**
     * Valkyries don't take fall damage.
     */
    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, @Nonnull DamageSource pSource) {
        return false;
    }

    @Override
    protected boolean canRide(@Nonnull Entity vehicle) {
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
        AetherPacketHandler.sendToNear(new ExplosionParticlePacket(this.getId(), 5), this.getX(), this.getY(), this.getZ(), 10.0, level.dimension());
    }

    /**
     * Teleports near a target outside of a specified radius. Returns false if it fails.
     */
    protected boolean teleportAroundTarget(Entity target) {
        Vec2 targetVec = new Vec2((this.random.nextFloat() - 0.5F), (this.random.nextFloat() - 0.5F)).normalized();
        double x = target.getX() + targetVec.x * 7;
        double y = target.getY();
        double z = target.getZ() + targetVec.y * 7;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(x, y, z);

        while(blockpos$mutableblockpos.getY() > this.level.getMinBuildHeight() && !this.level.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMotion()) {
            blockpos$mutableblockpos.move(Direction.DOWN);
        }

        BlockState blockstate = this.level.getBlockState(blockpos$mutableblockpos);
        boolean isValidSpot = blockstate.is(AetherTags.Blocks.VALKYRIE_TELEPORTABLE_ON);
        return isValidSpot && this.teleport(x, y, z);
    }

    /**
     * Teleports to the specified position. Returns false if it fails.
     */
    protected boolean teleport(double pX, double pY, double pZ) {
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
        player.sendSystemMessage(message);
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
        protected int teleportTimer;
        public ValkyrieTeleportGoal(AbstractValkyrie mob) {
            this.valkyrie = mob;
            this.teleportTimer = this.valkyrie.getRandom().nextInt(200);
        }

        @Override
        public boolean canUse() {
            return true;
        }

        @Override
        public void tick() {
            if (this.teleportTimer++ < 450) {
                return;
            }
            if (this.valkyrie.getTarget() != null && this.valkyrie.teleportAroundTarget(valkyrie.getTarget())) {
                this.teleportTimer = this.valkyrie.random.nextInt(40);
            } else {
                this.teleportTimer -= 20;
            }
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }

    /**
     * Lunge at the target when in the air.
     */
    public static class LungeGoal extends Goal {
        private final AbstractValkyrie valkyrie;
        private final double speedModifier;
        private int flyingTicks;

        public LungeGoal(AbstractValkyrie mob, double speedModifier) {
            this.valkyrie = mob;
            this.speedModifier = speedModifier;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !this.valkyrie.onGround;
        }

        @Override
        public void tick() {
            LivingEntity target = this.valkyrie.getTarget();
            double motionY = this.valkyrie.getDeltaMovement().y;
            if (target != null) {
                if (motionY < 0 && this.valkyrie.lastMotionY >= 0 && this.valkyrie.distanceTo(target) <= 16) {
                    double x = target.getX() - this.valkyrie.getX();
                    double z = target.getZ() - this.valkyrie.getZ();
                    double angle = Math.atan2(x, z);
                    this.valkyrie.setDeltaMovement(Math.sin(angle) * 0.25, motionY, Math.cos(angle) * 0.25);
                    this.valkyrie.setYRot((float) angle * 180 / Mth.PI);
                    this.flyingTicks = 10;
                }

                if (this.flyingTicks > 0) {
                    this.flyingTicks--;
                    double fallSpeed;
                    AttributeInstance gravity = this.valkyrie.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
                    if (gravity != null) {
                        fallSpeed = Math.max(gravity.getValue() * -0.625, -0.275);
                    } else {
                        fallSpeed = -0.275;
                    }
                    if (motionY < fallSpeed) {
                        this.valkyrie.setDeltaMovement(this.valkyrie.getDeltaMovement().x, fallSpeed, this.valkyrie.getDeltaMovement().z);
                        this.valkyrie.setEntityOnGround(false);
                    }
                }
                Vec3 position = target.position();
                this.valkyrie.getMoveControl().setWantedPosition(position.x, position.y, position.z, this.speedModifier);
            }
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
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
            if (this.operation == Operation.JUMPING) {
                this.operation = Operation.MOVE_TO;
            }
            super.tick();
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
        protected Vec3 getTempMobPos() {
            return super.getTempMobPos();
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
        protected Node findAcceptedNode(int pX, int pY, int pZ) {
            Node node = null;
            FlyNodeEvaluatorAccessor flyNodeEvaluatorAccessor = (FlyNodeEvaluatorAccessor) this;
            BlockPathTypes blockpathtypes = flyNodeEvaluatorAccessor.callGetCachedBlockPathType(pX, pY, pZ);
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
