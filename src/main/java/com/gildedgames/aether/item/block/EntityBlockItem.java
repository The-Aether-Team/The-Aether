package com.gildedgames.aether.item.block;

import com.gildedgames.aether.client.renderer.AetherRenderers;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;

import java.util.function.Consumer;

public class EntityBlockItem extends BlockItem {
    private final LazyOptional<BlockEntity> blockEntity;

    public <B extends Block> EntityBlockItem(B block, NonNullSupplier<BlockEntity> blockEntity, Properties properties) {
        super(block, properties);
        this.blockEntity = LazyOptional.of(blockEntity);
    }

    public LazyOptional<BlockEntity> getBlockEntity() {
        return this.blockEntity;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(AetherRenderers.entityBlockItemRenderProperties);
    }
}
