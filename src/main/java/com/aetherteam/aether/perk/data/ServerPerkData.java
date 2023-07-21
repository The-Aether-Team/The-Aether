package com.aetherteam.aether.perk.data;

import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.clientbound.ClientDeveloperGlowPacket;
import com.aetherteam.aether.network.packet.clientbound.ClientHaloPacket;
import com.aetherteam.aether.network.packet.clientbound.ClientMoaSkinPacket;
import com.aetherteam.aether.perk.PerkUtil;
import com.aetherteam.aether.perk.types.DeveloperGlow;
import com.aetherteam.aether.perk.types.Halo;
import com.aetherteam.aether.perk.types.MoaData;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.api.users.UserData;
import com.aetherteam.nitrogen.network.BasePacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class ServerPerkData<T> {
    /**
     * {@link ServerPerkData} instance for handling the {@link MoaData} perk type.<br>
     * This constructs the class with functions that are specific to calling and handling {@link MoaData} perk-related data.
     */
    public static final ServerPerkData<MoaData> MOA_SKIN_INSTANCE = new ServerPerkData<>(
            (data) -> (server) -> data.getSavedData(server).getStoredSkinData(),
            (data) -> (server, uuid, perk) -> data.getSavedData(server).modifyStoredSkinData(uuid, perk),
            (data) -> (server, uuid) -> data.getSavedData(server).removeStoredSkinData(uuid),
            (data) -> ClientMoaSkinPacket.Apply::new,
            (data) -> ClientMoaSkinPacket.Remove::new,
            (data) -> ClientMoaSkinPacket.Sync::new,
            (data) -> perk -> perk.moaSkin().getUserPredicate()
    );
    /**
     * {@link ServerPerkData} instance for handling the {@link Halo} perk type.<br>
     * This constructs the class with functions that are specific to calling and handling {@link Halo} perk-related data.
     */
    public static final ServerPerkData<Halo> HALO_INSTANCE = new ServerPerkData<>(
            (data) -> (server) -> data.getSavedData(server).getStoredHaloData(),
            (data) -> (server, uuid, perk) -> data.getSavedData(server).modifyStoredHaloData(uuid, perk),
            (data) -> (server, uuid) -> data.getSavedData(server).removeStoredHaloData(uuid),
            (data) -> ClientHaloPacket.Apply::new,
            (data) -> ClientHaloPacket.Remove::new,
            (data) -> ClientHaloPacket.Sync::new,
            (data) -> perk -> PerkUtil.hasHalo()
    );
    /**
     * {@link ServerPerkData} instance for handling the {@link DeveloperGlow} perk type.<br>
     * This constructs the class with functions that are specific to calling and handling {@link DeveloperGlow} perk-related data.
     */
    public static final ServerPerkData<DeveloperGlow> DEVELOPER_GLOW_INSTANCE = new ServerPerkData<>(
            (data) -> (server) -> data.getSavedData(server).getStoredDeveloperGlowData(),
            (data) -> (server, uuid, perk) -> data.getSavedData(server).modifyStoredDeveloperGlowData(uuid, perk),
            (data) -> (server, uuid) -> data.getSavedData(server).removeStoredDeveloperGlowData(uuid),
            (data) -> ClientDeveloperGlowPacket.Apply::new,
            (data) -> ClientDeveloperGlowPacket.Remove::new,
            (data) -> ClientDeveloperGlowPacket.Sync::new,
            (data) -> perk -> PerkUtil.hasDeveloperGlow()
    );

    private final Function<MinecraftServer, Map<UUID, T>> savedMap;
    private final TriConsumer<MinecraftServer, UUID, T> modify;
    private final BiConsumer<MinecraftServer, UUID> remove;
    private final BiFunction<UUID, T, BasePacket> applyPacket;
    private final Function<UUID, BasePacket> removePacket;
    private final Function<Map<UUID, T>, BasePacket> syncPacket;
    private final Function<T, Predicate<User>> verificationPredicate;

    public ServerPerkData(Function<ServerPerkData<T>, Function<MinecraftServer, Map<UUID, T>>> savedMap,
                          Function<ServerPerkData<T>, TriConsumer<MinecraftServer, UUID, T>> modify,
                          Function<ServerPerkData<T>, BiConsumer<MinecraftServer, UUID>> remove,
                          Function<ServerPerkData<T>, BiFunction<UUID, T, BasePacket>> applyPacket,
                          Function<ServerPerkData<T>, Function<UUID, BasePacket>> removePacket,
                          Function<ServerPerkData<T>, Function<Map<UUID, T>, BasePacket>> syncPacket,
                          Function<ServerPerkData<T>, Function<T, Predicate<User>>> verificationPredicate) {
        this.savedMap = savedMap.apply(this);
        this.modify = modify.apply(this);
        this.remove = remove.apply(this);
        this.applyPacket = applyPacket.apply(this);
        this.removePacket = removePacket.apply(this);
        this.syncPacket = syncPacket.apply(this);
        this.verificationPredicate = verificationPredicate.apply(this);
    }

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

    /**
     * Calls a get method from {@link PerkSavedData}
     * @param server The {@link MinecraftServer}.
     * @return A {@link Map} of {@link UUID}s and a perk data type.
     */
    protected Map<UUID, T> getSavedMap(MinecraftServer server) {
        return this.savedMap.apply(server);
    }

    /**
     * Calls a modify method from {@link PerkSavedData}
     * @param server The {@link MinecraftServer}.
     * @param uuid The player {@link UUID}.
     * @param perk The perk data type.
     */
    protected void modifySavedData(MinecraftServer server, UUID uuid, T perk) {
        this.modify.accept(server, uuid, perk);
    }

    /**
     * Calls a remove method from {@link PerkSavedData}.
     * @param server The {@link MinecraftServer}.
     * @param uuid The player {@link UUID}.
     */
    protected void removeSavedData(MinecraftServer server, UUID uuid) {
        this.remove.accept(server, uuid);
    }

    /**
     * Sends an application packet.
     * @param uuid The player {@link UUID}.
     * @param perk The perk data type.
     * @return The packet.
     */
    protected BasePacket getApplyPacket(UUID uuid, T perk) {
        return this.applyPacket.apply(uuid, perk);
    }

    /**
     * Sends a removal packet.
     * @param uuid The player {@link UUID}.
     * @return The packet.
     */
    protected BasePacket getRemovePacket(UUID uuid) {
        return this.removePacket.apply(uuid);
    }

    /**
     * Sends a sync packet.
     * @param serverPerkData A {@link Map} of {@link UUID}s and a perk data type.
     * @return The packet.
     */
    protected BasePacket getSyncPacket(Map<UUID, T> serverPerkData) {
        return this.syncPacket.apply(serverPerkData);
    }

    /**
     * Gets the verification requirement for this perk.
     * @param perk The perk data type.
     * @return A {@link User} {@link Predicate} for the perk.
     */
    protected Predicate<User> getVerificationPredicate(T perk) {
        return this.verificationPredicate.apply(perk);
    }
}
