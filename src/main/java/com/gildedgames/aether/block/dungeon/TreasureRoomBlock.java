package com.gildedgames.aether.block.dungeon;

import com.gildedgames.aether.client.particle.AetherParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * A block that can switch between being air and a solid block. This is useful for creating doors in dungeons, as bosses
 * can detect and switch these blocks. Only creative players can interact with these blocks when they're invisible.
 */
public class TreasureRoomBlock extends Block {
    public static final BooleanProperty INVISIBLE = BooleanProperty.create("invisible");
    public static final VoxelShape SHAPE = Block.box(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D);

    public TreasureRoomBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(INVISIBLE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(INVISIBLE);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return state.getValue(INVISIBLE) ? RenderShape.INVISIBLE : super.getRenderShape(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(INVISIBLE)) {
            if (context instanceof EntityCollisionContext entity && entity.getEntity() instanceof Player player && player.isCreative()) {
                return SHAPE;
            }
            return Shapes.empty();
        }
        return super.getShape(state, level, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(INVISIBLE) ? Shapes.empty() : super.getCollisionShape(state, level, pos, context);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.isCreative()) {
            BlockState blockState = state.cycle(INVISIBLE);
            level.setBlock(pos, blockState, 3);
            return InteractionResult.SUCCESS;
        } else {
            return super.use(state, level, pos, player, hand, hit);
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        boolean flag = super.canBeReplaced(state, context);
        if (!flag) {
            Level level = context.getLevel();
            BlockPos pos = context.getClickedPos();
            for (int i = 0; i < 2; i++) {
                double a = pos.getX() + 0.5D + (double) (level.random.nextFloat() - level.random.nextFloat()) * 0.375D;
                double b = pos.getY() + 0.5D + (double) (level.random.nextFloat() - level.random.nextFloat()) * 0.375D;
                double c = pos.getZ() + 0.5D + (double) (level.random.nextFloat() - level.random.nextFloat()) * 0.375D;
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.POOF, a, b, c, 1, 0.0, 0.0, 0.0, 0.0);
                }
            }
        }
        return flag;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.gameMode != null && minecraft.gameMode.getPlayerMode() == GameType.CREATIVE && minecraft.player != null && minecraft.level != null) {
            ItemStack itemstack = minecraft.player.getMainHandItem();
            Item item = itemstack.getItem();
            if (item instanceof BlockItem blockItem) {
                if (blockItem.getBlock() == this && state.getValue(INVISIBLE)) {
                    minecraft.level.addParticle(AetherParticleTypes.TREASURE_DOORWAY_BLOCK.get(), (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
