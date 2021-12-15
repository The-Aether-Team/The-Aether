package com.gildedgames.aether.client.event.listeners.abilities;

import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class AccessoryAbilityClientListener
{
    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.INVISIBILITY_CLOAK.get(), event.getPlayer()).ifPresent((triple) -> event.setCanceled(true));
    }

    @SubscribeEvent
    @SuppressWarnings("resource")
    public static void onRenderHand(RenderHandEvent event) {
        if (Minecraft.getInstance().player != null) {
            CuriosApi.getCuriosHelper().findEquippedCurio(AetherItems.INVISIBILITY_CLOAK.get(), Minecraft.getInstance().player).ifPresent((triple) -> event.setCanceled(true));
        }
    }
}
