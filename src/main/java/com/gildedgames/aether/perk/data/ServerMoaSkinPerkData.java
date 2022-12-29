package com.gildedgames.aether.perk.data;

import com.gildedgames.aether.network.AetherPacket;
import com.gildedgames.aether.network.packet.client.ClientMoaSkinPacket;
import com.gildedgames.aether.perk.types.MoaData;
import com.gildedgames.nitrogen.api.users.User;
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
    protected AetherPacket getApplyPacket(UUID uuid, MoaData perk) {
        return new ClientMoaSkinPacket.Apply(uuid, perk);
    }

    @Override
    protected AetherPacket getRemovePacket(UUID uuid) {
        return new ClientMoaSkinPacket.Remove(uuid);
    }

    @Override
    protected AetherPacket getSyncPacket(Map<UUID, MoaData> serverPerkData) {
        return new ClientMoaSkinPacket.Sync(serverPerkData);
    }

    @Override
    protected Predicate<User> getVerificationPredicate(MoaData perk) {
        return perk.moaSkin().getUserPredicate();
    }
}
