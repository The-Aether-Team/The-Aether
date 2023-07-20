package com.aetherteam.aether.perk.data;

import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.api.users.UserData;
import com.aetherteam.nitrogen.network.BasePacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public abstract class ServerPerkData<T> {
    /**
     * Syncs the data for a perk from the server to the client.
     * @param player The {@link Player} to send the packet to.
     */
    public void syncFromServer(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            PacketRelay.sendToPlayer(AetherPacketHandler.INSTANCE, this.getSyncPacket(this.getServerPerkData(serverPlayer.getServer())), serverPlayer); // Send to client.
        }
    }

    /**
     * Applies a perk as long as the player's {@link User} class exists and matches the verification requirement from {@link ServerPerkData#getVerificationPredicate(Object)}.<br>
     * The perk is sent to all players' clients with a packet, and the data for it is stored to the server world.
     * @param server The {@link MinecraftServer}
     * @param uuid The player's {@link UUID}
     * @param perk The perk data type.
     */
    public void applyPerkWithVerification(MinecraftServer server, UUID uuid, T perk) {
        Map<UUID, User> storedUsers = UserData.Server.getStoredUsers();
        if (storedUsers.containsKey(uuid)) { // Checks if User exists.
            User user = storedUsers.get(uuid);
            if (user != null && this.getVerificationPredicate(perk).test(user)) { // Checks verification requirement to have the perk that is trying to be applied.
                PacketRelay.sendToAll(AetherPacketHandler.INSTANCE, this.getApplyPacket(uuid, perk)); // Send to clients.
                this.modifySavedData(server, uuid, perk); // Save to world.
            }
        }
    }

    /**
     * Removes a perk from a player. The removal is relayed and synced to all players' clients with a packet.
     * @param server The {@link MinecraftServer}.
     * @param uuid The {@link UUID} of the player.
     */
    public void removePerk(MinecraftServer server, UUID uuid) {
        PacketRelay.sendToAll(AetherPacketHandler.INSTANCE, this.getRemovePacket(uuid)); // Send to clients.
        this.removeSavedData(server, uuid); // Save to world.
    }

    /**
     * Gets the data for all {@link UUID}s that have a specific perk (handled by subclasses).<br>
     * Before returning the data, this method runs verification checks on the information it gathers from the world.
     * @param server The {@link MinecraftServer}.
     * @return A {@link Map} of player {@link UUID}s and perk {@link Object} data types.
     */
    public Map<UUID, T> getServerPerkData(MinecraftServer server) {
        Map<UUID, User> storedUsers = UserData.Server.getStoredUsers(); // The Users tracked and stored to the server world.
        Map<UUID, T> verifiedPerkData = new HashMap<>(); // An empty map of UUIDs and perk objects.
        for (Map.Entry<UUID, T> serverPerkDataEntry : this.getSavedMap(server).entrySet()) { // Iterates through perk data that is stored by the server world.
            if (storedUsers.containsKey(serverPerkDataEntry.getKey())) { // Ensures the UUID from the perk data map also exists in the user map.
                User user = storedUsers.get(serverPerkDataEntry.getKey()); // Gets the User corresponding to the UUID.
                if (user != null && this.getVerificationPredicate(serverPerkDataEntry.getValue()).test(user)) { // Double checks whether the User has access to the perk that is currently attached to its UUID.
                    verifiedPerkData.put(serverPerkDataEntry.getKey(), serverPerkDataEntry.getValue()); // Adds the UUID and perk to the map indicating that the entry pair is verified.
                }
            }
        }
        return ImmutableMap.copyOf(verifiedPerkData);
    }

    /**
     * @param server The {@link MinecraftServer}.
     * @return The {@link PerkSavedData} for the "perks.dat" file of the world.
     */
    protected PerkSavedData getSavedData(MinecraftServer server) {
        return PerkSavedData.compute(server.overworld().getDataStorage());
    }

    protected abstract Map<UUID, T> getSavedMap(MinecraftServer server);

    protected abstract void modifySavedData(MinecraftServer server, UUID uuid, T perk);

    protected abstract void removeSavedData(MinecraftServer server, UUID uuid);

    protected abstract BasePacket getApplyPacket(UUID uuid, T perk);

    protected abstract BasePacket getRemovePacket(UUID uuid);

    protected abstract BasePacket getSyncPacket(Map<UUID, T> serverPerkData);

    protected abstract Predicate<User> getVerificationPredicate(T perk);
}
