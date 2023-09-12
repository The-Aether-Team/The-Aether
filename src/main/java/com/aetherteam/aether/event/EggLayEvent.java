package com.aetherteam.aether.event;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.fml.LogicalSide;

import javax.annotation.Nullable;

/**
 * EggLayEvent is fired before a Moa lays an egg.
 * <br>
 * This event is {@link Cancelable}.<br>
 * If the event is not canceled, the Moa will lay an egg.
 * <br>
 * This event does not have a result. {@link net.minecraftforge.eventbus.api.Event.HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.<br>
 * <br>
 * This event is only fired on the {@link LogicalSide#SERVER} side.<br>
 * <br>
 * If this event is canceled, the Moa will not lay an egg.
 */
@Cancelable
public class EggLayEvent extends EntityEvent {
    @Nullable
    private ItemStack stack;
    @Nullable
    private SoundEvent sound;
    private float volume;
    private float pitch;

    /**
     * @param entity The {@link Entity} laying the egg.
     * @param sound The original {@link SoundEvent} played by laying the egg.
     * @param volume The original volume of the sound as a {@link Float}.
     * @param pitch The original pitch of the sound as a {@link Float}.
     * @param stack The original egg {@link ItemStack} to be laid.
     */
    public EggLayEvent(Entity entity, @Nullable SoundEvent sound, float volume, float pitch, @Nullable ItemStack stack) {
        super(entity);
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.stack = stack;
    }

    /**
     * This method is {@link Nullable}. If null, no egg item will be laid.
     * @return The egg {@link ItemStack} to be laid.
     */
    @Nullable
    public ItemStack getStack() {
        return this.stack;
    }

    /**
     * Sets a new egg item stack to be laid.
     * @param item The egg {@link ItemStack}.
     */
    public void setStack(@Nullable ItemStack stack) {
        this.stack = stack;
    }

    /**
     * This method is {@link Nullable}. If null, no sound will play.
     * @return The {@link SoundEvent} to play when the egg is laid.
     */
    @Nullable
    public SoundEvent getSound() {
        return this.sound;
    }

    /**
     * Sets a new laying sound to play.
     * @param sound The laying {@link SoundEvent}.
     */
    public void setSound(@Nullable SoundEvent sound) {
        this.sound = sound;
    }

    /**
     * @return The volume of the laying sound as a {@link Float}
     */
    public float getVolume() {
        return this.volume;
    }

    /**
     * Sets a new volume for the laying sound.
     * @param volume The volume as a {@link Float}.
     */
    public void setVolume(float volume) {
        this.volume = volume;
    }

    /**
     * @return The pitch of the laying sound as a {@link Float}
     */
    public float getPitch() {
        return this.pitch;
    }

    /**
     * Sets a new pitch for the laying sound.
     * @param pitch The pitch as a {@link Float}.
     */
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
