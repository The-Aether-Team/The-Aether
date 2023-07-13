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
    public void syncFromServer(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            PacketRelay.sendToPlayer(AetherPacketHandler.INSTANCE, this.getSyncPacket(this.getServerPerkData(serverPlayer.getServer())), serverPlayer);
        }
    }

    public void applyPerkWithVerification(MinecraftServer server, UUID uuid, T perk) {
        Map<UUID, User> storedUsers = UserData.Server.getStoredUsers();
        if (storedUsers.containsKey(uuid)) {
            User user = storedUsers.get(uuid);
            if (user != null && this.getVerificationPredicate(perk).test(user)) {
                PacketRelay.sendToAll(AetherPacketHandler.INSTANCE, this.getApplyPacket(uuid, perk));
                this.modifySavedData(server, uuid, perk);
            }
        }
    }

    public void removePerk(MinecraftServer server, UUID uuid) {
        PacketRelay.sendToAll(AetherPacketHandler.INSTANCE, this.getRemovePacket(uuid));
        this.removeSavedData(server, uuid);
    }

    public Map<UUID, T> getServerPerkData(MinecraftServer server) {
        Map<UUID, User> storedUsers = UserData.Server.getStoredUsers();
        Map<UUID, T> verifiedPerkData = new HashMap<>();
        for (Map.Entry<UUID, T> serverPerkDataEntry : this.getSavedMap(server).entrySet()) {
            if (storedUsers.containsKey(serverPerkDataEntry.getKey())) {
                User user = storedUsers.get(serverPerkDataEntry.getKey());
                if (user != null && this.getVerificationPredicate(serverPerkDataEntry.getValue()).test(user)) {
                    verifiedPerkData.put(serverPerkDataEntry.getKey(), serverPerkDataEntry.getValue());
                }
            }
        }
        return ImmutableMap.copyOf(verifiedPerkData);
    }

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
