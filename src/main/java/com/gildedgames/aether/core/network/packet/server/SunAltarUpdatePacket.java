package com.gildedgames.aether.core.network.packet.server;

import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.capability.time.AetherTime;
import com.gildedgames.aether.core.network.AetherPacket.AbstractAetherPacket;
import com.gildedgames.aether.core.util.SunAltarWhitelist;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class SunAltarUpdatePacket extends AbstractAetherPacket {
    private final long dayTime;
    public SunAltarUpdatePacket(long time) {
        this.dayTime = time;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(this.dayTime);
    }

    public static SunAltarUpdatePacket decode(FriendlyByteBuf buf) {
        long time = buf.readLong();
        return new SunAltarUpdatePacket(time);
    }

    @Override
    public void execute(Player player) {
        if (player != null && (!AetherConfig.COMMON.sun_altar_whitelist.get() || player.hasPermissions(4) || SunAltarWhitelist.INSTANCE.isWhiteListed(player.getGameProfile()))) {
            AetherTime.get(player.level).ifPresent(aetherTime -> aetherTime.setDayTime(this.dayTime));
        }
    }
}
