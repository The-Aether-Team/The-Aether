package com.aetherteam.aether.entity.passive;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.EntityUtil;
import com.aetherteam.aether.entity.ai.goal.FallingRandomStrollGoal;
import com.aetherteam.aether.mixin.mixins.common.accessor.ServerGamePacketListenerImplAccessor;
import com.aetherteam.aether.network.packet.serverbound.AerbunnyPuffPacket;
import net.neoforged.neoforge.network.PacketDistributor;
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
import net.minecraft.world.entity.*;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.NeoForgeMod;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class Aerbunny extends AetherAnimal {
    private static final EntityDataAccessor<Integer> DATA_PUFFINESS_ID = SynchedEntityData.defineId(Aerbunny.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_AFRAID_TIME_ID = SynchedEntityData.defineId(Aerbunny.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_FAST_FALLING_ID = SynchedEntityData.defineId(Aerbunny.class, EntityDataSerializers.BOOLEAN);

    private static final int MAXIMUM_PUFFS = 11;

    private int puffSubtract;
    @Nullable
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
        super.defineSynchedData(builder);
        builder.define(DATA_PUFFINESS_ID, 0);
        builder.define(DATA_AFRAID_TIME_ID, 0);
        builder.define(DATA_FAST_FALLING_ID, false);
    }

    /**
     * Handles slow-falling, puffiness tracking, and other mount behavior.
     */
    @Override
    public void tick() {
        super.tick();
        if (!this.isFastFalling()) { // Handle slow-falling unless the Aerbunny is set to fall fast.
            this.handleFallSpeed();
        } else if (this.onGround()) {
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

        boolean blockIntersection = false;
        if (this.getVehicle() != null) {
            AABB vehicleBounds = this.getVehicle().getBoundingBox();
            BlockPos minPos = BlockPos.containing(vehicleBounds.minX, vehicleBounds.minY, vehicleBounds.minZ);
            BlockPos maxPos = BlockPos.containing(vehicleBounds.maxX, vehicleBounds.maxY, vehicleBounds.maxZ);
            for (int x = minPos.getX(); x <= maxPos.getX(); x++) {
                for (int y = minPos.getY(); y <= maxPos.getY(); y++) {
                    for (int z = minPos.getZ(); z <= maxPos.getZ(); z++) {
                        BlockPos pos = BlockPos.containing(x, y, z);
                        BlockState blockState = this.level().getBlockState(pos);
                        VoxelShape shape = blockState.getShape(this.getVehicle().level(), this.getVehicle().blockPosition());
                        for (AABB aabb : shape.toAabbs()) {
                            AABB offset = aabb.move(pos);
                            if (vehicleBounds.intersects(offset)) {
                                blockIntersection = true;
                            }
                        }
                    }
                }
            }
        }

        if (this.getVehicle() != null && (this.getVehicle().onGround() || this.getVehicle().isInFluidType() || blockIntersection)) { // Reset the last tracked fall position if the Aerbunny touches a surface.
            this.lastPos = null;
        }
    }

    /**
     * Handles the length of time that the Aerbunny is afraid.
     */
    @Override
    public void aiStep() {
        super.aiStep();
        if (this.getAfraidTime() > 0) {
            this.setAfraidTime(this.getAfraidTime() - 1);
        }
    }

    /**
     * Makes this entity fall slowly.
     */
    private void handleFallSpeed() {
        AttributeInstance gravity = this.getAttribute(NeoForgeMod.ENTITY_GRAVITY.value());
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
            if (!player.onGround() && !player.isFallFlying()) {
                AttributeInstance playerGravity = player.getAttribute(NeoForgeMod.ENTITY_GRAVITY.value());
                if (playerGravity != null) {
                    if (!player.getAbilities().flying && !player.isInFluidType() && playerGravity.getValue() > 0.02) {  // Entity isn't allowed to fall too slowly from gravity.
                        player.setDeltaMovement(player.getDeltaMovement().add(0.0, 0.05, 0.0));
                    }
                }

                if (this.level().isClientSide()) {
                    var data = player.getData(AetherDataAttachments.AETHER_PLAYER);
                    if (player.getDeltaMovement().y() <= 0.0) {
                        if (this.lastPos == null) { // Tracks the last position when the player starts falling.
                            this.lastPos = this.position();
                        }
                        // The player is only able to jump if the Aerbunny's position is below the last tracked falling position, to avoid infinite jump exploits.
                        if (!player.onGround() && data.isJumping() && player.getDeltaMovement().y() <= 0.0 && this.position().y() < this.lastPos.y() - 1.1) {
                            player.setDeltaMovement(player.getDeltaMovement().x(), 0.125, player.getDeltaMovement().z());
                            PacketDistributor.sendToServer(new AerbunnyPuffPacket(this.getId())); // Calls Aerbunny#puff() on the server.
                            this.spawnExplosionParticle();
                            this.lastPos = null;
                        }
                    }
                }
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
        if (this.isAlive() && this.isPassenger() && this.getVehicle() != null && this.getVehicle().isEyeInFluidType(NeoForgeMod.WATER_TYPE.value())
                && !this.level().getBlockState(BlockPos.containing(this.getVehicle().getX(), this.getVehicle().getEyeY(), this.getVehicle().getZ())).is(Blocks.BUBBLE_COLUMN)) {
            this.stopRiding();
        }
    }

    /**
     * Handles right-clicking the Aerbunny for mounting and dismounting.
     *
     * @param player The interacting {@link Player}.
     * @param hand   The {@link InteractionHand}.
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
     *
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
                player.getData(AetherDataAttachments.AETHER_PLAYER).setMountedAerbunny(this);
                this.level().playSound(player, this, AetherSoundEvents.ENTITY_AERBUNNY_LIFT.get(), SoundSource.NEUTRAL, 1.0F, (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    /**
     * Stop tracking mounted Aerbunny with {@link AetherPlayerAttachment}.
     */
    @Override
    public void stopRiding() {
        if (this.getVehicle() instanceof Player player) {
            player.getData(AetherDataAttachments.AETHER_PLAYER).setMountedAerbunny(null);
        }
        super.stopRiding();
    }

    /**
     * Sets the Aerbunny as afraid when hit by a player.
     *
     * @param source The {@link DamageSource}.
     * @param amount The damage amount, as a {@link Float}.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean flag = super.hurt(source, amount);
        if (flag && source.getEntity() instanceof Player) {
            this.setAfraidTime(100 + this.getRandom().nextInt(50));
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
            this.level().broadcastEntityEvent(this, (byte) 70);
        }
        this.setDeltaMovement(new Vec3(motion.x(), 0.25, motion.z()));
    }

    /**
     * Sets the puffiness to the maximum amount, from {@link AerbunnyPuffPacket}.
     */
    public void puff() {
        if (this.level() instanceof ServerLevel) {
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
     *
     * @param puffiness The {@link Integer} value.
     */
    public void setPuffiness(int puffiness) {
        this.entityData.set(DATA_PUFFINESS_ID, puffiness);
    }

    /**
     * @return The {@link Integer} value for how long the Aerbunny should be afraid for.
     */
    public int getAfraidTime() {
        return this.entityData.get(DATA_AFRAID_TIME_ID);
    }

    /**
     * Sets how long the Aerbunny should be afraid for.
     *
     * @param afraidTime The {@link Integer} value.
     */
    public void setAfraidTime(int afraidTime) {
        this.entityData.set(DATA_AFRAID_TIME_ID, afraidTime);
    }

    /**
     * @return The {@link Boolean} value for whether the Aerbunny is falling fast.
     */
    public boolean isFastFalling() {
        return this.entityData.get(DATA_FAST_FALLING_ID);
    }

    /**
     * Sets whether the Aerbunny is falling fast.
     *
     * @param fastFalling The {@link Boolean} value.
     */
    public void setFastFalling(boolean fastFalling) {
        this.entityData.set(DATA_FAST_FALLING_ID, fastFalling);
    }

    /**
     * @return The {@link Integer} amount to subtract from puffiness in animation.
     */
    public int getPuffSubtract() {
        return this.puffSubtract;
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
    public float getMyRidingOffset(Entity entity) {
        return 0.125F;
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
     *
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
    public void handleEntityEvent(byte id) {
        if (id == 70) {
            this.spawnExplosionParticle();
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("AfraidTime", this.getAfraidTime());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("AfraidTime")) {
            this.setAfraidTime(tag.getInt("AfraidTime"));
        }
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
            return this.aerbunny.getAfraidTime() > 0;
        }

        @Override
        public boolean canContinueToUse() {
            return !this.aerbunny.getNavigation().isDone() && this.aerbunny.getRandom().nextInt(20) != 0;
        }

        @Override
        public void start() {
            LivingEntity attacker = this.aerbunny.level().getNearestPlayer(this.aerbunny, 12);
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
            if (this.aerbunny.level() instanceof ServerLevel serverLevel) {
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
                if (this.aerbunny.onGround()) {
                    this.aerbunny.getJumpControl().jump();
                } else {
                    int x = Mth.floor(this.aerbunny.getX());
                    int y = Mth.floor(this.aerbunny.getBoundingBox().minY);
                    int z = Mth.floor(this.aerbunny.getZ());
                    if (this.checkForSurfaces(this.aerbunny.level(), x, y, z) && !this.aerbunny.horizontalCollision) {
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
