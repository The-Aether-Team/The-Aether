package com.gildedgames.aether.core.capability;

import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public interface INBTSynchable<T extends Tag> extends INBTSerializable<T> {
    T serializeSynchableNBT();
    void deserializeSynchableNBT(T nbt);
    void updateSynchableNBTFromServer();
    void updateSynchableNBTFromClient();
}
