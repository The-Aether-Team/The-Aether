package com.gildedgames.aether.network.packet.server;

import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.network.AetherPacket;
import com.gildedgames.aether.api.SunAltarWhitelist;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;

public record SunAltarUpdatePacket(long dayTime) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(this.dayTime);
    }

    public static SunAltarUpdatePacket decode(FriendlyByteBuf buf) {
        long time = buf.readLong();
        return new SunAltarUpdatePacket(time);
    }

    /**
     * Updates the time on the server, then updates that time for all players in the Aether.
     */
    @Override
    public void execute(Player player) {
        if (player != null && player.level instanceof ServerLevel level && (!AetherConfig.COMMON.sun_altar_whitelist.get() || player.hasPermissions(4) || SunAltarWhitelist.INSTANCE.isWhiteListed(player.getGameProfile()))) {
            level.setDayTime(this.dayTime);
            level.getServer().getPlayerList().broadcastAll(new ClientboundSetTimePacket(level.getGameTime(), level.getDayTime(), level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)), level.dimension());
        }
    }
}
