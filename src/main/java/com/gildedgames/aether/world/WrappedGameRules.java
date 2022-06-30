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
    public boolean getBoolean(@Nonnull GameRules.Key<GameRules.BooleanValue> pKey) {
        return this.gameRules.getRule(pKey).get() && !blacklist.contains(pKey);
    }
}
