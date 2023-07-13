package com.aetherteam.aether.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class TriggerTrapEvent extends BlockEvent {
    private final Player player;

    public TriggerTrapEvent(Player player, LevelAccessor level, BlockPos pos, BlockState state) {
        super(level, pos, state);
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}
