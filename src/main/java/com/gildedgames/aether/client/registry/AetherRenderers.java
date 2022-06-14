package com.gildedgames.aether.client.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.accessory.CapeRenderer;
import com.gildedgames.aether.client.renderer.accessory.GlovesRenderer;
import com.gildedgames.aether.client.renderer.accessory.PendantRenderer;
import com.gildedgames.aether.client.renderer.accessory.RepulsionShieldRenderer;
import com.gildedgames.aether.client.renderer.accessory.model.CapeModel;
import com.gildedgames.aether.client.renderer.accessory.model.GlovesModel;
import com.gildedgames.aether.client.renderer.accessory.model.PendantModel;
import com.gildedgames.aether.client.renderer.entity.*;
import com.gildedgames.aether.client.renderer.entity.model.*;
import com.gildedgames.aether.client.renderer.player.layer.*;
import com.gildedgames.aether.client.renderer.entity.model.HaloModel;
import com.gildedgames.aether.client.renderer.blockentity.AetherBlockEntityWithoutLevelRenderer;
import com.gildedgames.aether.client.renderer.blockentity.ChestMimicRenderer;
import com.gildedgames.aether.client.renderer.blockentity.SkyrootBedRenderer;
import com.gildedgames.aether.client.renderer.blockentity.TreasureChestRenderer;
import com.gildedgames.aether.common.registry.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherRenderers {
    public static final Lazy<BlockEntityWithoutLevelRenderer> blockEntityWithoutLevelRenderer = () ->
            new AetherBlockEntityWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());

    public static final IItemRenderProperties entityBlockItemRenderProperties = new IItemRenderProperties() {
        @Override
        public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
            return AetherRenderers.blockEntityWithoutLevelRenderer.get();
        }
    };

    public static void registerBlockRenderLayers() {
        RenderType cutout = RenderType.cutout();
        RenderType translucent = RenderType.translucent();

        registerBlockRenderer(AetherBlocks.SKYROOT_DOOR, cutout);
        registerBlockRenderer(AetherBlocks.SKYROOT_TRAPDOOR, cutout);
        registerBlockRenderer(AetherBlocks.COLD_AERCLOUD, translucent);
        registerBlockRenderer(AetherBlocks.BLUE_AERCLOUD, translucent);
        registerBlockRenderer(AetherBlocks.GOLDEN_AERCLOUD, translucent);
        registerBlockRenderer(AetherBlocks.PINK_AERCLOUD, translucent);
        registerBlockRenderer(AetherBlocks.AEROGEL, translucent);
        registerBlockRenderer(AetherBlocks.AEROGEL_SLAB, translucent);
        registerBlockRenderer(AetherBlocks.AEROGEL_STAIRS, translucent);
        registerBlockRenderer(AetherBlocks.AEROGEL_WALL, translucent);
        registerBlockRenderer(AetherBlocks.QUICKSOIL_GLASS, translucent);
        registerBlockRenderer(AetherBlocks.AETHER_PORTAL, translucent);
        registerBlockRenderer(AetherBlocks.BERRY_BUSH, cutout);
        registerBlockRenderer(AetherBlocks.BERRY_BUSH_STEM, cutout);
        registerBlockRenderer(AetherBlocks.AMBROSIUM_TORCH, cutout);
        registerBlockRenderer(AetherBlocks.AMBROSIUM_WALL_TORCH, cutout);
        registerBlockRenderer(AetherBlocks.SKYROOT_SAPLING, cutout);
        registerBlockRenderer(AetherBlocks.GOLDEN_OAK_SAPLING, cutout);
        registerBlockRenderer(AetherBlocks.PURPLE_FLOWER, cutout);
        registerBlockRenderer(AetherBlocks.WHITE_FLOWER, cutout);
        registerBlockRenderer(AetherBlocks.POTTED_BERRY_BUSH, cutout);
        registerBlockRenderer(AetherBlocks.POTTED_BERRY_BUSH_STEM, cutout);
        registerBlockRenderer(AetherBlocks.POTTED_PURPLE_FLOWER, cutout);
        registerBlockRenderer(AetherBlocks.POTTED_WHITE_FLOWER, cutout);
        registerBlockRenderer(AetherBlocks.POTTED_SKYROOT_SAPLING, cutout);
        registerBlockRenderer(AetherBlocks.POTTED_GOLDEN_OAK_SAPLING, cutout);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(AetherBlockEntityTypes.SKYROOT_BED.get(), SkyrootBedRenderer::new);
        event.registerBlockEntityRenderer(AetherBlockEntityTypes.SKYROOT_SIGN.get(), SignRenderer::new);
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

//        event.registerEntityRenderer(AetherEntityTypes.SLIDER.get(), SliderRenderer::new);
//        event.registerEntityRenderer(AetherEntityTypes.VALKYRIE_QUEEN.get(), ValkyrieQueenRenderer::new);
//        event.registerEntityRenderer(AetherEntityTypes.SUN_SPIRIT.get(), SunSpiritRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.SKYROOT_BOAT.get(), SkyrootBoatRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.CLOUD_MINION.get(), CloudMinionRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.COLD_PARACHUTE.get(), (context) -> new ParachuteRenderer(context, AetherBlocks.COLD_AERCLOUD));
        event.registerEntityRenderer(AetherEntityTypes.GOLDEN_PARACHUTE.get(), (context) -> new ParachuteRenderer(context, AetherBlocks.GOLDEN_AERCLOUD));
        event.registerEntityRenderer(AetherEntityTypes.FLOATING_BLOCK.get(), FloatingBlockRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.TNT_PRESENT.get(), TNTPresentRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.ZEPHYR_SNOWBALL.get(), (context) -> new ThrownItemRenderer<>(context, 3.0F, true));
        event.registerEntityRenderer(AetherEntityTypes.CLOUD_CRYSTAL.get(), IceCrystalRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.GOLDEN_DART.get(), GoldenDartRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.POISON_DART.get(), PoisonDartRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.ENCHANTED_DART.get(), EnchantedDartRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.POISON_NEEDLE.get(), PoisonNeedleRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.LIGHTNING_KNIFE.get(), LightningKnifeRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.HAMMER_PROJECTILE.get(), HammerProjectileRenderer::new);
    }

    @SubscribeEvent
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
        event.registerLayerDefinition(AetherModelLayers.MOA_SADDLE, () -> MoaModel.createBodyLayer(new CubeDeformation(0.25F)));
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
        event.registerLayerDefinition(AetherModelLayers.FIRE_MINION, SunSpiritModel::createBodyLayer);

//        event.registerLayerDefinition(AetherModelLayers.SLIDER, SliderModel::createBodyLayer);
//        event.registerLayerDefinition(AetherModelLayers.VALKYRIE_QUEEN, ValkyrieQueenModel:createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.SUN_SPIRIT, SunSpiritModel::createBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.SKYROOT_BOAT, BoatModel::createBodyModel);

        event.registerLayerDefinition(AetherModelLayers.CLOUD_MINION, CloudMinionModel::createBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.ICE_CRYSTAL, CrystalModel::createBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.VALKYRIE_ARMOR_WINGS, () -> ValkyrieWingsModel.createMainLayer(3.5F, 3.375F));

        event.registerLayerDefinition(AetherModelLayers.PENDANT, PendantModel::createLayer);
        event.registerLayerDefinition(AetherModelLayers.GLOVES, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), false));
        event.registerLayerDefinition(AetherModelLayers.GLOVES_SLIM, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), true));
        event.registerLayerDefinition(AetherModelLayers.GLOVES_ARM, () -> GlovesModel.createLayer(CubeDeformation.NONE, false));
        event.registerLayerDefinition(AetherModelLayers.GLOVES_ARM_SLIM, () -> GlovesModel.createLayer(CubeDeformation.NONE, true));
        event.registerLayerDefinition(AetherModelLayers.GLOVES_SLEEVE, () -> GlovesModel.createLayer(new CubeDeformation(0.25F), false));
        event.registerLayerDefinition(AetherModelLayers.GLOVES_SLEEVE_SLIM, () -> GlovesModel.createLayer(new CubeDeformation(0.25F), true));
        event.registerLayerDefinition(AetherModelLayers.CAPE, CapeModel::createLayer);
        event.registerLayerDefinition(AetherModelLayers.SHIELD_OF_REPULSION, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1.1F), false), 64, 64));
        event.registerLayerDefinition(AetherModelLayers.SHIELD_OF_REPULSION_SLIM, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1.1F), true), 64, 64));
        event.registerLayerDefinition(AetherModelLayers.SHIELD_OF_REPULSION_ARM, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(0.4F), false), 64, 64));
        event.registerLayerDefinition(AetherModelLayers.SHIELD_OF_REPULSION_ARM_SLIM, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(0.4F), true), 64, 64));

        event.registerLayerDefinition(AetherModelLayers.PLAYER_HALO, () -> HaloModel.createLayer(0.0F, 0.0F, 0.0F, 0.0F));
    }

    public static void registerCuriosRenderers() {
        CuriosRendererRegistry.register(AetherItems.IRON_PENDANT.get(), PendantRenderer::new);
        CuriosRendererRegistry.register(AetherItems.GOLDEN_PENDANT.get(), PendantRenderer::new);
        CuriosRendererRegistry.register(AetherItems.ZANITE_PENDANT.get(), PendantRenderer::new);
        CuriosRendererRegistry.register(AetherItems.ICE_PENDANT.get(), PendantRenderer::new);

        CuriosRendererRegistry.register(AetherItems.LEATHER_GLOVES.get(), GlovesRenderer::new);
        CuriosRendererRegistry.register(AetherItems.CHAINMAIL_GLOVES.get(), GlovesRenderer::new);
        CuriosRendererRegistry.register(AetherItems.IRON_GLOVES.get(), GlovesRenderer::new);
        CuriosRendererRegistry.register(AetherItems.GOLDEN_GLOVES.get(), GlovesRenderer::new);
        CuriosRendererRegistry.register(AetherItems.DIAMOND_GLOVES.get(), GlovesRenderer::new);
        CuriosRendererRegistry.register(AetherItems.NETHERITE_GLOVES.get(), GlovesRenderer::new);
        CuriosRendererRegistry.register(AetherItems.ZANITE_GLOVES.get(), GlovesRenderer::new);
        CuriosRendererRegistry.register(AetherItems.GRAVITITE_GLOVES.get(), GlovesRenderer::new);
        CuriosRendererRegistry.register(AetherItems.NEPTUNE_GLOVES.get(), GlovesRenderer::new);
        CuriosRendererRegistry.register(AetherItems.PHOENIX_GLOVES.get(), GlovesRenderer::new);
        CuriosRendererRegistry.register(AetherItems.OBSIDIAN_GLOVES.get(), GlovesRenderer::new);
        CuriosRendererRegistry.register(AetherItems.VALKYRIE_GLOVES.get(), GlovesRenderer::new);

        CuriosRendererRegistry.register(AetherItems.RED_CAPE.get(), CapeRenderer::new);
        CuriosRendererRegistry.register(AetherItems.BLUE_CAPE.get(), CapeRenderer::new);
        CuriosRendererRegistry.register(AetherItems.YELLOW_CAPE.get(), CapeRenderer::new);
        CuriosRendererRegistry.register(AetherItems.WHITE_CAPE.get(), CapeRenderer::new);
        CuriosRendererRegistry.register(AetherItems.SWET_CAPE.get(), CapeRenderer::new);
        CuriosRendererRegistry.register(AetherItems.AGILITY_CAPE.get(), CapeRenderer::new);
        CuriosRendererRegistry.register(AetherItems.VALKYRIE_CAPE.get(), CapeRenderer::new);

        CuriosRendererRegistry.register(AetherItems.SHIELD_OF_REPULSION.get(), RepulsionShieldRenderer::new);
    }

    @SubscribeEvent
    public static void addPlayerLayers(EntityRenderersEvent.AddLayers event) {
        EntityRenderDispatcher renderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        String[] types = new String[]{"default", "slim"};
        for (String type : types) {
            PlayerRenderer playerRenderer = event.getSkin(type);
            if (playerRenderer != null) {
                playerRenderer.addLayer(new DeveloperGlowLayer<>(playerRenderer));
                playerRenderer.addLayer(new EnchantedDartLayer<>(renderDispatcher, playerRenderer));
                playerRenderer.addLayer(new GoldenDartLayer<>(renderDispatcher, playerRenderer));
                playerRenderer.addLayer(new PoisonDartLayer<>(renderDispatcher, playerRenderer));
                playerRenderer.addLayer(new PlayerHaloLayer<>(playerRenderer, Minecraft.getInstance().getEntityModels()));
                playerRenderer.addLayer(new PlayerWingsLayer<>(playerRenderer, Minecraft.getInstance().getEntityModels()));
            }
        }
    }

    private static void registerBlockRenderer(Supplier<? extends Block> block, RenderType render) {
        ItemBlockRenderTypes.setRenderLayer(block.get(), render);
    }
}
