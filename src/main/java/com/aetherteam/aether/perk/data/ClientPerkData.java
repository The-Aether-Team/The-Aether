package com.aetherteam.aether.perk.data;

import com.aetherteam.nitrogen.api.users.UserData;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;

public abstract class ClientPerkData<T> {
    /**
     * Adds a perk to a client map of player {@link UUID}s paired with perks.<br>
     * The type of perk is handled by subclasses.
     *
     * @param uuid The player's {@link UUID}.
     * @param perk The perk data type.
     */
    public void applyPerk(UUID uuid, T perk) {
        this.getMap().put(uuid, perk);
    }

    /**
     * Removes a perk from a player that currently has one on the client.
     *
     * @param uuid The player's {@link UUID}.
     */
    public void removePerk(UUID uuid) {
        this.getMap().remove(uuid);
    }

    /**
     * @return A map of player {@link UUID}s paired with perks on the client.
     */
    public Map<UUID, T> getClientPerkData() {
        return ImmutableMap.copyOf(this.getMap());
    }

    /**
     * A basic check for whether the player can have perk data synced to the client.
     *
     * @param player The {@link Player}.
     * @return The result of the check, as a {@link Boolean}.
     */
    public boolean canSync(Player player) {
        return UserData.Client.getClientUser() != null && player.level().isClientSide() && Minecraft.getInstance().player != null && player.getUUID().equals(Minecraft.getInstance().player.getUUID());
    }

    public abstract void syncFromClient(Player player);

    protected abstract Map<UUID, T> getMap();
}
