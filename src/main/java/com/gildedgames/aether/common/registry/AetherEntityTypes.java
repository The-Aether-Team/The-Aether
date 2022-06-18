package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.miscellaneous.Parachute;
import com.gildedgames.aether.common.entity.miscellaneous.SkyrootBoat;
import com.gildedgames.aether.common.entity.monster.*;
import com.gildedgames.aether.common.entity.monster.dungeon.*;
import com.gildedgames.aether.common.entity.passive.*;
import com.gildedgames.aether.common.entity.block.FloatingBlockEntity;
import com.gildedgames.aether.common.entity.block.TntPresent;
import com.gildedgames.aether.common.entity.miscellaneous.CloudMinion;
import com.gildedgames.aether.common.entity.passive.Aerbunny;
import com.gildedgames.aether.common.entity.projectile.PoisonNeedle;
import com.gildedgames.aether.common.entity.projectile.ZephyrSnowball;
import com.gildedgames.aether.common.entity.projectile.crystal.CloudCrystal;
import com.gildedgames.aether.common.entity.projectile.crystal.ThunderCrystal;
import com.gildedgames.aether.common.entity.projectile.dart.EnchantedDart;
import com.gildedgames.aether.common.entity.projectile.dart.GoldenDart;
import com.gildedgames.aether.common.entity.projectile.dart.PoisonDart;
import com.gildedgames.aether.common.entity.projectile.weapon.HammerProjectile;
import com.gildedgames.aether.common.entity.projectile.weapon.ThrownLightningKnife;

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
    public static final RegistryObject<EntityType<Phyg>> PHYG = ENTITIES.register("phyg",
            () -> EntityType.Builder.of(Phyg::new, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(10).build("phyg"));

    public static final RegistryObject<EntityType<FlyingCow>> FLYING_COW = ENTITIES.register("flying_cow",
            () -> EntityType.Builder.of(FlyingCow::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10).build("flying_cow"));

    public static final RegistryObject<EntityType<Sheepuff>> SHEEPUFF = ENTITIES.register("sheepuff",
            () -> EntityType.Builder.of(Sheepuff::new, MobCategory.CREATURE).sized(0.9F, 1.3F).clientTrackingRange(10).build("sheepuff"));

    public static final RegistryObject<EntityType<Moa>> MOA = ENTITIES.register("moa",
            () -> EntityType.Builder.of(Moa::new, MobCategory.CREATURE).sized(1.0F, 2.0F).clientTrackingRange(10).build("moa"));

    public static final RegistryObject<EntityType<Aerwhale>> AERWHALE = ENTITIES.register("aerwhale",
            () -> EntityType.Builder.of(Aerwhale::new, MobCategory.CREATURE).fireImmune().sized(3.0F, 3.0F).clientTrackingRange(10).build("aerwhale"));

    public static final RegistryObject<EntityType<Aerbunny>> AERBUNNY = ENTITIES.register("aerbunny",
            () -> EntityType.Builder.of(Aerbunny::new, MobCategory.CREATURE).sized(0.4F, 0.4F).clientTrackingRange(10).build("aerbunny"));

    // Hostile Mobs
    public static final RegistryObject<EntityType<Swet>> BLUE_SWET = ENTITIES.register("blue_swet",
            () -> EntityType.Builder.of(Swet::new, MobCategory.MONSTER).sized(0.8F, 0.8F).clientTrackingRange(10).build("blue_swet"));

    public static final RegistryObject<EntityType<Swet>> GOLDEN_SWET = ENTITIES.register("golden_swet",
            () -> EntityType.Builder.of(Swet::new, MobCategory.MONSTER).sized(0.8F, 0.8F).clientTrackingRange(10).build("golden_swet"));

    public static final RegistryObject<EntityType<PassiveWhirlwind>> WHIRLWIND = ENTITIES.register("whirlwind",
            () -> EntityType.Builder.of(PassiveWhirlwind::new, MobCategory.MONSTER).fireImmune().sized(0.6F, 0.8F).clientTrackingRange(8).build("whirlwind"));

    public static final RegistryObject<EntityType<EvilWhirlwind>> EVIL_WHIRLWIND = ENTITIES.register("evil_whirlwind",
            () -> EntityType.Builder.of(EvilWhirlwind::new, MobCategory.MONSTER).fireImmune().sized(0.6F, 0.8F).clientTrackingRange(8).build("evil_whirlwind"));

    public static final RegistryObject<EntityType<AechorPlant>> AECHOR_PLANT = ENTITIES.register("aechor_plant",
            () -> EntityType.Builder.of(AechorPlant::new, MobCategory.MONSTER).sized(1.0F, 1.0F).clientTrackingRange(8).build("aechor_plant"));

    public static final RegistryObject<EntityType<Cockatrice>> COCKATRICE = ENTITIES.register("cockatrice",
            () -> EntityType.Builder.of(Cockatrice::new, MobCategory.MONSTER).sized(1.0F, 2.0F).clientTrackingRange(10).build("cockatrice"));

    public static final RegistryObject<EntityType<Zephyr>> ZEPHYR = ENTITIES.register("zephyr",
            () -> EntityType.Builder.of(Zephyr::new, MobCategory.MONSTER).sized(4.0F, 4.0F).clientTrackingRange(10).build("zephyr"));

    // Dungeon
    public static final RegistryObject<EntityType<Sentry>> SENTRY = ENTITIES.register("sentry",
            () -> EntityType.Builder.of(Sentry::new, MobCategory.MONSTER).sized(2.0F, 2.0F).clientTrackingRange(10).build("sentry"));

    public static final RegistryObject<EntityType<Mimic>> MIMIC = ENTITIES.register("mimic",
            () -> EntityType.Builder.of(Mimic::new, MobCategory.MONSTER).sized(1.0F, 2.0F).clientTrackingRange(8).build("mimic"));

    public static final RegistryObject<EntityType<Valkyrie>> VALKYRIE = ENTITIES.register("valkyrie",
            () -> EntityType.Builder.of(Valkyrie::new, MobCategory.MONSTER).sized(0.8F, 1.95F).clientTrackingRange(8).build("valkyrie"));

    public static final RegistryObject<EntityType<FireMinion>> FIRE_MINION = ENTITIES.register("fire_minion",
            () -> EntityType.Builder.of(FireMinion::new, MobCategory.MONSTER).fireImmune().sized(0.8F, 1.95F).clientTrackingRange(8).build("fire_minion"));

    public static final RegistryObject<EntityType<ValkyrieQueen>> VALKYRIE_QUEEN = ENTITIES.register("valkyrie_queen",
            () -> EntityType.Builder.of(ValkyrieQueen::new, MobCategory.MONSTER).sized(0.8F, 1.95F).fireImmune().clientTrackingRange(10).build("valkyrie_queen"));


    // Miscellaneous
    public static final RegistryObject<EntityType<SkyrootBoat>> SKYROOT_BOAT = ENTITIES.register("skyroot_boat",
            () -> EntityType.Builder.<SkyrootBoat>of(SkyrootBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build("skyroot_boat"));

    public static final RegistryObject<EntityType<CloudMinion>> CLOUD_MINION = ENTITIES.register("cloud_minion",
            () -> EntityType.Builder.<CloudMinion>of(CloudMinion::new, MobCategory.MISC).sized(0.75F, 0.75F).clientTrackingRange(5).build("cloud_minion"));

    public static final RegistryObject<EntityType<Parachute>> COLD_PARACHUTE = ENTITIES.register("cold_parachute",
            () -> EntityType.Builder.of(Parachute::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(8).build("cold_parachute"));

    public static final RegistryObject<EntityType<Parachute>> GOLDEN_PARACHUTE = ENTITIES.register("golden_parachute",
            () -> EntityType.Builder.of(Parachute::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(8).build("golden_parachute"));

    public static final RegistryObject<EntityType<FloatingBlockEntity>> FLOATING_BLOCK = ENTITIES.register("floating_block",
            () -> EntityType.Builder.<FloatingBlockEntity>of(FloatingBlockEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(10).updateInterval(20).build("floating_block"));

    public static final RegistryObject<EntityType<TntPresent>> TNT_PRESENT = ENTITIES.register("tnt_present",
            () -> EntityType.Builder.<TntPresent>of(TntPresent::new, MobCategory.MISC).fireImmune().sized(1.0F, 1.0F).clientTrackingRange(10).updateInterval(10).build("tnt_present"));

    // Projectiles
    public static final RegistryObject<EntityType<ZephyrSnowball>> ZEPHYR_SNOWBALL = ENTITIES.register("zephyr_snowball",
            () -> EntityType.Builder.<ZephyrSnowball>of(ZephyrSnowball::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(4).updateInterval(10).build("zephyr_snowball"));

    public static final RegistryObject<EntityType<CloudCrystal>> CLOUD_CRYSTAL = ENTITIES.register("cloud_crystal",
            () -> EntityType.Builder.<CloudCrystal>of(CloudCrystal::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build("cloud_crystal"));

    public static final RegistryObject<EntityType<ThunderCrystal>> THUNDER_CRYSTAL = ENTITIES.register("thunder_crystal",
            () -> EntityType.Builder.<ThunderCrystal>of(ThunderCrystal::new, MobCategory.MISC).sized(0.7F, 0.7F).updateInterval(2).build("thunder_crystal"));

    public static final RegistryObject<EntityType<GoldenDart>> GOLDEN_DART = ENTITIES.register("golden_dart",
            () -> EntityType.Builder.<GoldenDart>of(GoldenDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("golden_dart"));

    public static final RegistryObject<EntityType<PoisonDart>> POISON_DART = ENTITIES.register("poison_dart",
            () -> EntityType.Builder.<PoisonDart>of(PoisonDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("poison_dart"));

    public static final RegistryObject<EntityType<EnchantedDart>> ENCHANTED_DART = ENTITIES.register("enchanted_dart",
            () -> EntityType.Builder.<EnchantedDart>of(EnchantedDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("enchanted_dart"));

    public static final RegistryObject<EntityType<PoisonNeedle>> POISON_NEEDLE = ENTITIES.register("poison_needle",
            () -> EntityType.Builder.<PoisonNeedle>of(PoisonNeedle::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("poison_needle"));

    public static final RegistryObject<EntityType<ThrownLightningKnife>> LIGHTNING_KNIFE = ENTITIES.register("lightning_knife",
            () -> EntityType.Builder.<ThrownLightningKnife>of(ThrownLightningKnife::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("lightning_knife"));

    public static final RegistryObject<EntityType<HammerProjectile>> HAMMER_PROJECTILE = ENTITIES.register("hammer_projectile",
            () -> EntityType.Builder.<HammerProjectile>of(HammerProjectile::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("hammer_projectile"));

    public static void registerSpawnPlacements() {
        SpawnPlacements.register(AetherEntityTypes.PHYG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.FLYING_COW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.SHEEPUFF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.MOA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.AERBUNNY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.AERWHALE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Aerwhale::checkAerwhaleSpawnRules);

        SpawnPlacements.register(AetherEntityTypes.BLUE_SWET.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Swet::checkSwetSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.GOLDEN_SWET.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Swet::checkSwetSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.WHIRLWIND.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractWhirlwind::checkWhirlwindSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.EVIL_WHIRLWIND.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractWhirlwind::checkWhirlwindSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.AECHOR_PLANT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AechorPlant::checkAechorPlantSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.COCKATRICE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Cockatrice::checkCockatriceSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.ZEPHYR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zephyr::checkZephyrSpawnRules);
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(AetherEntityTypes.PHYG.get(), Phyg.createMobAttributes().build());
        event.put(AetherEntityTypes.FLYING_COW.get(), FlyingCow.createMobAttributes().build());
        event.put(AetherEntityTypes.SHEEPUFF.get(), Sheepuff.createMobAttributes().build());
        event.put(AetherEntityTypes.AERBUNNY.get(), Aerbunny.createMobAttributes().build());
        event.put(AetherEntityTypes.MOA.get(), Moa.createMobAttributes().build());
        event.put(AetherEntityTypes.AERWHALE.get(), Aerwhale.createMobAttributes().build());

        event.put(AetherEntityTypes.BLUE_SWET.get(), Swet.createMobAttributes().build());
        event.put(AetherEntityTypes.GOLDEN_SWET.get(), Swet.createMobAttributes().build());
        event.put(AetherEntityTypes.WHIRLWIND.get(), AbstractWhirlwind.createMobAttributes().build());
        event.put(AetherEntityTypes.EVIL_WHIRLWIND.get(), AbstractWhirlwind.createMobAttributes().build());
        event.put(AetherEntityTypes.AECHOR_PLANT.get(), AechorPlant.createMobAttributes().build());
        event.put(AetherEntityTypes.COCKATRICE.get(), Cockatrice.createMobAttributes().build());
        event.put(AetherEntityTypes.ZEPHYR.get(), Zephyr.createMobAttributes().build());

        event.put(AetherEntityTypes.SENTRY.get(), Sentry.createMobAttributes().build());
        event.put(AetherEntityTypes.MIMIC.get(), Mimic.createMobAttributes().build());
        event.put(AetherEntityTypes.VALKYRIE.get(), Valkyrie.createValkyrieAttributes().build());
        event.put(AetherEntityTypes.FIRE_MINION.get(), FireMinion.createMobAttributes().build());

        event.put(AetherEntityTypes.VALKYRIE_QUEEN.get(), ValkyrieQueen.createQueenAttributes().build());

        event.put(AetherEntityTypes.CLOUD_MINION.get(), CloudMinion.createMobAttributes().build());
    }
}