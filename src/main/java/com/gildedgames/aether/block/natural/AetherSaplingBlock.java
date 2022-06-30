package com.gildedgames.aether.block.natural;

import com.gildedgames.aether.world.generation.tree.LevelTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class AetherSaplingBlock extends SaplingBlock {
    private final LevelTreeGrower treeGrower;

    public AetherSaplingBlock(LevelTreeGrower treeGrower, BlockBehaviour.Properties properties) {
        super(treeGrower, properties);
        this.treeGrower = treeGrower;
    }

    @Override
    public void advanceTree(@Nonnull ServerLevel serverLevel, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull RandomSource randomSource) {
        if (state.getValue(STAGE) == 0) {
            serverLevel.setBlock(pos, state.cycle(STAGE), 4);
        } else {
            if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(serverLevel, randomSource, pos)) return;
            this.treeGrower.growTree(serverLevel, serverLevel.getChunkSource().getGenerator(), pos, state, randomSource);
        }
    }
}
