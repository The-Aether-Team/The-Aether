package com.aetherteam.aether.perk.data;

import com.aetherteam.aether.network.AetherPacket;
import com.aetherteam.aether.network.packet.clientbound.ClientDeveloperGlowPacket;
import com.aetherteam.aether.perk.types.DeveloperGlow;
import com.aetherteam.aether.perk.PerkUtil;
import com.aetherteam.nitrogen.api.users.User;
import net.minecraft.server.MinecraftServer;

import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class ServerDeveloperGlowPerkData extends ServerPerkData<DeveloperGlow> {
    public static final ServerDeveloperGlowPerkData INSTANCE = new ServerDeveloperGlowPerkData();

    @Override
    protected Map<UUID, DeveloperGlow> getSavedMap(MinecraftServer server) {
        return this.getSavedData(server).getStoredDeveloperGlowData();
    }

    @Override
    protected void modifySavedData(MinecraftServer server, UUID uuid, DeveloperGlow perk) {
        this.getSavedData(server).modifyStoredDeveloperGlowData(uuid, perk);
    }

    @Override
    protected void removeSavedData(MinecraftServer server, UUID uuid) {
        this.getSavedData(server).removeStoredDeveloperGlowData(uuid);
    }

    @Override
    protected AetherPacket getApplyPacket(UUID uuid, DeveloperGlow perk) {
        return new ClientDeveloperGlowPacket.Apply(uuid, perk);
    }

    @Override
    protected AetherPacket getRemovePacket(UUID uuid) {
        return new ClientDeveloperGlowPacket.Remove(uuid);
    }

    @Override
    protected AetherPacket getSyncPacket(Map<UUID, DeveloperGlow> serverPerkData) {
        return new ClientDeveloperGlowPacket.Sync(serverPerkData);
    }

    @Override
    protected Predicate<User> getVerificationPredicate(DeveloperGlow perk) {
        return PerkUtil.hasDeveloperGlow();
    }
}
