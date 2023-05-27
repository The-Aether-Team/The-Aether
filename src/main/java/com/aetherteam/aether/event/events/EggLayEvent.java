package com.aetherteam.aether.event.events;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.Cancelable;

import javax.annotation.Nullable;

@Cancelable
public class EggLayEvent extends EntityEvent {
    private Item item;
    private SoundEvent sound;
    private float volume;
    private float pitch;

    public EggLayEvent(Entity entity, SoundEvent sound, float volume, float pitch, Item item) {
        super(entity);
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.item = item;
    }

    @Nullable
    public Item getItem() {
        return this.item;
    }

    public void setItem(@Nullable Item item) {
        this.item = item;
    }

    @Nullable
    public SoundEvent getSound() {
        return this.sound;
    }

    public void setSound(@Nullable SoundEvent sound) {
        this.sound = sound;
    }

    public float getVolume() {
        return this.volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
