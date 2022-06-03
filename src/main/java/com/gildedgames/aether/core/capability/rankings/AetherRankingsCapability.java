package com.gildedgames.aether.core.capability.rankings;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.capability.CapabilitySyncing;
import com.gildedgames.aether.core.network.AetherPacket;
import com.gildedgames.aether.core.network.packet.AetherRankingsSyncPacket;
import com.gildedgames.aether.core.util.AetherCustomizations;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.tuple.Triple;

public class AetherRankingsCapability extends CapabilitySyncing implements AetherRankings {
    private final Player player;

    private boolean sleeveGloves = false;
    private boolean haloEnabled = true;
    private String haloColor = null;
    private boolean developerGlowEnabled = false;
    private String developerGlowColor = null;

    public AetherRankingsCapability(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("HatGloves", this.areSleeveGloves());
        tag.putBoolean("RenderHalo", this.isHaloEnabled());
        if (this.getHaloHex() != null) {
            tag.putString("HaloColor", this.getHaloHex());
        }
        tag.putBoolean("RenderDeveloperGlow", this.isDeveloperGlowEnabled());
        if (this.getDeveloperGlowHex() != null) {
            tag.putString("DeveloperGlowColor", this.getDeveloperGlowHex());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("HatGloves")) {
            this.setAreSleeveGloves(tag.getBoolean("HatGloves"));
        }
        if (tag.contains("RenderHalo")) {
            this.setIsHaloEnabled(tag.getBoolean("RenderHalo"));
        }
        if (tag.contains("HaloColor")) {
            this.setHaloColor(tag.getString("HaloColor"));
        }
        if (tag.contains("RenderDeveloperGlow")) {
            this.setIsDeveloperGlowEnabled(tag.getBoolean("RenderDeveloperGlow"));
        }
        if (tag.contains("DeveloperGlowColor")) {
            this.setDeveloperGlowColor(tag.getString("DeveloperGlowColor"));
        }
    }

    @Override
    public CompoundTag serializeSynchableNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("HatGloves_Syncing", this.areSleeveGloves());
        tag.putBoolean("RenderHalo_Syncing", this.isHaloEnabled());
        if (this.getHaloHex() != null) {
            tag.putString("HaloColor_Syncing", this.getHaloHex());
        }
        tag.putBoolean("RenderDeveloperGlow_Syncing", this.isDeveloperGlowEnabled());
        if (this.getDeveloperGlowHex() != null) {
            tag.putString("DeveloperGlowColor_Syncing", this.getDeveloperGlowHex());
        }
        return tag;
    }

    @Override
    public void deserializeSynchableNBT(CompoundTag tag) {
        if (tag.contains("HatGloves_Syncing")) {
            this.setAreSleeveGloves(tag.getBoolean("HatGloves_Syncing"));
        }
        if (tag.contains("RenderHalo_Syncing")) {
            this.setIsHaloEnabled(tag.getBoolean("RenderHalo_Syncing"));
        }
        if (tag.contains("HaloColor_Syncing")) {
            this.setHaloColor(tag.getString("HaloColor_Syncing"));
        }
        if (tag.contains("RenderDeveloperGlow_Syncing")) {
            this.setIsDeveloperGlowEnabled(tag.getBoolean("RenderDeveloperGlow_Syncing"));
        }
        if (tag.contains("DeveloperGlowColor_Syncing")) {
            this.setDeveloperGlowColor(tag.getString("DeveloperGlowColor_Syncing"));
        }
    }

    @Override
    public void copyFrom(AetherRankings other) {
        this.setAreSleeveGloves(other.areSleeveGloves());
        this.setIsHaloEnabled(other.isHaloEnabled());
        this.setHaloColor(other.getHaloHex());
        this.setIsDeveloperGlowEnabled(other.isDeveloperGlowEnabled());
        this.setDeveloperGlowColor(other.getDeveloperGlowHex());
    }

    @Override
    public void onUpdate() {
        this.updateSyncableNBTFromServer(this.getPlayer().getLevel(), true);
        if (this.getPlayer().level.isClientSide()) {
            AetherCustomizations.INSTANCE.sync();
        }

        Aether.LOGGER.info(this.getPlayer().getDisplayName().getString() + ": halo: " + this.getHaloHex());
        Aether.LOGGER.info(this.getPlayer().getDisplayName().getString() + ": glow: " + this.getDeveloperGlowHex());
    }

    @Override
    public void setAreSleeveGloves(boolean areHatGloves) {
        this.markDirty(true);
        this.sleeveGloves = areHatGloves;
        this.updateSyncableNBTFromClient(this.getPlayer().level);
    }

    @Override
    public boolean areSleeveGloves() {
        return this.sleeveGloves;
    }

    @Override
    public void setIsHaloEnabled(boolean renderHalo) {
        this.markDirty(true);
        this.haloEnabled = renderHalo;
        this.updateSyncableNBTFromClient(this.getPlayer().level);
    }

    @Override
    public boolean isHaloEnabled() {
        return this.haloEnabled;
    }

    @Override
    public void setHaloColor(String color) {
        this.markDirty(true);
        this.haloColor = color;
        this.updateSyncableNBTFromClient(this.getPlayer().level);
    }

    @Override
    public Triple<Float, Float, Float> getHaloColor() {
        return this.getColor(this.haloColor);
    }

    @Override
    public String getHaloHex() {
        return this.haloColor;
    }

    @Override
    public void setIsDeveloperGlowEnabled(boolean renderDeveloperGlow) {
        this.markDirty(true);
        this.developerGlowEnabled = renderDeveloperGlow;
        this.updateSyncableNBTFromClient(this.getPlayer().level);
    }

    @Override
    public boolean isDeveloperGlowEnabled() {
        return this.developerGlowEnabled;
    }

    @Override
    public void setDeveloperGlowColor(String color) {
        this.markDirty(true);
        this.developerGlowColor = color;
        this.updateSyncableNBTFromClient(this.getPlayer().level);
    }

    @Override
    public Triple<Float, Float, Float> getDeveloperGlowColor() {
        return this.getColor(this.developerGlowColor);
    }

    @Override
    public String getDeveloperGlowHex() {
        return this.developerGlowColor;
    }

    @Override
    public AetherPacket.AbstractAetherPacket getSyncPacket(CompoundTag tag) {
        return new AetherRankingsSyncPacket(this.getPlayer().getId(), tag);
    }

    private Triple<Float, Float, Float> getColor(String hex) {
        try {
            int decimal = Integer.parseInt(hex, 16);
            int r = (decimal & 16711680) >> 16;
            int g = (decimal & '\uff00') >> 8;
            int b = (decimal & 255);
            return Triple.of((float) r / 255.0F, (float) g / 255.0F, (float) b / 255.0F);
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}
