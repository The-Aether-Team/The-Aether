package com.aether.entity;

import com.aether.Aether;
import com.aether.entity.item.FloatingBlockEntity;
import com.aether.entity.monster.MimicEntity;
import com.aether.item.AetherItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Aether.MODID)
public class AetherEntityTypes {

	public static final EntityType<MimicEntity> MIMIC = null;
	//public static final EntityType<SentryEntity> SENTRY = null;
	//public static final EntityType<ValkyrieEntity> VALKYRIE = null;
	//public static final EntityType<FireMinionEntity> FIRE_MINION = null;
	public static final EntityType<FloatingBlockEntity> FLOATING_BLOCK = null;
	
	@EventBusSubscriber(modid = Aether.MODID, bus = EventBusSubscriber.Bus.MOD)
	public static class Registration {
		
		@SubscribeEvent
		public static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> event) {
			event.getRegistry().registerAll(new EntityType<?>[] {
				
				entity("mimic", AetherItems.MIMIC_SPAWN_EGG.getType(null)),
				//entity("sentry", AetherItems.SENTRY_SPAWN_EGG.getType(null)),
				//entity("valkyrie", AetherItems.VALKYRIE_SPAWN_EGG.getType(null)),
				//entity("fire_minion", AetherItems.FIRE_MINION_SPAWN_EGG.getType(null)),
				entity("floating_block", EntityType.Builder.<FloatingBlockEntity>create(FloatingBlockEntity::new, EntityClassification.MISC).size(0.98F, 0.98F).setCustomClientFactory((spawnEntity, world) -> new FloatingBlockEntity(world))),
				
			});
		}
		
		private static <E extends Entity> EntityType<E> entity(String name, EntityType.Builder<E> entityTypeBuilder) {
			return entity(name, entityTypeBuilder.build(name));
		}
		
		private static <E extends Entity> EntityType<E> entity(String name, EntityType<E> entityType) {			
			entityType.setRegistryName(name);
			return entityType;
		}
		
	}
	
}
