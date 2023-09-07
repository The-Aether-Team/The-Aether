package com.aetherteam.aether.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.fml.LogicalSide;

/**
 * TriggerTrapEvent is fired when a player steps on a trapped block.
 * <br>
 * This event is {@link Cancelable}.<br>
 * If the event is not canceled, the trapped block will be detected as having been stepped on.
 * <br>
 * This event does not have a result. {@link net.minecraftforge.eventbus.api.Event.HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.<br>
 * <br>
 * This event is fired on both {@link LogicalSide sides}.<br>
 * <br>
 * If this event is canceled, the trapped block will not trigger.
 */
@Cancelable
public class TriggerTrapEvent extends BlockEvent {
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
