package com.gildedgames.aether.common.item.block;

import com.gildedgames.aether.client.registry.AetherRenderers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;

import java.util.function.Consumer;

public class EntityBlockItem extends BlockItem {
    private final LazyOptional<BlockEntity> blockEntity;
    private static final IItemRenderProperties renderProperties = new IItemRenderProperties() {
        @Override
        public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
            return AetherRenderers.blockEntityWithoutLevelRenderer.get();
        }
    };

    public <B extends Block> EntityBlockItem(B block, NonNullSupplier<BlockEntity> blockEntity, Properties tab) {
        super(block, tab);
        this.blockEntity = LazyOptional.of(blockEntity);
    }

    public LazyOptional<BlockEntity> getBlockEntity() {
        return this.blockEntity;
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(renderProperties);
    }
}
