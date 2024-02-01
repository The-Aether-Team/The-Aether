package com.aetherteam.aether.item.miscellaneous;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ForgeSpawnEggItem;

import java.util.Objects;
import java.util.function.Supplier;

public class SliderSpawnEggItem extends ForgeSpawnEggItem {
    public SliderSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.item.SpawnEggItem#useOn(UseOnContext)}.<br><br>
     * Changed to round the X/Z values based on the click location.
     * This makes it so the slider will always spawn in the center of a 2x2 region based on what corner of a block the player is aiming.<br><br>
     * The position still needs to be aligned with Math.floor once the entity is spawned, so the Slider rounds it's X and Z coordinates down after spawning.
     * @param context The {@link UseOnContext} of the usage interaction.
     */
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemStack = context.getItemInHand();
            BlockPos blockPos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockState = level.getBlockState(blockPos);
            if (blockState.is(Blocks.SPAWNER)) {
                BlockEntity blockEntity = level.getBlockEntity(blockPos);
                if (blockEntity instanceof SpawnerBlockEntity spawnerBlockEntity) {
                    EntityType<?> entityType = this.getType(itemStack.getTag());
                    spawnerBlockEntity.setEntityId(entityType, level.getRandom());
                    blockEntity.setChanged();
                    level.sendBlockUpdated(blockPos, blockState, blockState, 3);
                    level.gameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
                    itemStack.shrink(1);
                    return InteractionResult.CONSUME;
                }
            }

            BlockPos relativePos;
            if (blockState.getCollisionShape(level, blockPos).isEmpty()) {
                relativePos = blockPos;
            } else {
                relativePos = blockPos.relative(direction);
            }
            Vec3 clickLoc = context.getClickLocation();
            BlockPos roundedPos = new BlockPos((int) Math.round(clickLoc.x()), relativePos.getY(), (int) Math.round(clickLoc.z()));

            EntityType<?> entityType = this.getType(itemStack.getTag());
            if (entityType.spawn((ServerLevel)level, itemStack, context.getPlayer(), roundedPos, MobSpawnType.SPAWN_EGG, false, !Objects.equals(blockPos, relativePos) && direction == Direction.UP) != null) {
                itemStack.shrink(1);
                level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
            }

            return InteractionResult.CONSUME;
        }
    }
}
