package com.aetherteam.aether.entity.monster.dungeon;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.EntityUtil;
import com.aetherteam.aether.entity.ai.goal.ContinuousMeleeAttackGoal;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class Mimic extends Monster {
	public Mimic(EntityType<? extends Mimic> type, Level level) {
		super(type, level);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(2,  new ContinuousMeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Mimic.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return Monster.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 40.0)
				.add(Attributes.ATTACK_DAMAGE, 3.0)
				.add(Attributes.MOVEMENT_SPEED, 0.28)
				.add(Attributes.FOLLOW_RANGE, 8.0);
	}

	/**
	 * Prevents Mimics from hurting each other and spawns particles when one is hurt by any other entity, and sets the entity as a target.
	 * @param source The {@link DamageSource}.
	 * @param amount The {@link Float} amount of damage.
	 * @return Whether the entity was hurt, as a {@link Boolean}.
	 */
	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (!(source.getDirectEntity() instanceof Mimic)) {
			if (source.getDirectEntity() instanceof LivingEntity livingEntity && this.hurtTime == 0) {
				if (this.getLevel() instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 20; i++) {
						serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.CHEST.defaultBlockState()), this.getX(), this.getY() + this.getBbHeight() / 1.5, this.getZ(), 1, this.getBbWidth() / 4.0, this.getBbHeight() / 4.0, this.getBbWidth() / 4.0, 0.05F);
					}
				}
				if (!(livingEntity instanceof Player player) || !player.isCreative()) {
					this.setTarget(livingEntity);
				}
			}
			return super.hurt(source, amount);
		} else {
			return false;
		}
	}

	/**
	 * Handle sounds when a target is hurt.
	 * @param entity The hurt {@link Entity}.
	 * @return Whether the entity was hurt, as a {@link Boolean}.
	 */
	@Override
	public boolean doHurtTarget(Entity entity) {
		boolean result = super.doHurtTarget(entity);
		if (entity instanceof LivingEntity livingEntity) { // Choose between attack or kill sound depending on remaining target health.
			SoundEvent sound = livingEntity.getHealth() <= 0.0 ? AetherSoundEvents.ENTITY_MIMIC_KILL.get() : AetherSoundEvents.ENTITY_MIMIC_ATTACK.get();
			this.playSound(sound, 1.0F, this.getVoicePitch());
		}
		return result;
	}

	@Override
	public void spawnAnim() {
		if (this.getLevel().isClientSide()) {
			EntityUtil.spawnSummoningExplosionParticles(this);
		} else {
			this.getLevel().broadcastEntityEvent(this, (byte) 70);
		}
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return AetherSoundEvents.ENTITY_MIMIC_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return AetherSoundEvents.ENTITY_MIMIC_DEATH.get();
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == 70) {
			EntityUtil.spawnSummoningExplosionParticles(this);
		} else {
			super.handleEntityEvent(id);
		}
	}
}