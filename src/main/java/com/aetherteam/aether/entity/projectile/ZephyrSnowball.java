package com.aetherteam.aether.entity.projectile;

import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.aetherteam.aether.entity.AetherEntityTypes;

import com.aetherteam.aether.mixin.mixins.common.accessor.PlayerAccessor;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.clientbound.ZephyrSnowballHitPacket;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;

public class ZephyrSnowball extends Fireball implements ItemSupplier {
	private int ticksInAir;

	public ZephyrSnowball(EntityType<? extends ZephyrSnowball> type, Level level) {
		super(type, level);
		this.setNoGravity(true);
	}

	public ZephyrSnowball(Level level, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		super(AetherEntityTypes.ZEPHYR_SNOWBALL.get(), shooter, accelX, accelY, accelZ, level);
		this.setNoGravity(true);
	}

	/**
	 * Warning for "deprecation" is suppressed because vanilla calls {@link Level#hasChunkAt(BlockPos)} just fine.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		if (!this.onGround) {
			++this.ticksInAir;
		}
		if (this.ticksInAir > 400) {
			this.discard();
		}
		if (this.level.isClientSide || (this.getOwner() == null || this.getOwner().isAlive()) && this.level.hasChunkAt(this.blockPosition())) {
			HitResult hitResult = ProjectileUtil.getHitResult(this, this::canHitEntity);
			if (hitResult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitResult)) {
				this.onHit(hitResult);
			}

			this.checkInsideBlocks();
			Vec3 vec3 = this.getDeltaMovement();
			double d0 = this.getX() + vec3.x;
			double d1 = this.getY() + vec3.y;
			double d2 = this.getZ() + vec3.z;
			ProjectileUtil.rotateTowardsMovement(this, 0.2F);
			float f = this.getInertia();
			if (this.isInWater()) {
				for (int i = 0; i < 4; ++i) {
					this.level.addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25, d1 - vec3.y * 0.25, d2 - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
				}
				f = 0.8F;
			}

			this.setDeltaMovement(vec3.add(this.xPower, this.yPower, this.zPower).scale(f));
			this.level.addParticle(this.getTrailParticle(), d0, d1 + 0.5, d2, 0.0, 0.0, 0.0);
			this.setPos(d0, d1, d2);
		} else {
			this.discard();
		}
	}

	@Override
	protected void onHit(@Nonnull HitResult result) {
		super.onHit(result);
		if (result.getType() == HitResult.Type.ENTITY) {
			Entity entity = ((EntityHitResult) result).getEntity();
			if (entity instanceof LivingEntity livingEntity && !EquipmentUtil.hasSentryBoots(livingEntity)) {
				if (livingEntity instanceof Player player && player.isBlocking()) {
					PlayerAccessor playerAccessor = (PlayerAccessor) player;
					playerAccessor.callHurtCurrentlyUsedShield(3.0F);
				} else {
					entity.setDeltaMovement(entity.getDeltaMovement().x, entity.getDeltaMovement().y + 0.5, entity.getDeltaMovement().z);
					entity.setDeltaMovement(entity.getDeltaMovement().x + (this.getDeltaMovement().x * 1.5F), entity.getDeltaMovement().y, entity.getDeltaMovement().z + (this.getDeltaMovement().z * 1.5F));
					if (livingEntity instanceof ServerPlayer player) {
						if (!this.level.isClientSide) {
							PacketRelay.sendToPlayer(AetherPacketHandler.INSTANCE, new ZephyrSnowballHitPacket(livingEntity.getId(), this.getDeltaMovement().x, this.getDeltaMovement().z), player);
						}
					}
				}
			}
		}
		this.discard();
	}

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	@Nonnull
	@Override
	protected ParticleOptions getTrailParticle() {
		return AetherParticleTypes.ZEPHYR_SNOWFLAKE.get();
	}

	@Nonnull
	@Override
	public ItemStack getItem() {
		return new ItemStack(Items.SNOWBALL);
	}

	@Nonnull
	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}