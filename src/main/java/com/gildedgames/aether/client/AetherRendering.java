package com.gildedgames.aether.client;

import com.gildedgames.aether.client.gui.screen.inventory.AltarScreen;
import com.gildedgames.aether.client.gui.screen.inventory.FreezerScreen;
import com.gildedgames.aether.client.gui.screen.inventory.IncubatorScreen;
import com.gildedgames.aether.client.gui.screen.inventory.LoreBookScreen;
import com.gildedgames.aether.common.registry.AetherWoodTypes;
import com.gildedgames.aether.client.renderer.entity.*;
import com.gildedgames.aether.client.renderer.tile.ChestMimicTileEntityRenderer;
import com.gildedgames.aether.client.renderer.tile.CustomItemStackTileEntityRenderer;
import com.gildedgames.aether.client.renderer.tile.SkyrootBedTileEntityRenderer;
import com.gildedgames.aether.client.renderer.tile.TreasureChestTileEntityRenderer;
import com.gildedgames.aether.common.entity.tile.ChestMimicTileEntity;
import com.gildedgames.aether.common.entity.tile.SkyrootBedTileEntity;
import com.gildedgames.aether.common.entity.tile.TreasureChestTileEntity;
import com.gildedgames.aether.common.registry.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.function.Supplier;

public class AetherRendering
{
    public static void registerColors() {
        Minecraft.getInstance().getItemColors().register((color, itemProvider) -> itemProvider > 0 ? -1 : ((IDyeableArmorItem) color.getItem()).getColor(color), AetherItems.LEATHER_GLOVES.get());
    }

    public static void registerBlockRenderLayers() {
        RenderType cutout = RenderType.cutout();
        RenderType mipped = RenderType.cutoutMipped();
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

    public static void registerEntityRenderers() {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.PHYG.get(), PhygRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.FLYING_COW.get(), FlyingCowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.SHEEPUFF.get(), SheepuffRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.MOA.get(), MoaRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.AERWHALE.get(), AerwhaleRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.AERBUNNY.get(), AerbunnyRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.WHIRLWIND.get(), WhirlwindRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.AECHOR_PLANT.get(), AechorPlantRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.COCKATRICE.get(), CockatriceRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.ZEPHYR.get(), ZephyrRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.SENTRY.get(), SentryRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.MIMIC.get(), MimicRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.COLD_PARACHUTE.get(), ColdParachuteRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.GOLDEN_PARACHUTE.get(), GoldenParachuteRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.FLOATING_BLOCK.get(), FloatingBlockRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.TNT_PRESENT.get(), TNTPresentRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.LIGHTNING_KNIFE.get(), LightningKnifeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.ZEPHYR_SNOWBALL.get(), m -> new SpriteRenderer<>(m, itemRenderer));
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.GOLDEN_DART.get(), DartRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.POISON_DART.get(), DartRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.ENCHANTED_DART.get(), DartRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.POISON_NEEDLE.get(), DartRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.PHOENIX_ARROW.get(), PhoenixArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.SPECTRAL_PHOENIX_ARROW.get(), PhoenixArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AetherEntityTypes.HAMMER_PROJECTILE.get(), HammerProjectileRenderer::new);
    }

    public static void registerTileEntityRenderers() {
        ClientRegistry.bindTileEntityRenderer(AetherTileEntityTypes.CHEST_MIMIC.get(), ChestMimicTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(AetherTileEntityTypes.TREASURE_CHEST.get(), TreasureChestTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(AetherTileEntityTypes.SKYROOT_BED.get(), SkyrootBedTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(AetherTileEntityTypes.SKYROOT_SIGN.get(), SignTileEntityRenderer::new);
    }

    public static CustomItemStackTileEntityRenderer chestMimicRenderer() {
        return new CustomItemStackTileEntityRenderer(ChestMimicTileEntity::new);
    }

    public static CustomItemStackTileEntityRenderer treasureChestRenderer() {
        return new CustomItemStackTileEntityRenderer(TreasureChestTileEntity::new);
    }

    public static CustomItemStackTileEntityRenderer skyrootBedRenderer() {
        return new CustomItemStackTileEntityRenderer(SkyrootBedTileEntity::new);
    }

    public static void registerGuiFactories() {
        ScreenManager.register(AetherContainerTypes.BOOK_OF_LORE.get(), LoreBookScreen::new);
        ScreenManager.register(AetherContainerTypes.ALTAR.get(), AltarScreen::new);
        ScreenManager.register(AetherContainerTypes.FREEZER.get(), FreezerScreen::new);
        ScreenManager.register(AetherContainerTypes.INCUBATOR.get(), IncubatorScreen::new);
    }

    public static void registerItemModelProperties() {
        ItemModelsProperties.register(AetherItems.PHOENIX_BOW.get(), new ResourceLocation("pulling"), (stack, world, living)
                -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);
        ItemModelsProperties.register(AetherItems.PHOENIX_BOW.get(), new ResourceLocation("pull"), (stack, world, living) -> {
            if (living == null) {
                return 0.0F;
            } else {
                return living.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - living.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemModelsProperties.register(AetherItems.CANDY_CANE_SWORD.get(), new ResourceLocation("named"), (stack, world, living)
                -> stack.getHoverName().getString().equalsIgnoreCase("green candy cane sword") ? 1.0F : 0.0F);

        ItemModelsProperties.register(AetherItems.NOTCH_HAMMER.get(), new ResourceLocation("named"), (stack, world, living)
                -> stack.getHoverName().getString().equalsIgnoreCase("hammer of jeb") ? 1.0F : 0.0F);
    }

    public static void registerWoodTypeAtlases() {
        Atlases.addWoodType(AetherWoodTypes.SKYROOT);
    }

    private static void render(Supplier<? extends Block> block, RenderType render) {
        RenderTypeLookup.setRenderLayer(block.get(), render);
    }
}
