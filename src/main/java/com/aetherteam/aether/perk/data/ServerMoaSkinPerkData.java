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

    @Override
    protected Map<UUID, MoaData> getSavedMap(MinecraftServer server) {
        return this.getSavedData(server).getStoredSkinData();
    }

    @Override
    protected void modifySavedData(MinecraftServer server, UUID uuid, MoaData perk) {
        this.getSavedData(server).modifyStoredSkinData(uuid, perk);
    }

    @Override
    protected void removeSavedData(MinecraftServer server, UUID uuid) {
        this.getSavedData(server).removeStoredSkinData(uuid);
    }

    @Override
    protected BasePacket getApplyPacket(UUID uuid, MoaData perk) {
        return new ClientMoaSkinPacket.Apply(uuid, perk);
    }

    @Override
    protected BasePacket getRemovePacket(UUID uuid) {
        return new ClientMoaSkinPacket.Remove(uuid);
    }

    @Override
    protected BasePacket getSyncPacket(Map<UUID, MoaData> serverPerkData) {
        return new ClientMoaSkinPacket.Sync(serverPerkData);
    }

    @Override
    protected Predicate<User> getVerificationPredicate(MoaData perk) {
        return perk.moaSkin().getUserPredicate();
    }
}
