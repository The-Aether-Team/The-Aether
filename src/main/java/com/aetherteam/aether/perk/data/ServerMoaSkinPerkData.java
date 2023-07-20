package com.aetherteam.aether.perk.data;

import com.aetherteam.aether.network.packet.clientbound.ClientMoaSkinPacket;
import com.aetherteam.aether.perk.types.MoaData;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.server.MinecraftServer;

import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class ServerMoaSkinPerkData extends ServerPerkData<MoaData> {
    public static final ServerMoaSkinPerkData INSTANCE = new ServerMoaSkinPerkData();

    /**
     * Returns {@link PerkSavedData#getStoredSkinData()}.
     */
    @Override
    protected Map<UUID, MoaData> getSavedMap(MinecraftServer server) {
        return this.getSavedData(server).getStoredSkinData();
    }

    /**
     * Calls {@link PerkSavedData#modifyStoredSkinData(UUID, MoaData)}.
     */
    @Override
    protected void modifySavedData(MinecraftServer server, UUID uuid, MoaData perk) {
        this.getSavedData(server).modifyStoredSkinData(uuid, perk);
    }

    /**
     * Calls {@link PerkSavedData#removeStoredSkinData(UUID)}.
     */
    @Override
    protected void removeSavedData(MinecraftServer server, UUID uuid) {
        this.getSavedData(server).removeStoredSkinData(uuid);
    }

    /**
     * Sends {@link ClientMoaSkinPacket.Apply} to the client.
     */
    @Override
    protected BasePacket getApplyPacket(UUID uuid, MoaData perk) {
        return new ClientMoaSkinPacket.Apply(uuid, perk);
    }

    /**
     * Sends {@link ClientMoaSkinPacket.Remove} to the client.
     */
    @Override
    protected BasePacket getRemovePacket(UUID uuid) {
        return new ClientMoaSkinPacket.Remove(uuid);
    }

    /**
     * Sends {@link ClientMoaSkinPacket.Sync} to the client.
     */
    @Override
    protected BasePacket getSyncPacket(Map<UUID, MoaData> serverPerkData) {
        return new ClientMoaSkinPacket.Sync(serverPerkData);
    }

    /**
     * Gets the verification requirement for this perk.
     * @param perk A {@link MoaData} class.
     * @return A {@link User} {@link Predicate} for the {@link MoaData}.
     */
    @Override
    protected Predicate<User> getVerificationPredicate(MoaData perk) {
        return perk.moaSkin().getUserPredicate();
    }
}
