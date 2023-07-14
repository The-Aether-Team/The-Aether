package com.aetherteam.aether.entity.monster;

import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.aetherteam.aether.loot.AetherLoot;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EvilWhirlwind extends AbstractWhirlwind {
    public EvilWhirlwind(EntityType<? extends EvilWhirlwind> type, Level level) {
        super(type, level);
    }

    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor level, @Nonnull DifficultyInstance difficulty, @Nonnull MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        this.lifeLeft = this.random.nextInt(512) + 512;
        this.lifeLeft /= 2;
        this.isEvil = true;
        return super.finalizeSpawn(level, difficulty, reason, spawnData, tag);
    }

    @Override
    public void updateParticles() {
        for (int i = 0; i < 3; i++) {
            double d2 = getX() + this.random.nextDouble() * 0.25;
            double d5 = getY() + getBbHeight() + 0.125;
            double d8 = getZ() + this.random.nextDouble() * 0.25;
            float f1 = this.random.nextFloat() * 360;
            this.level.addParticle(AetherParticleTypes.EVIL_WHIRLWIND.get(), d2, d5 - 0.25, d8, -Math.sin(0.01745329F * f1) * 0.75, 0.125, Math.cos(0.01745329F * f1) * 0.75);
        }
    }

    @Override
    public ResourceLocation getLootLocation() {
        return AetherLoot.EVIL_WHIRLWIND_JUNK;
    }

    @Override
    public int getDefaultColor() {
        return 0;
    }
}
