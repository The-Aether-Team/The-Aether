package com.aetherteam.aether.client.renderer;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.blockentity.AetherBlockEntityTypes;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.client.renderer.accessory.GlovesRenderer;
import com.aetherteam.aether.client.renderer.accessory.PendantRenderer;
import com.aetherteam.aether.client.renderer.accessory.ShieldOfRepulsionRenderer;
import com.aetherteam.aether.client.renderer.accessory.layer.ArmorStandCapeLayer;
//import com.aetherteam.aether.client.renderer.accessory.layer.EntityAccessoryLayer;
import com.aetherteam.aether.client.renderer.accessory.layer.EntityAccessoryLayer;
import com.aetherteam.aether.client.renderer.accessory.model.CapeModel;
import com.aetherteam.aether.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aether.client.renderer.accessory.model.PendantModel;
import com.aetherteam.aether.client.renderer.blockentity.ChestMimicRenderer;
import com.aetherteam.aether.client.renderer.blockentity.SkyrootBedRenderer;
import com.aetherteam.aether.client.renderer.blockentity.TreasureChestRenderer;
import com.aetherteam.aether.client.renderer.entity.*;
import com.aetherteam.aether.client.renderer.entity.model.*;
import com.aetherteam.aether.client.renderer.player.layer.DartLayer;
import com.aetherteam.aether.client.renderer.player.layer.DeveloperGlowLayer;
import com.aetherteam.aether.client.renderer.player.layer.PlayerHaloLayer;
import com.aetherteam.aether.client.renderer.player.layer.PlayerWingsLayer;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.projectile.dart.EnchantedDart;
import com.aetherteam.aether.entity.projectile.dart.GoldenDart;
import com.aetherteam.aether.entity.projectile.dart.PoisonDart;
import com.aetherteam.aether.item.AetherItems;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;

import java.util.List;

public class AetherRenderers {
    public static void registerEntityRenderers() {
        BlockEntityRenderers.register(AetherBlockEntityTypes.SKYROOT_BED.get(), SkyrootBedRenderer::new);
        BlockEntityRenderers.register(AetherBlockEntityTypes.SKYROOT_SIGN.get(), SignRenderer::new);
        BlockEntityRenderers.register(AetherBlockEntityTypes.SKYROOT_HANGING_SIGN.get(), HangingSignRenderer::new);
        BlockEntityRenderers.register(AetherBlockEntityTypes.CHEST_MIMIC.get(), ChestMimicRenderer::new);
        BlockEntityRenderers.register(AetherBlockEntityTypes.TREASURE_CHEST.get(), TreasureChestRenderer::new);

        EntityRendererRegistry.register(AetherEntityTypes.PHYG.get(), PhygRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.FLYING_COW.get(), FlyingCowRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.SHEEPUFF.get(), SheepuffRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.AERBUNNY.get(), AerbunnyRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.MOA.get(), MoaRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.AERWHALE.get(), AerwhaleRenderer::new);

        EntityRendererRegistry.register(AetherEntityTypes.BLUE_SWET.get(), BlueSwetRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.GOLDEN_SWET.get(), GoldenSwetRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.WHIRLWIND.get(), WhirlwindRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.EVIL_WHIRLWIND.get(), WhirlwindRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.AECHOR_PLANT.get(), AechorPlantRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.COCKATRICE.get(), CockatriceRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.ZEPHYR.get(), ZephyrRenderer::new);

        EntityRendererRegistry.register(AetherEntityTypes.MIMIC.get(), MimicRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.SENTRY.get(), SentryRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.VALKYRIE.get(), ValkyrieRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.FIRE_MINION.get(), FireMinionRenderer::new);

        EntityRendererRegistry.register(AetherEntityTypes.SLIDER.get(), SliderRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.VALKYRIE_QUEEN.get(), ValkyrieQueenRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.SUN_SPIRIT.get(), SunSpiritRenderer::new);

        EntityRendererRegistry.register(AetherEntityTypes.SKYROOT_BOAT.get(), (context) -> new SkyrootBoatRenderer(context, false));
        EntityRendererRegistry.register(AetherEntityTypes.SKYROOT_CHEST_BOAT.get(), (context) -> new SkyrootBoatRenderer(context, true));
        EntityRendererRegistry.register(AetherEntityTypes.CLOUD_MINION.get(), CloudMinionRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.COLD_PARACHUTE.get(), (context) -> new ParachuteRenderer(context, AetherBlocks.COLD_AERCLOUD));
        EntityRendererRegistry.register(AetherEntityTypes.GOLDEN_PARACHUTE.get(), (context) -> new ParachuteRenderer(context, AetherBlocks.GOLDEN_AERCLOUD));
        EntityRendererRegistry.register(AetherEntityTypes.FLOATING_BLOCK.get(), FloatingBlockRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.TNT_PRESENT.get(), TntPresentRenderer::new);

        EntityRendererRegistry.register(AetherEntityTypes.ZEPHYR_SNOWBALL.get(), (context) -> new ThrownItemRenderer<>(context, 3.0F, true));
        EntityRendererRegistry.register(AetherEntityTypes.CLOUD_CRYSTAL.get(), CloudCrystalRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.FIRE_CRYSTAL.get(), FireCrystalRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.ICE_CRYSTAL.get(), IceCrystalRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.THUNDER_CRYSTAL.get(), ThunderCrystalRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.GOLDEN_DART.get(), GoldenDartRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.POISON_DART.get(), PoisonDartRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.ENCHANTED_DART.get(), EnchantedDartRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.POISON_NEEDLE.get(), PoisonNeedleRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.LIGHTNING_KNIFE.get(), LightningKnifeRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.HAMMER_PROJECTILE.get(), HammerProjectileRenderer::new);
    }

    public static void registerLayerDefinitions() {
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.SKYROOT_BED_FOOT, BedRenderer::createFootLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.SKYROOT_BED_HEAD, BedRenderer::createHeadLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.CHEST_MIMIC, ChestRenderer::createSingleBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.PHYG, () -> PigModel.createBodyLayer(CubeDeformation.NONE));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.PHYG_WINGS, () -> QuadrupedWingsModel.createMainLayer(10.0F));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.PHYG_SADDLE, () -> PigModel.createBodyLayer(new CubeDeformation(0.5F)));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.PHYG_HALO, () -> HaloModel.createLayer(3.0F, -4.0F, 12.0F, -6.0F));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.FLYING_COW, CowModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.FLYING_COW_WINGS, () -> QuadrupedWingsModel.createMainLayer(0.0F));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.FLYING_COW_SADDLE, CowModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.SHEEPUFF, SheepuffModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.SHEEPUFF_WOOL, () -> SheepuffWoolModel.createFurLayer(new CubeDeformation(1.75F), 0.0F));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.SHEEPUFF_WOOL_PUFFED, () -> SheepuffWoolModel.createFurLayer(new CubeDeformation(3.75F), 2.0F));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.AERBUNNY, AerbunnyModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.MOA, () -> MoaModel.createBodyLayer(CubeDeformation.NONE));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.MOA_SADDLE, () -> MoaModel.createBodyLayer(new CubeDeformation(0.25F)));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.AERWHALE, AerwhaleModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.AERWHALE_CLASSIC, ClassicAerwhaleModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.SWET, SlimeModel::createInnerBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.SWET_OUTER, SlimeModel::createOuterBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.AECHOR_PLANT, AechorPlantModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.COCKATRICE, () -> CockatriceModel.createBodyLayer(CubeDeformation.NONE));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.ZEPHYR, ZephyrModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.ZEPHYR_TRANSPARENCY, ZephyrModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.ZEPHYR_CLASSIC, ClassicZephyrModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.MIMIC, MimicModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.SENTRY, SlimeModel::createOuterBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.VALKYRIE, ValkyrieModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.VALKYRIE_WINGS, () -> ValkyrieWingsModel.createMainLayer(4.5F, 2.5F));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.FIRE_MINION, FireMinionModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.SLIDER, SliderModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.VALKYRIE_QUEEN, ValkyrieModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.VALKYRIE_QUEEN_WINGS, () -> ValkyrieWingsModel.createMainLayer(4.5F, 2.5F));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.SUN_SPIRIT, SunSpiritModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.SKYROOT_BOAT, BoatModel::createBodyModel);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.SKYROOT_CHEST_BOAT, ChestBoatModel::createBodyModel);

        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.CLOUD_MINION, CloudMinionModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.CLOUD_CRYSTAL, CrystalModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.THUNDER_CRYSTAL, CrystalModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.VALKYRIE_ARMOR_WINGS, () -> ValkyrieWingsModel.createMainLayer(3.5F, 3.375F));

        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.PENDANT, PendantModel::createLayer);
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.GLOVES, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), false, false));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.GLOVES_TRIM, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), false, true));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.GLOVES_SLIM, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), true, false));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.GLOVES_TRIM_SLIM, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), true, true));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.GLOVES_FIRST_PERSON, () -> GlovesModel.createLayer(new CubeDeformation(0.25F), false, false));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.GLOVES_TRIM_FIRST_PERSON, () -> GlovesModel.createLayer(new CubeDeformation(0.25F), false, true));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.SHIELD_OF_REPULSION, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1.1F), false), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.SHIELD_OF_REPULSION_SLIM, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1.15F), true), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.SHIELD_OF_REPULSION_ARM, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(0.4F), false), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.CAPE, CapeModel::createLayer);

        EntityModelLayerRegistry.registerModelLayer(AetherModelLayers.PLAYER_HALO, () -> HaloModel.createLayer(0.0F, 0.0F, 0.0F, 0.0F));
    }

    /**
     * @see com.aetherteam.aether.client.AetherClient#onInitializeClient()
     */
    public static void registerCuriosRenderers() {
        AccessoriesRendererRegistry.registerRenderer(AetherItems.IRON_PENDANT.get(), PendantRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherItems.GOLDEN_PENDANT.get(), PendantRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherItems.ZANITE_PENDANT.get(), PendantRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherItems.ICE_PENDANT.get(), PendantRenderer::new);

        AccessoriesRendererRegistry.registerRenderer(AetherItems.LEATHER_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherItems.CHAINMAIL_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherItems.IRON_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherItems.GOLDEN_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherItems.DIAMOND_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherItems.NETHERITE_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherItems.ZANITE_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherItems.GRAVITITE_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherItems.NEPTUNE_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherItems.PHOENIX_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherItems.OBSIDIAN_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherItems.VALKYRIE_GLOVES.get(), GlovesRenderer::new);

        AccessoriesRendererRegistry.registerRenderer(AetherItems.SHIELD_OF_REPULSION.get(), ShieldOfRepulsionRenderer::new);
    }

    public static <T extends LivingEntity> void addEntityLayers(EntityType<? extends LivingEntity> entityType, LivingEntityRenderer<T, ?> renderer, LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper registrationHelper, EntityRendererProvider.Context context) {
        EntityRenderDispatcher renderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        if (renderer instanceof PlayerRenderer playerRenderer) {
            registrationHelper.register(new DeveloperGlowLayer<>(playerRenderer));
            registrationHelper.register(new DartLayer<>(renderDispatcher, playerRenderer, (entity) -> new GoldenDart(AetherEntityTypes.GOLDEN_DART.get(), entity.level()), AetherPlayer::getGoldenDartCount, 1.0F));
            registrationHelper.register(new DartLayer<>(renderDispatcher, playerRenderer, (entity) -> new PoisonDart(AetherEntityTypes.POISON_DART.get(), entity.level()), AetherPlayer::getPoisonDartCount, 2.0F));
            registrationHelper.register(new DartLayer<>(renderDispatcher, playerRenderer, (entity) -> new EnchantedDart(AetherEntityTypes.ENCHANTED_DART.get(), entity.level()), AetherPlayer::getEnchantedDartCount, 3.0F));
            registrationHelper.register(new PlayerHaloLayer<>(playerRenderer, Minecraft.getInstance().getEntityModels()));
            registrationHelper.register(new PlayerWingsLayer<>(playerRenderer, Minecraft.getInstance().getEntityModels()));
        }
        List<EntityType<? extends LivingEntity>> entities = List.of(EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.HUSK, EntityType.SKELETON, EntityType.STRAY, EntityType.PIGLIN, EntityType.ZOMBIFIED_PIGLIN, EntityType.ARMOR_STAND);
//        LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>> renderer = event.getRenderer(entityType);
        if (entities.contains(entityType)) {
            registrationHelper.register(new EntityAccessoryLayer((RenderLayerParent<LivingEntity, EntityModel<LivingEntity>>) renderer));
        }

        if (entityType == EntityType.ARMOR_STAND) {
            registrationHelper.register(new ArmorStandCapeLayer((LivingEntityRenderer<ArmorStand, ArmorStandModel>) renderer));
        }
    }
    
    public static void init() {
        registerEntityRenderers();
        registerLayerDefinitions();
        registerCuriosRenderers();
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(AetherRenderers::addEntityLayers);
    }
}
