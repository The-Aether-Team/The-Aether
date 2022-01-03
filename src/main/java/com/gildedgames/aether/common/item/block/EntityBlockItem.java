package com.gildedgames.aether.common.item.block;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class EntityBlockItem extends BlockItem {
    private Supplier<BlockEntityWithoutLevelRenderer> renderer;

    public <B extends Block> EntityBlockItem(B block, Properties tab) {
        super(block, tab);
    }

    public EntityBlockItem setBEWLR(Supplier<BlockEntityWithoutLevelRenderer> renderer) {
        this.renderer = renderer;
        return this;
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer.get();
            }
        });
    }
}
