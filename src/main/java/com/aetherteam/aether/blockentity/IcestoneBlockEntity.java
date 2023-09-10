package com.aetherteam.aether.blockentity;

import com.aetherteam.aether.AetherGameEvents;
import com.aetherteam.aether.block.FreezingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class IcestoneBlockEntity extends BlockEntity implements FreezingBlock {
    private final FreezingListener listener;
    private final Map<BlockPos, Integer> lastBrokenPositions = new HashMap<>();

    public IcestoneBlockEntity(BlockPos pos, BlockState state) {
        super(AetherBlockEntityTypes.ICESTONE.get(), pos, state);
        PositionSource positionSource = new BlockPositionSource(this.getBlockPos());
        this.listener = new FreezingListener(positionSource, 4);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, IcestoneBlockEntity blockEntity) {
        if (!blockEntity.lastBrokenPositions.isEmpty()) {
            for (Iterator<Map.Entry<BlockPos, Integer>> it = blockEntity.lastBrokenPositions.entrySet().iterator(); it.hasNext();) {
                Map.Entry<BlockPos, Integer> entry = it.next();
                int nextDelay = entry.setValue(entry.getValue() - 1);
                if (nextDelay == 0) {
                    blockEntity.freezeBlocks(level, blockEntity.getBlockPos(), blockEntity.getBlockState(), FreezingBlock.SQRT_8);
                    it.remove();
                }
            }
        }
    }

    public FreezingListener getListener() {
        return this.listener;
    }

    public Map<BlockPos, Integer> getLastBrokenPositions() {
        return this.lastBrokenPositions;
    }

    /**
     * Handles freezing blocks around Icestone when the {@link FreezingListener} detects certain update events in the radius of the block.
     * This makes it so that Icestone will only check to freeze blocks under certain circumstances, making this behavior more performant.<br><br>
     * The listener also detects and tracks the last positions of broken blocks in the vicinity so that they are temporarily not able to be frozen,
     * as handled by {@link com.aetherteam.aether.event.hooks.RecipeHooks#preventBlockFreezing(LevelAccessor, BlockPos, BlockPos)}.
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
        public boolean handleGameEvent(ServerLevel level, GameEvent.Message eventMessage) {
            GameEvent event = eventMessage.gameEvent();
            GameEvent.Context context = eventMessage.context();
            Vec3 pos = eventMessage.source();
            if (event == AetherGameEvents.ICESTONE_FREEZABLE_UPDATE.get() || event == GameEvent.BLOCK_PLACE || event == GameEvent.FLUID_PLACE || event == GameEvent.ENTITY_PLACE) {
                IcestoneBlockEntity.this.freezeBlocks(level, IcestoneBlockEntity.this.getBlockPos(), IcestoneBlockEntity.this.getBlockState(), FreezingBlock.SQRT_8);
                return true;
            } else if (event == GameEvent.BLOCK_DESTROY) {
                BlockState state = context.affectedState();
                if (state != null && FreezingBlock.cachedResults.contains(state.getBlock())) {
                    BlockPos blockPos = new BlockPos(pos);
                    IcestoneBlockEntity.this.lastBrokenPositions.put(blockPos, (int) (state.getBlock().defaultDestroyTime() * 200));
                    return true;
                }
            }
            return false;
        }
    }
}
