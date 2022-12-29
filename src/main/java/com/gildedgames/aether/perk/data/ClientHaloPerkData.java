package com.gildedgames.aether.perk.data;

import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.server.ServerHaloPacket;
import com.gildedgames.aether.perk.CustomizationsOptions;
import com.gildedgames.aether.perk.types.Halo;
import com.gildedgames.aether.util.PerkUtil;
import com.gildedgames.nitrogen.api.users.User;
import com.gildedgames.nitrogen.api.users.UserData;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientHaloPerkData extends ClientPerkData<Halo> {
    public static final ClientHaloPerkData INSTANCE = new ClientHaloPerkData();
    private static final Map<UUID, Halo> CLIENT_USER_HALO_DATA = new HashMap<>();

    @Override
    public void syncFromClient(Player player) {
        if (this.canSync(player)) {
            User user = UserData.Client.getClientUser();
            UUID uuid = player.getUUID();
            CustomizationsOptions.INSTANCE.load();
            boolean haloEnabled = CustomizationsOptions.INSTANCE.isHaloEnabled();
            String haloColor = CustomizationsOptions.INSTANCE.getHaloHex();
            Map<UUID, Halo> userHaloData = this.getClientPerkData();
            if (haloEnabled) {
                if (!userHaloData.containsKey(uuid) || userHaloData.get(uuid) == null || (haloColor != null && !userHaloData.get(uuid).hexColor().equals(haloColor))) {
                    if (PerkUtil.hasHalo().test(user)) {
                        AetherPacketHandler.sendToServer(new ServerHaloPacket.Apply(player.getUUID(), new Halo(haloColor)));
                    }
                }
            } else {
                AetherPacketHandler.sendToServer(new ServerHaloPacket.Remove(player.getUUID()));
            }
        }
    }

    @Override
    protected Map<UUID, Halo> getMap() {
        return CLIENT_USER_HALO_DATA;
    }
}
