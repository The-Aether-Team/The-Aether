package com.gildedgames.aether.common.entity.tile;

import com.gildedgames.aether.common.block.dungeon.TreasureChestBlock;
import com.gildedgames.aether.common.item.miscellaneous.DungeonKeyItem;
import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import com.gildedgames.aether.core.registry.AetherDungeonTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;

@OnlyIn(value = Dist.CLIENT, _interface = LidBlockEntity.class)
public class TreasureChestTileEntity extends RandomizableContainerBlockEntity implements LidBlockEntity
{
    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    protected float openness;
    protected float oOpenness;
    protected int openCount;
    private int tickInterval;
    private boolean locked;
    private String kind;

    protected TreasureChestTileEntity(BlockEntityType<?> tileEntityType, BlockPos pos, BlockState state) {
        super(tileEntityType, pos, state);
    }

    public TreasureChestTileEntity( BlockPos pos, BlockState state) {
        this(AetherTileEntityTypes.TREASURE_CHEST.get(), pos, state);
        this.kind = AetherDungeonTypes.BRONZE.getRegistryName();
        this.locked = true;
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    protected Component getDefaultName() {
        return new TranslatableComponent("container.aether." + this.getKind() + "_dungeon_chest");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return ChestMenu.threeRows(id, inventory, this);
    }

//    @Override
//    public void tick() {
//        if (++this.tickInterval % 20 * 4 == 0) {
//            //TODO: Switch to treasure chest block.
//            this.level.blockEvent(this.worldPosition, Blocks.ENDER_CHEST, 1, this.openCount);
//        }
//
//        this.oOpenness = this.openness;
//        int i = this.worldPosition.getX();
//        int j = this.worldPosition.getY();
//        int k = this.worldPosition.getZ();
//        if (this.openCount > 0 && this.openness == 0.0F) {
//            double d0 = (double)i + 0.5D;
//            double d1 = (double)k + 0.5D;
//            this.level.playSound(null, d0, (double)j + 0.5D, d1, SoundEvents.CHEST_OPEN, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
//        }
//
//        if (this.openCount == 0 && this.openness > 0.0F || this.openCount > 0 && this.openness < 1.0F) {
//            float f2 = this.openness;
//            if (this.openCount > 0) {
//                this.openness += 0.1F;
//            } else {
//                this.openness -= 0.1F;
//            }
//
//            if (this.openness > 1.0F) {
//                this.openness = 1.0F;
//            }
//
//            if (this.openness < 0.5F && f2 >= 0.5F) {
//                double d3 = (double)i + 0.5D;
//                double d2 = (double)k + 0.5D;
//                this.level.playSound(null, d3, (double)j + 0.5D, d2, SoundEvents.CHEST_OPEN, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
//            }
//
//            if (this.openness < 0.0F) {
//                this.openness = 0.0F;
//            }
//        }
//    }

    public boolean tryUnlock(Player player) {
        ItemStack stack = player.getMainHandItem();
        boolean keyMatches = stack.getItem() instanceof DungeonKeyItem && this.getKind().equals(((DungeonKeyItem) stack.getItem()).getDungeonType().getRegistryName());
        if (this.getLocked() && keyMatches) {
            this.setLocked(false);
            //this.level.markAndNotifyBlock(this.worldPosition, this.level.getChunkAt(this.worldPosition), this.getBlockState(), this.getBlockState(), BLOCK_UPDATE, 512);
            return true;
        } else {
            player.displayClientMessage(new TranslatableComponent("aether." + this.getKind() + "_dungeon_chest_locked"), true);
            return false;
        }
    }

    @Override
    public boolean triggerEvent(int integer, int openCount) {
        if (integer == 1) {
            this.openCount = openCount;
            return true;
        } else {
            return super.triggerEvent(integer, openCount);
        }
    }

    @Override
    public void startOpen(Player playerEntity) {
        if (!playerEntity.isSpectator()) {
            if (this.openCount < 0) {
                this.openCount = 0;
            }
            ++this.openCount;
            this.signalOpenCount();
        }
    }

    @Override
    public void stopOpen(Player playerEntity) {
        if (!playerEntity.isSpectator()) {
            --this.openCount;
            this.signalOpenCount();
        }
    }

    protected void signalOpenCount() {
        Block block = this.getBlockState().getBlock();
        if (block instanceof TreasureChestBlock) {
            this.level.blockEvent(this.worldPosition, block, 1, this.openCount);
            this.level.updateNeighborsAt(this.worldPosition, block);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getOpenNess(float p_195480_1_) {
        return Mth.lerp(p_195480_1_, this.oOpenness, this.openness);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> p_199721_1_) {
        this.items = p_199721_1_;
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

//    @Override
//    public ClientboundBlockEntityDataPacket getUpdatePacket() {
//        CompoundTag compound = new CompoundTag();
//        this.save(compound);
//        return new ClientboundBlockEntityDataPacket(this.getBlockPos(), null, compound);
//    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag compound = pkt.getTag();
        BlockState state = this.level.getBlockState(this.worldPosition);
        this.handleUpdateTag(compound);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.save(new CompoundTag());
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.locked = !compound.contains("Locked") || compound.getBoolean("Locked");
        this.kind = compound.contains("Kind") ? compound.getString("Kind") : AetherDungeonTypes.BRONZE.getRegistryName();
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(compound)) {
            ContainerHelper.loadAllItems(compound, this.items);
        }
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        compound.putBoolean("Locked", this.getLocked());
        compound.putString("Kind", this.getKind());
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.items);
        }
        return compound;
    }
}
