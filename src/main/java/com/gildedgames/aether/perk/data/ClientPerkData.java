package com.gildedgames.aether.perk.data;

import com.gildedgames.nitrogen.api.users.UserData;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;

public abstract class ClientPerkData<T> {
    public void applyPerk(UUID uuid, T perk) {
        this.getMap().put(uuid, perk);
    }

    public void removePerk(UUID uuid) {
        this.getMap().remove(uuid);
    }

    public Map<UUID, T> getClientPerkData() {
        return ImmutableMap.copyOf(this.getMap());
    }

    public boolean canSync(Player player) {
        return UserData.Client.getClientUser() != null && player.getLevel().isClientSide() && Minecraft.getInstance().player != null && player.getUUID().equals(Minecraft.getInstance().player.getUUID());
    }

    public abstract void syncFromClient(Player player);

    protected abstract Map<UUID, T> getMap();
}
