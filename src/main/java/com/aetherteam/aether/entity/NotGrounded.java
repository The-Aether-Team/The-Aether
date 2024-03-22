package com.aetherteam.aether.entity;

import net.minecraft.world.entity.Entity;

/**
 * Interface for more controlled handling of checking whether an entity is on the ground.
 * This gets around issues with {@link Entity#onGround()} sometimes not being true when it should be on the client, causing animation bugs.
 */
public interface NotGrounded {
    boolean isEntityOnGround();

    void setEntityOnGround(boolean onGround);
}