package com.gildedgames.aether.core.capability.interfaces;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface AetherRankingsSerializable extends INBTSerializable<CompoundTag>
{
    Player getPlayer();

    static LazyOptional<AetherRankingsSerializable> get(Player player) {
        return player.getCapability(AetherCapabilities.AETHER_RANKINGS_CAPABILITY);
    }

    void defineSynchedData();

    void copyFrom(AetherRankingsSerializable other);

    void onUpdate();

    void setSleeveGloves(boolean areHatGloves);
    boolean areSleeveGloves();

    void setRenderHalo(boolean renderHalo);
    boolean shouldRenderHalo();

    void setRenderDeveloperGlow(boolean renderDeveloperGlow);
    boolean shouldRenderDeveloperGlow();
}
