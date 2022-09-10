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
    /**
     * Identical to {@link net.minecraft.world.item.SpawnEggItem#useOn(UseOnContext)}, except it rounds the X/Z values based on the exact click location.
     * Entity placement adds 0.5 to the X and Z values after this, so the slider rounds it's X and Z coordinates down after spawning.
     */
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = pContext.getItemInHand();
            BlockPos blockpos = pContext.getClickedPos();
            Direction direction = pContext.getClickedFace();
            BlockState blockstate = level.getBlockState(blockpos);
            if (blockstate.is(Blocks.SPAWNER)) {
                BlockEntity blockentity = level.getBlockEntity(blockpos);
                if (blockentity instanceof SpawnerBlockEntity) {
                    BaseSpawner basespawner = ((SpawnerBlockEntity)blockentity).getSpawner();
                    EntityType<?> entitytype1 = this.getType(itemstack.getTag());
                    basespawner.setEntityId(entitytype1);
                    blockentity.setChanged();
                    level.sendBlockUpdated(blockpos, blockstate, blockstate, 3);
                    level.gameEvent(pContext.getPlayer(), GameEvent.BLOCK_CHANGE, blockpos);
                    itemstack.shrink(1);
                    return InteractionResult.CONSUME;
                }
            }

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(direction);
            }
            Vec3 clickLoc = pContext.getClickLocation();
            BlockPos roundedpos = new BlockPos(Math.round(clickLoc.x()), blockpos1.getY(), Math.round(clickLoc.z()));

            EntityType<?> entitytype = this.getType(itemstack.getTag());
            if (entitytype.spawn((ServerLevel)level, itemstack, pContext.getPlayer(), roundedpos, MobSpawnType.SPAWN_EGG, false, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP) != null) {
                itemstack.shrink(1);
                level.gameEvent(pContext.getPlayer(), GameEvent.ENTITY_PLACE, blockpos);
            }

            return InteractionResult.CONSUME;
        }
    }
}
