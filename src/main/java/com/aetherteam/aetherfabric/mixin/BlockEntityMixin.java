package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.pond.BlockEntityExtension;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin implements BlockEntityExtension {
}
