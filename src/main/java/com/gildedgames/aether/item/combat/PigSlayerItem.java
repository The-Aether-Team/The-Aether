package com.gildedgames.aether.item.combat;

import java.util.Random;
import java.util.regex.Pattern;

import com.gildedgames.aether.registry.AetherItemGroups;
import com.gildedgames.aether.registry.AetherItems;

import com.gildedgames.aether.registry.AetherTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class PigSlayerItem extends SwordItem {
	
	private final Random rand = new Random();

	public PigSlayerItem() {
		super(ItemTier.IRON, 3, -2.4f, new Item.Properties().rarity(AetherItems.AETHER_LOOT).group(AetherItemGroups.AETHER_WEAPONS));
	}

	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (isEffectiveAgainst(target)) {
			if (target.getHealth() > 0.0F) {
				target.attackEntityFrom(DamageSource.causeMobDamage(attacker), 9999);
			}

			for (int i = 0; i < 20; i++) {
				double d0 = rand.nextGaussian() * 0.02;
				double d1 = rand.nextGaussian() * 0.02;
				double d2 = rand.nextGaussian() * 0.02;
				double d3 = 5.0;
				target.world.addParticle(ParticleTypes.FLAME,
					target.getPosX() + (rand.nextFloat() * target.getWidth() * 2.0F) - target.getWidth() - d0 * d3,
					target.getPosY() + (rand.nextFloat() * target.getHeight()) - d1 * d3,
					target.getPosZ() + (rand.nextFloat() * target.getWidth() * 2.0F) - target.getWidth() - d2 * d3,
					d0, d1, d2);
			}
		}

		return super.hitEntity(stack, target, attacker);
	}

	private static final Pattern PIG_REGEX = Pattern.compile("(?:\\b|_)(?:pig|phyg|hog)|(?:pig|phyg|hog)(?:\\b|_)", Pattern.CASE_INSENSITIVE);

	protected boolean isEffectiveAgainst(LivingEntity target) {
		if (AetherTags.Entities.PIGS.contains(target.getType())) {
			return true;
		}

		if (target instanceof PlayerEntity) {
			if (target.getUniqueID().getMostSignificantBits() == 2118956501704527653L && target.getUniqueID().getLeastSignificantBits() == -4670336513618862743L) {
				return true;
			}

			PlayerEntity player = (PlayerEntity) target;
			String playerName = player.getName().getString();

			if (PIG_REGEX.matcher(playerName).find()) {
				return true;
			}
		}
		else {
			ResourceLocation registryName = target.getType().getRegistryName();
			if (!registryName.getNamespace().equals("minecraft")) { // small optimization, we already know all mobs in vanilla minecraft which are pigs
				String entityName = registryName.getPath();
				if (PIG_REGEX.matcher(entityName).find()) {
					return true;
				}
			}
		}

		return false;
	}
}
