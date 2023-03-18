package com.gildedgames.aether.entity;

import com.gildedgames.aether.capability.player.AetherPlayer;
import com.gildedgames.aether.mixin.mixins.common.accessor.ServerGamePacketListenerImplAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

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
     */
    default void riderTick(Mob vehicle) {
        if (vehicle.getControllingPassenger() instanceof Player player) {
            AetherPlayer.get(player).ifPresent(aetherPlayer -> {
                if (aetherPlayer.isJumping() && !this.isMountJumping()) {
                    this.setPlayerJumped(true);
                }
            });
        }
    }

    /**
     * Call this from your entity's travel method.
     */
    default <T extends Mob & MountableMob> void travel(T vehicle, Vec3 motion) {
        if (vehicle.isAlive()) {
            Entity entity = vehicle.getControllingPassenger();
            if (vehicle.isVehicle() && entity instanceof LivingEntity passenger) {
                vehicle.setYRot(passenger.getYRot() % 360.0F);
                vehicle.yRotO = vehicle.getYRot();
                vehicle.setXRot(passenger.getXRot() * 0.5F % 360.0F);
                vehicle.yBodyRot = vehicle.getYRot();
                vehicle.yHeadRot = vehicle.yBodyRot;
                float f = passenger.xxa * 0.5F;
                float f1 = passenger.zza;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                }
                if (vehicle.getPlayerJumped() && !vehicle.isMountJumping() && vehicle.canJump()) {
                    double jumpStrength = vehicle.getMountJumpStrength() * this.jumpFactor();
                    vehicle.setDeltaMovement(vehicle.getDeltaMovement().x(), jumpStrength, vehicle.getDeltaMovement().z());
                    if (vehicle.hasEffect(MobEffects.JUMP)) {
                        vehicle.push(0.0, 0.1 * (vehicle.getEffect(MobEffects.JUMP).getAmplifier() + 1), 0.0);
                    }
                    vehicle.setMountJumping(true);
                    vehicle.hasImpulse = true;
                    vehicle.setPlayerJumped(false);
                    vehicle.onJump(vehicle);
                }
                AttributeInstance stepHeight = vehicle.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
                if (stepHeight != null) {
                    if (stepHeight.hasModifier(vehicle.getDefaultStepHeightModifier())) {
                        stepHeight.removeModifier(vehicle.getDefaultStepHeightModifier());
                    }
                    if (!stepHeight.hasModifier(vehicle.getMountStepHeightModifier())) {
                        stepHeight.addTransientModifier(vehicle.getMountStepHeightModifier());
                    }
                }
                if (vehicle.isControlledByLocalInstance()) {
                    vehicle.setSpeed(vehicle.getSteeringSpeed());
                    this.travelWithInput(new Vec3(f, motion.y, f1));
                } else if (passenger instanceof Player)  {
                    vehicle.setDeltaMovement(Vec3.ZERO);
                }
                if (vehicle.isOnGround()) {
                    vehicle.setPlayerJumped(false);
                    vehicle.setMountJumping(false);
                }
                if (passenger instanceof ServerPlayer serverPlayer) {
                    ServerGamePacketListenerImplAccessor serverGamePacketListenerImplAccessor = (ServerGamePacketListenerImplAccessor) serverPlayer.connection;
                    serverGamePacketListenerImplAccessor.aether$setAboveGroundTickCount(0);
                    serverGamePacketListenerImplAccessor.aether$setAboveGroundVehicleTickCount(0);
                }
                vehicle.calculateEntityAnimation(false);
            } else {
                AttributeInstance stepHeight = vehicle.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
                if (stepHeight != null) {
                    if (stepHeight.hasModifier(vehicle.getMountStepHeightModifier())) {
                        stepHeight.removeModifier(vehicle.getMountStepHeightModifier());
                    }
                    if (!stepHeight.hasModifier(vehicle.getDefaultStepHeightModifier())) {
                        stepHeight.addTransientModifier(vehicle.getDefaultStepHeightModifier());
                    }
                }
                this.travelWithInput(motion);
            }
        }
    }

    /**
     * Usually, this just calls the entity's super$travel method.
     */
    void travelWithInput(Vec3 motion);

    boolean getPlayerJumped();

    void setPlayerJumped(boolean playerJumped);

    boolean canJump();

    double getMountJumpStrength();

    boolean isMountJumping();

    void setMountJumping(boolean isMountJumping);

    float getSteeringSpeed();

    double jumpFactor();

    default void onJump(Mob vehicle) {
        net.minecraftforge.common.ForgeHooks.onLivingJump(vehicle);
    }

    default AttributeModifier getMountStepHeightModifier() {
        return STEP_HEIGHT_MODIFIER;
    }

    default AttributeModifier getDefaultStepHeightModifier() {
        return DEFAULT_STEP_HEIGHT_MODIFIER;
    }
}