package com.aetherteam.aether.world;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.function.Supplier;

public class AetherPoi {
    public static final DeferredRegister<PoiType> POI = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, Aether.MODID);

    public static final Supplier<PoiType> AETHER_PORTAL = POI.register("aether_portal", () -> new PoiType(getBlockStates(AetherBlocks.AETHER_PORTAL.get()), 0, 1));

    private static Set<BlockState> getBlockStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }
}
