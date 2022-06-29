package com.gildedgames.aether.item.miscellaneous;

import com.gildedgames.aether.entity.passive.Moa;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.api.registers.MoaType;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class MoaEggItem extends Item
{
    private static final Map<MoaType, MoaEggItem> BY_ID = Maps.newIdentityHashMap();
    private final Supplier<MoaType> moaType;
    private final int color;

    public MoaEggItem(Supplier<MoaType> moaType, int shellColor, Properties properties) {
        super(properties);
        this.moaType = moaType;
        this.color = shellColor;
        BY_ID.put(moaType.get(), this);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack itemStack = context.getItemInHand();
        Player player = context.getPlayer();
        if (player != null && player.isCreative()) {
            Level level = context.getLevel();
            if (!(level instanceof ServerLevel serverLevel)) {
                return InteractionResult.SUCCESS;
            } else {
                BlockPos blockPos = context.getClickedPos();
                Direction direction = context.getClickedFace();
                BlockState blockState = level.getBlockState(blockPos);
                if (blockState.is(Blocks.SPAWNER)) {
                    BlockEntity blockentity = level.getBlockEntity(blockPos);
                    if (blockentity instanceof SpawnerBlockEntity spawnerBlockEntity) {
                        BaseSpawner basespawner = spawnerBlockEntity.getSpawner();
                        EntityType<Moa> entityType1 = AetherEntityTypes.MOA.get();
                        basespawner.setEntityId(entityType1);
                        basespawner.nextSpawnData.getEntityToSpawn().putString("MoaType", this.getMoaType().get().getRegistryName());
                        basespawner.nextSpawnData.getEntityToSpawn().putBoolean("PlayerGrown", true);
                        blockentity.setChanged();
                        level.sendBlockUpdated(blockPos, blockState, blockState, 3);
                        itemStack.shrink(1);
                        return InteractionResult.CONSUME;
                    }
                }

                BlockPos blockPos1;
                if (blockState.getCollisionShape(level, blockPos).isEmpty()) {
                    blockPos1 = blockPos;
                } else {
                    blockPos1 = blockPos.relative(direction);
                }

                ItemStack spawnStack = this.getStackWithTags(itemStack, false, this.getMoaType().get(), false, true);
                Entity entity = AetherEntityTypes.MOA.get().spawn(serverLevel, spawnStack, player, blockPos1, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockPos, blockPos1) && direction == Direction.UP);
                if (entity instanceof Moa) {
                    level.gameEvent(player, GameEvent.ENTITY_PLACE, blockPos);
                }
                return InteractionResult.CONSUME;
            }
        } else {
            return InteractionResult.FAIL;
        }
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (player.isCreative()) {
            BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
            if (hitResult.getType() != HitResult.Type.BLOCK) {
                return InteractionResultHolder.pass(itemstack);
            } else if (!(level instanceof ServerLevel serverLevel)) {
                return InteractionResultHolder.success(itemstack);
            } else {
                BlockPos blockpos = hitResult.getBlockPos();
                if (!(level.getBlockState(blockpos).getBlock() instanceof LiquidBlock)) {
                    return InteractionResultHolder.pass(itemstack);
                } else if (level.mayInteract(player, blockpos) && player.mayUseItemAt(blockpos, hitResult.getDirection(), itemstack)) {
                    ItemStack spawnStack = this.getStackWithTags(itemstack, false, this.getMoaType().get(), false, true);
                    Entity entity = AetherEntityTypes.MOA.get().spawn(serverLevel, spawnStack, player, blockpos, MobSpawnType.SPAWN_EGG, false, false);
                    if (entity == null) {
                        return InteractionResultHolder.pass(itemstack);
                    } else {
                        player.awardStat(Stats.ITEM_USED.get(this));
                        level.gameEvent(player, GameEvent.ENTITY_PLACE, blockpos);
                        return InteractionResultHolder.consume(itemstack);
                    }
                } else {
                    return InteractionResultHolder.fail(itemstack);
                }
            }
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    public ItemStack getStackWithTags(ItemStack stack, boolean isBaby, MoaType moaType, boolean isHungry, boolean isPlayerGrown) {
        ItemStack itemStack = stack.copy();
        CompoundTag tag = itemStack.getOrCreateTag();
        tag.putBoolean("IsBaby", isBaby);
        tag.putString("MoaType", moaType.getRegistryName());
        tag.putBoolean("Hungry", isHungry);
        tag.putBoolean("PlayerGrown", isPlayerGrown);
        return itemStack;
    }

    @OnlyIn(Dist.CLIENT)
    public int getColor() {
        return this.color;
    }

    public Supplier<MoaType> getMoaType() {
        return this.moaType;
    }

    @Nullable
    public static MoaEggItem byId(@Nullable MoaType moaType) {
        return BY_ID.get(moaType);
    }

    public static Iterable<MoaEggItem> moaEggs() {
        return Iterables.unmodifiableIterable(BY_ID.values());
    }
}
