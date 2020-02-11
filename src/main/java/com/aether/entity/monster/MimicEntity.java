package com.aether.entity.monster;

import com.aether.world.gen.feature.AetherDungeonFeature;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
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
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(2,  new MeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}
	
	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(8.0);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28000000417232513);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0);
		this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0);
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.BLOCK_WOOD_BREAK;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_CHEST_CLOSE;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.getImmediateSource() instanceof MimicEntity) {
			return false;
		}
		if (source.getImmediateSource() instanceof LivingEntity && this.hurtTime == 0) {
			if (this.world instanceof ServerWorld) {
				((ServerWorld) this.world).spawnParticle(new BlockParticleData(ParticleTypes.BLOCK, AetherDungeonFeature.Decorations.CHEST.getDefaultState()), this.posX, this.posY + this.getHeight() / 1.5, this.posZ, 20, this.getWidth() / 4.0, this.getHeight() / 4.0, this.getWidth() / 4.0, 0.05);
			}
			
			LivingEntity attacker = (LivingEntity) source.getImmediateSource();
			if (!(attacker instanceof PlayerEntity) || !((PlayerEntity) attacker).isCreative()) {
				this.setAttackTarget(attacker);
			}
		}
		return super.attackEntityFrom(source, amount);
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		boolean result = super.attackEntityAsMob(entityIn);
		
		if (entityIn instanceof LivingEntity) {
			// If the entity died as a result of this attack, then play the burp sound. Otherwise, play the eating sound.
			SoundEvent sound = (((LivingEntity) entityIn).getHealth() <= 0.0)? SoundEvents.ENTITY_PLAYER_BURP : SoundEvents.ENTITY_GENERIC_EAT;
			this.playSound(sound, 1.0f, this.getSoundPitch());
		}
		
		return result;
	}

}
