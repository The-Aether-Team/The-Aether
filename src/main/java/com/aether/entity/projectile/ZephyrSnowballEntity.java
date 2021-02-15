package com.aether.entity.projectile;

import com.aether.registry.AetherEntityTypes;
import com.aether.registry.AetherItems;

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
		super(AetherEntityTypes.ZEPHYR_SNOWBALL, x, y, z, accelX, accelY, accelZ, worldIn);
	}

	public ZephyrSnowballEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		super(AetherEntityTypes.ZEPHYR_SNOWBALL, shooter, accelX, accelY, accelZ, worldIn);
	}

	@Override
	protected boolean isFireballFiery() {
		return false;
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		if (result.getType() == RayTraceResult.Type.ENTITY) {
			Entity entity = ((EntityRayTraceResult)result).getEntity();
			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity)entity;
				boolean isPlayer = livingEntity instanceof PlayerEntity;

				if (isPlayer && ((PlayerEntity)entity).inventory.armorInventory.get(0).getItem() == AetherItems.SENTRY_BOOTS.get()) {
					return;
				}

				if (!livingEntity.isActiveItemStackBlocking()) {
					entity.setMotion(entity.getMotion().x, entity.getMotion().y + 0.5, entity.getMotion().z);
				}
				else {
					ItemStack activeItemStack = livingEntity.getActiveItemStack();
					activeItemStack.damageItem(1, livingEntity, p -> p.sendBreakAnimation(activeItemStack.getEquipmentSlot()));

					if (activeItemStack.getCount() <= 0) {
						world.playSound((PlayerEntity)null, entity.getPosition(), SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 1.0F, 0.8F + this.world.rand.nextFloat() * 0.4F);
					}
					else {
						world.playSound((PlayerEntity)null, entity.getPosition(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 1.0F, 0.8F + this.world.rand.nextFloat() * 0.4F);
					}
				}
				entity.setMotion(entity.getMotion().x + (this.getMotion().x * 1.5F), entity.getMotion().y, entity.getMotion().z + (this.getMotion().z * 1.5F));
			}
		}
		this.remove();
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.setNoGravity(true);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		//super.tick();
		if (this.world.isRemote || (this.func_234616_v_() == null || this.func_234616_v_().isAlive()) && this.world.isBlockLoaded(new BlockPos(this.getPosition()))) {
			if (this.isFireballFiery()) {
				this.setFire(1);
			}

			++this.ticksInAir;
			RayTraceResult raytraceresult = ProjectileHelper.func_234618_a_(this, this::func_230298_a_);
			if (raytraceresult.getType() != RayTraceResult.Type.MISS
					&& !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
				this.onImpact(raytraceresult);
			}

			Vector3d Vector3d = this.getMotion();
			double d0 = this.getPosX() + Vector3d.x;
			double d1 = this.getPosY() + Vector3d.y;
			double d2 = this.getPosZ() + Vector3d.z;
			ProjectileHelper.rotateTowardsMovement(this, 0.2F);
			float f = this.getMotionFactor();
			if (this.isInWater()) {
				for (int i = 0; i < 4; ++i) {
					this.world.addParticle(ParticleTypes.BUBBLE, d0 - Vector3d.x * 0.25D, d1 - Vector3d.y * 0.25D, d2 - Vector3d.z * 0.25D, Vector3d.x, Vector3d.y, Vector3d.z);
				}

				f = 0.8F;
			}

			this.setMotion(Vector3d.add(this.accelerationX, this.accelerationY, this.accelerationZ).scale(f));
			IParticleData particle = this.getParticle();
			if (particle != null) {
				this.world.addParticle(this.getParticle(), d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
			}
			this.setPosition(d0, d1, d2);
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
	protected IParticleData getParticle() {
		return null;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem() {
		return new ItemStack(Items.SNOWBALL);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}