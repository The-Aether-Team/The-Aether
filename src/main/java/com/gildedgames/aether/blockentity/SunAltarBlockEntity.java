package com.gildedgames.aether.blockentity;

import com.gildedgames.aether.Aether;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SunAltarBlockEntity extends BlockEntity implements Nameable {
    private Component name;

    public SunAltarBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AetherBlockEntityTypes.SUN_ALTAR.get(), pWorldPosition, pBlockState);
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
    protected void saveAdditional(@Nonnull CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (this.hasCustomName()) {
            pTag.putString("CustomName", Component.Serializer.toJson(this.name));
        }
    }

    @Override
    public void load(@Nonnull CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("CustomName", 8)) {
            this.name = Component.Serializer.fromJson(pTag.getString("CustomName"));
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        CompoundTag compound = packet.getTag();
        this.handleUpdateTag(compound);
    }

    @Nonnull
    public Component getName() {
        return this.name != null ? this.name : Component.translatable("menu." + Aether.MODID + ".sun_altar");
    }

    public void setCustomName(@Nullable Component pName) {
        this.name = pName;
    }

    @Nullable
    @Override
    public Component getCustomName() {
        return this.name;
    }
}
