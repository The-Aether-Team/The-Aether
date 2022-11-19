package com.gildedgames.aether.blockentity;

import com.gildedgames.aether.block.FreezingBlock;
import com.gildedgames.aether.event.AetherGameEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.*;

public class IcestoneBlockEntity extends BlockEntity implements FreezingBlock {
    private final FreezingListener listener;

    public IcestoneBlockEntity(BlockPos pos, BlockState state) {
        super(AetherBlockEntityTypes.ICESTONE.get(), pos, state);
        PositionSource positionSource = new BlockPositionSource(this.worldPosition);
        this.listener = new FreezingListener(positionSource, 4);
    }

    public FreezingListener getListener() {
        return this.listener;
    }

    /**
     * Handles freezing blocks around Icestone when the {@link FreezingListener} detects certain update events in the radius of the block.
     * This makes it so that Icestone will only check to freeze blocks under certain circumstances, making this behavior more performant.
     */
    class FreezingListener implements GameEventListener {
        private final PositionSource listenerSource;
        private final int listenerRadius;

        public FreezingListener(PositionSource source, int radius) {
            this.listenerSource = source;
            this.listenerRadius = radius;
        }

        @Override
        public PositionSource getListenerSource() {
            return this.listenerSource;
        }

        @Override
        public int getListenerRadius() {
            return this.listenerRadius;
        }

        @Override
        public boolean handleGameEvent(ServerLevel level, GameEvent.Message event) {
            if (event.gameEvent() == AetherGameEvents.ICESTONE_FREEZABLE_UPDATE.get() || event.gameEvent() == GameEvent.BLOCK_PLACE || event.gameEvent() == GameEvent.FLUID_PLACE || event.gameEvent() == GameEvent.ENTITY_PLACE) {
                IcestoneBlockEntity.this.freezeBlocks(level, IcestoneBlockEntity.this.getBlockPos(), IcestoneBlockEntity.this.getBlockState(), FreezingBlock.SQRT_8);
                return true;
            } else {
                return false;
            }
        }
    }
}
