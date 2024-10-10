package com.aetherteam.aether.client.renderer;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.blockentity.AetherBlockEntityTypes;
import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.renderer.accessory.GlovesRenderer;
import com.aetherteam.aether.client.renderer.accessory.PendantRenderer;
import com.aetherteam.aether.client.renderer.accessory.ShieldOfRepulsionRenderer;
import com.aetherteam.aether.client.renderer.accessory.layer.ArmorStandCapeLayer;
import com.aetherteam.aether.client.renderer.accessory.model.CapeModel;
import com.aetherteam.aether.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aether.client.renderer.accessory.model.PendantModel;
import com.aetherteam.aether.client.renderer.block.FastModel;
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
import io.wispforest.accessories.client.AccessoriesRenderLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AetherRenderers {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(AetherBlockEntityTypes.SKYROOT_BED.get(), SkyrootBedRenderer::new);
        event.registerBlockEntityRenderer(AetherBlockEntityTypes.SKYROOT_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(AetherBlockEntityTypes.SKYROOT_HANGING_SIGN.get(), HangingSignRenderer::new);
        event.registerBlockEntityRenderer(AetherBlockEntityTypes.CHEST_MIMIC.get(), ChestMimicRenderer::new);
        event.registerBlockEntityRenderer(AetherBlockEntityTypes.TREASURE_CHEST.get(), TreasureChestRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.PHYG.get(), PhygRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.FLYING_COW.get(), FlyingCowRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.SHEEPUFF.get(), SheepuffRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.AERBUNNY.get(), AerbunnyRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.MOA.get(), MoaRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.AERWHALE.get(), AerwhaleRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.BLUE_SWET.get(), BlueSwetRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.GOLDEN_SWET.get(), GoldenSwetRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.WHIRLWIND.get(), WhirlwindRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.EVIL_WHIRLWIND.get(), WhirlwindRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.AECHOR_PLANT.get(), AechorPlantRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.COCKATRICE.get(), CockatriceRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.ZEPHYR.get(), ZephyrRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.MIMIC.get(), MimicRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.SENTRY.get(), SentryRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.VALKYRIE.get(), ValkyrieRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.FIRE_MINION.get(), FireMinionRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.SLIDER.get(), SliderRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.VALKYRIE_QUEEN.get(), ValkyrieQueenRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.SUN_SPIRIT.get(), SunSpiritRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.SKYROOT_BOAT.get(), (context) -> new SkyrootBoatRenderer(context, false));
        event.registerEntityRenderer(AetherEntityTypes.SKYROOT_CHEST_BOAT.get(), (context) -> new SkyrootBoatRenderer(context, true));
        event.registerEntityRenderer(AetherEntityTypes.CLOUD_MINION.get(), CloudMinionRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.COLD_PARACHUTE.get(), (context) -> new ParachuteRenderer(context, AetherBlocks.COLD_AERCLOUD));
        event.registerEntityRenderer(AetherEntityTypes.GOLDEN_PARACHUTE.get(), (context) -> new ParachuteRenderer(context, AetherBlocks.GOLDEN_AERCLOUD));
        event.registerEntityRenderer(AetherEntityTypes.FLOATING_BLOCK.get(), FloatingBlockRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.TNT_PRESENT.get(), TntPresentRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.ZEPHYR_SNOWBALL.get(), (context) -> new ThrownItemRenderer<>(context, 3.0F, true));
        event.registerEntityRenderer(AetherEntityTypes.CLOUD_CRYSTAL.get(), CloudCrystalRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.FIRE_CRYSTAL.get(), FireCrystalRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.ICE_CRYSTAL.get(), IceCrystalRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.THUNDER_CRYSTAL.get(), ThunderCrystalRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.GOLDEN_DART.get(), GoldenDartRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.POISON_DART.get(), PoisonDartRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.ENCHANTED_DART.get(), EnchantedDartRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.POISON_NEEDLE.get(), PoisonNeedleRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.LIGHTNING_KNIFE.get(), LightningKnifeRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.HAMMER_PROJECTILE.get(), HammerProjectileRenderer::new);
    }

    /**
     * @see AetherClient#eventSetup()
     */
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AetherModelLayers.SKYROOT_BED_FOOT, BedRenderer::createFootLayer);
        event.registerLayerDefinition(AetherModelLayers.SKYROOT_BED_HEAD, BedRenderer::createHeadLayer);
        event.registerLayerDefinition(AetherModelLayers.CHEST_MIMIC, ChestRenderer::createSingleBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.PHYG, () -> PigModel.createBodyLayer(CubeDeformation.NONE));
        event.registerLayerDefinition(AetherModelLayers.PHYG_WINGS, () -> QuadrupedWingsModel.createMainLayer(10.0F));
        event.registerLayerDefinition(AetherModelLayers.PHYG_SADDLE, () -> PigModel.createBodyLayer(new CubeDeformation(0.5F)));
        event.registerLayerDefinition(AetherModelLayers.PHYG_HALO, () -> HaloModel.createLayer(3.0F, -4.0F, 12.0F, -6.0F));
        event.registerLayerDefinition(AetherModelLayers.FLYING_COW, CowModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.FLYING_COW_WINGS, () -> QuadrupedWingsModel.createMainLayer(0.0F));
        event.registerLayerDefinition(AetherModelLayers.FLYING_COW_SADDLE, CowModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.SHEEPUFF, SheepuffModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.SHEEPUFF_WOOL, () -> SheepuffWoolModel.createFurLayer(new CubeDeformation(1.75F), 0.0F));
        event.registerLayerDefinition(AetherModelLayers.SHEEPUFF_WOOL_PUFFED, () -> SheepuffWoolModel.createFurLayer(new CubeDeformation(3.75F), 2.0F));
        event.registerLayerDefinition(AetherModelLayers.AERBUNNY, AerbunnyModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.MOA, () -> MoaModel.createBodyLayer(CubeDeformation.NONE));
        event.registerLayerDefinition(AetherModelLayers.MOA_HAT, () -> MoaModel.createBodyLayer(new CubeDeformation(0.23F)));
        event.registerLayerDefinition(AetherModelLayers.MOA_SADDLE, () -> MoaModel.createBodyLayer(new CubeDeformation(0.27F)));
        event.registerLayerDefinition(AetherModelLayers.AERWHALE, AerwhaleModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.AERWHALE_CLASSIC, ClassicAerwhaleModel::createBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.SWET, SlimeModel::createInnerBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.SWET_OUTER, SlimeModel::createOuterBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.AECHOR_PLANT, AechorPlantModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.COCKATRICE, () -> CockatriceModel.createBodyLayer(CubeDeformation.NONE));
        event.registerLayerDefinition(AetherModelLayers.ZEPHYR, ZephyrModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.ZEPHYR_TRANSPARENCY, ZephyrModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.ZEPHYR_CLASSIC, ClassicZephyrModel::createBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.MIMIC, MimicModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.SENTRY, SlimeModel::createOuterBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.VALKYRIE, ValkyrieModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.VALKYRIE_WINGS, () -> ValkyrieWingsModel.createMainLayer(4.5F, 2.5F));
        event.registerLayerDefinition(AetherModelLayers.FIRE_MINION, FireMinionModel::createBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.SLIDER, SliderModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.VALKYRIE_QUEEN, ValkyrieModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.VALKYRIE_QUEEN_WINGS, () -> ValkyrieWingsModel.createMainLayer(4.5F, 2.5F));
        event.registerLayerDefinition(AetherModelLayers.SUN_SPIRIT, SunSpiritModel::createBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.SKYROOT_BOAT, BoatModel::createBodyModel);
        event.registerLayerDefinition(AetherModelLayers.SKYROOT_CHEST_BOAT, ChestBoatModel::createBodyModel);

        event.registerLayerDefinition(AetherModelLayers.CLOUD_MINION, CloudMinionModel::createBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.CLOUD_CRYSTAL, CrystalModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.THUNDER_CRYSTAL, CrystalModel::createBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.VALKYRIE_ARMOR_WINGS, () -> ValkyrieWingsModel.createMainLayer(3.5F, 3.375F));

        event.registerLayerDefinition(AetherModelLayers.PENDANT, PendantModel::createLayer);
        event.registerLayerDefinition(AetherModelLayers.GLOVES, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), false, false));
        event.registerLayerDefinition(AetherModelLayers.GLOVES_TRIM, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), false, true));
        event.registerLayerDefinition(AetherModelLayers.GLOVES_SLIM, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), true, false));
        event.registerLayerDefinition(AetherModelLayers.GLOVES_TRIM_SLIM, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), true, true));
        event.registerLayerDefinition(AetherModelLayers.GLOVES_FIRST_PERSON, () -> GlovesModel.createLayer(new CubeDeformation(0.25F), false, false));
        event.registerLayerDefinition(AetherModelLayers.GLOVES_TRIM_FIRST_PERSON, () -> GlovesModel.createLayer(new CubeDeformation(0.25F), false, true));
        event.registerLayerDefinition(AetherModelLayers.SHIELD_OF_REPULSION, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1.1F), false), 64, 64));
        event.registerLayerDefinition(AetherModelLayers.SHIELD_OF_REPULSION_SLIM, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1.15F), true), 64, 64));
        event.registerLayerDefinition(AetherModelLayers.SHIELD_OF_REPULSION_ARM, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(0.4F), false), 64, 64));
        event.registerLayerDefinition(AetherModelLayers.CAPE, CapeModel::createLayer);

        event.registerLayerDefinition(AetherModelLayers.PLAYER_HALO, () -> HaloModel.createLayer(0.0F, 0.0F, 0.0F, 0.0F));
    }

    /**
     * @see com.aetherteam.aether.client.AetherClient#clientSetup(FMLClientSetupEvent)
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

    /**
     * @see AetherClient#eventSetup()
     */
    public static void addEntityLayers(EntityRenderersEvent.AddLayers event) {
        EntityRenderDispatcher renderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        for (PlayerSkin.Model type : event.getSkins()) {
            PlayerRenderer playerRenderer = event.getSkin(type);
            if (playerRenderer != null) {
                playerRenderer.addLayer(new DeveloperGlowLayer<>(playerRenderer));
                playerRenderer.addLayer(new DartLayer<>(renderDispatcher, playerRenderer, (entity) -> new GoldenDart(AetherEntityTypes.GOLDEN_DART.get(), entity.level()), AetherPlayerAttachment::getGoldenDartCount, 1.0F));
                playerRenderer.addLayer(new DartLayer<>(renderDispatcher, playerRenderer, (entity) -> new PoisonDart(AetherEntityTypes.POISON_DART.get(), entity.level()), AetherPlayerAttachment::getPoisonDartCount, 2.0F));
                playerRenderer.addLayer(new DartLayer<>(renderDispatcher, playerRenderer, (entity) -> new EnchantedDart(AetherEntityTypes.ENCHANTED_DART.get(), entity.level()), AetherPlayerAttachment::getEnchantedDartCount, 3.0F));
                playerRenderer.addLayer(new PlayerHaloLayer<>(playerRenderer, Minecraft.getInstance().getEntityModels()));
                playerRenderer.addLayer(new PlayerWingsLayer<>(playerRenderer, Minecraft.getInstance().getEntityModels()));
            }
        }
//        List<EntityType<? extends LivingEntity>> entities = List.of(EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.HUSK, EntityType.SKELETON, EntityType.STRAY, EntityType.PIGLIN, EntityType.ZOMBIFIED_PIGLIN, EntityType.ARMOR_STAND);
//        for (EntityType<? extends LivingEntity> entityType : entities) {
//            LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>> renderer = event.getRenderer(entityType);
//            if (renderer != null) {
//                renderer.addLayer(new AccessoriesRenderLayer<>(renderer));
//            }
//        }
        LivingEntityRenderer<ArmorStand, ArmorStandModel> renderer = event.getRenderer(EntityType.ARMOR_STAND);
        if (renderer != null) {
            renderer.addLayer(new ArmorStandCapeLayer(renderer));
        }
    }

    /**
     * @see AetherClient#eventSetup()
     */
    public static void bakeModels(ModelEvent.ModifyBakingResult event) {
        List<Map.Entry<ModelResourceLocation, BakedModel>> models = new ArrayList<>();
        for (Map.Entry<ModelResourceLocation, BakedModel> model : event.getModels().entrySet()) {
            if (model.getKey().id().getNamespace().equals(Aether.MODID)) {
                String path = model.getKey().id().getPath();
                if (path.equals(AetherBlocks.BERRY_BUSH.getId().getPath())) {
                    models.add(model);
                } else if (path.equals(AetherBlocks.POTTED_BERRY_BUSH.getId().getPath())) {
                    models.add(model);
                }
            }
        }
        models.forEach(entry -> event.getModels().put(entry.getKey(), new FastModel(entry.getValue())));
    }
}
