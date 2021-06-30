package com.gildedgames.aether.core.api.registers;

import net.minecraft.block.Block;

import java.util.function.Supplier;

public class ParachuteType
{
    private final String registryName;
    private final Supplier<Block> aercloudBlock;

    public ParachuteType(String registryName, Supplier<Block> aercloudBlock) {
        this.registryName = registryName;
        this.aercloudBlock = aercloudBlock;
    }

    public String getRegistryName() {
        return this.registryName;
    }

    public Block getAercloudBlock() {
        return this.aercloudBlock.get();
    }
}
