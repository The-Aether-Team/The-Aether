package com.aetherteam.aether.capability;

import dev.onyxstudios.cca.api.v3.component.Component;
import io.github.fabricators_of_create.porting_lib.core.util.INBTSerializable;
import net.minecraft.nbt.CompoundTag;

public interface NbtComponent extends INBTSerializable<CompoundTag>, Component {
    String DATA = "DATA";
    @Override
    default void readFromNbt(CompoundTag tag) {
        deserializeNBT(tag.getCompound(DATA));
    }

    @Override
    default void writeToNbt(CompoundTag tag) {
        tag.put(DATA, serializeNBT());
    }
}
