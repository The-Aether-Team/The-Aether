package com.aetherteam.aether.world;

import net.minecraft.world.level.GameRules;

import java.util.Set;

/**
 * Wrapper for GameRules to override some gamerules in the Aether.
 * Any gamerules in the blacklist will always return false in an Aether dimension.
 */
public class WrappedGameRules extends GameRules {
    private final GameRules gameRules;
    private final Set<Key<BooleanValue>> blacklist;

    public WrappedGameRules(GameRules gameRules, Set<GameRules.Key<GameRules.BooleanValue>> keys) {
        this.gameRules = gameRules;
        this.blacklist = keys;
    }

    @Override
    public <T extends Value<T>> T getRule(Key<T> key) {
        return this.gameRules.getRule(key);
    }

    @Override
    public boolean getBoolean(Key<GameRules.BooleanValue> key) {
        return !this.blacklist.contains(key) && this.getRule(key).get();
    }
}
