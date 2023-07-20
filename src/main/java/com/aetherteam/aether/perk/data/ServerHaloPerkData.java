package com.aetherteam.aether.perk.data;

import com.aetherteam.aether.network.packet.clientbound.ClientHaloPacket;
import com.aetherteam.aether.perk.PerkUtil;
import com.aetherteam.aether.perk.types.Halo;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.server.MinecraftServer;

import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class ServerHaloPerkData extends ServerPerkData<Halo> {
    public static final ServerHaloPerkData INSTANCE = new ServerHaloPerkData();

    /**
     * Returns {@link PerkSavedData#getStoredHaloData()}.
     */
    @Override
    protected Map<UUID, Halo> getSavedMap(MinecraftServer server) {
        return this.getSavedData(server).getStoredHaloData();
    }

    /**
     * Calls {@link PerkSavedData#modifyStoredHaloData(UUID, Halo)}.
     */
    @Override
    protected void modifySavedData(MinecraftServer server, UUID uuid, Halo perk) {
        this.getSavedData(server).modifyStoredHaloData(uuid, perk);
    }

    /**
     * Calls {@link PerkSavedData#removeStoredHaloData(UUID)}.
     */
    @Override
    protected void removeSavedData(MinecraftServer server, UUID uuid) {
        this.getSavedData(server).removeStoredHaloData(uuid);
    }

    /**
     * Sends {@link ClientHaloPacket.Apply} to the client.
     */
    @Override
    protected BasePacket getApplyPacket(UUID uuid, Halo perk) {
        return new ClientHaloPacket.Apply(uuid, perk);
    }

    /**
     * Sends {@link ClientHaloPacket.Remove} to the client.
     */
    @Override
    protected BasePacket getRemovePacket(UUID uuid) {
        return new ClientHaloPacket.Remove(uuid);
    }

    /**
     * Sends {@link ClientHaloPacket.Sync} to the client.
     */
    @Override
    protected BasePacket getSyncPacket(Map<UUID, Halo> serverPerkData) {
        return new ClientHaloPacket.Sync(serverPerkData);
    }

    /**
     * Gets the verification requirement for this perk.
     * @param perk A {@link Halo} class.
     * @return A {@link User} {@link Predicate} for the {@link Halo}.
     */
    @Override
    protected Predicate<User> getVerificationPredicate(Halo perk) {
        return PerkUtil.hasHalo();
    }
}
