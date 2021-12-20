package com.gildedgames.aether.common.item.combat.loot;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;

import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

@Mod.EventBusSubscriber
public class PigSlayerItem extends SwordItem
{
	public PigSlayerItem() {
		super(Tiers.IRON, 3, -2.4f, new Item.Properties().durability(200).rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (AetherTags.Entities.PIGS.contains(target.getType())) {
			if (target.getHealth() > 0.0F) {
				target.hurt(DamageSource.mobAttack(attacker), 9999);
			}
			if (target.level instanceof ServerLevel) {
				ServerLevel world = (ServerLevel) target.level;
				for (int i = 0; i < 20; i++) {
					double d0 = world.getRandom().nextGaussian() * 0.02;
					double d1 = world.getRandom().nextGaussian() * 0.02;
					double d2 = world.getRandom().nextGaussian() * 0.02;
					double d3 = 5.0D;
					double x = target.getX() + (world.getRandom().nextFloat() * target.getBbWidth() * 2.0) - target.getBbWidth() - d0 * d3;
					double y = target.getY() + (world.getRandom().nextFloat() * target.getBbHeight()) - d1 * d3;
					double z = target.getZ() + (world.getRandom().nextFloat() * target.getBbWidth() * 2.0) - target.getBbWidth() - d2 * d3;
					world.sendParticles(ParticleTypes.FLAME, x, y, z, 1, d0, d1, d2, 0.0F);
				}
			}
		}
		return super.hurtEnemy(stack, target, attacker);
	}

	@SubscribeEvent
	public static void doPigSlayerDrops(LivingDropsEvent event) {
		if (event.getSource() instanceof EntityDamageSource) {
			LivingEntity entity = event.getEntityLiving();
			EntityDamageSource source = (EntityDamageSource) event.getSource();
			if (source.getDirectEntity() instanceof Player) {
				Player player = (Player) source.getDirectEntity();
				ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
				Item item = stack.getItem();
				if (item == AetherItems.PIG_SLAYER.get() && entity.getType().is(AetherTags.Entities.PIGS)) {
					if (entity.getRandom().nextInt(4) == 0) {
						ArrayList<ItemEntity> newDrops = new ArrayList<>(event.getDrops().size());
						for (ItemEntity drop : event.getDrops()) {
							ItemStack droppedStack = drop.getItem();
							if (droppedStack.getItem().getTags().contains(AetherTags.Items.PIG_DROPS.getName())) {
								ItemEntity dropEntity = new ItemEntity(entity.level, drop.getX(), drop.getY(), drop.getZ(), droppedStack.copy());
								dropEntity.setDefaultPickUpDelay();
								newDrops.add(dropEntity);
							}
						}
						event.getDrops().addAll(newDrops);
					}
				}
			}
		}
	}
}
