package com.gildedgames.aether.world;

import net.minecraft.world.level.GameRules;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * Class to override some gamerules in the Aether. Any gamerules in the blacklist will always return false in an Aether dimension.
 */
public class WrappedGameRules extends GameRules {
    public final GameRules gameRules;
    public final Set<Key<BooleanValue>> blacklist;

    public WrappedGameRules(GameRules gameRules, Set<GameRules.Key<GameRules.BooleanValue>> keys) {
        this.gameRules = gameRules;
        this.blacklist = keys;
    }

    @Override
    public <T extends Value<T>> T getRule(Key<T> pKey) {
        return this.gameRules.getRule(pKey);
    }

    @Override
    public boolean getBoolean(Key<GameRules.BooleanValue> pKey) {
        return !blacklist.contains(pKey) && this.getRule(pKey).get();
    }

    @Override
    public int getInt(Key<IntegerValue> pKey) {
        return super.getInt(pKey);
    }
}
