package com.gildedgames.aether.client;

import java.util.function.Supplier;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.gui.screen.inventory.AccessoriesScreen;
import com.gildedgames.aether.client.gui.screen.inventory.AltarScreen;
import com.gildedgames.aether.client.gui.screen.inventory.FreezerScreen;
import com.gildedgames.aether.client.gui.screen.inventory.IncubatorScreen;
import com.gildedgames.aether.client.gui.screen.inventory.LoreBookScreen;
import com.gildedgames.aether.client.renderer.entity.AechorPlantRenderer;
import com.gildedgames.aether.client.renderer.entity.AerbunnyRenderer;
import com.gildedgames.aether.client.renderer.entity.AerwhaleRenderer;
import com.gildedgames.aether.client.renderer.entity.CloudMinionRenderer;
import com.gildedgames.aether.client.renderer.entity.CockatriceRenderer;
import com.gildedgames.aether.client.renderer.entity.ColdParachuteRenderer;
import com.gildedgames.aether.client.renderer.entity.EnchantedDartRenderer;
import com.gildedgames.aether.client.renderer.entity.FireMinionRenderer;
import com.gildedgames.aether.client.renderer.entity.FloatingBlockRenderer;
import com.gildedgames.aether.client.renderer.entity.FlyingCowRenderer;
import com.gildedgames.aether.client.renderer.entity.GoldenDartRenderer;
import com.gildedgames.aether.client.renderer.entity.GoldenParachuteRenderer;
import com.gildedgames.aether.client.renderer.entity.HammerProjectileRenderer;
import com.gildedgames.aether.client.renderer.entity.IceCrystalRenderer;
import com.gildedgames.aether.client.renderer.entity.LightningKnifeRenderer;
import com.gildedgames.aether.client.renderer.entity.MimicRenderer;
import com.gildedgames.aether.client.renderer.entity.MoaRenderer;
import com.gildedgames.aether.client.renderer.entity.PhygRenderer;
import com.gildedgames.aether.client.renderer.entity.PoisonDartRenderer;
import com.gildedgames.aether.client.renderer.entity.SentryRenderer;
import com.gildedgames.aether.client.renderer.entity.SheepuffRenderer;
import com.gildedgames.aether.client.renderer.entity.TNTPresentRenderer;
import com.gildedgames.aether.client.renderer.entity.WhirlwindRenderer;
import com.gildedgames.aether.client.renderer.entity.ZephyrRenderer;
import com.gildedgames.aether.client.renderer.entity.model.*;
import com.gildedgames.aether.client.renderer.tile.ChestMimicTileEntityRenderer;
import com.gildedgames.aether.client.renderer.tile.CustomItemStackTileEntityRenderer;
import com.gildedgames.aether.client.renderer.tile.SkyrootBedTileEntityRenderer;
import com.gildedgames.aether.client.renderer.tile.TreasureChestTileEntityRenderer;
import com.gildedgames.aether.common.entity.tile.ChestMimicTileEntity;
import com.gildedgames.aether.common.entity.tile.SkyrootBedTileEntity;
import com.gildedgames.aether.common.entity.tile.TreasureChestTileEntity;
import com.gildedgames.aether.common.item.miscellaneous.MoaEggItem;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherContainerTypes;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherTileEntityTypes;

import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class AetherRendering
{
    public static void registerColors() {
        ItemColors colors = Minecraft.getInstance().getItemColors();

        colors.register((color, itemProvider) -> itemProvider > 0 ? -1 : ((DyeableLeatherItem) color.getItem()).getColor(color), AetherItems.LEATHER_GLOVES.get());

        //TODO: Once Moa Eggs are fully functional this code can probably be simplified similar to spawn eggs.
        colors.register((color, itemProvider) -> ((MoaEggItem) color.getItem()).getColor(itemProvider), AetherItems.BLUE_MOA_EGG.get());
        colors.register((color, itemProvider) -> ((MoaEggItem) color.getItem()).getColor(itemProvider), AetherItems.WHITE_MOA_EGG.get());
        colors.register((color, itemProvider) -> ((MoaEggItem) color.getItem()).getColor(itemProvider), AetherItems.BLACK_MOA_EGG.get());
        colors.register((color, itemProvider) -> ((MoaEggItem) color.getItem()).getColor(itemProvider), AetherItems.ORANGE_MOA_EGG.get());
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerBlockRenderLayers() {
        RenderType cutout = RenderType.cutout();
        RenderType translucent = RenderType.translucent();

        render(AetherBlocks.SKYROOT_DOOR, cutout);
        render(AetherBlocks.SKYROOT_TRAPDOOR, cutout);
        render(AetherBlocks.COLD_AERCLOUD, translucent);
        render(AetherBlocks.BLUE_AERCLOUD, translucent);
        render(AetherBlocks.GOLDEN_AERCLOUD, translucent);
        render(AetherBlocks.PINK_AERCLOUD, translucent);
        render(AetherBlocks.AEROGEL, translucent);
        render(AetherBlocks.AEROGEL_SLAB, translucent);
        render(AetherBlocks.AEROGEL_STAIRS, translucent);
        render(AetherBlocks.AEROGEL_WALL, translucent);
        render(AetherBlocks.QUICKSOIL_GLASS, translucent);
        render(AetherBlocks.AETHER_PORTAL, translucent);
        render(AetherBlocks.BERRY_BUSH, cutout);
        render(AetherBlocks.BERRY_BUSH_STEM, cutout);
        render(AetherBlocks.AMBROSIUM_TORCH, cutout);
        render(AetherBlocks.AMBROSIUM_WALL_TORCH, cutout);
        render(AetherBlocks.SKYROOT_SAPLING, cutout);
        render(AetherBlocks.GOLDEN_OAK_SAPLING, cutout);
        render(AetherBlocks.PURPLE_FLOWER, cutout);
        render(AetherBlocks.WHITE_FLOWER, cutout);
        render(AetherBlocks.POTTED_PURPLE_FLOWER, cutout);
        render(AetherBlocks.POTTED_WHITE_FLOWER, cutout);
        render(AetherBlocks.POTTED_SKYROOT_SAPLING, cutout);
        render(AetherBlocks.POTTED_GOLDEN_OAK_SAPLING, cutout);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        event.registerEntityRenderer(AetherEntityTypes.PHYG_TYPE, m -> PhygRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.FLYING_COW.get(), m -> FlyingCowRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.SHEEPUFF.get(), SheepuffRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.MOA.get(), MoaRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.AERWHALE.get(), AerwhaleRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.AERBUNNY.get(), AerbunnyRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.WHIRLWIND.get(), WhirlwindRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.AECHOR_PLANT.get(), AechorPlantRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.COCKATRICE.get(), CockatriceRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.ZEPHYR.get(), ZephyrRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.SENTRY.get(), SentryRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.MIMIC.get(), MimicRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.FIRE_MINION.get(), FireMinionRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.CLOUD_MINION.get(), CloudMinionRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.COLD_PARACHUTE.get(), ColdParachuteRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.GOLDEN_PARACHUTE.get(), GoldenParachuteRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.FLOATING_BLOCK.get(), FloatingBlockRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.TNT_PRESENT.get(), TNTPresentRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.ZEPHYR_SNOWBALL.get(), m -> new ThrownItemRenderer<>(m, itemRenderer));
        event.registerEntityRenderer(AetherEntityTypes.CLOUD_CRYSTAL.get(), IceCrystalRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.GOLDEN_DART.get(), GoldenDartRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.POISON_DART.get(), PoisonDartRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.ENCHANTED_DART.get(), EnchantedDartRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.POISON_NEEDLE.get(), PoisonDartRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.LIGHTNING_KNIFE.get(), LightningKnifeRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.HAMMER_PROJECTILE.get(), HammerProjectileRenderer::new);
    }

    public static void registerLayerProviders(EntityRenderersEvent.RegisterLayerDefinitions event) {

        event.registerLayerDefinition(new ModelLayerLocation(AetherEntityTypes.PHYG.getId(), "main"), () -> PigModel.createBodyLayer(new CubeDeformation(1.0f)));
        event.registerLayerDefinition(new ModelLayerLocation(AetherEntityTypes.FLYING_COW.getId(), "main"),  CowModel::createBodyLayer);
        event.registerLayerDefinition(new ModelLayerLocation(AetherEntityTypes.SHEEPUFF.getId(), "main"), SheepuffModel::createBodyLayer);
        event.registerLayerDefinition(new ModelLayerLocation(AetherEntityTypes.MOA.getId(), "main"), MoaModel::createBodyLayer);
        event.registerLayerDefinition(new ModelLayerLocation(AetherEntityTypes.AERWHALE.getId(), "main"), AerwhaleModel::createBodyLayer);
        event.registerLayerDefinition(new ModelLayerLocation(AetherEntityTypes.AERBUNNY.getId(), "main"), AerbunnyModel::createBodyLayer);
        event.registerLayerDefinition(new ModelLayerLocation(AetherEntityTypes.AECHOR_PLANT.getId(), "main"), AechorPlantModel::createMainLayer);
        event.registerLayerDefinition(new ModelLayerLocation(AetherEntityTypes.COCKATRICE.getId(), "main"), CockatriceModel::createBodyLayer);
        event.registerLayerDefinition(new ModelLayerLocation(AetherEntityTypes.ZEPHYR.getId(), "main"), ZephyrModel::createMainLayer);
        event.registerLayerDefinition(new ModelLayerLocation(AetherEntityTypes.SENTRY.getId(), "main"), SlimeModel::createOuterBodyLayer);
        event.registerLayerDefinition(new ModelLayerLocation(AetherEntityTypes.MIMIC.getId(), "main"), MimicModel::createBodyLayer);
        event.registerLayerDefinition(new ModelLayerLocation(AetherEntityTypes.FIRE_MINION.getId(), "main"), SunSpiritModel::createBodyLayer);

        event.registerLayerDefinition(new ModelLayerLocation(AetherEntityTypes.CLOUD_MINION.getId(), "main"), CloudMinionModel::createMainLayer);
//        event.registerLayerDefinition(new ModelLayerLocation(AetherEntityTypes.COLD_PARACHUTE.getId(), "main"), Parachu::createBodyLayer);
//        event.registerLayerDefinition(new ModelLayerLocation(AetherEntityTypes.FLOATING_BLOCK.getId(), "main"), CloudMinionModel::createBodyLayer);
        event.registerLayerDefinition(new ModelLayerLocation(AetherEntityTypes.CLOUD_CRYSTAL.getId(), "main"), CloudMinionModel::createMainLayer);
//        event.registerEntityRenderer(AetherEntityTypes.CLOUD_MINION.get(), CloudMinionRenderer::new);
//        event.registerEntityRenderer(AetherEntityTypes.COLD_PARACHUTE.get(), ColdParachuteRenderer::new);
//        event.registerEntityRenderer(AetherEntityTypes.GOLDEN_PARACHUTE.get(), GoldenParachuteRenderer::new);
//        event.registerEntityRenderer(AetherEntityTypes.FLOATING_BLOCK.get(), FloatingBlockRenderer::new);
//        event.registerEntityRenderer(AetherEntityTypes.TNT_PRESENT.get(), TNTPresentRenderer::new);
//        event.registerEntityRenderer(AetherEntityTypes.ZEPHYR_SNOWBALL.get(), m -> new ThrownItemRenderer<>(m, itemRenderer));
//        event.registerEntityRenderer(AetherEntityTypes.CLOUD_CRYSTAL.get(), IceCrystalRenderer::new);
//        event.registerEntityRenderer(AetherEntityTypes.GOLDEN_DART.get(), GoldenDartRenderer::new);
//        event.registerEntityRenderer(AetherEntityTypes.POISON_DART.get(), PoisonDartRenderer::new);
//        event.registerEntityRenderer(AetherEntityTypes.ENCHANTED_DART.get(), EnchantedDartRenderer::new);
//        event.registerEntityRenderer(AetherEntityTypes.POISON_NEEDLE.get(), PoisonDartRenderer::new);
//        event.registerEntityRenderer(AetherEntityTypes.LIGHTNING_KNIFE.get(), LightningKnifeRenderer::new);
//        event.registerEntityRenderer(AetherEntityTypes.HAMMER_PROJECTILE.get(), HammerProjectileRenderer::new);

    }

    public static void registerTileEntityRenderers() {
        // https://mcforge.readthedocs.io/en/1.18.x/blockentities/ber/
//        ClientRegistry.bindTileEntityRenderer(AetherTileEntityTypes.CHEST_MIMIC.get(), ChestMimicTileEntityRenderer::new);
//        ClientRegistry.bindTileEntityRenderer(AetherTileEntityTypes.TREASURE_CHEST.get(), TreasureChestTileEntityRenderer::new);
//        ClientRegistry.bindTileEntityRenderer(AetherTileEntityTypes.SKYROOT_BED.get(), SkyrootBedTileEntityRenderer::new);
//        ClientRegistry.bindTileEntityRenderer(AetherTileEntityTypes.SKYROOT_SIGN.get(), SignRenderer::new);
    }

//    public static CustomItemStackTileEntityRenderer chestMimicRenderer() {
//        return new CustomItemStackTileEntityRenderer(new ChestMimicTileEntity());
//    }
//
//    public static CustomItemStackTileEntityRenderer treasureChestRenderer() {
//        return new CustomItemStackTileEntityRenderer(TreasureChestTileEntity::new);
//    }
//
//    public static CustomItemStackTileEntityRenderer skyrootBedRenderer() {
//        return new CustomItemStackTileEntityRenderer(SkyrootBedTileEntity::new);
//    }

    public static void registerGuiFactories() {
        MenuScreens.register(AetherContainerTypes.ACCESSORIES.get(), AccessoriesScreen::new);
        MenuScreens.register(AetherContainerTypes.BOOK_OF_LORE.get(), LoreBookScreen::new);
        MenuScreens.register(AetherContainerTypes.ALTAR.get(), AltarScreen::new);
        MenuScreens.register(AetherContainerTypes.FREEZER.get(), FreezerScreen::new);
        MenuScreens.register(AetherContainerTypes.INCUBATOR.get(), IncubatorScreen::new);
    }

    public static void registerItemModelProperties() {
//        ItemProperties.register(AetherItems.PHOENIX_BOW.get(), new ResourceLocation("pulling"), (stack, world, living)
//                -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);
//        ItemProperties.register(AetherItems.PHOENIX_BOW.get(), new ResourceLocation("pull"), (stack, world, living) -> {
//            if (living == null) {
//                return 0.0F;
//            } else {
//                return living.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - living.getUseItemRemainingTicks()) / 20.0F;
//            }
//        });
//
//        ItemProperties.register(AetherItems.CANDY_CANE_SWORD.get(), new ResourceLocation(Aether.MODID, "named"), (stack, world, living)
//                -> stack.getHoverName().getString().equalsIgnoreCase("green candy cane sword") ? 1.0F : 0.0F);
//
//        ItemProperties.register(AetherItems.HAMMER_OF_NOTCH.get(), new ResourceLocation(Aether.MODID, "named"), (stack, world, living)
//                -> stack.getHoverName().getString().equalsIgnoreCase("hammer of jeb") ? 1.0F : 0.0F);
    }

    private static void render(Supplier<? extends Block> block, RenderType render) {
        ItemBlockRenderTypes.setRenderLayer(block.get(), render);
    }
}
