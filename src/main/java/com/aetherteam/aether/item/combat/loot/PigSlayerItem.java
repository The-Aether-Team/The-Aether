package com.aetherteam.aether.item.combat.loot;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.data.resources.AetherDamageTypes;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.AetherItems;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.combat.AetherSwordItem;
import com.aetherteam.aether.mixin.mixins.common.accessor.ZombifiedPiglinAccessor;
import com.aetherteam.aether.util.EquipmentUtil;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@Mod.EventBusSubscriber(modid = Aether.MODID)
public class PigSlayerItem extends AetherSwordItem {
	public PigSlayerItem() {
		super(AetherItemTiers.PIG_SLAYER, 3, -2.4F, new Item.Properties().rarity(AetherItems.AETHER_LOOT));
	}

	/**
	 * Deals 20 hearts of damage to the target if they're a Pig-type entity and if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}.<br><br>
	 * Flame particles are also spawned around the target when hit.
	 * @param stack The {@link ItemStack} used to hurt the target
	 * @param target The hurt {@link LivingEntity}.
	 * @param attacker The attacking {@link LivingEntity}.
	 * @return Whether the enemy was hurt or not, as a {@link Boolean}.
	 */
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (EquipmentUtil.isFullStrength(attacker)) {
			if (target.getType().is(AetherTags.Entities.PIGS)) {
				if (target instanceof ZombifiedPiglin zombifiedPiglin) {
					if (!(attacker instanceof Player player) || !player.isCreative()) {
						ZombifiedPiglinAccessor zombifiedPiglinAccessor = (ZombifiedPiglinAccessor) zombifiedPiglin;
						zombifiedPiglin.setTarget(attacker);
						zombifiedPiglinAccessor.callAlertOthers();
					}
				}
				DamageSource damageSource = AetherDamageTypes.entityDamageSource(attacker.level, AetherDamageTypes.ARMOR_PIERCING_ATTACK, attacker);
				target.hurt(damageSource, 26); // This doesn't deal 26 hearts of damage, it deals 20.
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
	 * Sets the normal weapon damage from the Pig Slayer to 0 if a Pig-type entity is being attacked. This allows for more consistent damage handling in {@link PigSlayerItem#hurtEnemy(ItemStack, LivingEntity, LivingEntity)}.
	 */
	@SubscribeEvent
	public static void onPigSlayerHurt(LivingHurtEvent event) {
		LivingEntity livingEntity = event.getEntity();
		DamageSource damageSource = event.getSource();
		if (canPerformAbility(livingEntity, damageSource) && !damageSource.is(DamageTypeTags.BYPASSES_ARMOR)) {
			event.setAmount(0);
		}
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
	 * Basic checks to perform the ability if the source is living, the target is a Pig-type entity, the item is a Pig Slayer, and if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}.
	 * @param target The killed {@link LivingEntity}.
	 * @param source The attacking {@link DamageSource}.
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
	 * Determines what drops should be doubled when a target is killed, with a 1/4 chance. Any items tagged as {@link AetherTags.Items#PIG_DROPS} are doubled.<br><br>
	 * The items that will be doubled are tracked in newDrops and added into drops which {@link PigSlayerItem#doPigSlayerDrops(LivingDropsEvent)} has access to.
	 * @param target The killed {@link LivingEntity}.
	 * @param drops The normal drops of the killed entity, as a {@link Collection Collection&lt;ItemEntity&gt;}.
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
