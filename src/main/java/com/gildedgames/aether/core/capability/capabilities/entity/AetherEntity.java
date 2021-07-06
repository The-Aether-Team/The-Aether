package com.gildedgames.aether.core.capability.capabilities.entity;

import com.gildedgames.aether.core.capability.interfaces.IAetherEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;

public class AetherEntity implements IAetherEntity
{
    private final LivingEntity entity;

    private int lightningImmunityTimer;

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
        nbt.putInt("LightningImmunityTimer", this.getLightingImmunityTimer());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt.contains("LightningImmunityTimer")) {
            this.setLightningImmunityTimer(nbt.getInt("LightningImmunityTimer"));
        }
    }

    @Override
    public void sync() {

    }

    @Override
    public void onUpdate() {
        tickDownLightningImmunity();
    }

    private void tickDownLightningImmunity() {
        if (!this.getEntity().level.isClientSide) {
            if (this.lightningImmunityTimer > 0) {
                this.lightningImmunityTimer--;
            } else {
                this.lightningImmunityTimer = 0;
            }
        }
    }

    @Override
    public void setLightningImmunityTimer(int timer) {
        this.lightningImmunityTimer = timer;
    }

    @Override
    public int getLightingImmunityTimer() {
        return this.lightningImmunityTimer;
    }
}
