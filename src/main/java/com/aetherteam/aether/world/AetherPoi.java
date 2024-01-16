package com.aetherteam.aether.world;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.google.common.collect.ImmutableSet;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public class AetherPoi {
    public static final LazyRegistrar<PoiType> POI = LazyRegistrar.create(Registries.POINT_OF_INTEREST_TYPE, Aether.MODID);

    public static final RegistryObject<PoiType> AETHER_PORTAL = POI.register("aether_portal", () -> new PoiType(getBlockStates(AetherBlocks.AETHER_PORTAL.get()), 0, 1));

    private static Set<BlockState> getBlockStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }
}
