package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.monster.*;
import com.gildedgames.aether.common.entity.monster.dungeon.Mimic;
import com.gildedgames.aether.common.entity.monster.dungeon.SentryEntity;
import com.gildedgames.aether.common.entity.passive.AetherAnimalEntity;
import com.gildedgames.aether.common.entity.block.FloatingBlockEntity;
import com.gildedgames.aether.common.entity.block.TNTPresentEntity;
import com.gildedgames.aether.common.entity.miscellaneous.CloudMinionEntity;
import com.gildedgames.aether.common.entity.miscellaneous.ColdParachuteEntity;
import com.gildedgames.aether.common.entity.miscellaneous.GoldenParachuteEntity;
import com.gildedgames.aether.common.entity.monster.dungeon.FireMinionEntity;
import com.gildedgames.aether.common.entity.passive.AerbunnyEntity;
import com.gildedgames.aether.common.entity.passive.AerwhaleEntity;
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

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Aether.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Aether.MODID);

    // Passive Mobs
    public static final RegistryObject<EntityType<PhygEntity>> PHYG = ENTITIES.register("phyg",
            () -> EntityType.Builder.<PhygEntity>of(PhygEntity::new, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(10).build("phyg"));

    public static final RegistryObject<EntityType<FlyingCowEntity>> FLYING_COW = ENTITIES.register("flying_cow",
            () -> EntityType.Builder.<FlyingCowEntity>of(FlyingCowEntity::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10).build("flying_cow"));

    public static final RegistryObject<EntityType<SheepuffEntity>> SHEEPUFF = ENTITIES.register("sheepuff",
            () -> EntityType.Builder.<SheepuffEntity>of(SheepuffEntity::new, MobCategory.CREATURE).sized(0.9F, 1.3F).clientTrackingRange(10).build("sheepuff"));

    public static final RegistryObject<EntityType<MoaEntity>> MOA = ENTITIES.register("moa",
            () -> EntityType.Builder.<MoaEntity>of(MoaEntity::new, MobCategory.CREATURE).sized(1.0F, 2.0F).build("moa"));

    public static final RegistryObject<EntityType<AerwhaleEntity>> AERWHALE = ENTITIES.register("aerwhale",
            () -> EntityType.Builder.<AerwhaleEntity>of(AerwhaleEntity::new, MobCategory.CREATURE).sized(3.0F, 3.0F).fireImmune().build("aerwhale"));

    public static final RegistryObject<EntityType<AerbunnyEntity>> AERBUNNY = ENTITIES.register("aerbunny",
            () -> EntityType.Builder.<AerbunnyEntity>of(AerbunnyEntity::new, MobCategory.CREATURE).sized(0.4F, 0.4F).build("aerbunny"));

    // Hostile Mobs
    public static final RegistryObject<EntityType<Swet>> BLUE_SWET = ENTITIES.register("blue_swet",
            () -> EntityType.Builder.<Swet>of(Swet::new, MobCategory.MONSTER).sized(0.8F, 0.8F).build("blue_swet"));

    public static final RegistryObject<EntityType<Swet>> GOLDEN_SWET = ENTITIES.register("golden_swet",
            () -> EntityType.Builder.<Swet>of(Swet::new, MobCategory.MONSTER).sized(0.8F, 0.8F).build("golden_swet"));

    public static final RegistryObject<EntityType<Whirlwind>> WHIRLWIND = ENTITIES.register("whirlwind",
            () -> EntityType.Builder.<Whirlwind>of(PassiveWhirlwind::new, MobCategory.MONSTER).sized(0.6F, 0.8F).build("whirlwind"));

    public static final RegistryObject<EntityType<Whirlwind>> EVIL_WHIRLWIND = ENTITIES.register("evil_whirlwind",
            () -> EntityType.Builder.<Whirlwind>of(EvilWhirlwind::new, MobCategory.MONSTER).sized(0.6F, 0.8F).build("evil_whirlwind"));

    public static final RegistryObject<EntityType<AechorPlantEntity>> AECHOR_PLANT = ENTITIES.register("aechor_plant",
            () -> EntityType.Builder.<AechorPlantEntity>of(AechorPlantEntity::new, MobCategory.MONSTER).sized(1.0F, 1.0F).build("aechor_plant"));

    public static final RegistryObject<EntityType<CockatriceEntity>> COCKATRICE = ENTITIES.register("cockatrice",
            () -> EntityType.Builder.<CockatriceEntity>of(CockatriceEntity::new, MobCategory.MONSTER).sized(1.0F, 2.0F).build("cockatrice"));

    public static final RegistryObject<EntityType<Zephyr>> ZEPHYR = ENTITIES.register("zephyr",
            () -> EntityType.Builder.<Zephyr>of(Zephyr::new, MobCategory.MONSTER).sized(4.0F, 4.0F).build("zephyr"));

    // Dungeon

    public static final RegistryObject<EntityType<SentryEntity>> SENTRY = ENTITIES.register("sentry",
            () -> EntityType.Builder.<SentryEntity>of(SentryEntity::new, MobCategory.MONSTER).sized(2.0F, 2.0F).build("sentry"));

    public static final RegistryObject<EntityType<Mimic>> MIMIC = ENTITIES.register("mimic",
            () -> EntityType.Builder.<Mimic>of(Mimic::new, MobCategory.MONSTER).sized(1.0F, 2.0F).build("mimic"));

    //public static final EntityType<ValkyrieEntity> VALKYRIE_TYPE = EntityType.Builder.<ValkyrieEntity>of(ValkyrieEntity::new, EntityClassification.MONSTER).sized(0.8F, 1.95F).build("valkyrie");
    //public static final RegistryObject<EntityType<ValkyrieEntity>> VALKYRIE = ENTITIES.register("valkyrie", () -> VALKYRIE_TYPE);

    public static final RegistryObject<EntityType<FireMinionEntity>> FIRE_MINION = ENTITIES.register("fire_minion",
            () -> EntityType.Builder.<FireMinionEntity>of(FireMinionEntity::new, MobCategory.MONSTER).sized(0.8F, 1.95F).build("fire_minion"));


    // Miscellaneous

    public static final RegistryObject<EntityType<CloudMinionEntity>> CLOUD_MINION = ENTITIES.register("cloud_minion",
            () -> EntityType.Builder.<CloudMinionEntity>of(CloudMinionEntity::new, MobCategory.MISC).sized(0.75F, 0.75F).build("cloud_minion"));

    public static final RegistryObject<EntityType<ColdParachuteEntity>> COLD_PARACHUTE = ENTITIES.register("cold_parachute",
            () -> EntityType.Builder.of(ColdParachuteEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).build("cold_parachute"));

    public static final RegistryObject<EntityType<GoldenParachuteEntity>> GOLDEN_PARACHUTE = ENTITIES.register("golden_parachute",
            () -> EntityType.Builder.of(GoldenParachuteEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).build("golden_parachute"));

    public static final RegistryObject<EntityType<FloatingBlockEntity>> FLOATING_BLOCK = ENTITIES.register("floating_block",
            () -> EntityType.Builder.<FloatingBlockEntity>of(FloatingBlockEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).updateInterval(1).build("floating_block"));

    public static final RegistryObject<EntityType<TNTPresentEntity>> TNT_PRESENT = ENTITIES.register("tnt_present",
            () -> EntityType.Builder.<TNTPresentEntity>of(TNTPresentEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).build("tnt_present"));

    // Projectiles

    public static final RegistryObject<EntityType<ZephyrSnowballEntity>> ZEPHYR_SNOWBALL = ENTITIES.register("zephyr_snowball",
            () -> EntityType.Builder.<ZephyrSnowballEntity>of(ZephyrSnowballEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).build("zephyr_snowball"));

    public static final RegistryObject<EntityType<CloudCrystalEntity>> CLOUD_CRYSTAL = ENTITIES.register("cloud_crystal",
            () -> EntityType.Builder.<CloudCrystalEntity>of(CloudCrystalEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).build("cloud_crystal"));

    public static final RegistryObject<EntityType<GoldenDartEntity>> GOLDEN_DART = ENTITIES.register("golden_dart",
            () -> EntityType.Builder.<GoldenDartEntity>of(GoldenDartEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).build("golden_dart"));

    public static final RegistryObject<EntityType<PoisonDartEntity>> POISON_DART = ENTITIES.register("poison_dart",
            () -> EntityType.Builder.<PoisonDartEntity>of(PoisonDartEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).build("poison_dart"));

    public static final RegistryObject<EntityType<EnchantedDartEntity>> ENCHANTED_DART = ENTITIES.register("enchanted_dart",
            () -> EntityType.Builder.<EnchantedDartEntity>of(EnchantedDartEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).build("enchanted_dart"));

    public static final RegistryObject<EntityType<PoisonNeedleEntity>> POISON_NEEDLE = ENTITIES.register("poison_needle",
            () -> EntityType.Builder.<PoisonNeedleEntity>of(PoisonNeedleEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).build("poison_needle"));

    public static final RegistryObject<EntityType<LightningKnifeEntity>> LIGHTNING_KNIFE = ENTITIES.register("lightning_knife",
            () -> EntityType.Builder.<LightningKnifeEntity>of(LightningKnifeEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build("lightning_knife"));

    public static final RegistryObject<EntityType<HammerProjectileEntity>> HAMMER_PROJECTILE = ENTITIES.register("hammer_projectile",
            () -> EntityType.Builder.<HammerProjectileEntity>of(HammerProjectileEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build("hammer_projectile"));

    public static void registerSpawnPlacements() {
        SpawnPlacements.register(AetherEntityTypes.PHYG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);
        SpawnPlacements.register(AetherEntityTypes.FLYING_COW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);
        SpawnPlacements.register(AetherEntityTypes.SHEEPUFF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);
        SpawnPlacements.register(AetherEntityTypes.MOA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);
        SpawnPlacements.register(AetherEntityTypes.AERBUNNY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimalEntity::canAetherAnimalSpawn);

        SpawnPlacements.register(AetherEntityTypes.BLUE_SWET.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.GOLDEN_SWET.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.WHIRLWIND.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Whirlwind::canWhirlwindSpawn);
        SpawnPlacements.register(AetherEntityTypes.EVIL_WHIRLWIND.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Whirlwind::canWhirlwindSpawn);
        SpawnPlacements.register(AetherEntityTypes.AECHOR_PLANT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AechorPlantEntity::canAechorSpawn);
        SpawnPlacements.register(AetherEntityTypes.COCKATRICE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CockatriceEntity::canCockatriceSpawn);
        SpawnPlacements.register(AetherEntityTypes.ZEPHYR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zephyr::canZephyrSpawn);
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(AetherEntityTypes.PHYG.get(), PhygEntity.createMobAttributes().build());
        event.put(AetherEntityTypes.FLYING_COW.get(), FlyingCowEntity.createMobAttributes().build());
        event.put(AetherEntityTypes.SHEEPUFF.get(), SheepuffEntity.createMobAttributes().build());
        event.put(AetherEntityTypes.MOA.get(), MoaEntity.createMobAttributes().build());
        event.put(AetherEntityTypes.AERWHALE.get(), AerwhaleEntity.createMobAttributes().build());
        event.put(AetherEntityTypes.AERBUNNY.get(), AerbunnyEntity.createMobAttributes().build());

        event.put(AetherEntityTypes.BLUE_SWET.get(), Swet.createMobAttributes().build());
        event.put(AetherEntityTypes.GOLDEN_SWET.get(), Swet.createMobAttributes().build());
        event.put(AetherEntityTypes.WHIRLWIND.get(), Whirlwind.createMobAttributes().build());
        event.put(AetherEntityTypes.EVIL_WHIRLWIND.get(), Whirlwind.createMobAttributes().build());
        event.put(AetherEntityTypes.AECHOR_PLANT.get(), AechorPlantEntity.createMobAttributes().build());
        event.put(AetherEntityTypes.COCKATRICE.get(), CockatriceEntity.createMobAttributes().build());
        event.put(AetherEntityTypes.ZEPHYR.get(), Zephyr.createMobAttributes().build());

        event.put(AetherEntityTypes.SENTRY.get(), SentryEntity.createMobAttributes().build());
        event.put(AetherEntityTypes.MIMIC.get(), Mimic.createMobAttributes().build());
        //event.put(AetherEntityTypes.VALKYRIE.get(), ValkyrieEntity.createMobAttributes().build());
        event.put(AetherEntityTypes.FIRE_MINION.get(), FireMinionEntity.createMobAttributes().build());

        event.put(AetherEntityTypes.CLOUD_MINION.get(), CloudMinionEntity.registerAttributes().build());
    }
}