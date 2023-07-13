package com.aetherteam.aether.capability;

import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface INBTSynchable<T extends Tag> extends INBTSerializable<T> {
    Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions();
    void setSynched(Direction side, String key, Object value);
    void forceSync(Direction side);

    enum Direction {
        CLIENT,
        SERVER
    }

    enum Type {
        INT,
        FLOAT,
        DOUBLE,
        BOOLEAN,
        UUID
    }
}
