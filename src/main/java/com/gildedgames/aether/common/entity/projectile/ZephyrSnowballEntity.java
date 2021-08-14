package com.gildedgames.aether.common.entity.projectile;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;

import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ZephyrSnowballEntity extends AbstractFireballEntity {
	private int ticksInAir;

	public ZephyrSnowballEntity(EntityType<? extends ZephyrSnowballEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@OnlyIn(Dist.CLIENT)
	public ZephyrSnowballEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(AetherEntityTypes.ZEPHYR_SNOWBALL.get(), x, y, z, accelX, accelY, accelZ, worldIn);
	}

	public ZephyrSnowballEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		super(AetherEntityTypes.ZEPHYR_SNOWBALL.get(), shooter, accelX, accelY, accelZ, worldIn);
	}

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	@Override
	protected void onHit(RayTraceResult result) {
		super.onHit(result);
		if (result.getType() == RayTraceResult.Type.ENTITY) {
			Entity entity = ((EntityRayTraceResult)result).getEntity();
			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity)entity;
				boolean isPlayer = livingEntity instanceof PlayerEntity;

				//TODO: Was this a thing?
//				if (isPlayer && ((PlayerEntity)entity).inventory.armor.get(0).getItem() == AetherItems.SENTRY_BOOTS.get()) {
//					return;
//				}

				if (!livingEntity.isBlocking()) {
					entity.setDeltaMovement(entity.getDeltaMovement().x, entity.getDeltaMovement().y + 0.5, entity.getDeltaMovement().z);
				}
				else {
					ItemStack activeItemStack = livingEntity.getUseItem();
					activeItemStack.hurtAndBreak(1, livingEntity, p -> p.broadcastBreakEvent(activeItemStack.getEquipmentSlot()));

					if (activeItemStack.getCount() <= 0) {
						level.playSound((PlayerEntity)null, entity.blockPosition(), SoundEvents.SHIELD_BREAK, SoundCategory.PLAYERS, 1.0F, 0.8F + this.level.random.nextFloat() * 0.4F);
					}
					else {
						level.playSound((PlayerEntity)null, entity.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundCategory.PLAYERS, 1.0F, 0.8F + this.level.random.nextFloat() * 0.4F);
					}
				}
				entity.setDeltaMovement(entity.getDeltaMovement().x + (this.getDeltaMovement().x * 1.5F), entity.getDeltaMovement().y, entity.getDeltaMovement().z + (this.getDeltaMovement().z * 1.5F));
			}
		}
		this.remove();
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
			RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
			if (raytraceresult.getType() != RayTraceResult.Type.MISS
					&& !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
				this.onHit(raytraceresult);
			}

			Vector3d Vector3d = this.getDeltaMovement();
			double d0 = this.getX() + Vector3d.x;
			double d1 = this.getY() + Vector3d.y;
			double d2 = this.getZ() + Vector3d.z;
			ProjectileHelper.rotateTowardsMovement(this, 0.2F);
			float f = this.getInertia();
			if (this.isInWater()) {
				for (int i = 0; i < 4; ++i) {
					this.level.addParticle(ParticleTypes.BUBBLE, d0 - Vector3d.x * 0.25D, d1 - Vector3d.y * 0.25D, d2 - Vector3d.z * 0.25D, Vector3d.x, Vector3d.y, Vector3d.z);
				}

				f = 0.8F;
			}

			this.setDeltaMovement(Vector3d.add(this.xPower, this.yPower, this.zPower).scale(f));
			IParticleData particle = this.getTrailParticle();
			if (particle != null) {
				this.level.addParticle(this.getTrailParticle(), d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
			}
			this.setPos(d0, d1, d2);
		}
		else {
			this.remove();
			return;
		}
		/* END SLIGHTLY MODIFIED super.tick() CODE */
		if (!this.onGround) {
			++this.ticksInAir;
		}

		if (this.ticksInAir > 400) {
			this.remove();
		}
	}

	@Override
	protected IParticleData getTrailParticle() {
		return null;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem() {
		return new ItemStack(Items.SNOWBALL);
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}