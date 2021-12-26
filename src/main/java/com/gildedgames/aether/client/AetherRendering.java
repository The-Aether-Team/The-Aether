package com.gildedgames.aether.client;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.gui.screen.inventory.*;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.*;
import com.gildedgames.aether.client.renderer.player.layer.EnchantedDartLayer;
import com.gildedgames.aether.client.renderer.player.layer.GoldenDartLayer;
import com.gildedgames.aether.client.renderer.player.layer.PoisonDartLayer;
import com.gildedgames.aether.common.item.miscellaneous.MoaEggItem;
import com.gildedgames.aether.client.renderer.entity.*;
import com.gildedgames.aether.client.renderer.tile.ChestMimicBlockEntityRenderer;
import com.gildedgames.aether.client.renderer.tile.SkyrootBedRenderer;
import com.gildedgames.aether.client.renderer.tile.TreasureChestRenderer;
import com.gildedgames.aether.common.registry.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.EntityRenderersEvent;

import java.util.function.Supplier;

public class AetherRendering
{
    public static void registerColors() {
        ItemColors colors = Minecraft.getInstance().getItemColors();

        colors.register((color, itemProvider) -> itemProvider > 0 ? -1 : ((DyeableLeatherItem) color.getItem()).getColor(color), AetherItems.LEATHER_GLOVES.get());

        for (MoaEggItem moaEggItem : MoaEggItem.moaEggs()) {
            colors.register((color, itemProvider) -> moaEggItem.getColor(itemProvider), moaEggItem);
        }
    }

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
        registerBlockRenderer(AetherBlocks.POTTED_PURPLE_FLOWER, cutout);
        registerBlockRenderer(AetherBlocks.POTTED_WHITE_FLOWER, cutout);
        registerBlockRenderer(AetherBlocks.POTTED_SKYROOT_SAPLING, cutout);
        registerBlockRenderer(AetherBlocks.POTTED_GOLDEN_OAK_SAPLING, cutout);
    }

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AetherEntityTypes.PHYG.get(), PhygRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.FLYING_COW.get(), FlyingCowRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.SHEEPUFF.get(), SheepuffRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.AERBUNNY.get(), AerbunnyRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.MOA.get(), MoaRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.AERWHALE.get(), AerwhaleRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.WHIRLWIND.get(), WhirlwindRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.AECHOR_PLANT.get(), AechorPlantRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.COCKATRICE.get(), CockatriceRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.ZEPHYR.get(), ZephyrRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.SENTRY.get(), SentryRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.MIMIC.get(), MimicRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.CLOUD_MINION.get(), CloudMinionRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.COLD_PARACHUTE.get(), ColdParachuteRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.GOLDEN_PARACHUTE.get(), GoldenParachuteRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.FLOATING_BLOCK.get(), FloatingBlockRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.TNT_PRESENT.get(), TNTPresentRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.ZEPHYR_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.CLOUD_CRYSTAL.get(), IceCrystalRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.GOLDEN_DART.get(), GoldenDartRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.POISON_DART.get(), PoisonDartRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.ENCHANTED_DART.get(), EnchantedDartRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.POISON_NEEDLE.get(), PoisonDartRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.LIGHTNING_KNIFE.get(), LightningKnifeRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.HAMMER_PROJECTILE.get(), HammerProjectileRenderer::new);

        event.registerBlockEntityRenderer(AetherTileEntityTypes.CHEST_MIMIC.get(), ChestMimicBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(AetherTileEntityTypes.TREASURE_CHEST.get(), TreasureChestRenderer::new);
        event.registerBlockEntityRenderer(AetherTileEntityTypes.SKYROOT_BED.get(), SkyrootBedRenderer::new);
        event.registerBlockEntityRenderer(AetherTileEntityTypes.SKYROOT_SIGN.get(), SignRenderer::new);
    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AetherModelLayers.AECHOR_PLANT, AechorPlantModel::createMainLayer);
        event.registerLayerDefinition(AetherModelLayers.AERBUNNY, AerbunnyModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.AERWHALE, AerwhaleModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.CLOUD_CRYSTAL, CrystalModel::createMainLayer);
        event.registerLayerDefinition(AetherModelLayers.CLOUD_MINION, CloudMinionModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.COCKATRICE, CockatriceModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.FLYING_COW, CowModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.FLYING_COW_WINGS, FlyingCowWingModel::createMainLayer);
        event.registerLayerDefinition(AetherModelLayers.MIMIC, MimicModel::createBodyLayer);
//        event.registerLayerDefinition(AetherModelLayers.MOA, MoaModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.PHYG, () -> PigModel.createBodyLayer(CubeDeformation.NONE));
        event.registerLayerDefinition(AetherModelLayers.PHYG_WINGS, PhygWingModel::createMainLayer);
        event.registerLayerDefinition(AetherModelLayers.SENTRY, SlimeModel::createInnerBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.SHEEPUFF, SheepuffModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.SHEEPUFF_WOOL, SheepuffWoolModel::createFurLayer);
//        event.registerLayerDefinition(AetherModelLayers.SLIDER, );
        event.registerLayerDefinition(AetherModelLayers.SUN_SPIRIT, SunSpiritModel::createBodyLayer);
//        event.registerLayerDefinition(AetherModelLayers.SWET, );
//        event.registerLayerDefinition(AetherModelLayers.VALKYRIE, );
//        event.registerLayerDefinition(AetherModelLayers.VALKYRIE_QUEEN, );
        event.registerLayerDefinition(AetherModelLayers.ZEPHYR, ZephyrModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.ZEPHYR_CLASSIC, OldZephyrModel::createMainLayer);
        event.registerLayerDefinition(AetherModelLayers.ZEPHYR_TRANSPARENCY, ZephyrModel::createBodyLayer);
    }

    public static void addPlayerLayers(EntityRenderersEvent.AddLayers event) {
        String[] types = new String[] { "default", "slim" };
        for (String type : types) {
            PlayerRenderer playerRenderer =  event.getSkin(type);
            if (playerRenderer != null) {
//                    r.addLayer(new RepulsionShieldLayer<>(r, new HumanoidModel<>(1.1F)));
//                playerRenderer.addLayer(new GoldenDartLayer<>(playerRenderer));
//                playerRenderer.addLayer(new PoisonDartLayer<>(playerRenderer));
//                playerRenderer.addLayer(new EnchantedDartLayer<>(playerRenderer));
            }
        }
    }

    /*public static CustomItemStackTileEntityRenderer chestMimicRenderer() {
        return new CustomItemStackTileEntityRenderer(ChestMimicTileEntity::new);
    }

    public static CustomItemStackTileEntityRenderer treasureChestRenderer() {
        return new CustomItemStackTileEntityRenderer(TreasureChestTileEntity::new);
    }

    public static CustomItemStackTileEntityRenderer skyrootBedRenderer() {
        return new CustomItemStackTileEntityRenderer(SkyrootBedTileEntity::new);
    }*/

    public static void registerGuiFactories() {
        MenuScreens.register(AetherContainerTypes.ACCESSORIES.get(), AccessoriesScreen::new);
        MenuScreens.register(AetherContainerTypes.BOOK_OF_LORE.get(), LoreBookScreen::new);
        MenuScreens.register(AetherContainerTypes.ALTAR.get(), AltarScreen::new);
        MenuScreens.register(AetherContainerTypes.FREEZER.get(), FreezerScreen::new);
        MenuScreens.register(AetherContainerTypes.INCUBATOR.get(), IncubatorScreen::new);
    }

    public static void registerItemModelProperties() {
        ItemProperties.register(AetherItems.PHOENIX_BOW.get(), new ResourceLocation("pulling"), (stack, world, living, i)
                -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);
        ItemProperties.register(AetherItems.PHOENIX_BOW.get(), new ResourceLocation("pull"), (stack, world, living, i) -> {
            if (living == null) {
                return 0.0F;
            } else {
                return living.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - living.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemProperties.register(AetherItems.CANDY_CANE_SWORD.get(), new ResourceLocation(Aether.MODID, "named"), (stack, world, living, i)
                -> stack.getHoverName().getString().equalsIgnoreCase("green candy cane sword") ? 1.0F : 0.0F);

        ItemProperties.register(AetherItems.HAMMER_OF_NOTCH.get(), new ResourceLocation(Aether.MODID, "named"), (stack, world, living, i)
                -> stack.getHoverName().getString().equalsIgnoreCase("hammer of jeb") ? 1.0F : 0.0F);
    }

    private static void registerBlockRenderer(Supplier<? extends Block> block, RenderType render) {
        ItemBlockRenderTypes.setRenderLayer(block.get(), render);
    }
}
