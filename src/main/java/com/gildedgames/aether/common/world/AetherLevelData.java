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

    private long dayTime = 0;
    private boolean eternalDay = false;

    public AetherLevelData(WorldData worldData, ServerLevelData levelData) {
        super(worldData, levelData);
        this.wrapped = levelData;
        this.gameRules = new WrappedGameRules(worldData.getGameRules(), ImmutableSet.of(GameRules.RULE_WEATHER_CYCLE));
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

    @Override
    public void setThundering(boolean pThundering) {
        this.wrapped.setThundering(pThundering);
    }

    public boolean getEternalDay() {
        return this.eternalDay;
    }

    public void setEternalDay(boolean pEternalDay) {
        this.eternalDay = pEternalDay;
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
