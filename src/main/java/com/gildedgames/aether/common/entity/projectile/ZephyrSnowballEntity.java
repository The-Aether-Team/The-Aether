package com.gildedgames.aether.common.entity.projectile;

import com.gildedgames.aether.client.registry.AetherParticleTypes;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;

import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.ZephyrSnowballHitPacket;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class ZephyrSnowballEntity extends Fireball implements ItemSupplier {
	private int ticksInAir;

	public ZephyrSnowballEntity(EntityType<? extends ZephyrSnowballEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	public ZephyrSnowballEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		super(AetherEntityTypes.ZEPHYR_SNOWBALL.get(), shooter, accelX, accelY, accelZ, worldIn);
	}

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (result.getType() == HitResult.Type.ENTITY) {
			Entity entity = ((EntityHitResult)result).getEntity();
			if (entity instanceof LivingEntity livingEntity) {
				boolean isPlayer = livingEntity instanceof Player;

				//TODO: Was this a thing?
				if (isPlayer && ((Player)entity).getInventory().armor.get(0).getItem() == AetherItems.SENTRY_BOOTS.get()) {
					this.discard();
					return;
				}
				if (!livingEntity.isBlocking()) {
					entity.setDeltaMovement(entity.getDeltaMovement().x, entity.getDeltaMovement().y + 0.5, entity.getDeltaMovement().z);
				} else {
					ItemStack activeItemStack = livingEntity.getUseItem();
					activeItemStack.hurtAndBreak(1, livingEntity, p -> p.broadcastBreakEvent(activeItemStack.getEquipmentSlot()));

					if (activeItemStack.getCount() <= 0) {
						level.playSound((Player)null, entity.blockPosition(), SoundEvents.SHIELD_BREAK, SoundSource.PLAYERS, 1.0F, 0.8F + this.level.random.nextFloat() * 0.4F);
					}
					else {
						level.playSound((Player)null, entity.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 0.8F + this.level.random.nextFloat() * 0.4F);
					}
				}
				entity.setDeltaMovement(entity.getDeltaMovement().x + (this.getDeltaMovement().x * 1.5F), entity.getDeltaMovement().y, entity.getDeltaMovement().z + (this.getDeltaMovement().z * 1.5F));
				if(isPlayer && !this.level.isClientSide) {
					AetherPacketHandler.sendToPlayer(new ZephyrSnowballHitPacket(livingEntity.getId(), this.getDeltaMovement().x, this.getDeltaMovement().z), (ServerPlayer) livingEntity);
				}
			}
		}
		this.discard();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.setNoGravity(true);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		//super.tick();
		if (this.level.isClientSide || (this.getOwner() == null || this.getOwner().isAlive()) && this.level.hasChunkAt(new BlockPos(this.blockPosition()))) {
			if (this.shouldBurn()) {
				this.setSecondsOnFire(1);
			}

			++this.ticksInAir;
			HitResult raytraceresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
			if (raytraceresult.getType() != HitResult.Type.MISS
					&& !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
				this.onHit(raytraceresult);
			}

			Vec3 Vector3d = this.getDeltaMovement();
			double d0 = this.getX() + Vector3d.x;
			double d1 = this.getY() + Vector3d.y;
			double d2 = this.getZ() + Vector3d.z;
			ProjectileUtil.rotateTowardsMovement(this, 0.2F);
			float f = this.getInertia();
			if (this.isInWater()) {
				for (int i = 0; i < 4; ++i) {
					this.level.addParticle(ParticleTypes.BUBBLE, d0 - Vector3d.x * 0.25D, d1 - Vector3d.y * 0.25D, d2 - Vector3d.z * 0.25D, Vector3d.x, Vector3d.y, Vector3d.z);
				}

				f = 0.8F;
			}

			this.setDeltaMovement(Vector3d.add(this.xPower, this.yPower, this.zPower).scale(f));
			ParticleOptions particle = this.getTrailParticle();
			if (particle != null) {
				this.level.addParticle(this.getTrailParticle(), d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
			}
			this.setPos(d0, d1, d2);
		} else {
			this.discard();
			return;
		}
		/* END SLIGHTLY MODIFIED super.tick() CODE */
		if (!this.onGround) {
			++this.ticksInAir;
		}

		if (this.ticksInAir > 400) {
			this.discard();
		}
	}

	@Override
	protected ParticleOptions getTrailParticle() {
		return AetherParticleTypes.ZEPHYR_SNOWFLAKE.get();
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(Items.SNOWBALL);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}