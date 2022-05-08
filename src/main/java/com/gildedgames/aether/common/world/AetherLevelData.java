package com.gildedgames.aether.common.world;

import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WorldData;

public class AetherLevelData extends DerivedLevelData {
    private final WorldData worldData;
    private final ServerLevelData wrapped;

    private long dayTime;
    private boolean eternalDay;

    public AetherLevelData(WorldData worldData, ServerLevelData levelData) {
        super(worldData, levelData);
        this.worldData = worldData;
        this.wrapped = levelData;
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
}
