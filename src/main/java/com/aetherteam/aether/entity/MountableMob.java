package com.aetherteam.aether.entity;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.mixin.mixins.common.accessor.ServerGamePacketListenerImplAccessor;
import com.aetherteam.aether.network.packet.serverbound.StepHeightPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.UUID;

/**
 * This interface has several methods for handling the movement for mounted mobs.
 */
public interface MountableMob {
    UUID MOUNT_HEIGHT_UUID = UUID.fromString("B2D5A57A-8DA5-4127-8091-14A4CCD000F1");
    UUID DEFAULT_HEIGHT_UUID = UUID.fromString("31535561-F99D-4E14-ACE7-F636EAAD6180");
    AttributeModifier STEP_HEIGHT_MODIFIER = new AttributeModifier(MOUNT_HEIGHT_UUID, "Mounted step height increase", 0.4, AttributeModifier.Operation.ADDITION);
    AttributeModifier DEFAULT_STEP_HEIGHT_MODIFIER = new AttributeModifier(DEFAULT_HEIGHT_UUID, "Default step height increase", -0.1, AttributeModifier.Operation.ADDITION);

    /**
     * Call this at the beginning of your entity's tick method to update the state of the entity.
     *
     * @param vehicle The {@link Mob} being ridden.
     */
    default void riderTick(Mob vehicle) {
        if (vehicle.getControllingPassenger() instanceof Player player) {
            if (player.getData(AetherDataAttachments.AETHER_PLAYER).isJumping() && !this.isMountJumping()) {
                this.setPlayerJumped(true);
            }
        }
    }

    /**
     * Call this from your entity's tick method.
     *
     * @param vehicle The entity being ridden.
     */
    default <T extends Mob & MountableMob> void tick(T vehicle) {
        if (vehicle.isAlive()) {
            Entity entity = vehicle.getControllingPassenger();
            if (vehicle.isVehicle() && entity instanceof LivingEntity passenger) {
                if (vehicle.getPlayerJumped() && !vehicle.isMountJumping() && vehicle.canJump()) {
                    vehicle.onJump(vehicle);
                    vehicle.setMountJumping(true);
                    vehicle.setPlayerJumped(false);
                }
                if (vehicle.onGround()) {
                    vehicle.setPlayerJumped(false);
                    vehicle.setMountJumping(false);
                }
                if (passenger instanceof ServerPlayer serverPlayer) { // Prevents the player from being kicked for flying.
                    ServerGamePacketListenerImplAccessor serverGamePacketListenerImplAccessor = (ServerGamePacketListenerImplAccessor) serverPlayer.connection;
                    serverGamePacketListenerImplAccessor.aether$setAboveGroundTickCount(0);
                    serverGamePacketListenerImplAccessor.aether$setAboveGroundVehicleTickCount(0);
                }
            }
        }
    }

    /**
     * Call this from your entity's travel method.
     *
     * @param vehicle The entity being ridden.
     * @param motion  The {@link Vec3} travel movement vector.
     */
    default <T extends Mob & MountableMob> void travel(T vehicle, Vec3 motion) {
        Entity entity = vehicle.getControllingPassenger();
        if (vehicle.isVehicle() && entity instanceof LivingEntity passenger) {
            // Handles rotations.
            vehicle.setYRot(passenger.getYRot() % 360.0F);
            vehicle.yRotO = vehicle.getYRot();
            vehicle.setXRot(passenger.getXRot() * 0.5F % 360.0F);
            vehicle.setYBodyRot(vehicle.getYRot());
            vehicle.setYHeadRot(vehicle.yBodyRot);
            // Handles movement.
            float f = passenger.xxa * 0.5F;
            float f1 = passenger.zza;
            if (f1 <= 0.0F) {
                f1 *= 0.25F;
            }
            // Handles jumping.
            if (vehicle.getPlayerJumped() && !vehicle.isMountJumping() && vehicle.canJump()) {
                double jumpStrength = vehicle.getMountJumpStrength() * this.jumpFactor();
                vehicle.setDeltaMovement(vehicle.getDeltaMovement().x(), jumpStrength, vehicle.getDeltaMovement().z());
                if (vehicle.hasEffect(MobEffects.JUMP)) {
                    MobEffectInstance jumpBoost = vehicle.getEffect(MobEffects.JUMP);
                    if (jumpBoost != null) {
                        vehicle.push(0.0, 0.1 * (jumpBoost.getAmplifier() + 1), 0.0);
                    }
                }
                vehicle.hasImpulse = true;
            }
            // Handles step height.
            AttributeInstance stepHeight = vehicle.getAttribute(NeoForgeMod.STEP_HEIGHT.value());
            if (stepHeight != null) {
                if (stepHeight.hasModifier(vehicle.getDefaultStepHeightModifier())) {
                    stepHeight.removeModifier(vehicle.getDefaultStepHeightModifier().getId());
                }
                if (!stepHeight.hasModifier(vehicle.getMountStepHeightModifier())) {
                    stepHeight.addTransientModifier(vehicle.getMountStepHeightModifier());
                }
                if (vehicle.level().isClientSide()) {
                    PacketDistributor.sendToServer(new StepHeightPacket(vehicle.getId()));
                }
            }
            // Handles movement.
            if (vehicle.isControlledByLocalInstance()) {
                vehicle.setSpeed(vehicle.getSteeringSpeed());
                this.travelWithInput(new Vec3(f, motion.y, f1));
            } else if (passenger instanceof Player) {
                vehicle.setDeltaMovement(Vec3.ZERO);
            }
            vehicle.calculateEntityAnimation(false);
        } else {
            // Handles step height.
            AttributeInstance stepHeight = vehicle.getAttribute(NeoForgeMod.STEP_HEIGHT.value());
            if (stepHeight != null) {
                if (stepHeight.hasModifier(vehicle.getMountStepHeightModifier())) {
                    stepHeight.removeModifier(vehicle.getMountStepHeightModifier().getId());
                }
                if (!stepHeight.hasModifier(vehicle.getDefaultStepHeightModifier())) {
                    stepHeight.addTransientModifier(vehicle.getDefaultStepHeightModifier());
                }
            }
            this.travelWithInput(motion);
        }
    }

    /**
     * Usually, this just calls the entity's super$travel method.
     *
     * @param motion The {@link Vec3} movement vector from input.
     */
    void travelWithInput(Vec3 motion);

    /**
     * @return Whether the player attempted to jump, as a {@link Boolean}.
     */
    boolean getPlayerJumped();

    /**
     * Sets whether the player attempted to jump.
     *
     * @param playerJumped The {@link Boolean} value.
     */
    void setPlayerJumped(boolean playerJumped);

    /**
     * @return Whether the mount can jump, as a {@link Boolean}.
     */
    boolean canJump();

    /**
     * @return A {@link Double} for the strength of the mount's jump.
     */
    double getMountJumpStrength();

    /**
     * @return Whether the mount is jumping, as a {@link Boolean}.
     */
    boolean isMountJumping();

    /**
     * Sets whether the mount is jumping.
     *
     * @param isMountJumping The {@link Boolean} value.
     */
    void setMountJumping(boolean isMountJumping);

    /**
     * @return A {@link Float} for the steering speed of the mount.
     */
    float getSteeringSpeed();

    /**
     * @return A {@link Double} for the mount's jump factor, accounting for movement-affecting blocks.
     */
    double jumpFactor();

    /**
     * Called when the mount jumps.
     *
     * @param vehicle The vehicle {@link Mob}.
     */
    default void onJump(Mob vehicle) {
        CommonHooks.onLivingJump(vehicle);
    }

    default AttributeModifier getMountStepHeightModifier() {
        return STEP_HEIGHT_MODIFIER;
    }

    default AttributeModifier getDefaultStepHeightModifier() {
        return DEFAULT_STEP_HEIGHT_MODIFIER;
    }
}
