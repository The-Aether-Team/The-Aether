package com.aetherteam.aether.mixin.mixins.client.aether_fabric;

import com.aetherteam.aether.blockentity.TreasureChestBlockEntity;
import com.aetherteam.aether.client.renderer.blockentity.TreasureChestRenderer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChestRenderer.class)
public abstract class ChestRendererMixin {
    @WrapOperation(
        method = "render(Lnet/minecraft/world/level/block/entity/BlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Sheets;chooseMaterial(Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/level/block/state/properties/ChestType;Z)Lnet/minecraft/client/resources/model/Material;")
    )
    private Material aetherFabric$adjustMaterial(BlockEntity blockEntity, ChestType chestType, boolean holiday, Operation<Material> original) {
        if (((ChestRenderer<?>)(Object) this) instanceof TreasureChestRenderer treasureChestRenderer) {
            return treasureChestRenderer.getMaterial((TreasureChestBlockEntity) blockEntity, chestType);
        }

        return original.call(blockEntity, chestType, holiday);
    }
}
