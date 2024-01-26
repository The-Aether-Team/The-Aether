package com.aetherteam.aether.event;

import com.aetherteam.nitrogen.entity.BossRoomTracker;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.fml.LogicalSide;

/**
 * BossFightEvent is fired when an event for a boss fight occurs.<br>
 *  * If a method utilizes this {@link Event} as its parameter, the method will receive every child event of this class.<br>
 *  * <br>
 *  * All children of this event are fired on the {@link MinecraftForge#EVENT_BUS}.
 */
public class BossFightEvent extends EntityEvent {
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
     * This event is not {@link Cancelable}. <br>
     * <br>
     * This event does not have a result. {@link HasResult} <br>
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
     * This event is not {@link Cancelable}. <br>
     * <br>
     * This event does not have a result. {@link HasResult} <br>
     * <br>
     * This event is fired on both {@link LogicalSide sides}.
     */
    public static class Stop extends BossFightEvent {
        public Stop(Entity entity, BossRoomTracker<?> dungeon) {
            super(entity, dungeon);
        }
    }
}
