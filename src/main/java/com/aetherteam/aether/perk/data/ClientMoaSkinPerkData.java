package com.aetherteam.aether.perk.data;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.serverbound.ServerMoaSkinPacket;
import com.aetherteam.aether.perk.CustomizationsOptions;
import com.aetherteam.aether.perk.types.MoaData;
import com.aetherteam.aether.perk.types.MoaSkins;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.api.users.UserData;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientMoaSkinPerkData extends ClientPerkData<MoaData> {
    public static final ClientMoaSkinPerkData INSTANCE = new com.aetherteam.aether.perk.data.ClientMoaSkinPerkData();
    private static final Map<UUID, MoaData> CLIENT_USER_SKIN_DATA = new HashMap<>();

    /**
     * Updates the player's Moa Skin data on the server if changes to the information occur on the client.
     * @param player The {@link Player}.
     */
    @Override
    public void syncFromClient(Player player) {
        if (this.canSync(player)) {
            User user = UserData.Client.getClientUser(); // The client's User.
            UUID uuid = player.getUUID(); // The player's UUID.
            AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                UUID lastRiddenMoa = aetherPlayer.getLastRiddenMoa(); // The UUID of the last Moa ridden by the player.
                CustomizationsOptions.INSTANCE.load(); // Loads data from the client's "customizations.txt" file.
                String moaSkinName = CustomizationsOptions.INSTANCE.getMoaSkin(); // Retrieves the chosen Moa Skin from the file.
                Map<String, MoaSkins.MoaSkin> moaSkins = MoaSkins.getMoaSkins(); // A map of registered Moa Skins.
                Map<UUID, MoaData> userSkinsData = this.getClientPerkData(); // The current client data for UUIDs paired with MoaData.
                if (moaSkinName != null && !moaSkinName.isEmpty() && moaSkins.containsKey(moaSkinName)) { // Checks whether the Moa Skin option name corresponds to a registered Moa Skin.
                    MoaSkins.MoaSkin moaSkin = MoaSkins.getMoaSkins().get(moaSkinName); // Retrieves the Moa Skin corresponding with the given name.
                    MoaData moaData = new MoaData(lastRiddenMoa, moaSkin); // Creates MoaData pairing the last ridden Moa with the skin.
                    if (!userSkinsData.containsKey(uuid) // A long check to see if the player doesn't have any applied skin yet, the Moa the skin belongs to does not match, or the new skin doesn't match the old one, to know to tell the server to update this information.
                            || userSkinsData.get(uuid) == null
                            || (userSkinsData.get(uuid).moaUUID() == null && moaData.moaUUID() != null)
                            || (userSkinsData.get(uuid).moaSkin() == null && moaData.moaSkin() != null)
                            || (userSkinsData.get(uuid).moaUUID() != null && moaData.moaUUID() != null && !userSkinsData.get(uuid).moaUUID().equals(moaData.moaUUID()))
                            || (userSkinsData.get(uuid).moaSkin() != null && moaData.moaSkin() != null && !userSkinsData.get(uuid).moaSkin().equals(moaData.moaSkin()))) {
                        if (moaSkin.getUserPredicate().test(user)) { // Verifies whether the User can have the Moa Skin.
                            PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new ServerMoaSkinPacket.Apply(player.getUUID(), new MoaData(lastRiddenMoa, moaSkin))); // Send a modification packet to the server.
                        }
                    }
                } else if ((moaSkinName == null || moaSkinName.isEmpty()) && userSkinsData.containsKey(uuid) && userSkinsData.get(uuid) != null && (userSkinsData.get(uuid).moaUUID() != null || userSkinsData.get(uuid).moaSkin() != null)) {
                    PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new ServerMoaSkinPacket.Remove(player.getUUID())); // Send a removal packet to the server if the player had a Moa Skin but the skin in the options is set to blank.
                }
            });
        }
    }

    /**
     * @return A {@link Map} of {@link UUID}s paired with {@link MoaData} on the client.
     */
    @Override
    protected Map<UUID, MoaData> getMap() {
        return CLIENT_USER_SKIN_DATA;
    }
}
