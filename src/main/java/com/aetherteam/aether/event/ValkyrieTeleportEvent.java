package com.aetherteam.aether.event;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.fml.LogicalSide;

/**
 * ValkyrieTeleportEvent is fired before a Valkyrie teleports.
 * <br>
 * This event is {@link Cancelable}.<br>
 * If the event is not canceled, the entity will be teleported.
 * <br>
 * This event does not have a result. {@link net.minecraftforge.eventbus.api.Event.HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.<br>
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
