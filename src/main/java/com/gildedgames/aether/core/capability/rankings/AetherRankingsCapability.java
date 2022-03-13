package com.gildedgames.aether.core.capability.rankings;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;

public class AetherRankingsCapability implements AetherRankings {
    private final Player player;

    private static final EntityDataAccessor<Boolean> DATA_SLEEVE_GLOVES_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_RENDER_HALO_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_RENDER_DEVELOPER_GLOW_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);

    public AetherRankingsCapability(Player player) {
        this.player = player;
        this.defineSynchedData();
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("HatGloves", this.areSleeveGloves());
        tag.putBoolean("RenderHalo", this.shouldRenderHalo());
        tag.putBoolean("RenderDeveloperGlow", this.shouldRenderDeveloperGlow());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("HatGloves")) {
            this.setSleeveGloves(tag.getBoolean("HatGloves"));
        }
        if (tag.contains("RenderHalo")) {
            this.setRenderHalo(tag.getBoolean("RenderHalo"));
        }
        if (tag.contains("RenderDeveloperGlow")) {
            this.setRenderDeveloperGlow(tag.getBoolean("RenderDeveloperGlow"));
        }
    }

    @Override
    public void defineSynchedData() {
        this.getPlayer().getEntityData().define(DATA_SLEEVE_GLOVES_ID, false);
        this.getPlayer().getEntityData().define(DATA_RENDER_HALO_ID, true);
        this.getPlayer().getEntityData().define(DATA_RENDER_DEVELOPER_GLOW_ID, false);
    }

    @Override
    public void copyFrom(AetherRankings other) {
        this.setSleeveGloves(other.areSleeveGloves());
        this.setRenderHalo(other.shouldRenderHalo());
        this.setRenderDeveloperGlow(other.shouldRenderDeveloperGlow());
    }

    @Override
    public void onUpdate() { }

    @Override
    public void setSleeveGloves(boolean areHatGloves) {
        this.getPlayer().getEntityData().set(DATA_SLEEVE_GLOVES_ID, areHatGloves);
    }

    @Override
    public boolean areSleeveGloves() {
        return this.getPlayer().getEntityData().get(DATA_SLEEVE_GLOVES_ID);
    }

    @Override
    public void setRenderHalo(boolean renderHalo) {
        this.getPlayer().getEntityData().set(DATA_RENDER_HALO_ID, renderHalo);
    }

    @Override
    public boolean shouldRenderHalo() {
        return this.getPlayer().getEntityData().get(DATA_RENDER_HALO_ID);
    }

    @Override
    public void setRenderDeveloperGlow(boolean renderDeveloperGlow) {
        this.getPlayer().getEntityData().set(DATA_RENDER_DEVELOPER_GLOW_ID, renderDeveloperGlow);
    }

    @Override
    public boolean shouldRenderDeveloperGlow() {
        return this.getPlayer().getEntityData().get(DATA_RENDER_DEVELOPER_GLOW_ID);
    }
}
