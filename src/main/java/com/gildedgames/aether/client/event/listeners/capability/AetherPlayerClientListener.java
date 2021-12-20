package com.gildedgames.aether.client.event.listeners.capability;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.server.HittingPacket;
import com.gildedgames.aether.core.network.packet.server.JumpPacket;
import com.gildedgames.aether.core.network.packet.server.MovementPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.player.Input;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class AetherPlayerClientListener
{
    @SubscribeEvent
    public static void onMove(MovementInputUpdateEvent event) {
        IAetherPlayer.get(event.getPlayer()).ifPresent((aetherPlayer) -> {
            Input input = event.getInput();
            boolean isJumping = input.jumping;
            if (isJumping != aetherPlayer.isJumping()) {
                AetherPacketHandler.sendToServer(new JumpPacket(event.getPlayer().getId(), isJumping));
                aetherPlayer.setJumping(isJumping);
            }
            boolean isMoving = isJumping || input.up || input.down || input.left || input.right;
            if (isMoving != aetherPlayer.isMoving()) {
                AetherPacketHandler.sendToServer(new MovementPacket(event.getPlayer().getId(), isMoving));
                aetherPlayer.setMoving(isMoving);
            }
        });
    }

    @SubscribeEvent
    public static void onClick(InputEvent.MouseInputEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            IAetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                boolean isLeftClick = event.getButton() == GLFW.GLFW_MOUSE_BUTTON_1;
                boolean isPressing = event.getAction() == GLFW.GLFW_PRESS;
                boolean isHitting = isLeftClick && isPressing;
                AetherPacketHandler.sendToServer(new HittingPacket(player.getId(), isHitting));
                aetherPlayer.setHitting(isHitting);
            });
        }
    }
}
