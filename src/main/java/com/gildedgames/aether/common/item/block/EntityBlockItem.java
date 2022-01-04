package com.gildedgames.aether.common.item.block;

import com.gildedgames.aether.client.registry.AetherRenderers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class EntityBlockItem extends BlockItem {
    private Supplier<BlockEntity> blockEntity;

    public <B extends Block> EntityBlockItem(B block, Properties tab) {
        super(block, tab);
    }

    public EntityBlockItem setBlockEntity(Supplier<BlockEntity> blockEntity) {
        this.blockEntity = blockEntity;
        return this;
    }

    public BlockEntity getBlockEntity() {
        return this.blockEntity.get();
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return AetherRenderers.blockEntityWithoutLevelRenderer;
            }
        });
    }
}
