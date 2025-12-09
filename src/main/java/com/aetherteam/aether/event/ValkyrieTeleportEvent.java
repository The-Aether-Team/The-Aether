package com.aetherteam.aether.event;

import com.aetherteam.nitrogen.fabric.events.CancellableCallbackImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;

/**
 * ValkyrieTeleportEvent is fired before a Valkyrie teleports.
 * <br>
 * This event is {@link CancellableCallbackImpl}.<br>
 * If the event is not canceled, the entity will be teleported.
 * <br>
 * This event is only fired on the {@link EnvType#SERVER} side.<br>
 * <br>
 * If this event is canceled, the entity will not be teleported.
 */
public class ValkyrieTeleportEvent extends CancellableCallbackImpl {

    private final Entity entity;
    private final double targetX;
    private final double targetY;
    private final double targetZ;

    public ValkyrieTeleportEvent(Entity entity, double targetX, double targetY, double targetZ) {
        this.entity = entity;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
    }

    public Entity getEntity() {
        return entity;
    }

    public double getTargetX() {
        return targetX;
    }

    public double getTargetY() {
        return targetY;
    }

    public double getTargetZ() {
        return targetZ;
    }

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, invokers -> event -> {
        for (var invoker : invokers) invoker.teleport(event);
    });

    public interface Callback {
        void teleport(ValkyrieTeleportEvent event);
    }
}
