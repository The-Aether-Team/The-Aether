package com.gildedgames.aether.core.capability.rankings;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.commons.lang3.tuple.Triple;

public interface AetherRankings extends INBTSynchable<CompoundTag> {
    Player getPlayer();

    static LazyOptional<AetherRankings> get(Player player) {
        return player.getCapability(AetherCapabilities.AETHER_RANKINGS_CAPABILITY);
    }

    void copyFrom(AetherRankings other);

    void onUpdate();

    void setAreSleeveGloves(boolean areHatGloves);
    boolean areSleeveGloves();

    void setIsHaloEnabled(boolean renderHalo);
    boolean isHaloEnabled();

    void setHaloColor(String color);
    Triple<Float, Float, Float> getHaloColor();
    String getHaloHex();

    void setIsDeveloperGlowEnabled(boolean renderDeveloperGlow);
    boolean isDeveloperGlowEnabled();

    void setDeveloperGlowColor(String color);
    Triple<Float, Float, Float> getDeveloperGlowColor();
    String getDeveloperGlowHex();
}
