package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.client.WorldDisplayHelper;
import com.aetherteam.aether.mixin.mixins.common.accessor.IntegratedServerAccessor;
import com.aetherteam.nitrogen.network.BasePacket;
import com.google.common.collect.Lists;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public record SetupLevelDisplayPacket() implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {

    }

    public static SetupLevelDisplayPacket decode(FriendlyByteBuf buf) {
        return new SetupLevelDisplayPacket();
    }

    @Override
    public void execute(@Nullable Player player) {
        if (player != null && player.getServer() != null) {
            MinecraftServer server = player.getServer();
            if (server instanceof IntegratedServer integratedServer) {
                IntegratedServerAccessor accessor = (IntegratedServerAccessor) integratedServer;
                server.getConnection().stop();
                if (accessor.aether$getLanPinger() != null) {
                    accessor.aether$getLanPinger().interrupt();
                    accessor.aether$setLanPinger(null);
                }
                accessor.aether$setPublishedPort(-1);
                server.getPlayerList().saveAll();
                Lists.newArrayList(server.getPlayerList().getPlayers()).stream().filter(serverPlayer -> !serverPlayer.getUUID().equals(accessor.aether$getUUID()))
                        .forEach(serverPlayer -> serverPlayer.connection.disconnect(Component.translatable("multiplayer.disconnect.server_shutdown")));

                Minecraft.getInstance().execute(() -> {
                    Minecraft.getInstance().options.hideGui = true;
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                    WorldDisplayHelper.setMenu();
                });
            }
        }
    }
}
