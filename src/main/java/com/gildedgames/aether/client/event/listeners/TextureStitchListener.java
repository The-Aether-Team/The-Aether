package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.Aether;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TextureStitchListener
{
    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (event.getAtlas().location() == InventoryMenu.BLOCK_ATLAS) {
            event.addSprite(new ResourceLocation(Aether.MODID, "gui/slots/cape"));
            event.addSprite(new ResourceLocation(Aether.MODID, "gui/slots/gloves"));
            event.addSprite(new ResourceLocation(Aether.MODID, "gui/slots/misc"));
            event.addSprite(new ResourceLocation(Aether.MODID, "gui/slots/pendant"));
            event.addSprite(new ResourceLocation(Aether.MODID, "gui/slots/ring"));
            event.addSprite(new ResourceLocation(Aether.MODID, "gui/slots/shield"));
        }
    }
}
