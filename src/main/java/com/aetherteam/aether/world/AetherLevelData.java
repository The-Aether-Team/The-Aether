package com.aetherteam.aether.world;

import com.google.common.collect.ImmutableSet;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WorldData;

/**
 * A wrapper for ServerLevelData. This is used to detach the day cycle from the Overworld and to allow the weather to be
 * set from the Aether. It gets applied to any dimension where the effects are equal to the Aether's dimension type ID.
 * A gamerule wrapper is used to prevent the overworld's weather cycle from being affected by the Aether.
 */
public class AetherLevelData extends DerivedLevelData {
    private final ServerLevelData wrapped;
    private final WrappedGameRules gameRules;

    private long dayTime;

    public AetherLevelData(WorldData worldData, ServerLevelData overworldData, long dayTime) {
        super(worldData, overworldData);
        this.wrapped = overworldData;
        this.gameRules = new WrappedGameRules(worldData.getGameRules(), ImmutableSet.of(GameRules.RULE_WEATHER_CYCLE, GameRules.RULE_DOFIRETICK));
        this.dayTime = dayTime;
    }

    /**
     * @return The world time in ticks.
     */
    @Override
    public long getDayTime() {
        return this.dayTime;
    }

    /**
     * Sets the world time.
     *
     * @param time The {@link Integer} for the time in ticks.
     */
    @Override
    public void setDayTime(long time) {
        this.dayTime = time;
    }

    /**
     * Sets the number of ticks the weather will be clear for.
     *
     * @param time The {@link Integer} for the time in ticks.
     */
    @Override
    public void setClearWeatherTime(int time) {
        this.wrapped.setClearWeatherTime(time);
    }

    /**
     * Sets whether it is raining.
     *
     * @param raining The {@link Boolean} value.
     */
    @Override
    public void setRaining(boolean raining) {
        this.wrapped.setRaining(raining);
    }

    /**
     * Sets the number of ticks until rain.
     *
     * @param time The {@link Integer} for the time in ticks.
     */
    @Override
    public void setRainTime(int time) {
        this.wrapped.setRainTime(time);
    }

    /**
     * Sets whether it is thundering.
     *
     * @param thundering The {@link Boolean} value.
     */
    @Override
    public void setThundering(boolean thundering) {
        this.wrapped.setThundering(thundering);
    }

    /**
     * Defines the number of ticks until next lightning bolt.
     *
     * @param time The {@link Integer} for the time in ticks.
     */
    @Override
    public void setThunderTime(int time) {
        this.wrapped.setThunderTime(time);
    }

    /**
     * Gets the game rules class instance.
     *
     * @return The {@link WrappedGameRules} instance.
     */
    @Override
    public WrappedGameRules getGameRules() {
        return this.gameRules;
    }
}
