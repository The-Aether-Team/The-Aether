package com.aetherteam.aether.event;

import com.aetherteam.nitrogen.fabric.events.CancellableCallbackImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/**
 * TriggerTrapEvent is fired when a player steps on a trapped block.
 * <br>
 * This event is {@link CancellableCallbackImpl}.<br>
 * If the event is not canceled, the trapped block will be detected as having been stepped on.
 * <br>
 * This event is fired on both {@link EnvType sides}.<br>
 * <br>
 * If this event is canceled, the trapped block will not trigger.
 */
public class TriggerTrapEvent extends CancellableCallbackImpl {
    private final LevelAccessor level;
    private final BlockPos pos;
    private final BlockState state;
    private final Player player;

    /**
     * @param player The {@link Player} stepping on the trapped block.
     * @param level  The {@link LevelAccessor} that the block is in.
     * @param pos    The {@link BlockPos} of the block.
     * @param state  The {@link BlockState} of the block.
     */
    public TriggerTrapEvent(Player player, LevelAccessor level, BlockPos pos, BlockState state) {
        this.level = level;
        this.pos = pos;
        this.state = state;
        this.player = player;
    }

    public LevelAccessor getLevel() {
        return level;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockState getState() {
        return state;
    }

    /**
     * @return The {@link Player} stepping on the trapped block.
     */
    public Player getPlayer() {
        return this.player;
    }

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, invokers -> event -> {
        for (var invoker : invokers) invoker.trapTriggered(event);
    });

    public interface Callback {
        void trapTriggered(TriggerTrapEvent event);
    }
}
