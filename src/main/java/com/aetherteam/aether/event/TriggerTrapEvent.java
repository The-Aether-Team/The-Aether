package com.aetherteam.aether.event;

import io.github.fabricators_of_create.porting_lib.event.common.BlockEvents;
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
 * This event is cancelable.<br>
 * If the event is not canceled, the trapped block will be detected as having been stepped on.
 * <br>
 * This event does not have a result.<br>
 * <br>
 * This event is fired on both {@link EnvType sides}.<br>
 * <br>
 * If this event is canceled, the trapped block will not trigger.
 */
public class TriggerTrapEvent extends BlockEvents {
    public static final Event<TriggerTrapCallback> TRIGGER_TRAP = EventFactory.createArrayBacked(TriggerTrapCallback.class, callbacks -> event -> {
        for (TriggerTrapCallback e : callbacks)
            e.onTriggerTrap(event);
    });
    private final Player player;

    /**
     * @param player The {@link Player} stepping on the trapped block.
     * @param level The {@link LevelAccessor} that the block is in.
     * @param pos The {@link BlockPos} of the block.
     * @param state The {@link BlockState} of the block.
     */
    public TriggerTrapEvent(Player player, LevelAccessor level, BlockPos pos, BlockState state) {
        super(level, pos, state);
        this.player = player;
    }

    /**
     * @return The {@link Player} stepping on the trapped block.
     */
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void sendEvent() {
        TRIGGER_TRAP.invoker().onTriggerTrap(this);
    }

    @FunctionalInterface
    public interface TriggerTrapCallback {
        void onTriggerTrap(TriggerTrapEvent event);
    }
}
