package com.aetherteam.aether.mixin.mixins.client.aether_fabric;

import com.aetherteam.aether.client.renderer.entity.SkyrootBoatRenderer;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BoatRenderer.class)
public abstract class BoatRendererMixin {
    // When finding aethers boat type, will attempt to adjust the model and location to the aether specific data
    @WrapMethod(method = "method_32163")
    private Pair aetherFabric$adjustDataGet(boolean chest, EntityRendererProvider.Context context, Boat.Type type, Operation<Pair> original) {
        return (type.getName().equals("aether:skyroot"))
            ? SkyrootBoatRenderer.getModelWithLocation(context, chest)
            : original.call(chest, context, type);
    }
}
