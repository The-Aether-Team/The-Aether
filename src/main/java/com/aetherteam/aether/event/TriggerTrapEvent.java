package com.aetherteam.aether.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.fml.LogicalSide;

/**
 * TriggerTrapEvent is fired when a player steps on a trapped block.
 * <br>
 * This event is {@link ICancellableEvent}.<br>
 * If the event is not canceled, the trapped block will be detected as having been stepped on.
 * <br>
 * This event is fired on the {@link net.neoforged.neoforge.common.NeoForge#EVENT_BUS}.<br>
 * <br>
 * This event is fired on both {@link LogicalSide sides}.<br>
 * <br>
 * If this event is canceled, the trapped block will not trigger.
 */
public class TriggerTrapEvent extends BlockEvent implements ICancellableEvent {
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
}
