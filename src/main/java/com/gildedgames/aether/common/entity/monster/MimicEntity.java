package com.gildedgames.aether.common.entity.monster;

import com.gildedgames.aether.common.registry.AetherEntityTypes;

import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MimicEntity extends CreatureEntity {

	public MimicEntity(EntityType<? extends MimicEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public MimicEntity(World worldIn) {
		super(AetherEntityTypes.MIMIC.get(), worldIn);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(2,  new MeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute createMobAttributes() {
		return CreatureEntity.createMobAttributes()
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
			if (this.level instanceof ServerWorld) {
				((ServerWorld) this.level).sendParticles(new BlockParticleData(ParticleTypes.BLOCK, Blocks.CHEST.defaultBlockState()), this.getX(), this.getY() + this.getBbHeight() / 1.5, this.getZ(), 20, this.getBbWidth() / 4.0, this.getBbHeight() / 4.0, this.getBbWidth() / 4.0, 0.05);
			}
			
			LivingEntity attacker = (LivingEntity) source.getDirectEntity();
			if (!(attacker instanceof PlayerEntity) || !((PlayerEntity) attacker).isCreative()) {
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