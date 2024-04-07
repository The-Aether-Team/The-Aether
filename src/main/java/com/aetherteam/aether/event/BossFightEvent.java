package com.aetherteam.aether.event;

import com.aetherteam.nitrogen.entity.BossRoomTracker;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.event.entity.EntityEvent;

/**
 * BossFightEvent is fired when an event for a boss fight occurs.<br>
 * * If a method utilizes this {@link EntityEvent} as its parameter, the method will receive every child event of this class.<br>
 * * <br>
 * * All children of this event are fired on the {@link net.neoforged.neoforge.common.NeoForge#EVENT_BUS}.
 */
public abstract class BossFightEvent extends EntityEvent {
    private final BossRoomTracker<?> dungeon;

    /**
     * @param entity  The {@link Entity} triggering the boss fight.
     * @param dungeon The {@link BossRoomTracker} representing the boss's dungeon.
     */
    public BossFightEvent(Entity entity, BossRoomTracker<?> dungeon) {
        super(entity);
        this.dungeon = dungeon;
    }

    /**
     * @return The {@link BossRoomTracker} representing the boss's dungeon.
     */
    public BossRoomTracker<?> getDungeon() {
        return this.dungeon;
    }

    /**
     * BossFightEvent.Start is fired when a boss starts a fight.
     * <br>
     * This event is not {@link net.neoforged.bus.api.ICancellableEvent}. <br>
     * <br>
     * This event is fired on both {@link LogicalSide sides}.
     */
    public static class Start extends BossFightEvent {
        public Start(Entity entity, BossRoomTracker<?> dungeon) {
            super(entity, dungeon);
        }
    }

    /**
     * BossFightEvent.Start is fired when a boss stops a fight.
     * <br>
     * This event is not {@link net.neoforged.bus.api.ICancellableEvent}. <br>
     * <br>
     * This event is fired on both {@link LogicalSide sides}.
     */
    public static class Stop extends BossFightEvent {
        public Stop(Entity entity, BossRoomTracker<?> dungeon) {
            super(entity, dungeon);
        }
    }

    /**
     * BossFightEvent.AddPlayer is fired when a player is added to a boss fight.
     * <br>
     * This event is not {@link net.neoforged.bus.api.ICancellableEvent}. <br>
     * <br>
     * This event is only fired on the {@link LogicalSide#SERVER} side.<br>
     */
    public static class AddPlayer extends BossFightEvent {
        private final ServerPlayer player;

        public AddPlayer(Entity entity, BossRoomTracker<?> dungeon, ServerPlayer player) {
            super(entity, dungeon);
            this.player = player;
        }

        /**
         * @return The {@link ServerPlayer} belonging to the boss fight.
         */
        public ServerPlayer getPlayer() {
            return this.player;
        }
    }

    /**
     * BossFightEvent.RemovePlayer is fired when a player is removed from a boss fight.
     * <br>
     * This event is not {@link net.neoforged.bus.api.ICancellableEvent}. <br>
     * <br>
     * This event is only fired on the {@link LogicalSide#SERVER} side.<br>
     */
    public static class RemovePlayer extends BossFightEvent {
        private final ServerPlayer player;

        public RemovePlayer(Entity entity, BossRoomTracker<?> dungeon, ServerPlayer player) {
            super(entity, dungeon);
            this.player = player;
        }

        /**
         * @return The {@link ServerPlayer} belonging to the boss fight.
         */
        public ServerPlayer getPlayer() {
            return this.player;
        }
    }
}
