package com.aetherteam.aether.perk.data;

import com.aetherteam.aether.network.packet.serverbound.ServerHaloPacket;
import com.aetherteam.aether.perk.CustomizationsOptions;
import com.aetherteam.aether.perk.PerkUtil;
import com.aetherteam.aether.perk.types.Halo;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.api.users.UserData;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientHaloPerkData extends ClientPerkData<Halo> {
    public static final ClientHaloPerkData INSTANCE = new ClientHaloPerkData();
    private static final Map<UUID, Halo> CLIENT_USER_HALO_DATA = new HashMap<>();

    /**
     * Updates the player's Halo data on the server if changes to the information occur on the client.
     *
     * @param player The {@link Player}.
     */
    @Override
    public void syncFromClient(Player player) {
        if (this.canSync(player)) {
            User user = UserData.Client.getClientUser(); // The client's User.
            UUID uuid = player.getUUID(); // The player's UUID.
            CustomizationsOptions.INSTANCE.load(); // Loads data from the client's "customizations.txt" file.
            boolean haloEnabled = CustomizationsOptions.INSTANCE.isHaloEnabled(); // Retrieves whether the Halo is enabled according to the file.
            String haloColor = CustomizationsOptions.INSTANCE.getHaloHex(); // Retrieves the Halo hex color from the file.
            Map<UUID, Halo> userHaloData = this.getClientPerkData(); // The current client data for UUIDs paired with Halos.
            if (haloEnabled) { // Checks whether the Halo is enabled in the options.
                if (!userHaloData.containsKey(uuid) || userHaloData.get(uuid) == null || (haloColor != null && !userHaloData.get(uuid).hexColor().equals(haloColor))) { // A check to see if any options have changed.
                    if (PerkUtil.hasHalo().test(user)) { // Verifies whether the User can have the Halo.
                        PacketDistributor.sendToServer(new ServerHaloPacket.Apply(player.getUUID(), new Halo(haloColor))); // Send a modification packet to the server.
                    }
                }
            } else { // Removes the Halo if it isn't enabled in the options.
                PacketDistributor.sendToServer(new ServerHaloPacket.Remove(player.getUUID())); // Send a removal packet to the server.
            }
        }
    }

    /**
     * @return A {@link Map} of {@link UUID}s paired with {@link Halo}s on the client.
     */
    @Override
    protected Map<UUID, Halo> getMap() {
        return CLIENT_USER_HALO_DATA;
    }
}
