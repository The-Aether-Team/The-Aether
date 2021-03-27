package com.gildedgames.aether.common.entity.tile;

import com.gildedgames.aether.common.block.dungeon.TreasureChestBlock;
import com.gildedgames.aether.common.item.misc.DungeonKeyItem;
import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import com.gildedgames.aether.core.api.registers.DungeonType;
import com.gildedgames.aether.core.registry.AetherDungeonTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class TreasureChestTileEntity extends LockableLootTileEntity implements IChestLid, ITickableTileEntity
{
    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    protected float openness;
    protected float oOpenness;
    protected int openCount;
    private int tickInterval;
    private boolean locked;
    private String kind;

    protected TreasureChestTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    public TreasureChestTileEntity() {
        this(AetherTileEntityTypes.TREASURE_CHEST.get());
        this.kind = AetherDungeonTypes.BRONZE.get().getRegistryName().toString();
        this.locked = true;
    }

    public TreasureChestTileEntity(Supplier<DungeonType> type) {
        this();
        this.kind = type.get().getRegistryName().toString();
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + this.getKind().replace(":", ".") + "_dungeon_chest");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory inventory) {
        return ChestContainer.threeRows(id, inventory, this);
    }

    @Override
    public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        this.locked = !compound.contains("Locked") || compound.getBoolean("Locked");
        this.kind = compound.contains("Kind") ? compound.getString("Kind") : AetherDungeonTypes.BRONZE.get().getRegistryName().toString();
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(compound)) {
            ItemStackHelper.loadAllItems(compound, this.items);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        compound.putBoolean("Locked", this.getLocked());
        compound.putString("Kind", this.getKind());
        if (!this.trySaveLootTable(compound)) {
            ItemStackHelper.saveAllItems(compound, this.items);
        }

        return compound;
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT compound = new CompoundNBT();
        this.save(compound);
        return new SUpdateTileEntityPacket(this.getBlockPos(), 191, compound);
    }

    @Override
    public void tick() {
        if (++this.tickInterval % 20 * 4 == 0) {
            this.level.blockEvent(this.worldPosition, Blocks.ENDER_CHEST, 1, this.openCount);
        }

        this.oOpenness = this.openness;
        int i = this.worldPosition.getX();
        int j = this.worldPosition.getY();
        int k = this.worldPosition.getZ();
        if (this.openCount > 0 && this.openness == 0.0F) {
            double d0 = (double)i + 0.5D;
            double d1 = (double)k + 0.5D;
            this.level.playSound(null, d0, (double)j + 0.5D, d1, SoundEvents.CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
        }

        if (this.openCount == 0 && this.openness > 0.0F || this.openCount > 0 && this.openness < 1.0F) {
            float f2 = this.openness;
            if (this.openCount > 0) {
                this.openness += 0.1F;
            } else {
                this.openness -= 0.1F;
            }

            if (this.openness > 1.0F) {
                this.openness = 1.0F;
            }

            if (this.openness < 0.5F && f2 >= 0.5F) {
                double d3 = (double)i + 0.5D;
                double d2 = (double)k + 0.5D;
                this.level.playSound(null, d3, (double)j + 0.5D, d2, SoundEvents.CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
            }

            if (this.openness < 0.0F) {
                this.openness = 0.0F;
            }
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
    public void setRemoved() {
        this.clearCache();
        super.setRemoved();
    }

    @Override
    public void startOpen(PlayerEntity playerEntity) {
        if (!playerEntity.isSpectator()) {
            if (this.openCount < 0) {
                this.openCount = 0;
            }

            ++this.openCount;
            this.signalOpenCount();
        }
    }

    @Override
    public void stopOpen(PlayerEntity playerEntity) {
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
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> p_199721_1_) {
        this.items = p_199721_1_;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getOpenNess(float p_195480_1_) {
        return MathHelper.lerp(p_195480_1_, this.oOpenness, this.openness);
    }

    @Override
    public boolean canOpen(PlayerEntity player) {
        ItemStack stack = player.getMainHandItem();
        boolean canOpen = super.canOpen(player) && (stack.getItem() instanceof DungeonKeyItem && this.getKind().equals(((DungeonKeyItem) stack.getItem()).getDungeonType().getRegistryName().toString())) || !this.getLocked();
        if (this.getLocked() && stack.getItem() instanceof DungeonKeyItem) stack.shrink(1);
        this.setLocked(!canOpen);
        return canOpen;
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
}
