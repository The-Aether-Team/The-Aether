package com.gildedgames.aether.common.entity.monster.dungeon;

import com.gildedgames.aether.common.entity.NotGrounded;
import com.gildedgames.aether.common.entity.ai.goal.target.MostDamageTargetGoal;
import com.gildedgames.aether.common.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.common.event.events.ValkyrieTeleportEvent;
import com.gildedgames.aether.common.registry.AetherItems;

import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.ExplosionParticlePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.*;
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
public class Valkyrie extends Monster implements NotGrounded, NeutralMob {
    /** Calculates wing angles. */
    public float sinage;
    /** Increments every tick to decide when the valkyries are ready to teleport. */
    private int teleportTimer;

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;

    MostDamageTargetGoal mostDamageTargetGoal;

    public Valkyrie(EntityType<? extends Valkyrie> type, Level worldIn) {
        super(type, worldIn);
        this.teleportTimer = this.getRandom().nextInt(200);
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
        super.tick();
        if (this.level.isClientSide) {
            this.handleWingSinage();
        }
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.teleportTimer++;
    }

    @Override
    public boolean isEntityOnGround() {
        return false;
    }

    @Override
    public void setEntityOnGround(boolean onGround) {}

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
            if (!this.level.isClientSide) {
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
                if (this.getTarget() == null && level.getDifficulty() != Difficulty.PEACEFUL) {
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
        if (!this.onGround)
        {
            this.sinage += 0.75F;
        }
        else
        {
            this.sinage += 0.15F;
        }

        if (this.sinage > 3.141593F * 2F)
        {
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
                this.valkyrie.teleportTimer = 400;
            } else {
                this.valkyrie.teleportTimer -= 20;
            }
        }
    }
}
