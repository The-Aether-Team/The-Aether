package com.gildedgames.aether.blockentity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.block.FreezingBlock;
import com.gildedgames.aether.event.AetherGameEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.*;

import javax.annotation.Nonnull;

public class IcestoneBlockEntity extends BlockEntity implements FreezingBlock {
    private final FreezingListener listener;

    public IcestoneBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AetherBlockEntityTypes.ICESTONE.get(), pWorldPosition, pBlockState);
        PositionSource positionsource = new BlockPositionSource(this.worldPosition);
        this.listener = new FreezingListener(positionsource, 3);
    }

    public FreezingListener getListener() {
        return this.listener;
    }

    class FreezingListener implements GameEventListener {
        private final PositionSource listenerSource;
        private final int listenerRadius;

        public FreezingListener(PositionSource source, int radius) {
            this.listenerSource = source;
            this.listenerRadius = radius;
        }

        @Nonnull
        @Override
        public PositionSource getListenerSource() {
            return this.listenerSource;
        }

        @Override
        public int getListenerRadius() {
            return this.listenerRadius;
        }

        @Override
        public boolean handleGameEvent(@Nonnull ServerLevel level, @Nonnull GameEvent.Message event) {
            if (event.gameEvent() == AetherGameEvents.ICESTONE_FREEZABLE_UPDATE.get()) {
                Aether.LOGGER.info(event);
                IcestoneBlockEntity.this.freezeBlocks(level, IcestoneBlockEntity.this.getBlockPos(), IcestoneBlockEntity.this.getBlockState(), FreezingBlock.SQRT_8); //can probably simply freeze the source pos of the game event instead of loop, as long as its within the radius.
                return true;
            } else {
                return false;
            }
        }
    }
}
