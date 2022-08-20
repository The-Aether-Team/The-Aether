package com.gildedgames.aether.entity.monster.dungeon.boss;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.entity.BossMob;
import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.client.BossInfoPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;

public class Slider extends Mob implements BossMob, Enemy {
    public static final EntityDataAccessor<Boolean> DATA_AWAKE_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> DATA_CRITICAL_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Component> DATA_BOSS_NAME_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.COMPONENT);

    private final ServerBossEvent bossFight;

    public Slider(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
        this.bossFight = new ServerBossEvent(this.getBossName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
        this.bossFight.setVisible(false);
        this.xpReward = XP_REWARD_BOSS;
        this.setRot(0, 0);
        //this.moveControl = new SliderMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 5, true, false, null));
        //this.goalSelector.addGoal(2, new SliderAttackGoal(this));
        //this.goalSelector.addGoal(3, new SliderAwakeGoal(this));
    }

//    @Nonnull
//    protected PathNavigation createNavigation(@Nonnull Level level) {
//        return new SlideNavigation(this, level);
//    }

    public static AttributeSupplier.Builder createSliderAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.1D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_AWAKE_ID, false);
        this.entityData.define(DATA_CRITICAL_ID, false);
        this.entityData.define(DATA_BOSS_NAME_ID, Component.literal("Slider"));
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public boolean hurt(@Nonnull DamageSource source, float amount) {
        if (!this.level.isClientSide && source.getEntity() instanceof LivingEntity livingEntity && livingEntity.getMainHandItem().is(AetherTags.Items.SLIDER_DAMAGING_ITEMS)) {
            if (super.hurt(source, amount) && level.getDifficulty() != Difficulty.PEACEFUL && this.getHealth() > 0) {
                if (!this.isBossFight()) {
                    this.setAwake(true);
                    this.setBossFight(true);
                }
                if (this.getHealth() - amount <= this.getMaxHealth() / 2.0) {
                    this.setCritical(true);
                }
            }
        }
        return false;
    }

    /**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in order
     * to view its associated boss bar.
     */
    @Override
    public void startSeenByPlayer(@Nonnull ServerPlayer pPlayer) {
        super.startSeenByPlayer(pPlayer);
        AetherPacketHandler.sendToPlayer(new BossInfoPacket.Display(this.bossFight.getId()), pPlayer);
        this.bossFight.addPlayer(pPlayer);
    }

    /**
     * Removes the given player from the list of players tracking this entity.
     */
    @Override
    public void stopSeenByPlayer(@Nonnull ServerPlayer pPlayer) {
        super.stopSeenByPlayer(pPlayer);
        AetherPacketHandler.sendToPlayer(new BossInfoPacket.Remove(this.bossFight.getId()), pPlayer);
        this.bossFight.removePlayer(pPlayer);
    }

    @Override
    public void setCustomName(@Nullable Component pName) {
        super.setCustomName(pName);
        this.setBossName(pName);
    }

    public boolean isAwake() {
        return this.entityData.get(DATA_AWAKE_ID);
    }

    public void setAwake(boolean ready) {
        this.entityData.set(DATA_AWAKE_ID, ready);
    }

    public boolean isCritical() {
        return this.entityData.get(DATA_CRITICAL_ID);
    }

    public void setCritical(boolean ready) {
        this.entityData.set(DATA_CRITICAL_ID, ready);
    }

    @Override
    public Component getBossName() {
        return this.entityData.get(DATA_BOSS_NAME_ID);
    }

    @Override
    public void setBossName(Component component) {
        this.entityData.set(DATA_BOSS_NAME_ID, component);
        this.bossFight.setName(component);
    }

    @Override
    public boolean isBossFight() {
        return this.bossFight.isVisible();
    }

    @Override
    public void setBossFight(boolean isFighting) {
        this.bossFight.setVisible(isFighting);
    }

    public boolean canAttack(LivingEntity pTarget) {
        return pTarget.canBeSeenAsEnemy();
    }

    // Max amount of time the Slider will rest between slides
    public int getMovementPause() {
        return 30;
    }

    /**
     * Freeze Y rotation
     */
    @Override
    public void setYRot(float pYRot) {
        super.setYRot(0);
    }

    @Override
    public boolean shouldDiscardFriction() {
        return true;
    }

    @Override
    protected boolean isAffectedByFluids() {
        return false;
    }

    @Override
    protected int calculateFallDamage(float pDistance, float pDamageMultiplier) {
        return 0;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

//    public static class SliderAttackGoal extends Goal {
//        private final Slider slime;
//
//        public SliderAttackGoal(Slider p_33648_) {
//            this.slime = p_33648_;
//            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
//        }
//
//        /**
//         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
//         * method as well.
//         */
//        public boolean canUse() {
//            LivingEntity livingentity = this.slime.getTarget();
//            if (livingentity == null) {
//                return false;
//            } else {
//                return this.slime.canAttack(livingentity) && this.slime.getMoveControl() instanceof SliderMoveControl;
//            }
//        }
//
//        /**
//         * Execute a one shot task or start executing a continuous task
//         */
//        public void start() {
//            super.start();
//        }
//
//        /**
//         * Returns whether an in-progress EntityAIBase should continue executing
//         */
//        public boolean canContinueToUse() {
//            return true;
//        }
//
//        public boolean requiresUpdateEveryTick() {
//            return true;
//        }
//
//        /**
//         * Keep ticking a continuous task that has already been started
//         */
//        public void tick() {
//            LivingEntity livingentity = this.slime.getTarget();
//
////            Aether.LOGGER.info(this.slime.getNavigation().getPath());
//
//            if (this.slime.getNavigation().isDone() && livingentity != null) {
//                boolean pathCreated = this.slime.getNavigation().moveTo(livingentity, 1);
//                Path path = this.slime.getNavigation().getPath();
//
//                if (pathCreated && path != null && this.slime.getTarget() instanceof ServerPlayer player) {
//                    Node last = null;
//
//                    for (int i = 0; i < path.getNodeCount(); i++) {
//                        Node node = path.getNode(i);
//
//                        if (last != null) {
//
//                            if (node.x != last.x) {
//                                Node first = last.x < node.x ? last : node;
//                                Node second = last.x > node.x ? last : node;
//
//                                for (int x = first.x; x <= second.x; x++) {
//                                    this.slime.level.setBlock(new BlockPos(x, first.y, first.z), Blocks.DANDELION.defaultBlockState(), 3);
//                                }
//                            }
//
//                            if (node.z != last.z) {
//                                Node first = last.z < node.z ? last : node;
//                                Node second = last.z > node.z ? last : node;
//
//                                for (int z = first.z; z <= second.z; z++) {
//                                    this.slime.level.setBlock(new BlockPos(first.x, first.y, z), Blocks.POPPY.defaultBlockState(), 3);
//                                }
//                            }
//
//                            if (node.y != last.y) {
//                                Node first = last.y < node.y ? last : node;
//                                Node second = last.y > node.y ? last : node;
//
//                                for (int y = first.y; y < second.y; y++) {
//                                    this.slime.level.setBlock(new BlockPos(first.x, y, first.z), Blocks.TRIPWIRE.defaultBlockState(), 3);
//                                }
//                            }
//                        }
//
//                        last = node;
//                    }
//                }
//            }
//            //
////            ((SliderEntity.SliderMoveControl) this.slime.getMoveControl()).setDirection(this.slime.getYRot(), true);
//        }
//    }
//
//    public static class SliderAwakeGoal extends Goal {
//        private final Slider slime;
//
//        public SliderAwakeGoal(Slider p_33660_) {
//            this.slime = p_33660_;
//            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
//        }
//
//        /**
//         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
//         * method as well.
//         */
//        public boolean canUse() {
//            return true;
//        }
//
//        /**
//         * Keep ticking a continuous task that has already been started
//         */
//        public void tick() {
//            ((Slider.SliderMoveControl) this.slime.getMoveControl()).setWantedMovement(1.0D);
//        }
//    }
//
//
//    public static class SliderMoveControl extends MoveControl {
//        private int moveDelay;
//        private int energyUsed;
//        private final Slider slider;
//        private boolean isAggressive;
//
//        public SliderMoveControl(Slider slider) {
//            super(slider);
//            this.slider = slider;
//        }
//
//        public void setWantedMovement(double pSpeed) {
//            this.speedModifier = pSpeed;
//        }
//
//        public void recharge() {
//            this.moveDelay = Math.min(this.energyUsed, this.slider.getMovementPause());
//            this.operation = Operation.WAIT;
//        }
//
//        @Override
//        public void setWantedPosition(double pX, double pY, double pZ, double pSpeed) {
//            this.wantedX = pX;
//            this.wantedY = pY;
//            this.wantedZ = pZ;
//            this.speedModifier = pSpeed;
//        }
//
//        public float getAxisMovement(double cur, double target) {
//            double dist = Math.abs(cur - target);
//
//            if (dist > 0) {
//                if (dist < this.mob.getSpeed()) {
//                    return (float) (target - cur);
//                }
//
//                return cur < target ? this.mob.getSpeed() : -this.mob.getSpeed();
//            }
//
//            return 0;
//        }
//
//        public void tick() {
//            Aether.LOGGER.info(moveDelay + " " + this.operation);
//
//            if (this.operation != MoveControl.Operation.MOVE_TO) {
//                this.mob.setSpeed(0.0F);
//                this.mob.setDeltaMovement(Vec3.ZERO);
//
//                if (this.moveDelay > 0) {
//                    --this.moveDelay;
//                } else {
//                    this.operation = MoveControl.Operation.MOVE_TO;
//                }
//            } else {
//                this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
//                this.energyUsed++;
//
//                Vec3 motion = new Vec3(
//                        getAxisMovement(this.mob.getX(), this.getWantedX()),
//                        getAxisMovement(this.mob.getY(), this.getWantedY()),
//                        getAxisMovement(this.mob.getZ(), this.getWantedZ()));
//
//                this.mob.setDeltaMovement(motion);
//            }
//        }
//    }
}