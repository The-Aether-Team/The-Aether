package com.aetherteam.aether.capability.accessory;

import com.aetherteam.aether.capability.AetherCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.common.util.LazyOptional;

import java.util.Map;

public interface MobAccessory extends INBTSerializable<CompoundTag> {
    Mob getMob();

    static LazyOptional<MobAccessory> get(Mob mob) {
        return mob.getCapability(AetherCapabilities.MOB_ACCESSORY_CAPABILITY);
    }

    void setGuaranteedDrop(String identifier);

    float getEquipmentDropChance(String identifier);

    void setDropChance(String identifier, float chance);

    Map<String, Float> getAccessoryDropChances();
}
