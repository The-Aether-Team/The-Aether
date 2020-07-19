package com.aether;

import java.util.ArrayList;

import com.aether.capability.AetherCapabilities;
import com.aether.item.AetherItems;
import com.aether.network.AetherPacketHandler;
import com.aether.player.IAetherPlayer;
import com.aether.tags.AetherEntityTypeTags;
import com.aether.tags.AetherItemTags;
import com.aether.world.dimension.AetherDimensions;
import com.aether.world.storage.loot.functions.DoubleDrops;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonProxy {

	@SubscribeEvent
	public void commonSetup(FMLCommonSetupEvent event) {
		AetherPacketHandler.register();
		AetherCapabilities.register();
		registerLootTableFunctions();
		registerLootTableConditions();
	}
	
	@SubscribeEvent
	public void clientSetup(FMLClientSetupEvent event) {
		
	}
	
	protected void registerLootTableFunctions() {
		LootFunctionManager.registerFunction(new DoubleDrops.Serializer());
	}
	
	protected void registerLootTableConditions() {
//		LootConditionManager.registerCondition(new ######.Serializer());
	}
	
	@SubscribeEvent
	public void checkBlockBanned(PlayerInteractEvent.RightClickBlock event) {
		PlayerEntity player = event.getPlayer();
		
		if (player.dimension != AetherDimensions.THE_AETHER) {
			return;
		}
		
		World world = event.getWorld();
		
		if (world.getBlockState(event.getPos()).isIn(BlockTags.BEDS)) {
			event.setCanceled(true);
			return;
		}

		if (event.getItemStack().getItem().isIn(AetherItemTags.BANNED_IN_AETHER)) {
			double x = event.getPos().getX();
			double y = event.getPos().getY();
			double z = event.getPos().getZ();
			for (int i = 0; i < 10; i++) {
				world.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, 0.0, 0.0, 0.0); 
			}
			
			event.setCanceled(true);
			return;
		}
	}
	
	@SubscribeEvent
	public void doSkyrootDoubleDrops(LivingDropsEvent event) {
		if (!(event.getSource() instanceof EntityDamageSource)) {
			return;
		}
		
		LivingEntity entity = event.getEntityLiving();
		EntityDamageSource source = (EntityDamageSource) event.getSource();
		
		if (!(source.getImmediateSource() instanceof PlayerEntity)) {
			return;
		}
		
		PlayerEntity player = (PlayerEntity) source.getImmediateSource();
		ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
		Item item = stack.getItem();
		
		if (item == AetherItems.SKYROOT_SWORD && !entity.getType().isContained(AetherEntityTypeTags.NO_SKYROOT_DOUBLE_DROPS)) {
			ArrayList<ItemEntity> newDrops = new ArrayList<>(event.getDrops().size());
			for (ItemEntity drop : event.getDrops()) {
				ItemStack droppedStack = drop.getItem();
				if (!droppedStack.getItem().isIn(AetherItemTags.NO_SKYROOT_DOUBLE_DROPS)) {
					newDrops.add(new ItemEntity(entity.world, drop.getPosX(), drop.getPosY(), drop.getPosZ(), droppedStack.copy()));
				}
			}
			event.getDrops().addAll(newDrops);
		}
	}
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		IAetherPlayer original = event.getOriginal().getCapability(AetherCapabilities.AETHER_PLAYER_CAPABILITY).orElseThrow(() -> new IllegalStateException("Player " + event.getOriginal().getName().getUnformattedComponentText() + " has no AetherPlayer capability!"));
		IAetherPlayer newPlayer = event.getPlayer().getCapability(AetherCapabilities.AETHER_PLAYER_CAPABILITY).orElseThrow(() -> new IllegalStateException("Player " + event.getOriginal().getName().getUnformattedComponentText() + " has no AetherPlayer capability!"));
		
		newPlayer.copyFrom(original);
	}
	
}
