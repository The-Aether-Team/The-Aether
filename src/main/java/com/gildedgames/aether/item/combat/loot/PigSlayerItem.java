package com.gildedgames.aether.item.combat.loot;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.item.AetherItems;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.util.EquipmentUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber
public class PigSlayerItem extends SwordItem {
	public PigSlayerItem() {
		super(AetherItemTiers.PIG_SLAYER, 3, -2.4F, new Item.Properties().rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
	}

	/**
	 * Deals 20 damage (plus an extra 3.5 for every armor point the target might have) to the target if they're a pig entity and if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}.
	 * Flame particles are spawned around the target when hit.
	 * @param stack The stack used to hurt the target
	 * @param target The hurt entity.
	 * @param attacker The attacking entity.
	 * @return Whether the enemy was hurt or not.
	 */
	@Override
	public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
		if (EquipmentUtil.isFullStrength(attacker)) {
			if (target.getType().is(AetherTags.Entities.PIGS)) {
				if (target instanceof ZombifiedPiglin zombifiedPiglin) {
					zombifiedPiglin.setTarget(attacker);
					zombifiedPiglin.alertOthers(); // AT: m_34473_()V
				}
				DamageSource damageSource = attacker instanceof Player player ? DamageSource.playerAttack(player) : DamageSource.mobAttack(attacker);
				target.hurt(damageSource, 20 + (target.getArmorValue() * 3.5F));
				if (target.getLevel() instanceof ServerLevel level) {
					for (int i = 0; i < 20; i++) {
						double d0 = level.getRandom().nextGaussian() * 0.02;
						double d1 = level.getRandom().nextGaussian() * 0.02;
						double d2 = level.getRandom().nextGaussian() * 0.02;
						double d3 = 5.0;
						double x = target.getX() + (level.getRandom().nextFloat() * target.getBbWidth() * 2.0) - target.getBbWidth() - d0 * d3;
						double y = target.getY() + (level.getRandom().nextFloat() * target.getBbHeight()) - d1 * d3;
						double z = target.getZ() + (level.getRandom().nextFloat() * target.getBbWidth() * 2.0) - target.getBbWidth() - d2 * d3;
						level.sendParticles(ParticleTypes.FLAME, x, y, z, 1, d0, d1, d2, 0.0);
					}
				}
			}
		}
		return super.hurtEnemy(stack, target, attacker);
	}

	/**
	 * @see PigSlayerItem#handlePigSlayerAbility(LivingEntity, Collection)
	 */
	@SubscribeEvent
	public static void doPigSlayerDrops(LivingDropsEvent event) {
		LivingEntity livingEntity = event.getEntity();
		DamageSource damageSource = event.getSource();
		Collection<ItemEntity> drops = event.getDrops();
		if (canPerformAbility(livingEntity, damageSource)) {
			handlePigSlayerAbility(livingEntity, drops);
		}
	}

	/**
	 * Basic checks to perform the ability if the source is living, the target is a pig, the item is a pig slayer, and if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}.
	 * @param target The killed entity.
	 * @param source The attacking damage source.
	 */
	private static boolean canPerformAbility(LivingEntity target, DamageSource source) {
		if (source.getDirectEntity() instanceof LivingEntity attacker) {
			if (EquipmentUtil.isFullStrength(attacker)) {
				if (target.getType().is(AetherTags.Entities.PIGS)) {
					return attacker.getMainHandItem().is(AetherItems.PIG_SLAYER.get());
				}
			}
		}
		return false;
	}

	/**
	 * Determines what drops should be doubled when a target is killed, with a 1/4 chance. Any items tagged as {@link AetherTags.Items#PIG_DROPS} are doubled.
	 * The items that are able to be doubled are tracked in newDrops and added into drops which {@link PigSlayerItem#doPigSlayerDrops(LivingDropsEvent)} has access to.
	 * @param target The killed entity.
	 * @param drops The normal drops of the killed entity.
	 */
	private static void handlePigSlayerAbility(LivingEntity target, Collection<ItemEntity> drops) {
		if (target.getRandom().nextInt(4) == 0) {
			ArrayList<ItemEntity> newDrops = new ArrayList<>(drops.size());
			for (ItemEntity drop : drops) {
				ItemStack droppedStack = drop.getItem();
				if (droppedStack.is(AetherTags.Items.PIG_DROPS)) {
					ItemEntity dropEntity = new ItemEntity(target.getLevel(), drop.getX(), drop.getY(), drop.getZ(), droppedStack.copy());
					dropEntity.setDefaultPickUpDelay();
					newDrops.add(dropEntity);
				}
			}
			drops.addAll(newDrops);
		}
	}
}
