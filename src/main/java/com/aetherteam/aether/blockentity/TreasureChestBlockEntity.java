package com.aetherteam.aether.blockentity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.miscellaneous.DungeonKeyItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

/**
 * [CODE COPY] - {@link ChestBlockEntity}.<br><br>
 * Has additional locking behavior.
 */
public class TreasureChestBlockEntity extends RandomizableContainerBlockEntity implements LidBlockEntity, WorldlyContainer {
    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        protected void onOpen(Level level, BlockPos pos, BlockState state) {
            TreasureChestBlockEntity.playSound(level, pos, state, SoundEvents.CHEST_OPEN);
        }

        protected void onClose(Level level, BlockPos pos, BlockState state) {
            TreasureChestBlockEntity.playSound(level, pos, state, SoundEvents.CHEST_CLOSE);
        }

        protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int count, int openCount) {
            TreasureChestBlockEntity.this.signalOpenCount(level, pos, state, count, openCount);
        }

        protected boolean isOwnContainer(Player player) {
            if (player.containerMenu instanceof ChestMenu chestMenu) {
                Container container = chestMenu.getContainer();
                return container == TreasureChestBlockEntity.this || container instanceof CompoundContainer compoundContainer && compoundContainer.contains(TreasureChestBlockEntity.this);
            } else {
                return false;
            }
        }
    };
    private final ChestLidController chestLidController = new ChestLidController();
    private boolean locked;
    private ResourceLocation kind;
    @Nullable
    private LazyOptional<IItemHandlerModifiable> chestHandler;

    public TreasureChestBlockEntity() {
        this(AetherBlockEntityTypes.TREASURE_CHEST.get(), BlockPos.ZERO, AetherBlocks.TREASURE_CHEST.get().defaultBlockState());
    }

    public TreasureChestBlockEntity(BlockPos pos, BlockState state) {
        this(AetherBlockEntityTypes.TREASURE_CHEST.get(), pos, state);
    }

    protected TreasureChestBlockEntity(BlockEntityType<?> tileEntityType, BlockPos pos, BlockState state) {
        super(tileEntityType, pos, state);
        this.kind = new ResourceLocation(Aether.MODID, "bronze");
        this.locked = true;
    }

    /**
     * Attempts to unlock the Treasure Chest with a Dungeon Key.
     * @param player The {@link Player} attempting to unlock the Treasure Chest.
     * @return Whether the Treasure Chest was unlocked.
     */
    public boolean tryUnlock(Player player, ItemStack stack) {
        boolean keyMatches = stack.getItem() instanceof DungeonKeyItem dungeonKeyItem && this.getKind().equals(dungeonKeyItem.getDungeonType());
        boolean hasNoKeys = (!(player.getMainHandItem().getItem() instanceof DungeonKeyItem mainHandItem) || !this.getKind().equals(mainHandItem.getDungeonType()))
                && (!(player.getOffhandItem().getItem() instanceof DungeonKeyItem offhandItem) || !this.getKind().equals(offhandItem.getDungeonType()));
        if (this.getLocked() && keyMatches && this.level != null) {
            this.setLocked(false);
            this.setChanged();
            this.level.markAndNotifyBlock(this.worldPosition, this.level.getChunkAt(this.worldPosition), this.getBlockState(), this.getBlockState(), 2, 512);
            return true;
        } else if (hasNoKeys) {
            player.displayClientMessage(Component.translatable(this.getKind().getNamespace() + "." + this.getKind().getPath() + "_treasure_chest_locked"), true);
        }
        return false;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
        if (!this.remove && capability == ForgeCapabilities.ITEM_HANDLER) {
            if (this.chestHandler == null) {
                this.chestHandler = LazyOptional.of(this::createHandler);
            }
            return this.chestHandler.cast();
        }
        return super.getCapability(capability, side);
    }

    private IItemHandlerModifiable createHandler() {
        BlockState blockState = this.getBlockState();
        if (!(blockState.getBlock() instanceof ChestBlock)) {
            return new InvWrapper(this) {
                @Override
                public ItemStack extractItem(int slot, int amount, boolean simulate) {
                    if (TreasureChestBlockEntity.this.getLocked()) {
                        return ItemStack.EMPTY;
                    }
                    return super.extractItem(slot, amount, simulate);
                }
            };
        }
        Container inv = ChestBlock.getContainer((ChestBlock) blockState.getBlock(), blockState, this.getLevel(), getBlockPos(), true);
        return new InvWrapper(inv == null ? this : inv);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (this.chestHandler != null) {
            this.chestHandler.invalidate();
            this.chestHandler = null;
        }
    }

    /**
     * Warning for "deprecation" is suppressed because the method is fine to override.
     */
    @SuppressWarnings("deprecation")
    @Override
    public void setBlockState(BlockState state) {
        super.setBlockState(state);
        if (this.chestHandler != null) {
            LazyOptional<?> oldHandler = this.chestHandler;
            this.chestHandler = null;
            oldHandler.invalidate();
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return ChestMenu.threeRows(id, inventory, this);
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, Direction direction) {
        if (direction != Direction.DOWN && stack.getItem().canFitInsideContainerItems()) {
            return this.canPlaceItem(index, stack);
        }
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        if (direction == Direction.DOWN) {
            return !this.getLocked();
        }
        return false;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        if (this.getLocked()) {
            return false;
        }
        return super.canPlaceItem(index, stack);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> stacks) {
        this.items = stacks;
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("menu." + this.getKind().getNamespace() + "." + this.getKind().getPath() + "_treasure_chest");
    }

    public static void setDungeonType(BlockGetter level, BlockPos pos, ResourceLocation dungeonType) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof TreasureChestBlockEntity treasure) {
            treasure.setKind(dungeonType);
        }
    }

    public void setKind(ResourceLocation kind) {
        this.kind = kind;
    }

    public ResourceLocation getKind() {
        return this.kind;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean getLocked() {
        return this.locked;
    }

    @Override
    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    protected void signalOpenCount(Level level, BlockPos pos, BlockState state, int count, int openCount) {
        level.blockEvent(pos, state.getBlock(), 1, openCount);
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            this.chestLidController.shouldBeOpen(type > 0);
            return true;
        } else {
            return super.triggerEvent(id, type);
        }
    }

    public static void lidAnimateTick(Level level, BlockPos pos, BlockState state, TreasureChestBlockEntity blockEntity) {
        blockEntity.chestLidController.tickLid();
    }

    @Override
    public float getOpenNess(float partialTicks) {
        return this.chestLidController.getOpenness(partialTicks);
    }

    private static void playSound(Level level, BlockPos pos, BlockState state, SoundEvent sound) {
        double d0 = pos.getX() + 0.5;
        double d1 = pos.getY() + 0.5;
        double d2 = pos.getZ() + 0.5;
        level.playSound(null, d0, d1, d2, sound, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("Locked", this.getLocked());
        tag.putString("Kind", this.getKind().toString());
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.items);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.locked = !tag.contains("Locked") || tag.getBoolean("Locked");
        this.kind = tag.contains("Kind") ? new ResourceLocation(tag.getString("Kind")) : new ResourceLocation(Aether.MODID, "bronze");
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, this.items);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet) {
        CompoundTag compound = packet.getTag();
        if (compound != null) {
            this.handleUpdateTag(compound);
        }
    }
}
