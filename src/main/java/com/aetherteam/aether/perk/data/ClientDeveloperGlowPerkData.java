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

    /**
     * Updates the player's Developer Glow data on the server if changes to the information occur on the client.
     * @param player The {@link Player}.
     */
    @Override
    public void syncFromClient(Player player) {
        if (this.canSync(player)) {
            User user = UserData.Client.getClientUser(); // The client's User.
            UUID uuid = player.getUUID(); // The player's UUID.
            CustomizationsOptions.INSTANCE.load(); // Loads data from the client's "customizations.txt" file.
            boolean developerGlowEnabled = CustomizationsOptions.INSTANCE.isDeveloperGlowEnabled(); // Retrieves whether the Developer Glow is enabled according to the file.
            String developerGlowColor = CustomizationsOptions.INSTANCE.getDeveloperGlowHex(); // Retrieves the Developer Glow hex color from the file.
            Map<UUID, DeveloperGlow> userDeveloperGlowData = this.getClientPerkData(); // The current client data for UUIDs paired with Developer Glows.
            if (developerGlowEnabled) { // Checks whether the Developer Glow is enabled in the options.
                if (!userDeveloperGlowData.containsKey(uuid) || userDeveloperGlowData.get(uuid) == null || (developerGlowColor != null && !userDeveloperGlowData.get(uuid).hexColor().equals(developerGlowColor))) { // A check to see if any options have changed.
                    if (PerkUtil.hasDeveloperGlow().test(user)) { // Verifies whether the User can have the Developer Glow.
                        PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new ServerDeveloperGlowPacket.Apply(player.getUUID(), new DeveloperGlow(developerGlowColor))); // Send a modification packet to the server.
                    }
                }
            } else { // Removes the Halo if it isn't enabled in the options.
                PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new ServerDeveloperGlowPacket.Remove(player.getUUID())); // Send a removal packet to the server.
            }
        }
    }

    /**
     * @return A {@link Map} of {@link UUID}s paired with {@link DeveloperGlow}s on the client.
     */
    @Override
    protected Map<UUID, DeveloperGlow> getMap() {
        return CLIENT_USER_DEVELOPER_GLOW_DATA;
    }
}
