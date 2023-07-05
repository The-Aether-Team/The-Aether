package com.aetherteam.aether.perk.data;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.server.ServerMoaSkinPacket;
import com.aetherteam.aether.perk.CustomizationsOptions;
import com.aetherteam.aether.perk.types.MoaData;
import com.aetherteam.aether.perk.types.MoaSkins;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.api.users.UserData;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientMoaSkinPerkData extends ClientPerkData<MoaData> {
    public static final ClientMoaSkinPerkData INSTANCE = new com.aetherteam.aether.perk.data.ClientMoaSkinPerkData();
    private static final Map<UUID, MoaData> CLIENT_USER_SKIN_DATA = new HashMap<>();

    @Override
    public void syncFromClient(Player player) {
        if (this.canSync(player)) {
            User user = UserData.Client.getClientUser();
            UUID uuid = player.getUUID();
            AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                UUID lastRiddenMoa = aetherPlayer.getLastRiddenMoa();
                CustomizationsOptions.INSTANCE.load();
                String moaSkinName = CustomizationsOptions.INSTANCE.getMoaSkin();
                Map<String, MoaSkins.MoaSkin> moaSkins = MoaSkins.getMoaSkins();
                Map<UUID, MoaData> userSkinsData = this.getClientPerkData();
                if (moaSkinName != null && !moaSkinName.isEmpty() && moaSkins.containsKey(moaSkinName)) {
                    MoaSkins.MoaSkin moaSkin = MoaSkins.getMoaSkins().get(moaSkinName);
                    MoaData moaData = new MoaData(lastRiddenMoa, moaSkin);
                    if (!userSkinsData.containsKey(uuid)
                            || userSkinsData.get(uuid) == null
                            || (userSkinsData.get(uuid).moaUUID() == null && moaData.moaUUID() != null)
                            || (userSkinsData.get(uuid).moaSkin() == null && moaData.moaSkin() != null)
                            || (userSkinsData.get(uuid).moaUUID() != null && moaData.moaUUID() != null && !userSkinsData.get(uuid).moaUUID().equals(moaData.moaUUID()))
                            || (userSkinsData.get(uuid).moaSkin() != null && moaData.moaSkin() != null && !userSkinsData.get(uuid).moaSkin().equals(moaData.moaSkin()))) {
                        Aether.LOGGER.info("d");
                        if (moaSkin.getUserPredicate().test(user)) {
                            Aether.LOGGER.info("e");
                            Aether.LOGGER.info(String.valueOf(lastRiddenMoa.toString()));
                            Aether.LOGGER.info(String.valueOf(moaSkin.getId()));
                            AetherPacketHandler.sendToServer(new ServerMoaSkinPacket.Apply(player.getUUID(), new MoaData(lastRiddenMoa, moaSkin)));
                        }
                    }
                } else if ((moaSkinName == null || moaSkinName.isEmpty()) && userSkinsData.containsKey(uuid) && userSkinsData.get(uuid) != null && (userSkinsData.get(uuid).moaUUID() != null || userSkinsData.get(uuid).moaSkin() != null)) {
                    Aether.LOGGER.info("f");
                    AetherPacketHandler.sendToServer(new ServerMoaSkinPacket.Remove(player.getUUID()));
                }
            });
        }
    }

    @Override
    protected Map<UUID, MoaData> getMap() {
        return CLIENT_USER_SKIN_DATA;
    }
}
