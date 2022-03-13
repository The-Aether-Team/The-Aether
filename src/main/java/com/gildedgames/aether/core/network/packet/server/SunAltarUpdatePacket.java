package com.gildedgames.aether.core.network.packet.server;

import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.capability.time.AetherTime;
import com.gildedgames.aether.core.network.AetherPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class SunAltarUpdatePacket extends AetherPacket.AbstractAetherPacket {
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
        if (player != null && player.hasPermissions(AetherConfig.COMMON.admin_sun_altar.get() ? 4 : 0)) {
            AetherTime.get(player.level).ifPresent(aetherTime -> {
                aetherTime.setDayTime(this.dayTime);
            });
        }
    }
}
