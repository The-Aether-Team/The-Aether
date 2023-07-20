package com.aetherteam.aether.perk.data;

import com.aetherteam.aether.network.packet.clientbound.ClientDeveloperGlowPacket;
import com.aetherteam.aether.perk.PerkUtil;
import com.aetherteam.aether.perk.types.DeveloperGlow;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.server.MinecraftServer;

import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class ServerDeveloperGlowPerkData extends ServerPerkData<DeveloperGlow> {
    public static final ServerDeveloperGlowPerkData INSTANCE = new ServerDeveloperGlowPerkData();

    /**
     * Returns {@link PerkSavedData#getStoredDeveloperGlowData()}.
     */
    @Override
    protected Map<UUID, DeveloperGlow> getSavedMap(MinecraftServer server) {
        return this.getSavedData(server).getStoredDeveloperGlowData();
    }

    /**
     * Calls {@link PerkSavedData#modifyStoredDeveloperGlowData(UUID, DeveloperGlow)}.
     */
    @Override
    protected void modifySavedData(MinecraftServer server, UUID uuid, DeveloperGlow perk) {
        this.getSavedData(server).modifyStoredDeveloperGlowData(uuid, perk);
    }

    /**
     * Calls {@link PerkSavedData#removeStoredDeveloperGlowData(UUID)}.
     */
    @Override
    protected void removeSavedData(MinecraftServer server, UUID uuid) {
        this.getSavedData(server).removeStoredDeveloperGlowData(uuid);
    }

    /**
     * Sends {@link ClientDeveloperGlowPacket.Apply} to the client.
     */
    @Override
    protected BasePacket getApplyPacket(UUID uuid, DeveloperGlow perk) {
        return new ClientDeveloperGlowPacket.Apply(uuid, perk);
    }

    /**
     * Sends {@link ClientDeveloperGlowPacket.Remove} to the client.
     */
    @Override
    protected BasePacket getRemovePacket(UUID uuid) {
        return new ClientDeveloperGlowPacket.Remove(uuid);
    }

    /**
     * Sends {@link ClientDeveloperGlowPacket.Sync} to the client.
     */
    @Override
    protected BasePacket getSyncPacket(Map<UUID, DeveloperGlow> serverPerkData) {
        return new ClientDeveloperGlowPacket.Sync(serverPerkData);
    }

    /**
     * Gets the verification requirement for this perk.
     * @param perk A {@link DeveloperGlow} class.
     * @return A {@link User} {@link Predicate} for the {@link DeveloperGlow}.
     */
    @Override
    protected Predicate<User> getVerificationPredicate(DeveloperGlow perk) {
        return PerkUtil.hasDeveloperGlow();
    }
}
