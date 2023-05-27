package com.aetherteam.aether.event.events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class EggLayEvent extends EntityEvent {
    private Item item;

    public EggLayEvent(Entity entity, Item item) {
        super(entity);
        this.item = item;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
