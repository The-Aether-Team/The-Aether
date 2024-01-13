package com.aetherteam.aether.capability.item;

import com.aetherteam.aether.capability.AetherCapabilities;
import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.Optional;

public interface DroppedItem extends Component {
    ItemEntity getItemEntity();

    static Optional<DroppedItem> get(ItemEntity item) {
        return AetherCapabilities.DROPPED_ITEM_CAPABILITY.maybeGet(item);
    }

    void setOwner(Entity owner);
    Entity getOwner();
}
