package com.aetherteam.aether.mixin.mixins.common.aether_fabric;

import com.aetherteam.aether.item.AetherItems;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ChestBoat.class)
public abstract class ChestBoatMixin {
    @WrapMethod(method = "getDropItem")
    private Item aetherFabric$adjustBoatItemDrop(Operation<Item> original) {
        return (((Boat)(Object) this).getVariant().getName().equals("aether:skyroot"))
            ? AetherItems.SKYROOT_CHEST_BOAT.get()
            : original.call();
    }
}
