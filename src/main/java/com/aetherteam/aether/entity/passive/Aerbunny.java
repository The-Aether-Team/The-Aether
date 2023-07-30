package com.aetherteam.aether.entity.passive;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.EntityUtil;
import com.aetherteam.aether.entity.ai.goal.FallingRandomStrollGoal;
import com.aetherteam.aether.mixin.mixins.common.accessor.ServerGamePacketListenerImplAccessor;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.serverbound.AerbunnyPuffPacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class Aerbunny extends AetherAnimal {
    private static final EntityDataAccessor<Integer> DATA_PUFFINESS_ID = SynchedEntityData.defineId(Aerbunny.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_FAST_FALLING = SynchedEntityData.defineId(Aerbunny.class, EntityDataSerializers.BOOLEAN);

    private static final int MAXIMUM_PUFFS = 11;

    private int puffSubtract;
    private boolean afraid;
    private Vec3 lastPos;

    public Aerbunny(EntityType<? extends Aerbunny> type, Level level) {
        super(type, level);
        this.moveControl = new AerbunnyMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RunWhenAfraid(this, 1.3));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2, Ingredient.of(AetherTags.Items.AERBUNNY_TEMPTATION_ITEMS), false));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new FallingRandomStrollGoal(this, 1.0, 80));
    }
   
    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0)
                .add(Attributes.MOVEMENT_SPEED, 0.28);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_PUFFINESS_ID, 0);
        this.getEntityData().define(DATA_FAST_FALLING, false);
    }

    /**
     * Handles slow-falling, puffiness tracking, and other mount behavior.
     */
    @Override
    public void tick() {
        super.tick();
        if (!this.isFastFalling()) { // Handle slow-falling unless the Aerbunny is set to fall fast.
            this.handleFallSpeed();
        } else if (this.isOnGround()) {
            this.setFastFalling(false);
        }
        this.setPuffiness(this.getPuffiness() - this.puffSubtract);
        if (this.getPuffiness() > 0) {
            this.puffSubtract = 1;
        } else {
            this.puffSubtract = 0;
            this.setPuffiness(0);
        }
        this.handlePlayerInput();
        if (this.getVehicle() != null && (this.getVehicle().isOnGround() || this.getVehicle().isInFluidType())) { // Reset the last tracked fall position if the Aerbunny touches a surface.
            this.lastPos = null;
        }
    }

    /**
     * Makes this entity fall slowly.
     */
    private void handleFallSpeed() {
        AttributeInstance gravity = this.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
        if (gravity != null) {
            double fallSpeed = Math.max(gravity.getValue() * -1.25, -0.1); // Entity isn't allowed to fall too slowly from gravity.
            if (this.getDeltaMovement().y() < fallSpeed) {
                this.setDeltaMovement(this.getDeltaMovement().x(), fallSpeed, this.getDeltaMovement().z());
            }
        }
    }

    /**
     * Makes the vehicle player fall slowly, and handles the jump ability for the player.
     */
    private void handlePlayerInput() {
        if (this.getVehicle() instanceof Player player) {
            if (player.isSpectator()) {
                this.stopRiding();
            }

            EntityUtil.copyRotations(this, player);

            player.resetFallDistance();
            if (!player.isOnGround() && !player.isFallFlying()) {
                AttributeInstance playerGravity = player.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
                if (playerGravity != null) {
                    if (!player.getAbilities().flying && !player.isInFluidType() && playerGravity.getValue() > 0.02) {  // Entity isn't allowed to fall too slowly from gravity.
                        player.setDeltaMovement(player.getDeltaMovement().add(0.0, 0.05, 0.0));
                    }
                }
                AetherPlayer.get(player).ifPresent(aetherPlayer -> {
                    Player innerPlayer = aetherPlayer.getPlayer();
                    if (this.getLevel().isClientSide()) {
                        if (innerPlayer.getDeltaMovement().y() <= 0.0) {
                            if (this.lastPos == null) { // Tracks the last position when the player starts falling.
                                this.lastPos = this.position();
                            }
                            // The player is only able to jump if the Aerbunny's position is below the last tracked falling position, to avoid infinite jump exploits.
                            if (!innerPlayer.isOnGround() && aetherPlayer.isJumping() && innerPlayer.getDeltaMovement().y() <= 0.0 && this.position().y() < this.lastPos.y() - 1.1) {
                                innerPlayer.setDeltaMovement(innerPlayer.getDeltaMovement().x(), 0.125, innerPlayer.getDeltaMovement().z());
                                PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new AerbunnyPuffPacket(this.getId())); // Calls Aerbunny#puff() on the server.
                                this.spawnExplosionParticle();
                                this.lastPos = null;
                            }
                        }
                    }
                });
            } else if (player.isFallFlying()) { // Dismount when wearing Elytra.
                this.stopRiding();
            }
            if (player instanceof ServerPlayer serverPlayer) { // Prevents the player from being kicked for flying.
                ServerGamePacketListenerImplAccessor serverGamePacketListenerImplAccessor = (ServerGamePacketListenerImplAccessor) serverPlayer.connection;
                serverGamePacketListenerImplAccessor.aether$setAboveGroundTickCount(0);
            }
        }
    }

    /**
     * Dismounts the Aerbunny when in water.
     */
    @Override
    public void baseTick() {
        super.baseTick();
        if (this.isAlive() && this.isPassenger() && this.getVehicle() != null && this.getVehicle().isEyeInFluidType(ForgeMod.WATER_TYPE.get())
                && !this.getLevel().getBlockState(BlockPos.containing(this.getVehicle().getX(), this.getVehicle().getEyeY(), this.getVehicle().getZ())).is(Blocks.BUBBLE_COLUMN)) {
            this.stopRiding();
        }
    }

    /**
     * Handles right-clicking the Aerbunny for mounting and dismounting.
     * @param player The interacting {@link Player}.
     * @param hand The {@link InteractionHand}.
     * @return The {@link InteractionResult}.
     */
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        InteractionResult result = super.mobInteract(player, hand);
        if (!(this.getVehicle() instanceof Player vehicle) || vehicle.equals(player)) { // Interacting player has to be the one wearing the Aerbunny.
            // Aerbunny can be mounted/dismounted if the shift key is held or no other interaction actions succeed, but only if the Aerbunny is not inside a block.
            if ((player.isShiftKeyDown() || result == InteractionResult.PASS || result == InteractionResult.FAIL) && !super.isInWall()) {
                return this.ridePlayer(player);
            }
        }
        return result;
    }

    /**
     * Method used for both mounting and dismounting the Aerbunny to a vehicle player.
     * @param player The {@link Player}.
     * @return The {@link InteractionResult}.
     */
    private InteractionResult ridePlayer(Player player) {
        if (!this.isBaby()) {
            if (this.isPassenger()) { // Dismount segment.
                this.stopRiding();
                this.setFastFalling(true); // Aerbunny will fall fast when dismounted.
                Vec3 playerMovement = player.getDeltaMovement();
                this.setDeltaMovement(playerMovement.x() * 5, playerMovement.y() * 0.5 + 0.5, playerMovement.z() * 5);
            } else if (this.startRiding(player)) { // Mount segment.
                AetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setMountedAerbunny(this));
                this.getLevel().playSound(player, this, AetherSoundEvents.ENTITY_AERBUNNY_LIFT.get(), SoundSource.NEUTRAL, 1.0F, (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    /**
     * Stop tracking mounted Aerbunny with {@link com.aetherteam.aether.capability.player.AetherPlayerCapability}.
     */
    @Override
    public void stopRiding() {
        if (this.getVehicle() instanceof Player player) {
            AetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setMountedAerbunny(null));
        }
        super.stopRiding();
    }

    /**
     * Sets the Aerbunny as afraid when hit by a player.
     * @param source The {@link DamageSource}.
     * @param amount The damage amount, as a {@link Float}.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean flag = super.hurt(source, amount);
        if (flag && source.getEntity() instanceof Player) {
            this.setAfraid(true);
        }
        return flag;
    }

    /**
     * Handles the small hops in the air.
     */
    protected void midairJump() {
        Vec3 motion = this.getDeltaMovement();
        if (motion.y() < 0) {
            this.puff();
        }
        this.setDeltaMovement(new Vec3(motion.x(), 0.25, motion.z()));
    }

    /**
     * Sets the puffiness to the maximum amount, from {@link AerbunnyPuffPacket}.
     */
    public void puff() {
        if (this.getLevel() instanceof ServerLevel) {
            this.setPuffiness(MAXIMUM_PUFFS);
        }
    }

    private void spawnExplosionParticle() {
        for (int i = 0; i < 5; i++) {
            EntityUtil.spawnMovementExplosionParticles(this);
        }
    }

    /**
     * @return The {@link Integer} value for the puffiness, used for animation.
     */
    public int getPuffiness() {
        return this.entityData.get(DATA_PUFFINESS_ID);
    }

    /**
     * Sets the puffiness value, used for animation.
     * @param puffiness The {@link Integer} value.
     */
    public void setPuffiness(int puffiness) {
        this.entityData.set(DATA_PUFFINESS_ID, puffiness);
    }

    /**
     * @return The {@link Boolean} value for whether the Aerbunny is falling fast.
     */
    public boolean isFastFalling() {
        return this.entityData.get(DATA_FAST_FALLING);
    }

    /**
     * Sets whether the Aerbunny is falling fast.
     * @param fastFalling The {@link Boolean} value.
     */
    public void setFastFalling(boolean fastFalling) {
        this.entityData.set(DATA_FAST_FALLING, fastFalling);
    }

    /**
     * @return The {@link Integer} amount to subtract from puffiness in animation.
     */
    public int getPuffSubtract() {
        return this.puffSubtract;
    }

    /**
     * @return The {@link Boolean} value for whether the Aerbunny is afraid.
     */
    public boolean isAfraid() {
        return this.afraid;
    }

    /**
     * Sets whether the Aerbunny is afraid.
     * @param afraid The {@link Boolean} value.
     */
    public void setAfraid(boolean afraid) {
        this.afraid = afraid;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherSoundEvents.ENTITY_AERBUNNY_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_AERBUNNY_DEATH.get();
    }

    /**
     * @return A {@link Float} for the midair speed of this entity.
     */
    @Override
    protected float getFlyingSpeed() {
        return this.getSpeed() * 0.216F;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(AetherTags.Items.AERBUNNY_TEMPTATION_ITEMS);
    }

    @Override
    public boolean canRiderInteract() {
        return true;
    }

    /**
     * @return The offset {@link Double} for an Aerbunny when riding another entity.
     */
    @Override
    public double getMyRidingOffset() {
        return this.getVehicle() != null && this.getVehicle().isCrouching() ? 0.4 : 0.575;
    }

    /**
     * @return Whether the Aerbunny can be interacted with. The Aerbunny can only be interacted when it is within a certain range of the player's view vector,
     * to avoid bugs with displaying the player's crosshairs.
     */
    @Override
    public boolean isPickable() {
        if (this.getVehicle() instanceof Player player) {
            return player.getBoundingBox().expandTowards(player.getViewVector(0.0F)).contains(this.getBoundingBox().getCenter().add(0, this.getBoundingBox().getSize() / 2, 0));
        }
        return true;
    }

    /**
     * Prevents the Aerbunny from being hurt by the vehicle entity.
     * @param damageSource The {@link DamageSource}.
     * @return Whether the Aerbunny is invulnerable to the damage, as a {@link Boolean}.
     */
    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return (this.getVehicle() != null && this.getVehicle() == damageSource.getEntity()) || super.isInvulnerableTo(damageSource);
    }

    /**
     * @return A {@link Boolean} for whether the Aerbunny is checked as being in a wall, which is false when this Aerbunny is mounted to another entity.
     */
    @Override
    public boolean isInWall() {
        return !this.isPassenger() && super.isInWall();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob entity) {
        return AetherEntityTypes.AERBUNNY.get().create(level);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Afraid", this.afraid);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.afraid = tag.getBoolean("Afraid");
    }

    /**
     * Sets a position for the Aerbunny to run away to.
     */
    public static class RunWhenAfraid extends Goal {
        private final Aerbunny aerbunny;
        private final double speedModifier;

        public RunWhenAfraid(Aerbunny aerbunny, double speedModifier) {
            this.aerbunny = aerbunny;
            this.speedModifier = speedModifier;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return this.aerbunny.isAfraid();
        }

        @Override
        public boolean canContinueToUse() {
            return !this.aerbunny.getNavigation().isDone() && this.aerbunny.getRandom().nextInt(20) != 0;
        }

        @Override
        public void start() {
            LivingEntity attacker = this.aerbunny.getLevel().getNearestPlayer(this.aerbunny, 12);
            if (attacker == null) {
                return;
            }
            Vec3 position = this.aerbunny.position();
            double angle = Mth.atan2(position.x() - attacker.getX(), position.z() - attacker.getZ());
            float angleOffset = this.aerbunny.getRandom().nextFloat() * 2 - 1;
            angle += angleOffset * 0.75;
            double x = position.x() + Math.sin(angle) * 8;
            double z = position.z() + Math.cos(angle) * 8;
            boolean flag = this.aerbunny.getNavigation().moveTo(x, this.aerbunny.getY(), z, this.speedModifier);
            if (!flag) {
                this.aerbunny.getLookControl().setLookAt(attacker, 30, 30);
            }
        }

        /**
         * Spawns crying particles.
         */
        @Override
        public void tick() {
            if (this.aerbunny.getLevel() instanceof ServerLevel serverLevel) {
                if (this.aerbunny.getRandom().nextInt(4) == 0) {
                    serverLevel.sendParticles(ParticleTypes.SPLASH, this.aerbunny.getRandomX(0.5), this.aerbunny.getRandomY(), this.aerbunny.getRandomZ(0.5), 2, 0, 0, 0, 0);
                }
            }
        }
    }

    /**
     * Handles jumping movement for the Aerbunny.
     */
    public static class AerbunnyMoveControl extends MoveControl {
        private final Aerbunny aerbunny;

        public AerbunnyMoveControl(Aerbunny aerbunny) {
            super(aerbunny);
            this.aerbunny = aerbunny;
        }

        @Override
        public void tick() {
            super.tick();
            if (this.aerbunny.zza != 0) {
                if (this.aerbunny.isOnGround()) {
                    this.aerbunny.getJumpControl().jump();
                } else {
                    int x = Mth.floor(this.aerbunny.getX());
                    int y = Mth.floor(this.aerbunny.getBoundingBox().minY);
                    int z = Mth.floor(this.aerbunny.getZ());
                    if (this.checkForSurfaces(this.aerbunny.getLevel(), x, y, z)) {
                        this.aerbunny.midairJump();
                    }
                }
            }
        }

        private boolean checkForSurfaces(Level level, int x, int y, int z) {
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, y, z);
            if (level.getBlockState(pos.setY(y - 1)).isAir()) {
                return false;
            }
            return level.getBlockState(pos.setY(y + 2)).isAir() && level.getBlockState(pos.setY(y + 1)).isAir();
        }
    }
}
