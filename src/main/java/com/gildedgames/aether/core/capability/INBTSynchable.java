package com.gildedgames.aether.core.capability;

import com.gildedgames.aether.core.network.AetherPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public interface INBTSynchable<T extends Tag> extends INBTSerializable<T> {
    T serializeSynchableNBT();
    void deserializeSynchableNBT(T nbt);
    void updateSynchableNBT();
}
