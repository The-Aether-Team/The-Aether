package com.gildedgames.aether.client.event.listeners.capability;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.server.JumpPacket;
import com.gildedgames.aether.core.network.packet.server.MovementPacket;
import net.minecraft.util.MovementInput;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class AetherPlayerClientListener
{
    @SubscribeEvent
    public static void onMove(InputUpdateEvent event) {
        IAetherPlayer.get(event.getPlayer()).ifPresent((aetherPlayer) -> {
            MovementInput input = event.getMovementInput();
            boolean isJumping = input.jumping;
            if (isJumping != aetherPlayer.isJumping()) {
                AetherPacketHandler.sendToServer(new JumpPacket(event.getPlayer().getUUID(), isJumping));
                aetherPlayer.setJumping(isJumping);
            }
            boolean isMoving = isJumping || input.up || input.down || input.left || input.right;
            if (isMoving != aetherPlayer.isMoving()) {
                AetherPacketHandler.sendToServer(new MovementPacket(event.getPlayer().getId(), isMoving));
                aetherPlayer.setMoving(isMoving);
            }
        });
    }
}
