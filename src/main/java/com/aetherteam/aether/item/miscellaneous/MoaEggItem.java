package com.aetherteam.aether.item.miscellaneous;

import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.aether.mixin.mixins.common.accessor.BaseSpawnerAccessor;
import com.google.common.collect.Iterables;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
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

import org.jetbrains.annotations.Nullable;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class MoaEggItem extends Item {
    private static final Map<Supplier<? extends MoaType>, MoaEggItem> BY_ID = new IdentityHashMap<>();
    private final Supplier<? extends MoaType> moaType;
    private final ResourceLocation moaTypeId;
    private final int color;

    public MoaEggItem(RegistryObject<? extends MoaType> moaType, int shellColor, Properties properties) {
        this(moaType, moaType.getId(), shellColor, properties);
    }

    public MoaEggItem(Supplier<? extends MoaType> moaType, ResourceLocation moaTypeId, int shellColor, Properties properties) {
        super(properties);
        this.moaType = moaType;
        this.moaTypeId = moaTypeId;
        this.color = shellColor;
        BY_ID.put(moaType, this);
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.item.SpawnEggItem#useOn(UseOnContext)}<br><br>
     * Modified for Moa spawning specifically and ensuring all the Moa's NBT tags are set.
     * @param context The {@link UseOnContext} of the usage interaction.
     */
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
                    BlockEntity blockEntity = level.getBlockEntity(blockPos);
                    if (blockEntity instanceof SpawnerBlockEntity spawnerBlockEntity) {
                        BaseSpawnerAccessor baseSpawnerAccessor = (BaseSpawnerAccessor) spawnerBlockEntity.getSpawner();
                        EntityType<Moa> entityType = AetherEntityTypes.MOA.get();
                        spawnerBlockEntity.setEntityId(entityType, level.getRandom());
                        baseSpawnerAccessor.aether$getNextSpawnData().getEntityToSpawn().putString("MoaType", this.getMoaTypeId().toString());
                        baseSpawnerAccessor.aether$getNextSpawnData().getEntityToSpawn().putBoolean("PlayerGrown", true); // Moas spawned from a Mob Spawner as set by a Moa Egg will always be tamed.
                        blockEntity.setChanged();
                        level.sendBlockUpdated(blockPos, blockState, blockState, 3);
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

                ItemStack spawnStack = this.getStackWithTags(itemStack, false, this.getMoaType(), false, true); // Setup tags for spawning entity.
                Entity entity = AetherEntityTypes.MOA.get().spawn(serverLevel, spawnStack, player, relativePos, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockPos, relativePos) && direction == Direction.UP);
                if (entity != null) {
                    level.gameEvent(player, GameEvent.ENTITY_PLACE, blockPos);
                }
                return InteractionResult.CONSUME;
            }
        } else {
            return InteractionResult.FAIL;
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.item.SpawnEggItem#use(Level, Player, InteractionHand)}<br><br>
     * Modified for Moa spawning specifically and ensuring all the Moa's NBT tags are set.
     * @param level The {@link Level} of the user.
     * @param player The {@link Player} using this item.
     * @param hand The {@link InteractionHand} in which the item is being used.
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (player.isCreative()) {
            BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
            if (hitResult.getType() != HitResult.Type.BLOCK) {
                return InteractionResultHolder.pass(heldStack);
            } else if (!(level instanceof ServerLevel serverLevel)) {
                return InteractionResultHolder.fail(heldStack);
            } else {
                BlockPos blockpos = hitResult.getBlockPos();
                if (!(level.getBlockState(blockpos).getBlock() instanceof LiquidBlock)) {
                    return InteractionResultHolder.pass(heldStack);
                } else if (level.mayInteract(player, blockpos) && player.mayUseItemAt(blockpos, hitResult.getDirection(), heldStack)) {
                    ItemStack spawnStack = this.getStackWithTags(heldStack, false, this.getMoaType(), false, true);
                    Entity entity = AetherEntityTypes.MOA.get().spawn(serverLevel, spawnStack, player, blockpos, MobSpawnType.SPAWN_EGG, false, false);
                    if (entity == null) {
                        return InteractionResultHolder.pass(heldStack);
                    } else {
                        player.awardStat(Stats.ITEM_USED.get(this));
                        level.gameEvent(player, GameEvent.ENTITY_PLACE, blockpos);
                        return InteractionResultHolder.consume(heldStack);
                    }
                } else {
                    return InteractionResultHolder.fail(heldStack);
                }
            }
        } else {
            return InteractionResultHolder.fail(heldStack);
        }
    }

    /**
     * Applies NBT tags to the Moa Egg item to used for spawning a Moa from it.
     * @param stack The {@link ItemStack} to apply the tags to.
     * @param isBaby {@link Boolean} for whether the Moa should spawn as a baby or not.
     * @param moaType {@link MoaType} of the Moa.
     * @param isHungry {@link Boolean} for if the Moa should spawn hungry or not.
     * @param isPlayerGrown @{link Boolean} for if the Moa was spawned as grown by a player.
     * @return The {@link ItemStack} with the applied tags.
     */
    public ItemStack getStackWithTags(ItemStack stack, boolean isBaby, MoaType moaType, boolean isHungry, boolean isPlayerGrown) {
        ItemStack itemStack = stack.copy();
        CompoundTag tag = itemStack.getOrCreateTag();
        tag.putBoolean("IsBaby", isBaby);
        tag.putString("MoaType", moaType.toString());
        tag.putBoolean("Hungry", isHungry);
        tag.putBoolean("PlayerGrown", isPlayerGrown);
        return itemStack;
    }

    @Environment(EnvType.CLIENT)
    public int getColor() {
        return this.color;
    }

    public MoaType getMoaType() {
        return this.moaType.get();
    }

    public ResourceLocation getMoaTypeId() {
        return this.moaTypeId;
    }

    /**
     * Gets a {@link MoaEggItem} from a {@link MoaType} by comparing the ID of the given type to the IDs of MoaTypes in the {@link MoaEggItem#BY_ID} list.
     * @param moaType The {@link MoaType} to get an egg for.
     * @return The {@link MoaEggItem} from the type.
     */
    @Nullable
    public static MoaEggItem byId(MoaType moaType) {
        if (moaType != null) {
            for (Supplier<? extends MoaType> holder : BY_ID.keySet()) {
                if (moaType.getId().equals(holder.get().getId())) {
                    return BY_ID.get(holder);
                }
            }
        }
        return null;
    }

    /**
     * @return An {@link Iterable} list of all {@link MoaEggItem}s.
     */
    public static Iterable<MoaEggItem> moaEggs() {
        return Iterables.unmodifiableIterable(BY_ID.values());
    }
}
