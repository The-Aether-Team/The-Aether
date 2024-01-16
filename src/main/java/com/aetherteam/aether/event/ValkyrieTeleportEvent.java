package com.aetherteam.aether.event;

import io.github.fabricators_of_create.porting_lib.entity.events.EntityEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
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
public class ValkyrieTeleportEvent extends EntityEvents.Teleport.EntityTeleportEvent {
    public static final Event<ValkyrieTeleport> EVENT = EventFactory.createArrayBacked(ValkyrieTeleport.class, callbacks -> event -> {
        for (ValkyrieTeleport callback : callbacks)
            callback.onValkyrieTeleport(event);
    });
    public ValkyrieTeleportEvent(Entity entity, double targetX, double targetY, double targetZ) {
        super(entity, targetX, targetY, targetZ);
    }

    @Override
    public void sendEvent() {
        EVENT.invoker().onValkyrieTeleport(this);
    }

    @FunctionalInterface
    public interface ValkyrieTeleport {
        void onValkyrieTeleport(ValkyrieTeleportEvent event);
    }
}
