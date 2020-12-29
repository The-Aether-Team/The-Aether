package com.aether.entity;

import static com.aether.entity.AetherEntityTypes.Registration.entity;

import com.aether.Aether;
import com.aether.entity.item.FloatingBlockEntity;
import com.aether.entity.monster.*;
import com.aether.entity.passive.AerwhaleEntity;
import com.aether.entity.passive.FlyingCowEntity;
import com.aether.entity.passive.MoaEntity;
import com.aether.entity.passive.PhygEntity;
import com.aether.entity.passive.SheepuffEntity;
import com.aether.entity.projectile.*;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Aether.MODID)
public class AetherEntityTypes {

	public static final EntityType<MimicEntity> MIMIC = entity("mimic", EntityType.Builder.<MimicEntity>create(MimicEntity::new, EntityClassification.MONSTER).size(1.0F, 2.0F));
	public static final EntityType<SentryEntity> SENTRY = entity("sentry", EntityType.Builder.<SentryEntity>create(SentryEntity::new, EntityClassification.MONSTER).size(2.0F, 2.0F));
	public static final EntityType<ZephyrEntity> ZEPHYR = entity("zephyr", EntityType.Builder.<ZephyrEntity>create(ZephyrEntity::new, EntityClassification.MONSTER).size(4.0F, 4.0F));
	public static final EntityType<CockatriceEntity> COCKATRICE = entity("cockatrice", EntityType.Builder.<CockatriceEntity>create(CockatriceEntity::new, EntityClassification.MONSTER).size(1.0F, 2.0F));
	public static final EntityType<AechorPlantEntity> AECHOR_PLANT = entity("aechor_plant", EntityType.Builder.<AechorPlantEntity>create(AechorPlantEntity::new, EntityClassification.MONSTER).size(1.0F, 1.0F));
	//public static final EntityType<ValkyrieEntity> VALKYRIE = entity("valkyrie", EntityType.Builder.<ValkyrieEntity>create(ValkyrieEntity::new, EntityClassification.MONSTER).size(??????));
	//public static final EntityType<FireMinionEntity> FIRE_MINION = entity("fire_minion", EntityType.Builder.<FireMinionEntity>create(FireMinionEntity::new, EntityClassification.MONSTER).size(??????));
	public static final EntityType<FloatingBlockEntity> FLOATING_BLOCK = entity("floating_block", EntityType.Builder.<FloatingBlockEntity>create(FloatingBlockEntity::new, EntityClassification.MISC).size(0.98F, 0.98F).setCustomClientFactory((spawnEntity, world) -> new FloatingBlockEntity(world)));
	public static final EntityType<LightningKnifeEntity> LIGHTNING_KNIFE = entity("lightning_knife", EntityType.Builder.<LightningKnifeEntity>create(LightningKnifeEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).setCustomClientFactory((spawnEntity, world) -> new LightningKnifeEntity(world)));
	public static final EntityType<ZephyrSnowballEntity> ZEPHYR_SNOWBALL = entity("zephyr_snowball", EntityType.Builder.<ZephyrSnowballEntity>create(ZephyrSnowballEntity::new, EntityClassification.MISC).size(1.0F, 1.0F));
	public static final EntityType<GoldenDartEntity> GOLDEN_DART = entity("golden_dart", EntityType.Builder.<GoldenDartEntity>create(GoldenDartEntity::new, EntityClassification.MISC).size(0.5F, 0.5F));
	public static final EntityType<EnchantedDartEntity> ENCHANTED_DART = entity("enchanted_dart", EntityType.Builder.<EnchantedDartEntity>create(EnchantedDartEntity::new, EntityClassification.MISC).size(0.5F, 0.5F));
	public static final EntityType<PoisonDartEntity> POISON_DART = entity("poison_dart", EntityType.Builder.<PoisonDartEntity>create(PoisonDartEntity::new, EntityClassification.MISC).size(0.5F, 0.5F));
	public static final EntityType<MoaEntity> MOA = entity("moa", EntityType.Builder.<MoaEntity>create(MoaEntity::new, EntityClassification.CREATURE).size(1.0F,  2.0F));
	public static final EntityType<PhygEntity> PHYG = entity("phyg", EntityType.Builder.create(PhygEntity::new, EntityClassification.CREATURE).size(0.9F, 0.9F));
	public static final EntityType<FlyingCowEntity> FLYING_COW = entity("flying_cow", EntityType.Builder.<FlyingCowEntity>create(FlyingCowEntity::new, EntityClassification.CREATURE).size(0.9F, 1.4F));
	public static final EntityType<SheepuffEntity> SHEEPUFF = entity("sheepuff", EntityType.Builder.<SheepuffEntity>create(SheepuffEntity::new, EntityClassification.CREATURE).size(0.9F, 1.4F));
	public static final EntityType<AerwhaleEntity> AERWHALE = entity("aerwhale", EntityType.Builder.<AerwhaleEntity>create(AerwhaleEntity::new, EntityClassification.CREATURE).size(3.0F, 3.0F).immuneToFire());
	public static final EntityType<WhirlwindEntity> WHIRLWIND = entity("whirlwind", EntityType.Builder.create(WhirlwindEntity::new, EntityClassification.MONSTER).size(0.6F, 0.8F));

	@net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = Aether.MODID, bus = EventBusSubscriber.Bus.MOD)
	public static class Registration {

		@SubscribeEvent
		public static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> event) {
			event.getRegistry().registerAll(new EntityType<?>[] {
					MIMIC,
					SENTRY,
					ZEPHYR,
					COCKATRICE,
					AECHOR_PLANT,
					//VALKYRIE,
					//FIRE_MINION,
					PHYG,
					FLYING_COW,
					SHEEPUFF,
					MOA,
					FLOATING_BLOCK,
					LIGHTNING_KNIFE,
					ZEPHYR_SNOWBALL,
					GOLDEN_DART,
					ENCHANTED_DART,
					POISON_DART,
					AERWHALE,
					WHIRLWIND
			});
		}

		public static <E extends Entity> EntityType<E> entity(String name, EntityType.Builder<E> entityTypeBuilder) {
			return entity(name, entityTypeBuilder.build(name));
		}

		public static <E extends Entity> EntityType<E> entity(String name, EntityType<E> entityType) {
			entityType.setRegistryName(name);
			return entityType;
		}

	}

}