package com.gildedgames.aether.perk.data;

import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.server.ServerDeveloperGlowPacket;
import com.gildedgames.aether.perk.CustomizationsOptions;
import com.gildedgames.aether.perk.types.DeveloperGlow;
import com.gildedgames.aether.util.PerkUtil;
import com.gildedgames.nitrogen.api.users.User;
import com.gildedgames.nitrogen.api.users.UserData;
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
                        AetherPacketHandler.sendToServer(new ServerDeveloperGlowPacket.Apply(player.getUUID(), new DeveloperGlow(developerGlowColor)));
                    }
                }
            } else {
                AetherPacketHandler.sendToServer(new ServerDeveloperGlowPacket.Remove(player.getUUID()));
            }
        }
    }

    @Override
    protected Map<UUID, DeveloperGlow> getMap() {
        return CLIENT_USER_DEVELOPER_GLOW_DATA;
    }
}
