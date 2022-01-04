package com.gildedgames.aether.common.item.block;

import com.gildedgames.aether.client.registry.AetherRenderers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class EntityBlockItem extends BlockItem {
    public <B extends Block> EntityBlockItem(B block, Properties tab) {
        super(block, tab);
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
