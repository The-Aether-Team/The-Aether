package com.gildedgames.aether.client;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.gui.screen.inventory.*;
import com.gildedgames.aether.common.item.miscellaneous.MoaEggItem;
import com.gildedgames.aether.client.renderer.entity.*;
import com.gildedgames.aether.client.renderer.tile.ChestMimicBlockEntityRenderer;
import com.gildedgames.aether.client.renderer.tile.CustomItemStackTileEntityRenderer;
import com.gildedgames.aether.client.renderer.tile.SkyrootBedRenderer;
import com.gildedgames.aether.client.renderer.tile.TreasureChestRenderer;
import com.gildedgames.aether.common.registry.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.function.Supplier;

public class AetherRendering
{
    public static void registerColors() {
        ItemColors colors = Minecraft.getInstance().getItemColors();

        colors.register((color, itemProvider) -> itemProvider > 0 ? -1 : ((IDyeableArmorItem) color.getItem()).getColor(color), AetherItems.LEATHER_GLOVES.get());

        for (MoaEggItem moaEggItem : MoaEggItem.moaEggs()) {
            colors.register((color, itemProvider) -> moaEggItem.getColor(itemProvider), moaEggItem);
        }
    }

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

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

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

        event.registerEntityRenderer(AetherEntityTypes.ZEPHYR_SNOWBALL.get(), m -> new SpriteRenderer<>(m, itemRenderer));
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
        ScreenManager.register(AetherContainerTypes.ACCESSORIES.get(), AccessoriesScreen::new);
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

        ItemModelsProperties.register(AetherItems.CANDY_CANE_SWORD.get(), new ResourceLocation(Aether.MODID, "named"), (stack, world, living)
                -> stack.getHoverName().getString().equalsIgnoreCase("green candy cane sword") ? 1.0F : 0.0F);

        ItemModelsProperties.register(AetherItems.HAMMER_OF_NOTCH.get(), new ResourceLocation(Aether.MODID, "named"), (stack, world, living)
                -> stack.getHoverName().getString().equalsIgnoreCase("hammer of jeb") ? 1.0F : 0.0F);
    }

    private static void render(Supplier<? extends Block> block, RenderType render) {
        RenderTypeLookup.setRenderLayer(block.get(), render);
    }
}
