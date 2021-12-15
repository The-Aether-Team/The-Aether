package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.block.FloatingBlockEntity;
import com.gildedgames.aether.common.entity.block.TNTPresentEntity;
import com.gildedgames.aether.common.entity.miscellaneous.CloudMinionEntity;
import com.gildedgames.aether.common.entity.miscellaneous.ColdParachuteEntity;
import com.gildedgames.aether.common.entity.miscellaneous.GoldenParachuteEntity;
import com.gildedgames.aether.common.entity.monster.AechorPlantEntity;
import com.gildedgames.aether.common.entity.monster.CockatriceEntity;
import com.gildedgames.aether.common.entity.monster.FireMinionEntity;
import com.gildedgames.aether.common.entity.monster.MimicEntity;
import com.gildedgames.aether.common.entity.monster.SentryEntity;
import com.gildedgames.aether.common.entity.monster.WhirlwindEntity;
import com.gildedgames.aether.common.entity.monster.ZephyrEntity;
import com.gildedgames.aether.common.entity.passive.AerbunnyEntity;
import com.gildedgames.aether.common.entity.passive.AerwhaleEntity;
import com.gildedgames.aether.common.entity.passive.AetherAnimalEntity;
import com.gildedgames.aether.common.entity.passive.FlyingCowEntity;
import com.gildedgames.aether.common.entity.passive.MoaEntity;
import com.gildedgames.aether.common.entity.passive.PhygEntity;
import com.gildedgames.aether.common.entity.passive.SheepuffEntity;
import com.gildedgames.aether.common.entity.projectile.PoisonNeedleEntity;
import com.gildedgames.aether.common.entity.projectile.ZephyrSnowballEntity;
import com.gildedgames.aether.common.entity.projectile.crystal.CloudCrystalEntity;
import com.gildedgames.aether.common.entity.projectile.dart.EnchantedDartEntity;
import com.gildedgames.aether.common.entity.projectile.dart.GoldenDartEntity;
import com.gildedgames.aether.common.entity.projectile.dart.PoisonDartEntity;
import com.gildedgames.aether.common.entity.projectile.weapon.HammerProjectileEntity;
import com.gildedgames.aether.common.entity.projectile.weapon.LightningKnifeEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Aether.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherEntityTypes
{
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Aether.MODID);

	// Passive Mobs
	public static final EntityType<PhygEntity> PHYG_TYPE = EntityType.Builder.<PhygEntity>of(PhygEntity::new, EntityClassification.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(10).build("phyg");
	public static final RegistryObject<EntityType<PhygEntity>> PHYG = ENTITIES.register("phyg", () -> PHYG_TYPE);

	public static final EntityType<FlyingCowEntity> FLYING_COW_TYPE = EntityType.Builder.<FlyingCowEntity>of(FlyingCowEntity::new, EntityClassification.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10).build("flying_cow");
	public static final RegistryObject<EntityType<FlyingCowEntity>> FLYING_COW = ENTITIES.register("flying_cow", () -> FLYING_COW_TYPE);

	public static final EntityType<SheepuffEntity> SHEEPUFF_TYPE = EntityType.Builder.<SheepuffEntity>of(SheepuffEntity::new, EntityClassification.CREATURE).sized(0.9F, 1.3F).clientTrackingRange(10).build("sheepuff");
	public static final RegistryObject<EntityType<SheepuffEntity>> SHEEPUFF = ENTITIES.register("sheepuff", () -> SHEEPUFF_TYPE);

	public static final EntityType<MoaEntity> MOA_TYPE = EntityType.Builder.<MoaEntity>of(MoaEntity::new, EntityClassification.CREATURE).sized(1.0F,  2.0F).build("moa");
	public static final RegistryObject<EntityType<MoaEntity>> MOA = ENTITIES.register("moa", () -> MOA_TYPE);

	public static final EntityType<AerwhaleEntity> AERWHALE_TYPE = EntityType.Builder.<AerwhaleEntity>of(AerwhaleEntity::new, EntityClassification.CREATURE).sized(3.0F, 3.0F).fireImmune().build("aerwhale");
	public static final RegistryObject<EntityType<AerwhaleEntity>> AERWHALE = ENTITIES.register("aerwhale", () -> AERWHALE_TYPE);

	public static final EntityType<AerbunnyEntity> AERBUNNY_TYPE = EntityType.Builder.<AerbunnyEntity>of(AerbunnyEntity::new, EntityClassification.CREATURE).sized(0.4F, 0.4F).build("aerbunny");
	public static final RegistryObject<EntityType<AerbunnyEntity>> AERBUNNY = ENTITIES.register("aerbunny", () -> AERBUNNY_TYPE);

	// Hostile Mobs
	//Swet

	public static final EntityType<WhirlwindEntity> WHIRLWIND_TYPE = EntityType.Builder.<WhirlwindEntity>of(WhirlwindEntity::new, EntityClassification.MONSTER).sized(0.6F, 0.8F).build("whirlwind");
	public static final RegistryObject<EntityType<WhirlwindEntity>> WHIRLWIND = ENTITIES.register("whirlwind", () -> WHIRLWIND_TYPE);

	public static final EntityType<AechorPlantEntity> AECHOR_PLANT_TYPE = EntityType.Builder.<AechorPlantEntity>of(AechorPlantEntity::new, EntityClassification.MONSTER).sized(1.0F, 1.0F).build("aechor_plant");
	public static final RegistryObject<EntityType<AechorPlantEntity>> AECHOR_PLANT = ENTITIES.register("aechor_plant", () -> AECHOR_PLANT_TYPE);

	public static final EntityType<CockatriceEntity> COCKATRICE_TYPE = EntityType.Builder.<CockatriceEntity>of(CockatriceEntity::new, EntityClassification.MONSTER).sized(1.0F, 2.0F).build("cockatrice");
	public static final RegistryObject<EntityType<CockatriceEntity>> COCKATRICE = ENTITIES.register("cockatrice", () -> COCKATRICE_TYPE);

	public static final EntityType<ZephyrEntity> ZEPHYR_TYPE = EntityType.Builder.<ZephyrEntity>of(ZephyrEntity::new, EntityClassification.MONSTER).sized(4.0F, 4.0F).build("zephyr");
	public static final RegistryObject<EntityType<ZephyrEntity>> ZEPHYR = ENTITIES.register("zephyr", () -> ZEPHYR_TYPE);

	public static final EntityType<SentryEntity> SENTRY_TYPE = EntityType.Builder.<SentryEntity>of(SentryEntity::new, EntityClassification.MONSTER).sized(2.0F, 2.0F).build("sentry");
	public static final RegistryObject<EntityType<SentryEntity>> SENTRY = ENTITIES.register("sentry", () -> SENTRY_TYPE);

	public static final EntityType<MimicEntity> MIMIC_TYPE = EntityType.Builder.<MimicEntity>of(MimicEntity::new, EntityClassification.MONSTER).sized(1.0F, 2.0F).build("mimic");
	public static final RegistryObject<EntityType<MimicEntity>> MIMIC = ENTITIES.register("mimic", () -> MIMIC_TYPE);

	//public static final EntityType<ValkyrieEntity> VALKYRIE_TYPE = EntityType.Builder.<ValkyrieEntity>of(ValkyrieEntity::new, EntityClassification.MONSTER).sized(0.8F, 1.95F).build("valkyrie");
	//public static final RegistryObject<EntityType<ValkyrieEntity>> VALKYRIE = ENTITIES.register("valkyrie", () -> VALKYRIE_TYPE);

	public static final EntityType<FireMinionEntity> FIRE_MINION_TYPE = EntityType.Builder.<FireMinionEntity>of(FireMinionEntity::new, EntityClassification.MONSTER).sized(0.8F, 1.95F).build("fire_minion");
	public static final RegistryObject<EntityType<FireMinionEntity>> FIRE_MINION = ENTITIES.register("fire_minion", () -> FIRE_MINION_TYPE);

	public static final EntityType<CloudMinionEntity> CLOUD_MINION_TYPE = EntityType.Builder.<CloudMinionEntity>of(CloudMinionEntity::new, EntityClassification.MISC).sized(0.75F, 0.75F).build("cloud_minion");
	public static final RegistryObject<EntityType<CloudMinionEntity>> CLOUD_MINION = ENTITIES.register("cloud_minion", () -> CLOUD_MINION_TYPE);

	public static final EntityType<ColdParachuteEntity> COLD_PARACHUTE_TYPE = EntityType.Builder.of(ColdParachuteEntity::new, EntityClassification.MISC).sized(1.0F, 1.0F).build("cold_parachute");
	public static final RegistryObject<EntityType<ColdParachuteEntity>> COLD_PARACHUTE = ENTITIES.register("cold_parachute", () -> COLD_PARACHUTE_TYPE);

	public static final EntityType<GoldenParachuteEntity> GOLDEN_PARACHUTE_TYPE = EntityType.Builder.of(GoldenParachuteEntity::new, EntityClassification.MISC).sized(1.0F, 1.0F).build("golden_parachute");
	public static final RegistryObject<EntityType<GoldenParachuteEntity>> GOLDEN_PARACHUTE = ENTITIES.register("golden_parachute", () -> GOLDEN_PARACHUTE_TYPE);

	public static final EntityType<FloatingBlockEntity> FLOATING_BLOCK_TYPE = EntityType.Builder.<FloatingBlockEntity>of(FloatingBlockEntity::new, EntityClassification.MISC).sized(1.0F, 1.0F).updateInterval(1).build("floating_block");
	public static final RegistryObject<EntityType<FloatingBlockEntity>> FLOATING_BLOCK = ENTITIES.register("floating_block", () -> FLOATING_BLOCK_TYPE);

	public static final EntityType<TNTPresentEntity> TNT_PRESENT_TYPE = EntityType.Builder.<TNTPresentEntity>of(TNTPresentEntity::new, EntityClassification.MISC).sized(1.0F, 1.0F).build("tnt_present");
	public static final RegistryObject<EntityType<TNTPresentEntity>> TNT_PRESENT = ENTITIES.register("tnt_present", () -> TNT_PRESENT_TYPE);

	// Projectiles
	
	public static final EntityType<ZephyrSnowballEntity> ZEPHYR_SNOWBALL_TYPE = EntityType.Builder.<ZephyrSnowballEntity>of(ZephyrSnowballEntity::new, EntityClassification.MISC).sized(1.0F, 1.0F).build("zephyr_snowball");
	public static final RegistryObject<EntityType<ZephyrSnowballEntity>> ZEPHYR_SNOWBALL = ENTITIES.register("zephyr_snowball", () -> ZEPHYR_SNOWBALL_TYPE);

	public static final EntityType<CloudCrystalEntity> CLOUD_CRYSTAL_TYPE = EntityType.Builder.<CloudCrystalEntity>of(CloudCrystalEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build("cloud_crystal");
	public static final RegistryObject<EntityType<CloudCrystalEntity>> CLOUD_CRYSTAL = ENTITIES.register("cloud_crystal", () -> CLOUD_CRYSTAL_TYPE);

	public static final EntityType<GoldenDartEntity> GOLDEN_DART_TYPE = EntityType.Builder.<GoldenDartEntity>of(GoldenDartEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build("golden_dart");
	public static final RegistryObject<EntityType<GoldenDartEntity>> GOLDEN_DART = ENTITIES.register("golden_dart", () -> GOLDEN_DART_TYPE);

	public static final EntityType<PoisonDartEntity> POISON_DART_TYPE = EntityType.Builder.<PoisonDartEntity>of(PoisonDartEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build("poison_dart");
	public static final RegistryObject<EntityType<PoisonDartEntity>> POISON_DART = ENTITIES.register("poison_dart", () -> POISON_DART_TYPE);

	public static final EntityType<EnchantedDartEntity> ENCHANTED_DART_TYPE = EntityType.Builder.<EnchantedDartEntity>of(EnchantedDartEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build("enchanted_dart");
	public static final RegistryObject<EntityType<EnchantedDartEntity>> ENCHANTED_DART = ENTITIES.register("enchanted_dart", () -> ENCHANTED_DART_TYPE);

	public static final EntityType<PoisonNeedleEntity> POISON_NEEDLE_TYPE = EntityType.Builder.<PoisonNeedleEntity>of(PoisonNeedleEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build("poison_needle");
	public static final RegistryObject<EntityType<PoisonNeedleEntity>> POISON_NEEDLE = ENTITIES.register("poison_needle", () -> POISON_NEEDLE_TYPE);

	public static final EntityType<LightningKnifeEntity> LIGHTNING_KNIFE_TYPE = EntityType.Builder.<LightningKnifeEntity>of(LightningKnifeEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).build("lightning_knife");
	public static final RegistryObject<EntityType<LightningKnifeEntity>> LIGHTNING_KNIFE = ENTITIES.register("lightning_knife", () -> LIGHTNING_KNIFE_TYPE);

	public static final EntityType<HammerProjectileEntity> HAMMER_PROJECTILE_TYPE = EntityType.Builder.<HammerProjectileEntity>of(HammerProjectileEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).build("hammer_projectile");
	public static final RegistryObject<EntityType<HammerProjectileEntity>> HAMMER_PROJECTILE = ENTITIES.register("hammer_projectile", () -> HAMMER_PROJECTILE_TYPE);

	public static void registerSpawnPlacements() {
		EntitySpawnPlacementRegistry.register(AetherEntityTypes.PHYG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);
		EntitySpawnPlacementRegistry.register(AetherEntityTypes.FLYING_COW.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);
		EntitySpawnPlacementRegistry.register(AetherEntityTypes.SHEEPUFF.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);
		EntitySpawnPlacementRegistry.register(AetherEntityTypes.MOA.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);
		EntitySpawnPlacementRegistry.register(AetherEntityTypes.AERBUNNY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);

		EntitySpawnPlacementRegistry.register(AetherEntityTypes.WHIRLWIND.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WhirlwindEntity::canWhirlwindSpawn);
		EntitySpawnPlacementRegistry.register(AetherEntityTypes.AECHOR_PLANT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AechorPlantEntity::canAechorSpawn);
		EntitySpawnPlacementRegistry.register(AetherEntityTypes.COCKATRICE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CockatriceEntity::canCockatriceSpawn);
		EntitySpawnPlacementRegistry.register(AetherEntityTypes.ZEPHYR.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZephyrEntity::canZephyrSpawn);
	}

	@SubscribeEvent
	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(AetherEntityTypes.PHYG.get(), PhygEntity.createMobAttributes().build());
		event.put(AetherEntityTypes.FLYING_COW.get(), FlyingCowEntity.createMobAttributes().build());
		event.put(AetherEntityTypes.SHEEPUFF.get(), SheepuffEntity.createMobAttributes().build());
		event.put(AetherEntityTypes.MOA.get(), MoaEntity.createMobAttributes().build());
		event.put(AetherEntityTypes.AERWHALE.get(), AerwhaleEntity.createMobAttributes().build());
		event.put(AetherEntityTypes.AERBUNNY.get(), AerbunnyEntity.createMobAttributes().build());

		//event.put(AetherEntityTypes.SWET.get(), SwetEntity.registerAttributes().build());
		event.put(AetherEntityTypes.WHIRLWIND.get(), WhirlwindEntity.createMobAttributes().build());
		event.put(AetherEntityTypes.AECHOR_PLANT.get(), AechorPlantEntity.createMobAttributes().build());
		event.put(AetherEntityTypes.COCKATRICE.get(), CockatriceEntity.createMobAttributes().build());
		event.put(AetherEntityTypes.ZEPHYR.get(), ZephyrEntity.createMobAttributes().build());

		event.put(AetherEntityTypes.SENTRY.get(), SentryEntity.createMobAttributes().build());
		event.put(AetherEntityTypes.MIMIC.get(), MimicEntity.createMobAttributes().build());
		//event.put(AetherEntityTypes.VALKYRIE.get(), ValkyrieEntity.createMobAttributes().build());
		event.put(AetherEntityTypes.FIRE_MINION.get(), FireMinionEntity.createMobAttributes().build());

		event.put(AetherEntityTypes.CLOUD_MINION.get(), CloudMinionEntity.registerAttributes().build());
	}
}