package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.AetherAnimalEntity;
import com.gildedgames.aether.common.entity.block.FloatingBlockEntity;
import com.gildedgames.aether.common.entity.block.TNTPresentEntity;
import com.gildedgames.aether.common.entity.passive.AerwhaleEntity;
import com.gildedgames.aether.common.entity.passive.FlyingCowEntity;
import com.gildedgames.aether.common.entity.passive.MoaEntity;
import com.gildedgames.aether.common.entity.passive.PhygEntity;
import com.gildedgames.aether.common.entity.passive.SheepuffEntity;

import com.gildedgames.aether.common.entity.monster.*;
import com.gildedgames.aether.common.entity.projectile.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.monster.SlimeEntity;
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

	public static final EntityType<PhygEntity> PHYG_TYPE = EntityType.Builder.create(PhygEntity::new, EntityClassification.CREATURE).size(0.9F, 0.9F).build("phyg");
	public static final RegistryObject<EntityType<PhygEntity>> PHYG = ENTITIES.register("phyg", () -> PHYG_TYPE);

	public static final EntityType<FlyingCowEntity> FLYING_COW_TYPE = EntityType.Builder.<FlyingCowEntity>create(FlyingCowEntity::new, EntityClassification.CREATURE).size(0.9F, 1.4F).build("flying_cow");
	public static final RegistryObject<EntityType<FlyingCowEntity>> FLYING_COW = ENTITIES.register("flying_cow", () -> FLYING_COW_TYPE);

	public static final EntityType<SheepuffEntity> SHEEPUFF_TYPE = EntityType.Builder.<SheepuffEntity>create(SheepuffEntity::new, EntityClassification.CREATURE).size(0.9F, 1.4F).build("sheepuff");
	public static final RegistryObject<EntityType<SheepuffEntity>> SHEEPUFF = ENTITIES.register("sheepuff", () -> SHEEPUFF_TYPE);

	public static final EntityType<MoaEntity> MOA_TYPE = EntityType.Builder.<MoaEntity>create(MoaEntity::new, EntityClassification.CREATURE).size(1.0F,  2.0F).build("moa");
	public static final RegistryObject<EntityType<MoaEntity>> MOA = ENTITIES.register("moa", () -> MOA_TYPE);

	public static final EntityType<AerwhaleEntity> AERWHALE_TYPE = EntityType.Builder.<AerwhaleEntity>create(AerwhaleEntity::new, EntityClassification.CREATURE).size(3.0F, 3.0F).immuneToFire().build("aerwhale");
	public static final RegistryObject<EntityType<AerwhaleEntity>> AERWHALE = ENTITIES.register("aerwhale", () -> AERWHALE_TYPE);

	public static final EntityType<WhirlwindEntity> WHIRLWIND_TYPE = EntityType.Builder.create(WhirlwindEntity::new, EntityClassification.MONSTER).size(0.6F, 0.8F).build("whirlwind");
	public static final RegistryObject<EntityType<WhirlwindEntity>> WHIRLWIND = ENTITIES.register("whirlwind", () -> WHIRLWIND_TYPE);

	public static final EntityType<AechorPlantEntity> AECHOR_PLANT_TYPE = EntityType.Builder.create(AechorPlantEntity::new, EntityClassification.MONSTER).size(1.0F, 1.0F).build("aechor_plant");
	public static final RegistryObject<EntityType<AechorPlantEntity>> AECHOR_PLANT = ENTITIES.register("aechor_plant", () -> AECHOR_PLANT_TYPE);

	public static final EntityType<CockatriceEntity> COCKATRICE_TYPE = EntityType.Builder.<CockatriceEntity>create(CockatriceEntity::new, EntityClassification.MONSTER).size(1.0F, 2.0F).build("cockatrice");
	public static final RegistryObject<EntityType<CockatriceEntity>> COCKATRICE = ENTITIES.register("cockatrice", () -> COCKATRICE_TYPE);

	public static final EntityType<ZephyrEntity> ZEPHYR_TYPE = EntityType.Builder.<ZephyrEntity>create(ZephyrEntity::new, EntityClassification.MONSTER).size(4.0F, 4.0F).build("zephyr");
	public static final RegistryObject<EntityType<ZephyrEntity>> ZEPHYR = ENTITIES.register("zephyr", () -> ZEPHYR_TYPE);

	public static final EntityType<SentryEntity> SENTRY_TYPE = EntityType.Builder.<SentryEntity>create(SentryEntity::new, EntityClassification.MONSTER).size(2.0F, 2.0F).build("sentry");
	public static final RegistryObject<EntityType<SentryEntity>> SENTRY = ENTITIES.register("sentry", () -> SENTRY_TYPE);

	public static final EntityType<MimicEntity> MIMIC_TYPE = EntityType.Builder.<MimicEntity>create(MimicEntity::new, EntityClassification.MONSTER).size(1.0F, 2.0F).build("mimic");
	public static final RegistryObject<EntityType<MimicEntity>> MIMIC = ENTITIES.register("mimic", () -> MIMIC_TYPE);

	//public static final EntityType<ValkyrieEntity> VALKYRIE = entity("valkyrie", EntityType.Builder.<ValkyrieEntity>create(ValkyrieEntity::new, EntityClassification.MONSTER).size(??????));
	//public static final EntityType<FireMinionEntity> FIRE_MINION = entity("fire_minion", EntityType.Builder.<FireMinionEntity>create(FireMinionEntity::new, EntityClassification.MONSTER).size(??????));

	public static final EntityType<FloatingBlockEntity> FLOATING_BLOCK_TYPE = EntityType.Builder.<FloatingBlockEntity>create(FloatingBlockEntity::new, EntityClassification.MISC).size(0.98F, 0.98F).build("floating_block");
	public static final RegistryObject<EntityType<FloatingBlockEntity>> FLOATING_BLOCK = ENTITIES.register("floating_block", () -> FLOATING_BLOCK_TYPE);
	//.setCustomClientFactory((spawnEntity, world) -> new FloatingBlockEntity(world))

	public static final EntityType<TNTPresentEntity> TNT_PRESENT_TYPE = EntityType.Builder.<TNTPresentEntity>create(TNTPresentEntity::new, EntityClassification.MISC).size(0.98F, 0.98F).build("tnt_present");
	public static final RegistryObject<EntityType<TNTPresentEntity>> TNT_PRESENT = ENTITIES.register("tnt_present", () -> TNT_PRESENT_TYPE);

	public static final EntityType<LightningKnifeEntity> LIGHTNING_KNIFE_TYPE = EntityType.Builder.<LightningKnifeEntity>create(LightningKnifeEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).build("lightning_knife");
	public static final RegistryObject<EntityType<LightningKnifeEntity>> LIGHTNING_KNIFE = ENTITIES.register("lightning_knife", () -> LIGHTNING_KNIFE_TYPE);
	//.setCustomClientFactory((spawnEntity, world) -> new LightningKnifeEntity(world)

	public static final EntityType<ZephyrSnowballEntity> ZEPHYR_SNOWBALL_TYPE = EntityType.Builder.<ZephyrSnowballEntity>create(ZephyrSnowballEntity::new, EntityClassification.MISC).size(1.0F, 1.0F).build("zephyr_snowball");
	public static final RegistryObject<EntityType<ZephyrSnowballEntity>> ZEPHYR_SNOWBALL = ENTITIES.register("zephyr_snowball", () -> ZEPHYR_SNOWBALL_TYPE);

	public static final EntityType<GoldenDartEntity> GOLDEN_DART_TYPE = EntityType.Builder.<GoldenDartEntity>create(GoldenDartEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("golden_dart");
	public static final RegistryObject<EntityType<GoldenDartEntity>> GOLDEN_DART = ENTITIES.register("golden_dart", () -> GOLDEN_DART_TYPE);

	public static final EntityType<PoisonDartEntity> POISON_DART_TYPE = EntityType.Builder.<PoisonDartEntity>create(PoisonDartEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("poison_dart");
	public static final RegistryObject<EntityType<PoisonDartEntity>> POISON_DART = ENTITIES.register("poison_dart", () -> POISON_DART_TYPE);

	public static final EntityType<EnchantedDartEntity> ENCHANTED_DART_TYPE = EntityType.Builder.<EnchantedDartEntity>create(EnchantedDartEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("enchanted_dart");
	public static final RegistryObject<EntityType<EnchantedDartEntity>> ENCHANTED_DART = ENTITIES.register("enchanted_dart", () -> ENCHANTED_DART_TYPE);

	public static final EntityType<PhoenixArrowEntity> PHOENIX_ARROW_TYPE = EntityType.Builder.<PhoenixArrowEntity>create(PhoenixArrowEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("phoenix_arrow");
	public static final RegistryObject<EntityType<PhoenixArrowEntity>> PHOENIX_ARROW = ENTITIES.register("phoenix_arrow", () -> PHOENIX_ARROW_TYPE);

	public static final EntityType<SpectralPhoenixArrowEntity> SPECTRAL_PHOENIX_ARROW_TYPE = EntityType.Builder.<SpectralPhoenixArrowEntity>create(SpectralPhoenixArrowEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("spectral_phoenix_arrow");
	public static final RegistryObject<EntityType<SpectralPhoenixArrowEntity>> SPECTRAL_PHOENIX_ARROW = ENTITIES.register("spectral_phoenix_arrow", () -> SPECTRAL_PHOENIX_ARROW_TYPE);

	public static final EntityType<HammerProjectileEntity> HAMMER_PROJECTILE_TYPE = EntityType.Builder.<HammerProjectileEntity>create(HammerProjectileEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).build("hammer_projectile");
	public static final RegistryObject<EntityType<HammerProjectileEntity>> HAMMER_PROJECTILE = ENTITIES.register("hammer_projectile", () -> HAMMER_PROJECTILE_TYPE);


	public static void registerSpawnPlacements() {
		EntitySpawnPlacementRegistry.register(AetherEntityTypes.PHYG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);
		EntitySpawnPlacementRegistry.register(AetherEntityTypes.FLYING_COW.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);
		EntitySpawnPlacementRegistry.register(AetherEntityTypes.SHEEPUFF.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);
		EntitySpawnPlacementRegistry.register(AetherEntityTypes.MOA.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);

		EntitySpawnPlacementRegistry.register(AetherEntityTypes.WHIRLWIND.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WhirlwindEntity::canWhirlwindSpawn);
		EntitySpawnPlacementRegistry.register(AetherEntityTypes.AECHOR_PLANT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AechorPlantEntity::canAechorSpawn);
		EntitySpawnPlacementRegistry.register(AetherEntityTypes.COCKATRICE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CockatriceEntity::canCockatriceSpawn);
		EntitySpawnPlacementRegistry.register(AetherEntityTypes.ZEPHYR.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZephyrEntity::canZephyrSpawn);
	}

	@SubscribeEvent
	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(AetherEntityTypes.PHYG.get(), PhygEntity.registerAttributes().create());
		event.put(AetherEntityTypes.FLYING_COW.get(), FlyingCowEntity.registerAttributes().create());
		event.put(AetherEntityTypes.SHEEPUFF.get(), SheepuffEntity.registerAttributes().create());
		event.put(AetherEntityTypes.MOA.get(), MoaEntity.registerAttributes().create());
		event.put(AetherEntityTypes.AERWHALE.get(), AerwhaleEntity.registerAttributes().create());

		event.put(AetherEntityTypes.WHIRLWIND.get(), WhirlwindEntity.registerAttributes().create());
		event.put(AetherEntityTypes.AECHOR_PLANT.get(), AechorPlantEntity.registerAttributes().create());
		event.put(AetherEntityTypes.COCKATRICE.get(), CockatriceEntity.registerAttributes().create());
		event.put(AetherEntityTypes.ZEPHYR.get(), ZephyrEntity.registerAttributes().create());

		event.put(AetherEntityTypes.SENTRY.get(), SlimeEntity.registerAttributes().create());
		event.put(AetherEntityTypes.MIMIC.get(), MimicEntity.registerAttributes().create());
	}
}