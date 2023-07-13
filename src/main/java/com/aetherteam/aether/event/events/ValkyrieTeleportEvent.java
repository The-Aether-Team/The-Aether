package com.aetherteam.aether.event.events;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Fired before a valkyrie teleports.
 */
@Cancelable
public class ValkyrieTeleportEvent extends EntityTeleportEvent {
    public ValkyrieTeleportEvent(Entity entity, double targetX, double targetY, double targetZ) {
        super(entity, targetX, targetY, targetZ);
    }
}
