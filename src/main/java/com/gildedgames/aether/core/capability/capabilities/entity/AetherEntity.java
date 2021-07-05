package com.gildedgames.aether.core.capability.capabilities.entity;

import com.gildedgames.aether.core.capability.interfaces.IAetherEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;

public class AetherEntity implements IAetherEntity
{
    private final LivingEntity entity;

    public AetherEntity(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public LivingEntity getEntity() {
        return this.entity;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }

    @Override
    public void sync() {

    }

    @Override
    public void onUpdate() {

    }
}
