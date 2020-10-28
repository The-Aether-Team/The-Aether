package com.aether.entity;

import com.aether.Aether;
import com.aether.entity.item.FloatingBlockEntity;
import com.aether.entity.monster.MimicEntity;
import com.aether.entity.monster.SentryEntity;
import com.aether.entity.monster.ZephyrEntity;
import com.aether.entity.passive.FlyingCowEntity;
import com.aether.entity.passive.MoaEntity;
import com.aether.entity.passive.PhygEntity;
import com.aether.entity.passive.SheepuffEntity;
import com.aether.entity.projectile.LightningKnifeEntity;
import com.aether.entity.projectile.ZephyrSnowballEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Aether.MODID)
public class AetherEntityTypes {

	public static final EntityType<MimicEntity> MIMIC = EntityType.Builder.<MimicEntity>create(MimicEntity::new, EntityClassification.MONSTER).size(1.0f, 2.0f).build("mimic");
	public static final EntityType<SentryEntity> SENTRY = EntityType.Builder.<SentryEntity>create(SentryEntity::new, EntityClassification.MONSTER).size(2.0F, 2.0F).build("sentry");
	public static final EntityType<ZephyrEntity> ZEPHYR = EntityType.Builder.<ZephyrEntity>create(ZephyrEntity::new, EntityClassification.MONSTER).size(4.0F, 4.0F).build("zephyr");
	//public static final EntityType<ValkyrieEntity> VALKYRIE = null;
	//public static final EntityType<FireMinionEntity> FIRE_MINION = null;
	public static final EntityType<FloatingBlockEntity> FLOATING_BLOCK = EntityType.Builder.<FloatingBlockEntity>create(FloatingBlockEntity::new, EntityClassification.MISC).size(0.98F, 0.98F).setCustomClientFactory((spawnEntity, world) -> new FloatingBlockEntity(world)).build("floating_block");
	public static final EntityType<LightningKnifeEntity> LIGHTNING_KNIFE = EntityType.Builder.<LightningKnifeEntity>create(LightningKnifeEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setCustomClientFactory((spawnEntity, world) -> new LightningKnifeEntity(world)).build("lightning_knife");
	public static final EntityType<ZephyrSnowballEntity> ZEPHYR_SNOWBALL = EntityType.Builder.<ZephyrSnowballEntity>create(ZephyrSnowballEntity::new, EntityClassification.MISC).size(1.0F, 1.0F).build("zephyr_snowball");
	public static final EntityType<MoaEntity> MOA = EntityType.Builder.<MoaEntity>create(MoaEntity::new, EntityClassification.CREATURE).size(1.0F,  2.0F).build("moa");
	public static final EntityType<PhygEntity> PHYG = EntityType.Builder.create(PhygEntity::new, EntityClassification.CREATURE).size(0.9F, 0.9F).build("phyg");
	public static final EntityType<FlyingCowEntity> FLYING_COW = EntityType.Builder.<FlyingCowEntity>create(FlyingCowEntity::new, EntityClassification.CREATURE).size(0.9F, 1.4F).build("flying_cow");
	public static final EntityType<SheepuffEntity> SHEEPUFF = EntityType.Builder.<SheepuffEntity>create(SheepuffEntity::new, EntityClassification.CREATURE).size(0.9F, 1.4F).build("sheepuff");

	@EventBusSubscriber(modid = Aether.MODID, bus = EventBusSubscriber.Bus.MOD)
	public static class Registration {
		
		@SubscribeEvent
		public static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> event) {
			event.getRegistry().registerAll(new EntityType<?>[] {
				
				entity("mimic", MIMIC),
				entity("sentry", SENTRY),
				entity("zephyr", ZEPHYR),
				//entity("valkyrie", VALKYRIE),
				//entity("fire_minion", FIRE_MINION),
				entity("phyg", PHYG),
				entity("flying_cow", FLYING_COW),
				entity("sheepuff", SHEEPUFF),
				entity("moa", MOA),
				entity("floating_block", FLOATING_BLOCK),
				entity("lightning_knife", LIGHTNING_KNIFE),
				entity("zephyr_snowball", ZEPHYR_SNOWBALL),
			});
		}
		
		private static <E extends Entity> EntityType<E> entity(String name, EntityType<E> entityType) {			
			entityType.setRegistryName(name);	
			return entityType;
		}

		public static void registerSpawnPlacements() {
			EntitySpawnPlacementRegistry.register(PHYG, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);
			EntitySpawnPlacementRegistry.register(FLYING_COW, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);
			EntitySpawnPlacementRegistry.register(SHEEPUFF, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);
			EntitySpawnPlacementRegistry.register(MOA, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);
			EntitySpawnPlacementRegistry.register(ZEPHYR, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZephyrEntity::canZephyrSpawn);
		}
		
	}
	
}
