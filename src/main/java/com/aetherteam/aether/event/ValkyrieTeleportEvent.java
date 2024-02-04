package com.aetherteam.aether.event;

import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.fml.LogicalSide;

/**
 * ValkyrieTeleportEvent is fired before a Valkyrie teleports.
 * <br>
 * This event is {@link ICancellableEvent}.<br>
 * If the event is not canceled, the entity will be teleported.
 * <br>
 * This event is fired on the {@link net.neoforged.neoforge.common.NeoForge#EVENT_BUS}.<br>
 * <br>
 * This event is only fired on the {@link LogicalSide#SERVER} side.<br>
 * <br>
 * If this event is canceled, the entity will not be teleported.
 * @see EntityTeleportEvent
 */
public class ValkyrieTeleportEvent extends EntityTeleportEvent implements ICancellableEvent {
    public ValkyrieTeleportEvent(Entity entity, double targetX, double targetY, double targetZ) {
        super(entity, targetX, targetY, targetZ);
    }
}
