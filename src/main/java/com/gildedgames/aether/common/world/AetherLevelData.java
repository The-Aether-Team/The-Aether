package com.gildedgames.aether.common.world;

import com.google.common.collect.ImmutableSet;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WorldData;

import javax.annotation.Nonnull;

public class AetherLevelData extends DerivedLevelData {
    private final ServerLevelData wrapped;
    private final WrappedGameRules gameRules;

    private long dayTime;

    public AetherLevelData(WorldData worldData, ServerLevelData levelData, long dayTime) {
        super(worldData, levelData);
        this.wrapped = levelData;
        this.gameRules = new WrappedGameRules(worldData.getGameRules(), ImmutableSet.of(GameRules.RULE_WEATHER_CYCLE));
        this.dayTime = dayTime;
    }

    @Override
    public long getDayTime() {
        return this.dayTime;
    }

    @Override
    public void setDayTime(long pTime) {
        this.dayTime = pTime;
    }

    @Override
    public void setClearWeatherTime(int pTime) {
        this.wrapped.setClearWeatherTime(pTime);
    }

    @Override
    public void setRaining(boolean pRaining) {
        this.wrapped.setRaining(pRaining);
    }

    /**
     * Sets the number of ticks until rain.
     */
    @Override
    public void setRainTime(int pTime) {
        this.wrapped.setRainTime(pTime);
    }

    @Override
    public void setThundering(boolean pThundering) {
        this.wrapped.setThundering(pThundering);
    }

    /**
     * Defines the number of ticks until next thunderbolt.
     */
    @Override
    public void setThunderTime(int pTime) {
        this.wrapped.setThunderTime(pTime);
    }

    /**
     * Gets the GameRules class Instance.
     *
     */
    @Override
    @Nonnull
    public WrappedGameRules getGameRules() {
        return this.gameRules;
    }
}
