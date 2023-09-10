package com.aetherteam.aether.entity.monster.dungeon;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.EntityUtil;
import com.aetherteam.aether.entity.NotGrounded;
import com.aetherteam.aether.entity.ai.goal.MostDamageTargetGoal;
import com.aetherteam.aether.event.AetherEventDispatch;
import com.aetherteam.aether.event.ValkyrieTeleportEvent;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import java.util.EnumSet;

/**
 * Abstract class that holds common code for Valkyrie and ValkyrieQueen, both of which are children of this class.
 */
public abstract class AbstractValkyrie extends Monster implements NotGrounded {
    private static final EntityDataAccessor<Boolean> DATA_ENTITY_ON_GROUND_ID = SynchedEntityData.defineId(AbstractValkyrie.class, EntityDataSerializers.BOOLEAN);

    /**
     * Goal for targeting in groups of entities
     */
    private MostDamageTargetGoal mostDamageTargetGoal;
    private double lastMotionY;

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
   
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.5);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ENTITY_ON_GROUND_ID, true);
    }

    /**
     * Handles some movement logic for the Valkyrie.
     */
    @Override
    public void tick() {
        super.tick();
        if (this.isOnGround()) {
            this.setEntityOnGround(true);
        }
        double motionY = this.getDeltaMovement().y();
        if (!this.isOnGround() && Math.abs(motionY - this.lastMotionY) > 0.07 && Math.abs(motionY - this.lastMotionY) < 0.09) {
            this.setDeltaMovement(this.getDeltaMovement().add(0, 0.055, 0));
        }
    }

    /**
     * Handles some motion tracking for the Valkyrie.
     */
    @Override
    public void travel(Vec3 motion) {
        this.lastMotionY = this.getDeltaMovement().y();
        this.flyingSpeed = this.getSpeed() * 0.216F;
        super.travel(motion);
    }

    @Override
    protected void jumpFromGround() {
        super.jumpFromGround();
        this.setEntityOnGround(false);
    }

    /**
     * The Valkyrie will be provoked to attack the player if attacked.
     * @param source The {@link DamageSource}.
     * @param amount The {@link Float} amount of damage.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean result = super.hurt(source, amount);
        if (!this.getLevel().isClientSide() && result && source.getEntity() instanceof LivingEntity living) {
            this.mostDamageTargetGoal.addAggro(living, amount);
        }
        return result;
    }

    /**
     * Teleports near a target outside a specified radius.
     * @return Whether the teleportation succeeded, as a {@link Boolean}.
     */
    protected boolean teleportAroundTarget(Entity target) {
        Vec2 targetVec = new Vec2(this.getRandom().nextFloat() - 0.5F, this.getRandom().nextFloat() - 0.5F).normalized();
        double x = target.getX() + targetVec.x * 7;
        double y = target.getY();
        double z = target.getZ() + targetVec.y * 7;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(x, y, z);
        while (mutableBlockPos.getY() > this.getLevel().getMinBuildHeight() && !this.getLevel().getBlockState(mutableBlockPos).getMaterial().blocksMotion()) {
            mutableBlockPos.move(Direction.DOWN);
        }

        BlockState blockState = this.getLevel().getBlockState(mutableBlockPos);
        boolean isValidSpot = blockState.is(AetherTags.Blocks.VALKYRIE_TELEPORTABLE_ON); // Valkyries can only teleport within the Silver Dungeon.
        return isValidSpot && this.teleport(x, y, z);
    }

    /**
     * Teleports to the specified position.
     * @return Whether the teleportation succeeded, as a {@link Boolean}.
     */
    protected boolean teleport(double x, double y, double z) {
        ValkyrieTeleportEvent event = AetherEventDispatch.onValkyrieTeleport(this, x, y, z);
        if (event.isCanceled()) {
            return false;
        }
        boolean flag = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), false);
        if (flag) {
            this.spawnExplosionParticles();
        }
        return flag;
    }

    /**
     * Spawn explosion particles in {@link AbstractValkyrie#handleEntityEvent(byte)}.
     */
    public void spawnExplosionParticles() {
        if (!this.getLevel().isClientSide()) {
            this.getLevel().broadcastEntityEvent(this, (byte) 70);
        }
    }

    /**
     * Sends a message to the player who interacted with the Valkyrie.
     * @param player The interacting {@link Player}.
     * @param message The message {@link Component}.
     */
    protected void chat(Player player, Component message) {
        player.sendSystemMessage(message);
    }

    /**
     * @return Whether this entity has been set as on the ground, as a {@link Boolean} value.
     */
    @Override
    public boolean isEntityOnGround() {
        return this.getEntityData().get(DATA_ENTITY_ON_GROUND_ID);
    }

    /**
     * Sets whether this entity is on the ground.
     * @param onGround The {@link Boolean} value.
     */
    @Override
    public void setEntityOnGround(boolean onGround) {
        this.getEntityData().set(DATA_ENTITY_ON_GROUND_ID, onGround);
    }

    @Override
    protected boolean canRide(Entity vehicle) {
        return false;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 70) {
            for (int i = 0; i < 5; i++) {
                EntityUtil.spawnMovementExplosionParticles(this);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    /**
     * Goal that allows the mob to teleport to a random spot near the target to confuse them.
     */
    public static class ValkyrieTeleportGoal extends Goal {
        private final AbstractValkyrie valkyrie;
        protected int teleportTimer;

        public ValkyrieTeleportGoal(AbstractValkyrie valkyrie) {
            this.valkyrie = valkyrie;
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
            if (this.valkyrie.getTarget() != null && this.valkyrie.teleportAroundTarget(this.valkyrie.getTarget())) {
                this.teleportTimer = this.valkyrie.getRandom().nextInt(40);
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

        public LungeGoal(AbstractValkyrie valkyrie, double speedModifier) {
            this.valkyrie = valkyrie;
            this.speedModifier = speedModifier;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !this.valkyrie.isOnGround();
        }

        @Override
        public void tick() {
            LivingEntity target = this.valkyrie.getTarget();
            double motionY = this.valkyrie.getDeltaMovement().y();
            if (target != null) {
                if (motionY < 0.2 && this.valkyrie.lastMotionY >= 0.2 && this.valkyrie.distanceTo(target) <= 16) {
                    double x = target.getX() - this.valkyrie.getX();
                    double z = target.getZ() - this.valkyrie.getZ();
                    motionY -= 0.1;
                    double angle = Math.atan2(x, z);
                    this.valkyrie.setDeltaMovement(Math.sin(angle) * 0.3, motionY, Math.cos(angle) * 0.3);
                    this.valkyrie.setYRot((float) angle * Mth.RAD_TO_DEG);
                    this.flyingTicks = 8;
                }

                if (this.flyingTicks > 0) {
                    this.flyingTicks--;
                    double fallSpeed;
                    AttributeInstance gravity = this.valkyrie.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
                    if (gravity != null) {
                        fallSpeed = Math.max(gravity.getValue() * -0.625, -0.275);
                    } else {
                        fallSpeed = -0.275;
                    }
                    if (motionY < fallSpeed) {
                        this.valkyrie.setDeltaMovement(this.valkyrie.getDeltaMovement().x(), fallSpeed, this.valkyrie.getDeltaMovement().z());
                        this.valkyrie.setEntityOnGround(false);
                    }
                }
                Vec3 position = target.position();
                this.valkyrie.getMoveControl().setWantedPosition(position.x(), position.y(), position.z(), this.speedModifier);
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
}
