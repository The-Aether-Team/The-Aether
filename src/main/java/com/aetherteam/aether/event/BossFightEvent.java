package com.aetherteam.aether.event;

import com.aetherteam.nitrogen.entity.BossRoomTracker;
import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.EntityEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

/**
 * BossFightEvent is fired when an event for a boss fight occurs.<br>
 *  * If a method utilizes this {@link BaseEvent} as its parameter, the method will receive every child event of this class.<br>
 */
public abstract class BossFightEvent extends EntityEvents {
    public static final Event<StartCallback> START = EventFactory.createArrayBacked(StartCallback.class, callbacks -> start -> {
        for (StartCallback e : callbacks)
            e.onBossFightStart(start);
    });
    public static final Event<StopCallback> STOP = EventFactory.createArrayBacked(StopCallback.class, callbacks -> stop -> {
        for (StopCallback e : callbacks)
            e.onBossFightStop(stop);
    });
    public static final Event<AddPlayerCallback> ADD_PLAYER = EventFactory.createArrayBacked(AddPlayerCallback.class, callbacks -> event -> {
        for (AddPlayerCallback e : callbacks)
            e.onBossFightAddPlayer(event);
    });
    public static final Event<RemovePlayerCallback> REMOVE_PLAYER = EventFactory.createArrayBacked(RemovePlayerCallback.class, callbacks -> event -> {
        for (RemovePlayerCallback e : callbacks)
            e.onBossFightRemovePlayer(event);
    });
    private final BossRoomTracker<?> dungeon;

    /**
     * @param entity The {@link Entity} triggering the boss fight.
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
     * This event is not cancelable. <br>
     * <br>
     * This event does not have a result. <br>
     * <br>
     * This event is fired on both {@link EnvType sides}.
     */
    public static class Start extends BossFightEvent {
        public Start(Entity entity, BossRoomTracker<?> dungeon) {
            super(entity, dungeon);
        }

        @Override
        public void sendEvent() {
            START.invoker().onBossFightStart(this);
        }
    }

    /**
     * BossFightEvent.Start is fired when a boss stops a fight.
     * <br>
     * This event is not cancelable. <br>
     * <br>
     * This event does not have a result. <br>
     * <br>
     * This event is fired on both {@link EnvType sides}.
     */
    public static class Stop extends BossFightEvent {
        public Stop(Entity entity, BossRoomTracker<?> dungeon) {
            super(entity, dungeon);
        }

        @Override
        public void sendEvent() {
            STOP.invoker().onBossFightStop(this);
        }
    }

    /**
     * BossFightEvent.AddPlayer is fired when a player is added to a boss fight.
     * <br>
     * This event is not cancelable. <br>
     * <br>
     * This event does not have a result. <br>
     * <br>
     * This event is only fired on the {@link EnvType#SERVER} side.<br>
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

        @Override
        public void sendEvent() {
            ADD_PLAYER.invoker().onBossFightAddPlayer(this);
        }
    }

    /**
     * BossFightEvent.RemovePlayer is fired when a player is removed from a boss fight.
     * <br>
     * This event is not cancelable. <br>
     * <br>
     * This event does not have a result. <br>
     * <br>
     * This event is only fired on the {@link EnvType#SERVER} side.<br>
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

        @Override
        public void sendEvent() {
            REMOVE_PLAYER.invoker().onBossFightRemovePlayer(this);
        }
    }

    @FunctionalInterface
    public interface StartCallback {
        void onBossFightStart(Start event);
    }

    @FunctionalInterface
    public interface StopCallback {
        void onBossFightStop(Stop event);
    }

    @FunctionalInterface
    public interface AddPlayerCallback {
        void onBossFightAddPlayer(AddPlayer event);
    }

    @FunctionalInterface
    public interface RemovePlayerCallback {
        void onBossFightRemovePlayer(RemovePlayer event);
    }
}
