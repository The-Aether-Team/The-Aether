package com.gildedgames.aether.client.event.handlers;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.JumpPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class AetherClientCapabilityHandler
{
    @SubscribeEvent
    public static void onJump(InputUpdateEvent event) {
        IAetherPlayer.get(event.getPlayer()).ifPresent((player) -> {
            boolean isJumping = event.getMovementInput().jump;
            if (isJumping != player.isJumping()) {
                AetherPacketHandler.sendToServer(new JumpPacket(event.getPlayer().getUniqueID(), isJumping));

                player.setJumping(isJumping);
            }
        });
    }
}
