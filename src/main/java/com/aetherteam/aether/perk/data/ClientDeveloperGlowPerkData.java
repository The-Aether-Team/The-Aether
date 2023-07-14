package com.aetherteam.aether.perk.data;

import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.serverbound.ServerDeveloperGlowPacket;
import com.aetherteam.aether.perk.CustomizationsOptions;
import com.aetherteam.aether.perk.PerkUtil;
import com.aetherteam.aether.perk.types.DeveloperGlow;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.api.users.UserData;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientDeveloperGlowPerkData extends ClientPerkData<DeveloperGlow> {
    public static final ClientDeveloperGlowPerkData INSTANCE = new ClientDeveloperGlowPerkData();
    private static final Map<UUID, DeveloperGlow> CLIENT_USER_DEVELOPER_GLOW_DATA = new HashMap<>();

    @Override
    public void syncFromClient(Player player) {
        if (this.canSync(player)) {
            User user = UserData.Client.getClientUser();
            UUID uuid = player.getUUID();
            CustomizationsOptions.INSTANCE.load();
            boolean developerGlowEnabled = CustomizationsOptions.INSTANCE.isDeveloperGlowEnabled();
            String developerGlowColor = CustomizationsOptions.INSTANCE.getDeveloperGlowHex();
            Map<UUID, DeveloperGlow> userDeveloperGlowData = this.getClientPerkData();
            if (developerGlowEnabled) {
                if (!userDeveloperGlowData.containsKey(uuid) || userDeveloperGlowData.get(uuid) == null || (developerGlowColor != null && !userDeveloperGlowData.get(uuid).hexColor().equals(developerGlowColor))) {
                    if (PerkUtil.hasDeveloperGlow().test(user)) {
                        PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new ServerDeveloperGlowPacket.Apply(player.getUUID(), new DeveloperGlow(developerGlowColor)));
                    }
                }
            } else {
                PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new ServerDeveloperGlowPacket.Remove(player.getUUID()));
            }
        }
    }

    @Override
    protected Map<UUID, DeveloperGlow> getMap() {
        return CLIENT_USER_DEVELOPER_GLOW_DATA;
    }
}
