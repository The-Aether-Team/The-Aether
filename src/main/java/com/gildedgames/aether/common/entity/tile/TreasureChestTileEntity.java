package com.gildedgames.aether.common.entity.tile;

import com.gildedgames.aether.common.block.util.LockCodeItem;
import com.gildedgames.aether.common.item.misc.DungeonKeyItem;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import com.gildedgames.aether.core.api.registers.DungeonType;
import com.gildedgames.aether.core.registry.AetherDungeonTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.Validate;

import java.util.UUID;

public class TreasureChestTileEntity extends ChestTileEntity {
    private DungeonType kind;

    protected TreasureChestTileEntity(TileEntityType<?> typeIn) {
        super(typeIn);
    }

    public TreasureChestTileEntity() {
        this(AetherTileEntityTypes.TREASURE_CHEST.get());
        this.lockKey = LockCodeItem.UNBOUND_LOCK;
    }

    public TreasureChestTileEntity(DungeonType type) {
        this();
        this.kind = type;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("gui.aether.dungeon_chest", new TranslationTextComponent(this.getKind().getTranslationKey()), new TranslationTextComponent(AetherBlocks.TREASURE_CHEST.get().getDescriptionId()));
    }

    public String createKey(DungeonType type) {
        // Only pair with keys that are matching
        if (this.getKind().equals(type) && this.lockKey.equals(LockCodeItem.UNBOUND_LOCK)) {
            String key = UUID.randomUUID().toString();

            this.lockKey = new LockCodeItem(key);

            return key;
        }

        return null;
    }

    @Override
    public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        this.kind = AetherDungeonTypes.BRONZE.get();

        if (compound.contains("Lock")) {
            this.lockKey = new LockCodeItem(compound.getString("Lock"));
        }

//		if (compound.contains("Kind", 8)) {
//			String kind = compound.getString("Kind");
//			if (!kind.isEmpty()) {
//				this.kind = AetherAPI.getDungeonType(new ResourceLocation(kind));
//				if (this.kind == null) {
//					this.kind = AetherDungeonTypes.BRONZE.get();
//				}
//			}
//		}
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);

        this.lockKey.addToTag(compound);
        compound.putString("Kind", this.getKind().getRegistryName().toString());

        return compound;
    }

    public void setKind(DungeonType kind) {
        this.kind = Validate.notNull(kind);
    }

    public DungeonType getKind() {
        DungeonType kind = this.kind;
        return (kind == null) ? AetherDungeonTypes.BRONZE.get() : kind;
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT compound = new CompoundNBT();
        this.save(compound);
        return new SUpdateTileEntityPacket(this.getBlockPos(), 191, compound);
    }

    @Override
    public boolean canOpen(PlayerEntity player) {
        Item item = player.getMainHandItem().getItem();

        return super.canOpen(player) && item instanceof DungeonKeyItem && this.getKind().equals(((DungeonKeyItem) item).getDungeonType());
    }
}
