package com.aetherteam.aether.api;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;
import java.util.function.Predicate;

/**
 * An object designed to hold {@link BlockLikeEntity}s together.
 * <br>These are ticked every post-tick until destroyed. (Similar to {@link PostTickEntity})
 * @author Jack Papel
 */
@SuppressWarnings("unused")
public class BlockLikeSet {
    private static final Set<BlockLikeSet> structures = new HashSet<>();
    private final Map<Vec3i, BlockLikeEntity> entries;

    /**
     * This constructor is useful for doors and other two-block {@link BlockLikeSet}s.
     * @param offset entity2's position - entity1's position
     */
    public BlockLikeSet(BlockLikeEntity entity1, BlockLikeEntity entity2, Vec3i offset) {
        this.entries = Map.of(
                Vec3i.ZERO, entity1,
                offset, entity2
        );
    }

    /**
     * One of the entries in this map must have an offset of {@link Vec3i#ZERO}.
     * <br>"Must" is a strong word. It's recommended. If it isn't the case, the physics won't be consistent, probably.
     */
    public BlockLikeSet(Map<Vec3i, BlockLikeEntity> map) {
        this.entries = map;
    }

    /**
     * @return An unordered iterator of all active {@link BlockLikeSet}s. Active means the set has not landed.
     */
    public static Iterator<BlockLikeSet> getAllSets() {
        return Set.copyOf(structures).iterator();
    }

    /**
     * @return An immutable copy of this {@link BlockLikeSet}'s entries.
     */
    public Map<Vec3i, BlockLikeEntity> getEntries(){
        return Map.copyOf(entries);
    }

    public void spawn(Level world) {
        entries.forEach((offset, block) -> {
            block.markPartOfSet();
            world.removeBlock(block.blockPosition(), false);
            world.addFreshEntity(block);
        });
        init();
    }

    // Aligns all BLEs to the master block
    protected void synchronize() {
        BlockLikeEntity master = getMasterBlock();
        entries.forEach((offset, block) -> block.alignWith(master, offset));
    }

    public void postTick() {
        this.synchronize();

        for (BlockLikeEntity block : entries.values()) {
            if (block.isRemoved()) {
                // If one block ceases, the rest must as well.
                Level world = block.level;
                BlockState state = block.getBlockState();
                boolean success = world.getBlockState(block.blockPosition()).is(state.getBlock());
                this.land(block, success);
                break;
            }
        }
    }

    public void land(BlockLikeEntity lander, boolean success) {
        this.synchronize();

        for (BlockLikeEntity block : entries.values()) {
            if (block != lander) {
                if (success) {
                    block.cease();
                } else {
                    Level world = block.level;
                    BlockState state = block.getBlockState();
                    BlockPos pos = block.blockPosition();

                    // If the block has been set already, remove it. We want this BLE to break.
                    // This is imperfect - if the block lands on a grass plant, say, then
                    // the grass plant is already gone. But this should prevent duplications.
                    if (world.getBlockState(pos).is(state.getBlock())) {
                        world.removeBlock(pos, false);
                    }
                    block.breakApart();
                }
            }
            block.dropItem = false;
        }
        this.entries.clear();
        this.remove();
    }

    public BlockLikeEntity getMasterBlock() {
        if (entries.containsKey(Vec3i.ZERO)) {
            return entries.get(Vec3i.ZERO);
        } else {
            return entries.values().iterator().next();
        }
    }

    private void init() {
        structures.add(this);
    }

    public void remove() {
        structures.remove(this);
    }

    /**
     * A builder intended to aid the creation of {@link BlockLikeSet}s.
     */
    @SuppressWarnings("unused")
    public static class Builder {
        protected final Map<Vec3i, BlockLikeEntity> entries;
        protected final BlockPos origin;

        /**
         * @param origin The position of the first block in the {@link BlockLikeSet}.
         */
        public Builder(BlockPos origin) {
            this.origin = origin;
            this.entries = new HashMap<>(2);
        }

        /**
         * @param entity The BlockLikeEntity to add to the {@link BlockLikeSet}.
         *               If there has already been an entity added at that location,
         *               this entity will be ignored and not added.
         */
        public Builder add(BlockLikeEntity entity){
            BlockPos pos = entity.blockPosition();
            if (!isAlreadyInSet(pos)) {
                this.entries.put(pos.subtract(origin), entity);
            }
            return this;
        }

        /**
         * Allows one to add to a {@link BlockLikeSet} only if a certain condition is met.
         * The predicate acts on an immutable copy of the entries so far.
         * @param entity The entity that should be added to the {@link BlockLikeSet}.
         * @param predicate A {@link Predicate} to test whether the block should be added.
         */
        public Builder addIf(BlockLikeEntity entity, Predicate<Map<Vec3i, BlockLikeEntity>> predicate){
            if (predicate.test(Map.copyOf(entries))){
                return this.add(entity);
            }
            return this;
        }

        /**
         * @return The size of the {@link BlockLikeSet} so far.
         */
        public int size(){
            return entries.size();
        }

        /**
         * @return The {@link BlockLikeSet} that has been built.
         */
        public BlockLikeSet build(){
            return new BlockLikeSet(entries);
        }

        /**
         * @param pos The position of the block to test
         * @return Whether the given block is already in the set.
         */
        public boolean isAlreadyInSet(BlockPos pos) {
            return this.entries.containsKey(pos.subtract(origin));
        }
    }
}
