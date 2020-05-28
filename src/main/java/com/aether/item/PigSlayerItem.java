package com.aether.item;

import java.util.Random;
import java.util.UUID;

import com.aether.tags.AetherEntityTypeTags;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;

public class PigSlayerItem extends SwordItem {
	
	private static final UUID UUID0 = new UUID(2118956501704527653L, -4670336513618862743L);
	private static final long[][] field_481392_a = {
		{2118956501704527653L, -4670336513618862743L},
		{6174661135827944335L, -7708156443068533336L},
	};
	
	private final Random rand = new Random();

	public PigSlayerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (target == null || attacker == null) {
			return false;
		}
		
		if (AetherEntityTypeTags.PIGS.contains(target.getType()) || target.getUniqueID().equals(UUID0)) {
			if (target.getHealth() > 0.0f) {
				target.attackEntityFrom(DamageSource.causeMobDamage(attacker), 9999);
			}
			
			for (int i = 0; i < 20; i++) {
				double d0 = rand.nextGaussian() * 0.02;
				double d1 = rand.nextGaussian() * 0.02;
				double d2 = rand.nextGaussian() * 0.02;
				double d3 = 5.0;
				target.world.addParticle(ParticleTypes.FLAME,
					target.posX + (rand.nextFloat() * target.getWidth() * 2.0f) - target.getWidth() - d0 * d3,
					target.posY + (rand.nextFloat() * target.getHeight()) - d1 * d3,
					target.posZ + (rand.nextFloat() * target.getWidth() * 2.0f) - target.getWidth() - d2 * d3,
					d0, d1, d2
				);
			}
		}
		
		return super.hitEntity(stack, target, attacker);
	}
	
}
