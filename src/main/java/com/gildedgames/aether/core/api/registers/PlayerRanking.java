package com.gildedgames.aether.core.api.registers;

public enum PlayerRanking
{
    NONE(false, false),
    CELEBRITY( false, true),
    TESTER(false, true), //MODDING LEGACY TESTERS
    CONTRIBUTOR(false, true),
    RETIRED_MODDING_LEGACY(true, true),
    MODDING_LEGACY(true, true),
    RETIRED_GILDED_GAMES(true, true),
    GILDED_GAMES(true, true);

    public final boolean hasDevGlow, hasHalo;

    PlayerRanking(boolean hasDevGlow, boolean hasHalo) {
        this.hasHalo = hasHalo;
        this.hasDevGlow = hasDevGlow;
    }

    public boolean hasHalo() {
        return this.hasHalo;
    }

    public boolean hasDevGlow() {
        return this.hasDevGlow;
    }
}
