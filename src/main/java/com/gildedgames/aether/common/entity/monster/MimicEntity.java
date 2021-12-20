package com.gildedgames.aether.common.entity.monster;

import com.gildedgames.aether.common.registry.AetherEntityTypes;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

public class MimicEntity extends PathfinderMob {

	public MimicEntity(EntityType<? extends MimicEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	public MimicEntity(Level worldIn) {
		super(AetherEntityTypes.MIMIC.get(), worldIn);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(2,  new MeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 40.0D)
				.add(Attributes.ATTACK_DAMAGE, 3.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.28000000417232513D)
				.add(Attributes.FOLLOW_RANGE, 8.0D);
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.WOOD_BREAK;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.CHEST_CLOSE;
	}
	
	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.getDirectEntity() instanceof MimicEntity) {
			return false;
		}
		if (source.getDirectEntity() instanceof LivingEntity && this.hurtTime == 0) {
			if (this.level instanceof ServerLevel) {
				ServerLevel world = (ServerLevel) this.level;
				for (int i = 0; i < 20; i++) {
					world.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.CHEST.defaultBlockState()), this.getX(), this.getY() + this.getBbHeight() / 1.5, this.getZ(), 1, this.getBbWidth() / 4.0, this.getBbHeight() / 4.0, this.getBbWidth() / 4.0, 0.05F);
				}
			}
			
			LivingEntity attacker = (LivingEntity) source.getDirectEntity();
			if (!(attacker instanceof Player) || !((Player) attacker).isCreative()) {
				this.setTarget(attacker);
			}
		}
		return super.hurt(source, amount);
	}
	
	@Override
	public boolean doHurtTarget(Entity entityIn) {
		boolean result = super.doHurtTarget(entityIn);
		
		if (entityIn instanceof LivingEntity) {
			// If the entity died as a result of this attack, then play the burp sound. Otherwise, play the eating sound.
			SoundEvent sound = (((LivingEntity) entityIn).getHealth() <= 0.0)? SoundEvents.PLAYER_BURP : SoundEvents.GENERIC_EAT;
			this.playSound(sound, 1.0F, this.getVoicePitch());
		}
		
		return result;
	}

	@Override
	public void spawnAnim() {
		if (this.level.isClientSide) {
			for(int i = 0; i < 20; ++i) {
				double d0 = this.random.nextGaussian() * 0.02D;
				double d1 = this.random.nextGaussian() * 0.02D;
				double d2 = this.random.nextGaussian() * 0.02D;
				this.level.addParticle(ParticleTypes.POOF, this.getX(0.0D) - d0 * 10.0D, this.getRandomY() - d1 * 10.0D, this.getRandomZ(1.0D) - d2 * 10.0D, d0, d1, d2);
			}
		} else {
			this.level.broadcastEntityEvent(this, (byte) 20);
		}
	}
}