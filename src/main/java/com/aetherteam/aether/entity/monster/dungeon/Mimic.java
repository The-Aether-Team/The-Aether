package com.aetherteam.aether.entity.monster.dungeon;

import com.aetherteam.aether.client.AetherSoundEvents;

import com.aetherteam.aether.entity.ai.goal.ContinuousMeleeAttackGoal;
import com.aetherteam.aether.entity.EntityUtil;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nonnull;

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

	@Nonnull
	public static AttributeSupplier.Builder createMobAttributes() {
		return Monster.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 40.0)
				.add(Attributes.ATTACK_DAMAGE, 3.0)
				.add(Attributes.MOVEMENT_SPEED, 0.28)
				.add(Attributes.FOLLOW_RANGE, 8.0);
	}
	
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (!(damageSource.getDirectEntity() instanceof Mimic)) {
			if (damageSource.getDirectEntity() instanceof LivingEntity livingEntity && this.hurtTime == 0) {
				if (this.level instanceof ServerLevel level) {
					for (int i = 0; i < 20; i++) {
						level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.CHEST.defaultBlockState()), this.getX(), this.getY() + this.getBbHeight() / 1.5, this.getZ(), 1, this.getBbWidth() / 4.0, this.getBbHeight() / 4.0, this.getBbWidth() / 4.0, 0.05F);
					}
				}
				if (!(livingEntity instanceof Player player) || !player.isCreative()) {
					this.setTarget(livingEntity);
				}
			}
			return super.hurt(damageSource, amount);
		} else {
			return false;
		}
	}
	
	@Override
	public boolean doHurtTarget(@Nonnull Entity entity) {
		boolean result = super.doHurtTarget(entity);
		if (entity instanceof LivingEntity livingEntity) {
			SoundEvent sound = livingEntity.getHealth() <= 0.0 ? AetherSoundEvents.ENTITY_MIMIC_KILL.get() : AetherSoundEvents.ENTITY_MIMIC_ATTACK.get();
			this.playSound(sound, 1.0F, this.getVoicePitch());
		}
		return result;
	}

	@Override
	public void spawnAnim() {
		if (this.level.isClientSide) {
			EntityUtil.spawnSummoningExplosionParticles(this);
		} else {
			this.level.broadcastEntityEvent(this, (byte) 20);
		}
	}

	@Override
	protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
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
}