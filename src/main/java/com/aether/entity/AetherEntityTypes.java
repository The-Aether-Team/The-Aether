package com.aether.entity;

import com.aether.Aether;
import com.aether.entity.item.FloatingBlockEntity;
import com.aether.entity.monster.MimicEntity;
import com.aether.item.AetherItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Aether.MODID)
public class AetherEntityTypes {

	public static final EntityType<MimicEntity> MIMIC = null;
	public static final EntityType<FloatingBlockEntity> FLOATING_BLOCK = null;
	
	@SuppressWarnings("unused")
	@EventBusSubscriber(modid = Aether.MODID, bus = EventBusSubscriber.Bus.MOD)
	public static class Registration {
		
		@SuppressWarnings("unchecked")
		@SubscribeEvent
		public static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> event) {
			event.getRegistry().registerAll(new EntityType<?>[] {
				
				entityType("mimic", getEntityType(AetherItems.MIMIC_SPAWN_EGG)),
				entityType("floating_block", EntityType.Builder.<FloatingBlockEntity>create(FloatingBlockEntity::new, EntityClassification.MISC).size(0.98F, 0.98F).setCustomClientFactory((spawnEntity, world) -> new FloatingBlockEntity(world))),
				
			});
		}
		
		@SuppressWarnings("unchecked")
		private static <E extends Entity> EntityType<E> getEntityType(SpawnEggItem egg) {
			return (EntityType<E>) egg.getType(null);
		}
		
		private static <E extends Entity> EntityType<E> entityType(String name, EntityType.Builder<E> entityTypeBuilder) {
			return entityType(name, entityTypeBuilder.build(name));
		}
		
		private static <E extends Entity> EntityType<E> entityType(String name, EntityType<E> entityType) {			
			entityType.setRegistryName(name);
			return entityType;
		}
		
	}
	
}
