package com.gildedgames.aether.common.block.entity;

import com.gildedgames.aether.common.item.miscellaneous.DungeonKeyItem;
import com.gildedgames.aether.common.registry.AetherBlockEntityTypes;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.core.registry.AetherDungeonTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;

public class TreasureChestBlockEntity extends RandomizableContainerBlockEntity implements LidBlockEntity
{
    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        protected void onOpen(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState state) {
            TreasureChestBlockEntity.playSound(level, pos, state, SoundEvents.CHEST_OPEN);
        }

        protected void onClose(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState state) {
            TreasureChestBlockEntity.playSound(level, pos, state, SoundEvents.CHEST_CLOSE);
        }

        protected void openerCountChanged(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState state, int p_155364_, int p_155365_) {
            TreasureChestBlockEntity.this.signalOpenCount(level, pos, state, p_155364_, p_155365_);
        }

        protected boolean isOwnContainer(Player player) {
            if (!(player.containerMenu instanceof ChestMenu chestMenu)) {
                return false;
            } else {
                Container container = chestMenu.getContainer();
                return container == TreasureChestBlockEntity.this || container instanceof CompoundContainer && ((CompoundContainer)container).contains(TreasureChestBlockEntity.this);
            }
        }
    };
    private final ChestLidController chestLidController = new ChestLidController();
    private boolean locked;
    private String kind;

    protected TreasureChestBlockEntity(BlockEntityType<?> tileEntityType, BlockPos pos, BlockState state) {
        super(tileEntityType, pos, state);
    }

    public TreasureChestBlockEntity(BlockPos pos, BlockState state) {
        this(AetherBlockEntityTypes.TREASURE_CHEST.get(), pos, state);
        this.kind = AetherDungeonTypes.BRONZE.getRegistryName();
        this.locked = true;
    }

    public TreasureChestBlockEntity() {
        super(AetherBlockEntityTypes.TREASURE_CHEST.get(), BlockPos.ZERO, AetherBlocks.TREASURE_CHEST.get().defaultBlockState());
    }

    @Nonnull
    @Override
    protected AbstractContainerMenu createMenu(int id, @Nonnull Inventory inventory) {
        return ChestMenu.threeRows(id, inventory, this);
    }

    @Nonnull
    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.aether." + this.getKind() + "_dungeon_chest");
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }

    @Override
    public void load(@Nonnull CompoundTag tag) {
        super.load(tag);
        this.locked = !tag.contains("Locked") || tag.getBoolean("Locked");
        this.kind = tag.contains("Kind") ? tag.getString("Kind") : AetherDungeonTypes.BRONZE.getRegistryName();
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, this.items);
        }
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("Locked", this.getLocked());
        tag.putString("Kind", this.getKind());
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.items);
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        CompoundTag compound = packet.getTag();
        this.handleUpdateTag(compound);
    }

    public static void lidAnimateTick(Level level, BlockPos pos, BlockState state, TreasureChestBlockEntity blockEntity) {
        blockEntity.chestLidController.tickLid();
    }

    static void playSound(Level level, BlockPos pos, BlockState state, SoundEvent sound) {
        double d0 = pos.getX() + 0.5;
        double d1 = pos.getY() + 0.5;
        double d2 = pos.getZ() + 0.5;
        level.playSound(null, d0, d1, d2, sound, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
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

    @Override
    public void startOpen(@Nonnull Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void stopOpen(@Nonnull Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Nonnull
    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(@Nonnull NonNullList<ItemStack> stacks) {
        this.items = stacks;
    }

    @Override
    public float getOpenNess(float pPartialTicks) {
        return this.chestLidController.getOpenness(pPartialTicks);
    }

    public boolean tryUnlock(Player player) {
        ItemStack stack = player.getMainHandItem();
        boolean keyMatches = stack.getItem() instanceof DungeonKeyItem dungeonKeyItem && this.getKind().equals(dungeonKeyItem.getDungeonType().getRegistryName());
        if (this.getLocked() && keyMatches && this.getLevel() != null) {
            this.setLocked(false);
            this.getLevel().markAndNotifyBlock(this.worldPosition, this.getLevel().getChunkAt(this.worldPosition), this.getBlockState(), this.getBlockState(), 2, 512);
            return true;
        } else {
            player.displayClientMessage(Component.translatable("aether." + this.getKind() + "_dungeon_chest_locked"), true);
            return false;
        }
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean getLocked() {
        return this.locked;
    }

    public String getKind() {
        return this.kind;
    }

    private LazyOptional<IItemHandlerModifiable> chestHandler;

    @Override
    public void setBlockState(@Nonnull BlockState state) {
        super.setBlockState(state);
        if (this.chestHandler != null) {
            net.minecraftforge.common.util.LazyOptional<?> oldHandler = this.chestHandler;
            this.chestHandler = null;
            oldHandler.invalidate();
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction side) {
        if (!this.remove && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (this.chestHandler == null)
                this.chestHandler = LazyOptional.of(this::createHandler);
            return this.chestHandler.cast();
        }
        return super.getCapability(capability, side);
    }

    @Nonnull
    private IItemHandlerModifiable createHandler() {
        BlockState blockState = this.getBlockState();
        if (!(blockState.getBlock() instanceof ChestBlock)) {
            return new InvWrapper(this);
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

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    protected void signalOpenCount(Level level, BlockPos pos, BlockState state, int p_155336_, int p_155337_) {
        level.blockEvent(pos, state.getBlock(), 1, p_155337_);
    }
}
