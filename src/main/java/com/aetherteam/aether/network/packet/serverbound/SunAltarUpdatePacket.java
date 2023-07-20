package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.command.SunAltarWhitelist;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;

/**
 * Updates the time on the server, then updates that time for all players in the Aether.
 */
public record SunAltarUpdatePacket(long dayTime) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(this.dayTime());
    }

    public static SunAltarUpdatePacket decode(FriendlyByteBuf buf) {
        long dayTime = buf.readLong();
        return new SunAltarUpdatePacket(dayTime);
    }

    @Override
    public void execute(Player playerEntity) {
        if (playerEntity != null && playerEntity.getLevel() instanceof ServerLevel level && (!AetherConfig.SERVER.sun_altar_whitelist.get() || playerEntity.hasPermissions(4) || SunAltarWhitelist.INSTANCE.isWhiteListed(playerEntity.getGameProfile()))) {
            // Get how many days have passed in the world first, then add to it.
            var dayBase = level.getDayTime() / (long) AetherDimensions.AETHER_TICKS_PER_DAY;
            var dayTime = (dayBase * AetherDimensions.AETHER_TICKS_PER_DAY) + this.dayTime();
            // Set the time.
            level.setDayTime(dayTime);
            level.getServer().getPlayerList().broadcastAll(new ClientboundSetTimePacket(level.getGameTime(), level.getDayTime(), level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)), level.dimension());
        }
    }
}
