package com.gildedgames.aether.common.entity.monster.dungeon;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.NotGrounded;
import com.gildedgames.aether.common.entity.ai.goal.target.MostDamageTargetGoal;
import com.gildedgames.aether.common.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.common.event.events.ValkyrieTeleportEvent;
import com.gildedgames.aether.common.registry.AetherItems;

import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.ExplosionParticlePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

/**
 * This class holds the implementation for valkyries. Valkyries are neutral mobs that patrol the silver dungeon.
 * They won't attack unless provoked. They can teleport within the temple. They respond to the player through chat
 * messages and drop a victory medal upon their defeat.
 */
public class Valkyrie extends Monster implements NeutralMob, NotGrounded {
    private static final EntityDataAccessor<Boolean> DATA_ENTITY_ON_GROUND_ID = SynchedEntityData.defineId(Valkyrie.class, EntityDataSerializers.BOOLEAN);
    /** Calculates wing angles. */
    public float sinage;
    /** Increments every tick to decide when the valkyries are ready to teleport. */
    private int teleportTimer;
    /** Prevents the player from quickly talking to the valkyrie in succession. */
    private int chatTimer;
    /** Keeps track of the previous y motion value. */
    private double motionYo;
    /** General neutral mob necessities */
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;
    /** Goal for targeting in groups of entities */
    MostDamageTargetGoal mostDamageTargetGoal;

    public Valkyrie(EntityType<? extends Valkyrie> type, Level worldIn) {
        super(type, worldIn);
        this.teleportTimer = this.getRandom().nextInt(200);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ENTITY_ON_GROUND_ID, true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new ValkyrieTeleportGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.65, true));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.5));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F, 8.0F));
        this.mostDamageTargetGoal = new MostDamageTargetGoal(this);
        this.targetSelector.addGoal(1, this.mostDamageTargetGoal);
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    @Nonnull
    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.MAX_HEALTH, 50.0);
    }

    @Override
    public void tick() {
        this.motionYo = this.getDeltaMovement().y;
        super.tick();
        if (this.level.isClientSide) {
            this.handleWingSinage();
        }
        if (this.isOnGround()) {
            this.setEntityOnGround(true);
        }
    }

    /**
     * Handles some movement logic for the valkyrie.
     */
    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.onGround && this.getTarget() != null && this.motionYo >= 0 && this.getDeltaMovement().y < 0 && this.distanceTo(this.getTarget()) <= 16F && this.hasLineOfSight(this.getTarget())) {
            double x = this.getTarget().getX() - this.getX();
            double z = this.getTarget().getZ() - this.getZ();
            double angle = Math.atan2(x, z);
            this.setDeltaMovement(Math.sin(angle) * 0.25, this.getDeltaMovement().y, Math.cos(angle) * 0.25);
            this.setYRot((float) angle * (180F / (float) Math.PI));
        }
        if (!this.onGround && Math.abs(this.getDeltaMovement().y - this.motionYo) > 0.07D && Math.abs(this.getDeltaMovement().y - this.motionYo) < 0.09D) {
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
        if (this.chatTimer > 0) {
            this.chatTimer--;
        }
    }

    /**
     * Sends a message to the player who interacted with the valkyrie.
     */
    private void chatItUp(Player player, Component message) {
        player.sendMessage(message, player.getUUID());
    }

    /**
     * Allows the players to chat up the valkyries if they're not provoked. Players will see the chat messages only
     * if they are the one who interacted with the valkyrie.
     */
    @Override
    @Nonnull
    protected InteractionResult mobInteract(Player player, @Nonnull InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
        if (this.getTarget() == null) {
            this.lookAt(player, 180.0F, 180.0F);
            if (!this.level.isClientSide && this.chatTimer <= 0) {
                String translationId;
                if (item.getItem() == AetherItems.VICTORY_MEDAL.get()) {
                    if (item.getCount() >= 10) {
                        translationId = "gui.aether.valkyrie.dialog.medal.1";
                    } else if (item.getCount() >= 5) {
                        translationId = "gui.aether.valkyrie.dialog.medal.2";
                    } else {
                        translationId = "gui.aether.valkyrie.dialog.medal.3";
                    }
                } else {
                    translationId = "gui.aether.valkyrie.dialog." + (char) (random.nextInt(3) + '1');
                }
                this.chatItUp(player, new TranslatableComponent(translationId));
                this.chatTimer = 60;
            }
        }
        return super.mobInteract(player, hand);
    }

    /**
     * The valkyrie will be provoked to attack the player if attacked.
     * This also handles the defeat message if their health drops below 0.
     */
    @Override
    public boolean hurt(@Nonnull DamageSource source, float pDamageAmount) {
        boolean result = super.hurt(source, pDamageAmount);
        if (!this.level.isClientSide && source.getEntity() instanceof LivingEntity living) {
            if (source.getEntity() instanceof Player player) {
                if (this.getTarget() == null && level.getDifficulty() != Difficulty.PEACEFUL && this.getHealth() > 0) {
                    chatItUp(player, new TranslatableComponent("gui.aether.valkyrie.dialog.attack." + (char) (random.nextInt(3) + '1')));
                }
            }
            this.mostDamageTargetGoal.addAggro(living, pDamageAmount);
        }
        return result;
    }

    /**
     * If the valkyrie kills the player, they will speak.
     */
    @Override
    public boolean doHurtTarget(@Nonnull Entity pEntity) {
        boolean result = super.doHurtTarget(pEntity);
        if (pEntity instanceof ServerPlayer player && player.getHealth() <= 0) {
            this.chatItUp(player, new TranslatableComponent("gui.aether.valkyrie.dialog.playerdeath." + (char) (random.nextInt(3) + '1')));
        }
        return result;
    }

    /**
     * Plays the valkyrie's defeat message.
     */
    @Override
    public void die(DamageSource pCause) {
        if (pCause.getEntity() instanceof Player player) {
            this.chatItUp(player, new TranslatableComponent("gui.aether.valkyrie.dialog.defeated." + (char) (random.nextInt(3) + '1')));
        }
        this.spawnExplosionParticles();
        super.die(pCause);
    }

    /**
     * Sets the position of the wings for rendering.
     */
    private void handleWingSinage() {
        if (!this.isEntityOnGround()) {
            this.sinage += 0.75F;
        } else {
            this.sinage += 0.15F;
        }
        if (this.sinage > 3.141593F * 2F) {
            this.sinage -= (3.141593F * 2F);
        }
    }

    /**
     * Spawns explosion particles.
     */
    private void spawnExplosionParticles() {
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
    protected void jumpFromGround() {
        super.jumpFromGround();
        this.setEntityOnGround(false);
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, @Nonnull DamageSource pSource) {
        return false;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int pTime) {
        this.remainingPersistentAngerTime = pTime;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID pTarget) {
        this.persistentAngerTarget = pTarget;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource pDamageSource) {
        return AetherSoundEvents.ENTITY_VALKYRIE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_VALKYRIE_DEATH.get();
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
        private final Valkyrie valkyrie;
        public ValkyrieTeleportGoal(Valkyrie mob) {
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