package com.gildedgames.aether.item.miscellaneous;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Spawns the slider at the XZ coordinates rounded from the click position.
 * The position still needs to be aligned with Mth.floor once the entity is spawned.
 */
public class SliderSpawnEggItem extends ForgeSpawnEggItem {
    public SliderSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = pContext.getItemInHand();
            Vec3 location = pContext.getClickLocation();
            BlockPos blockpos = pContext.getClickedPos();
            BlockPos blockpos1 = new BlockPos(Math.round(location.x),pContext.getClickedPos().getY(),Math.round(location.z));
            Direction direction = pContext.getClickedFace();
            BlockState blockstate = level.getBlockState(blockpos);
            if (blockstate.is(Blocks.SPAWNER)) {
                BlockEntity blockentity = level.getBlockEntity(blockpos1);
                if (blockentity instanceof SpawnerBlockEntity) {
                    BaseSpawner basespawner = ((SpawnerBlockEntity)blockentity).getSpawner();
                    EntityType<?> entitytype1 = this.getType(itemstack.getTag());
                    basespawner.setEntityId(entitytype1);
                    blockentity.setChanged();
                    level.sendBlockUpdated(blockpos1, blockstate, blockstate, 3);
                    level.gameEvent(pContext.getPlayer(), GameEvent.BLOCK_CHANGE, blockpos1);
                    itemstack.shrink(1);
                    return InteractionResult.CONSUME;
                }
            }

            BlockPos blockpos2;
            BlockPos blockpos3;
/*            if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
                blockpos2 = blockpos1;
                blockpos3 = blockpos;
            } else {
                blockpos2 = blockpos1.relative(direction);
                blockpos3 = blockpos.relative(direction);
            }*/
            blockpos2 = blockpos1.relative(direction);
            blockpos3 = blockpos.relative(direction);
            EntityType<?> entitytype = this.getType(itemstack.getTag());
            Entity entity = entitytype.spawn((ServerLevel)level, itemstack, pContext.getPlayer(), blockpos2, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos3) && direction == Direction.UP);
            if (entity != null) {
                itemstack.shrink(1);
                level.gameEvent(pContext.getPlayer(), GameEvent.ENTITY_PLACE, blockpos1);
            }
            return InteractionResult.CONSUME;
        }
    }
}
