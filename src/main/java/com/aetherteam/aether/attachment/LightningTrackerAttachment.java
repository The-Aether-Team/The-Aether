package com.aetherteam.aether.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * Capability class used to track {@link LightningBolt}s created by entities and weapons.
 *
 * @see com.aetherteam.aether.event.hooks.AbilityHooks.WeaponHooks#lightningTracking(Entity, LightningBolt)
 */
public class LightningTrackerAttachment {

    private int ownerID;
    @Nullable
    private Entity owner;

    public static final Codec<LightningTrackerAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("owner_id").forGetter(o -> o.owner.getId())
    ).apply(instance, LightningTrackerAttachment::new));

    protected LightningTrackerAttachment() {

    }

    private LightningTrackerAttachment(int ownerID) {
        this.ownerID = ownerID;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    /**
     * @return The owner {@link Entity} of the lightning.
     */
    @Nullable
    public Entity getOwner(Level level) {
        if (this.owner == null) {
            this.owner = level.getEntity(this.ownerID);
        }
        return this.owner;
    }
}
