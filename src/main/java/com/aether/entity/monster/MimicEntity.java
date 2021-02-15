package com.aether.entity.monster;

import com.aether.registry.AetherEntityTypes;

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
		super(AetherEntityTypes.MIMIC, worldIn);
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(2,  new MeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return CreatureEntity.func_233666_p_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 40.0D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.28000000417232513D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 8.0D);
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
				((ServerWorld) this.world).spawnParticle(new BlockParticleData(ParticleTypes.BLOCK, Blocks.CHEST.getDefaultState()), this.getPosX(), this.getPosY() + this.getHeight() / 1.5, this.getPosZ(), 20, this.getWidth() / 4.0, this.getHeight() / 4.0, this.getWidth() / 4.0, 0.05);
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
			this.playSound(sound, 1.0F, this.getSoundPitch());
		}
		
		return result;
	}

}