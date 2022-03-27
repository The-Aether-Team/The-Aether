package com.gildedgames.aether.core.capability.rankings;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface AetherRankings extends INBTSerializable<CompoundTag> {
    Player getPlayer();

    static LazyOptional<AetherRankings> get(Player player) {
        return player.getCapability(AetherCapabilities.AETHER_RANKINGS_CAPABILITY);
    }

    void defineSynchedData();

    void copyFrom(AetherRankings other);

    void onUpdate();

    void setSleeveGloves(boolean areHatGloves);
    boolean areSleeveGloves();

    void setRenderHalo(boolean renderHalo);
    boolean shouldRenderHalo();

    void setRenderDeveloperGlow(boolean renderDeveloperGlow);
    boolean shouldRenderDeveloperGlow();
}
