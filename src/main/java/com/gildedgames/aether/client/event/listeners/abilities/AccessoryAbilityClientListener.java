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
        CuriosApi.getCuriosHelper().findFirstCurio(event.getPlayer(), AetherItems.INVISIBILITY_CLOAK.get()).ifPresent((slotResult) -> event.setCanceled(true));
    }

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) { //TODO: This makes the item in the player's hand invisible which isn't great for playability.
        if (Minecraft.getInstance().player != null) {
            CuriosApi.getCuriosHelper().findFirstCurio(Minecraft.getInstance().player, AetherItems.INVISIBILITY_CLOAK.get()).ifPresent((slotResult) -> event.setCanceled(true));
        }
    }
}
