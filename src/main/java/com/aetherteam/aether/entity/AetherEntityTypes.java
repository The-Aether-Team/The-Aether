package com.aetherteam.aether.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.data.resources.AetherMobCategory;
import com.aetherteam.aether.entity.block.FloatingBlockEntity;
import com.aetherteam.aether.entity.block.TntPresent;
import com.aetherteam.aether.entity.miscellaneous.CloudMinion;
import com.aetherteam.aether.entity.miscellaneous.Parachute;
import com.aetherteam.aether.entity.miscellaneous.SkyrootBoat;
import com.aetherteam.aether.entity.miscellaneous.SkyrootChestBoat;
import com.aetherteam.aether.entity.monster.*;
import com.aetherteam.aether.entity.monster.dungeon.FireMinion;
import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import com.aetherteam.aether.entity.monster.dungeon.Valkyrie;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.aetherteam.aether.entity.monster.dungeon.boss.SunSpirit;
import com.aetherteam.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import com.aetherteam.aether.entity.passive.*;
import com.aetherteam.aether.entity.projectile.PoisonNeedle;
import com.aetherteam.aether.entity.projectile.ZephyrSnowball;
import com.aetherteam.aether.entity.projectile.crystal.CloudCrystal;
import com.aetherteam.aether.entity.projectile.crystal.FireCrystal;
import com.aetherteam.aether.entity.projectile.crystal.IceCrystal;
import com.aetherteam.aether.entity.projectile.crystal.ThunderCrystal;
import com.aetherteam.aether.entity.projectile.dart.EnchantedDart;
import com.aetherteam.aether.entity.projectile.dart.GoldenDart;
import com.aetherteam.aether.entity.projectile.dart.PoisonDart;
import com.aetherteam.aether.entity.projectile.weapon.HammerProjectile;
import com.aetherteam.aether.entity.projectile.weapon.ThrownLightningKnife;
import com.aetherteam.nitrogen.fabric.registries.DeferredHolder;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class AetherEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Aether.MODID);

    // Passive Mobs
    public static final DeferredHolder<EntityType<?>, EntityType<Phyg>> PHYG = ENTITY_TYPES.register("phyg",
            () -> EntityType.Builder.of(Phyg::new, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(10).build());

    public static final DeferredHolder<EntityType<?>, EntityType<FlyingCow>> FLYING_COW = ENTITY_TYPES.register("flying_cow",
            () -> EntityType.Builder.of(FlyingCow::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10).build());

    public static final DeferredHolder<EntityType<?>, EntityType<Sheepuff>> SHEEPUFF = ENTITY_TYPES.register("sheepuff",
            () -> EntityType.Builder.of(Sheepuff::new, MobCategory.CREATURE).sized(0.9F, 1.3F).clientTrackingRange(10).build());

    public static final DeferredHolder<EntityType<?>, EntityType<Moa>> MOA = ENTITY_TYPES.register("moa",
            () -> EntityType.Builder.of(Moa::new, MobCategory.CREATURE).sized(0.9F, 2.15F).clientTrackingRange(10).build());

    public static final DeferredHolder<EntityType<?>, EntityType<Aerbunny>> AERBUNNY = ENTITY_TYPES.register("aerbunny",
            () -> EntityType.Builder.of(Aerbunny::new, MobCategory.CREATURE).sized(0.6F, 0.5F).clientTrackingRange(10).build());

    public static final DeferredHolder<EntityType<?>, EntityType<Aerwhale>> AERWHALE = ENTITY_TYPES.register("aerwhale",
            () -> EntityType.Builder.of(Aerwhale::new, AetherMobCategory.AETHER_AERWHALE).fireImmune().sized(3.0F, 3.0F).clientTrackingRange(10).build());

    // Hostile Mobs
    public static final DeferredHolder<EntityType<?>, EntityType<Swet>> BLUE_SWET = ENTITY_TYPES.register("blue_swet",
            () -> EntityType.Builder.of(Swet::new, AetherMobCategory.AETHER_SURFACE_MONSTER).sized(0.9F, 0.9F).clientTrackingRange(10).build());

    public static final DeferredHolder<EntityType<?>, EntityType<Swet>> GOLDEN_SWET = ENTITY_TYPES.register("golden_swet",
            () -> EntityType.Builder.of(Swet::new, AetherMobCategory.AETHER_SURFACE_MONSTER).sized(0.9F, 0.9F).clientTrackingRange(10).build());

    public static final DeferredHolder<EntityType<?>, EntityType<PassiveWhirlwind>> WHIRLWIND = ENTITY_TYPES.register("whirlwind",
            () -> EntityType.Builder.of(PassiveWhirlwind::new, AetherMobCategory.AETHER_SURFACE_MONSTER).fireImmune().sized(0.6F, 0.8F).clientTrackingRange(8).build());

    public static final DeferredHolder<EntityType<?>, EntityType<EvilWhirlwind>> EVIL_WHIRLWIND = ENTITY_TYPES.register("evil_whirlwind",
            () -> EntityType.Builder.of(EvilWhirlwind::new, AetherMobCategory.AETHER_SURFACE_MONSTER).fireImmune().sized(0.6F, 0.8F).clientTrackingRange(8).build());

    public static final DeferredHolder<EntityType<?>, EntityType<AechorPlant>> AECHOR_PLANT = ENTITY_TYPES.register("aechor_plant",
            () -> EntityType.Builder.of(AechorPlant::new, AetherMobCategory.AETHER_SURFACE_MONSTER).sized(1.0F, 1.0F).clientTrackingRange(8).build());

    public static final DeferredHolder<EntityType<?>, EntityType<Cockatrice>> COCKATRICE = ENTITY_TYPES.register("cockatrice",
            () -> EntityType.Builder.of(Cockatrice::new, AetherMobCategory.AETHER_DARKNESS_MONSTER).sized(0.9F, 2.15F).clientTrackingRange(10).build());

    public static final DeferredHolder<EntityType<?>, EntityType<Zephyr>> ZEPHYR = ENTITY_TYPES.register("zephyr",
            () -> EntityType.Builder.of(Zephyr::new, AetherMobCategory.AETHER_SKY_MONSTER).sized(4.5F, 3.5F).clientTrackingRange(10).build());

    // Dungeon Mobs
    public static final DeferredHolder<EntityType<?>, EntityType<Mimic>> MIMIC = ENTITY_TYPES.register("mimic",
            () -> EntityType.Builder.of(Mimic::new, MobCategory.MONSTER).sized(1.0F, 2.0F).clientTrackingRange(8).build());

    public static final DeferredHolder<EntityType<?>, EntityType<Sentry>> SENTRY = ENTITY_TYPES.register("sentry",
            () -> EntityType.Builder.of(Sentry::new, MobCategory.MONSTER).sized(0.9F, 0.9F).clientTrackingRange(10).build());

    public static final DeferredHolder<EntityType<?>, EntityType<Slider>> SLIDER = ENTITY_TYPES.register("slider",
            () -> EntityType.Builder.of(Slider::new, MobCategory.MONSTER).sized(2.0F, 2.0F).fireImmune().clientTrackingRange(10).build());

    public static final DeferredHolder<EntityType<?>, EntityType<Valkyrie>> VALKYRIE = ENTITY_TYPES.register("valkyrie",
            () -> EntityType.Builder.of(Valkyrie::new, MobCategory.MONSTER).sized(0.8F, 1.95F).clientTrackingRange(8).build());

    public static final DeferredHolder<EntityType<?>, EntityType<ValkyrieQueen>> VALKYRIE_QUEEN = ENTITY_TYPES.register("valkyrie_queen",
            () -> EntityType.Builder.of(ValkyrieQueen::new, MobCategory.MONSTER).sized(0.8F, 1.95F).fireImmune().clientTrackingRange(10).build());

    public static final DeferredHolder<EntityType<?>, EntityType<FireMinion>> FIRE_MINION = ENTITY_TYPES.register("fire_minion",
            () -> EntityType.Builder.of(FireMinion::new, MobCategory.MONSTER).sized(1.1F, 1.95F).fireImmune().clientTrackingRange(8).build());

    public static final DeferredHolder<EntityType<?>, EntityType<SunSpirit>> SUN_SPIRIT = ENTITY_TYPES.register("sun_spirit",
            () -> EntityType.Builder.of(SunSpirit::new, MobCategory.MONSTER).sized(2.5F, 3.4F).fireImmune().clientTrackingRange(10).build());

    // Miscellaneous Entities
    public static final DeferredHolder<EntityType<?>, EntityType<SkyrootBoat>> SKYROOT_BOAT = ENTITY_TYPES.register("skyroot_boat",
            () -> EntityType.Builder.<SkyrootBoat>of(SkyrootBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build());

    public static final DeferredHolder<EntityType<?>, EntityType<SkyrootChestBoat>> SKYROOT_CHEST_BOAT = ENTITY_TYPES.register("skyroot_chest_boat",
            () -> EntityType.Builder.<SkyrootChestBoat>of(SkyrootChestBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build());

    public static final DeferredHolder<EntityType<?>, EntityType<CloudMinion>> CLOUD_MINION = ENTITY_TYPES.register("cloud_minion",
            () -> EntityType.Builder.<CloudMinion>of(CloudMinion::new, MobCategory.MISC).sized(0.75F, 0.75F).clientTrackingRange(5).build());

    public static final DeferredHolder<EntityType<?>, EntityType<Parachute>> COLD_PARACHUTE = ENTITY_TYPES.register("cold_parachute",
            () -> EntityType.Builder.of(Parachute::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(8).build());

    public static final DeferredHolder<EntityType<?>, EntityType<Parachute>> GOLDEN_PARACHUTE = ENTITY_TYPES.register("golden_parachute",
            () -> EntityType.Builder.of(Parachute::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(8).build());

    public static final DeferredHolder<EntityType<?>, EntityType<FloatingBlockEntity>> FLOATING_BLOCK = ENTITY_TYPES.register("floating_block",
            () -> EntityType.Builder.<FloatingBlockEntity>of(FloatingBlockEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(10).updateInterval(20).build());

    public static final DeferredHolder<EntityType<?>, EntityType<TntPresent>> TNT_PRESENT = ENTITY_TYPES.register("tnt_present",
            () -> EntityType.Builder.<TntPresent>of(TntPresent::new, MobCategory.MISC).fireImmune().sized(1.0F, 1.0F).eyeHeight(0.15F).clientTrackingRange(10).updateInterval(10).build());

    // Projectiles
    public static final DeferredHolder<EntityType<?>, EntityType<ZephyrSnowball>> ZEPHYR_SNOWBALL = ENTITY_TYPES.register("zephyr_snowball",
            () -> EntityType.Builder.<ZephyrSnowball>of(ZephyrSnowball::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(4).updateInterval(10).build());

    public static final DeferredHolder<EntityType<?>, EntityType<CloudCrystal>> CLOUD_CRYSTAL = ENTITY_TYPES.register("cloud_crystal",
            () -> EntityType.Builder.<CloudCrystal>of(CloudCrystal::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build());

    public static final DeferredHolder<EntityType<?>, EntityType<FireCrystal>> FIRE_CRYSTAL = ENTITY_TYPES.register("fire_crystal",
            () -> EntityType.Builder.<FireCrystal>of(FireCrystal::new, MobCategory.MISC).sized(0.85F, 0.85F).clientTrackingRange(4).updateInterval(10).fireImmune().build());

    public static final DeferredHolder<EntityType<?>, EntityType<IceCrystal>> ICE_CRYSTAL = ENTITY_TYPES.register("ice_crystal",
            () -> EntityType.Builder.<IceCrystal>of(IceCrystal::new, MobCategory.MISC).sized(1.2F, 1.2F).clientTrackingRange(4).updateInterval(10).fireImmune().build());

    public static final DeferredHolder<EntityType<?>, EntityType<ThunderCrystal>> THUNDER_CRYSTAL = ENTITY_TYPES.register("thunder_crystal",
            () -> EntityType.Builder.<ThunderCrystal>of(ThunderCrystal::new, MobCategory.MISC).sized(0.7F, 0.7F).updateInterval(2).build());

    public static final DeferredHolder<EntityType<?>, EntityType<GoldenDart>> GOLDEN_DART = ENTITY_TYPES.register("golden_dart",
            () -> EntityType.Builder.<GoldenDart>of(GoldenDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build());

    public static final DeferredHolder<EntityType<?>, EntityType<PoisonDart>> POISON_DART = ENTITY_TYPES.register("poison_dart",
            () -> EntityType.Builder.<PoisonDart>of(PoisonDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build());

    public static final DeferredHolder<EntityType<?>, EntityType<EnchantedDart>> ENCHANTED_DART = ENTITY_TYPES.register("enchanted_dart",
            () -> EntityType.Builder.<EnchantedDart>of(EnchantedDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build());

    public static final DeferredHolder<EntityType<?>, EntityType<PoisonNeedle>> POISON_NEEDLE = ENTITY_TYPES.register("poison_needle",
            () -> EntityType.Builder.<PoisonNeedle>of(PoisonNeedle::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build());

    public static final DeferredHolder<EntityType<?>, EntityType<ThrownLightningKnife>> LIGHTNING_KNIFE = ENTITY_TYPES.register("lightning_knife",
            () -> EntityType.Builder.<ThrownLightningKnife>of(ThrownLightningKnife::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build());

    public static final DeferredHolder<EntityType<?>, EntityType<HammerProjectile>> HAMMER_PROJECTILE = ENTITY_TYPES.register("hammer_projectile",
            () -> EntityType.Builder.<HammerProjectile>of(HammerProjectile::new, MobCategory.MISC).sized(0.35F, 0.35F).clientTrackingRange(4).updateInterval(10).build());

    /**
     * @see Aether#eventSetup()
     */
    public static void registerSpawnPlacements() {
        // Passive Mobs
        SpawnPlacements.register(AetherEntityTypes.PHYG.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.FLYING_COW.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.SHEEPUFF.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.MOA.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.AERBUNNY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.AERWHALE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Aerwhale::checkAerwhaleSpawnRules);

        // Hostile Mobs
        SpawnPlacements.register(AetherEntityTypes.BLUE_SWET.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Swet::checkSwetSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.GOLDEN_SWET.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Swet::checkSwetSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.WHIRLWIND.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractWhirlwind::checkWhirlwindSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.EVIL_WHIRLWIND.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractWhirlwind::checkWhirlwindSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.AECHOR_PLANT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AechorPlant::checkAechorPlantSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.COCKATRICE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Cockatrice::checkCockatriceSpawnRules);
        SpawnPlacements.register(AetherEntityTypes.ZEPHYR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zephyr::checkZephyrSpawnRules);
    }

    /**
     * @see Aether#eventSetup()
     */
    public static void registerEntityAttributes() {
        // Passive Mobs
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.PHYG.get(), Phyg.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.FLYING_COW.get(), FlyingCow.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.SHEEPUFF.get(), Sheepuff.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.MOA.get(), Moa.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.AERBUNNY.get(), Aerbunny.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.AERWHALE.get(), Aerwhale.createMobAttributes().build());

        // Hostile Mobs
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.BLUE_SWET.get(), Swet.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.GOLDEN_SWET.get(), Swet.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.WHIRLWIND.get(), AbstractWhirlwind.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.EVIL_WHIRLWIND.get(), AbstractWhirlwind.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.AECHOR_PLANT.get(), AechorPlant.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.COCKATRICE.get(), Cockatrice.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.ZEPHYR.get(), Zephyr.createMobAttributes().build());

        // Dungeon Mobs
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.MIMIC.get(), Mimic.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.SENTRY.get(), Sentry.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.SLIDER.get(), Slider.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.VALKYRIE.get(), Valkyrie.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.VALKYRIE_QUEEN.get(), ValkyrieQueen.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.FIRE_MINION.get(), FireMinion.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.SUN_SPIRIT.get(), SunSpirit.createMobAttributes().build());

        // Miscellaneous Entities
        FabricDefaultAttributeRegistry.register(AetherEntityTypes.CLOUD_MINION.get(), CloudMinion.createMobAttributes().build());
    }
}
