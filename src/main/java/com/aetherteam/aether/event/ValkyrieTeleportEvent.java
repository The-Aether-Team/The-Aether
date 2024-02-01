package com.aetherteam.aether.event;

import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.neoforged.neoforge;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.eventbus.api.Cancelable;
import net.neoforged.neoforge.fml.LogicalSide;

/**
 * ValkyrieTeleportEvent is fired before a Valkyrie teleports.
 * <br>
 * This event is {@link Cancelable}.<br>
 * If the event is not canceled, the entity will be teleported.
 * <br>
 * This event does not have a result. {@link net.neoforged.neoforge.eventbus.api.Event.HasResult}<br>
 * <br>
 * This event is fired on the {@link neoforged.neoforge#EVENT_BUS}.<br>
 * <br>
 * This event is only fired on the {@link LogicalSide#SERVER} side.<br>
 * <br>
 * If this event is canceled, the entity will not be teleported.
 * @see EntityTeleportEvent
 */
@Cancelable
public class ValkyrieTeleportEvent extends EntityTeleportEvent {
    public ValkyrieTeleportEvent(Entity entity, double targetX, double targetY, double targetZ) {
        super(entity, targetX, targetY, targetZ);
    }
}
