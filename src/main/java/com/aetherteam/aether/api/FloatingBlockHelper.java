package com.aetherteam.aether.api;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.block.FloatingBlockEntity;
import com.aetherteam.aether.util.FloatingBlockHelperImpls;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.aetherteam.aether.util.FloatingBlockHelperImpls.*;

/**
 * A helper class designed to aid in the creation of floating blocks.
 *
 * @author Jack Papel
 */
public interface FloatingBlockHelper {
    int MAX_MOVABLE_BLOCKS = PistonStructureResolver.MAX_PUSH_DEPTH;

    /**
     * {@link Any}
     */
    Any ANY = Any.getInstance();
    /**
     * {@link Standard}
     */
    Standard STANDARD = Standard.getInstance();
    /**
     * {@link FloatingBlockHelperImpls.Double}
     */
    FloatingBlockHelperImpls.Double DOUBLE = FloatingBlockHelperImpls.Double.getInstance();
    /**
     * {@link Pusher}
     */
    Pusher PUSHER = Pusher.getInstance();

    /**
     * The default conditions under which a floating block goes from floating to falling. By default, this is when a
     * floating block is 50 blocks from the height limit, and isn't a fast floater.
     */
    Function<FloatingBlockEntity, Boolean> DEFAULT_DROP_STATE = (entity) -> {
        Level world = entity.level;
        BlockPos pos = entity.blockPosition();
        int distFromTop = world.getMaxBuildHeight() - pos.getY();
        return !entity.isFastFloater() && distFromTop <= 50;
    };

    /**
     * Try to create a floating block of this type
     *
     * @param pos The position of the (root) block you want to float.
     * @return Whether the block(s) were successfully floated.
     */
    default boolean tryCreate(Level world, BlockPos pos) {
        return tryCreate(world, pos, false);
    }

    /**
     * Try to create a floating block of this type
     *
     * @param pos   The position of the (root) block you want to float.
     * @param force Whether to override the non-floaters blacklist.
     * @return Whether the block(s) were successfully floated.
     */
    boolean tryCreate(Level world, BlockPos pos, boolean force);

    /**
     * @return Whether this type of floating block is suitable for this block.
     */
    boolean isSuitableFor(BlockState state);

    /**
     * @param shouldDrop Whether the given block is floating or dropping.
     * @param pos        The position of the block to test.
     * @return Whether a floating block will be blocked at the given position.
     */
    boolean isBlocked(boolean shouldDrop, Level world, BlockPos pos);

    /**
     * @param pos The position of the block to query.
     * @return Whether the block at the specified location cannot be floated.
     */
    static boolean isBlockBlacklisted(Level world, BlockPos pos, BlockState state) {
        return state.is(AetherTags.Blocks.NON_FLOATERS) || !PistonBaseBlock.isPushable(state, world, pos, Direction.UP, true, Direction.UP);
    }

    /**
     * @param pos The position of the block to query.
     * @return Whether the block at the specified location cannot be floated.
     */
    static boolean isBlockBlacklisted(Level world, BlockPos pos) {
        return isBlockBlacklisted(world, pos, world.getBlockState(pos));
    }

    /**
     * @param pos             The position of the block
     * @param state           The blockstate of the block
     * @param partOfStructure Whether the block is part of a structure (A double floating block or floating block
     *                        pusher)
     * @return Whether a floating block created at the given position will drop.
     */
    static boolean willBlockDrop(Level world, BlockPos pos, BlockState state, boolean partOfStructure) {
        FloatingBlockEntity entity = new FloatingBlockEntity(world, pos, state, partOfStructure);
        boolean willDrop = entity.getDropState().get();
        entity.discard();
        return willDrop;
    }

    /**
     * @return Whether the tool, as given by the item usage context, is adequate to float the block.
     */
    static boolean isToolAdequate(Level world, BlockPos pos, ItemStack heldItem, BlockState state) {
        return world.getBlockEntity(pos) == null && state.getDestroySpeed(world, pos) != -1.0F
                && (!state.requiresCorrectToolForDrops() || heldItem.isCorrectToolForDrops(state))
                && !state.is(AetherTags.Blocks.NON_FLOATERS);
    }

    /**
     * A structure builder intended to aid the creation of floating block structures.
     */
    @SuppressWarnings("unused")
    class SetBuilder extends BlockLikeSet.Builder {
        private final Level world;

        /**
         * Note: This constructor also adds this block to the structure.
         *
         * @param initial The position of the first block in the {@link BlockLikeSet}.
         */
        public SetBuilder(Level world, BlockPos initial) {
            super(initial);
            this.world = world;
            this.add(initial);
        }

        /**
         * @param pos The position of the block that should be added to the structure.
         */
        public SetBuilder add(BlockPos pos) {
            FloatingBlockEntity entity = new FloatingBlockEntity(this.world, pos, world.getBlockState(pos), true);
            this.entries.put(pos.subtract(this.origin), entity);
            return this;
        }

        /**
         * @param pos       The position of the block that should be added to the structure.
         * @param predicate A predicate to test whether the block should be added.
         */
        public SetBuilder addif(BlockPos pos, Predicate<Map<Vec3i, BlockLikeEntity>> predicate) {
            if (predicate.test(Map.copyOf(this.entries))) {
                return this.add(pos);
            }
            return this;
        }
    }
}
