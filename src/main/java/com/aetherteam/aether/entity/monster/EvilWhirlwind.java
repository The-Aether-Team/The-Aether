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

import javax.annotation.Nullable;

public class EvilWhirlwind extends AbstractWhirlwind {
    public EvilWhirlwind(EntityType<? extends EvilWhirlwind> type, Level level) {
        super(type, level);
        this.setEvil(true);
    }

    /**
     * Sets the Whirlwind's lifespan.<br><br>
     * Warning for "deprecation" is suppressed because this is fine to override.
     *
     * @param level      The {@link ServerLevelAccessor} where the entity is spawned.
     * @param difficulty The {@link DifficultyInstance} of the game.
     * @param reason     The {@link MobSpawnType} reason.
     * @param spawnData  The {@link SpawnGroupData}.
     * @param tag        The {@link CompoundTag} to apply to this entity.
     * @return The {@link SpawnGroupData} to return.
     */
    @Override
    @SuppressWarnings("deprecation")
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        this.setLifeLeft((this.getRandom().nextInt(512) + 512) / 2);
        return spawnData;
    }

    @Override
    public void spawnParticles() {
        for (int i = 0; i < 3; i++) {
            double d2 = getX() + this.getRandom().nextDouble() * 0.25;
            double d5 = getY() + getBbHeight() + 0.125;
            double d8 = getZ() + this.getRandom().nextDouble() * 0.25;
            float f1 = this.getRandom().nextFloat() * 360;
            this.level().addParticle(AetherParticleTypes.EVIL_WHIRLWIND.get(), d2, d5 - 0.25, d8, -Math.sin(0.0175F * f1) * 0.75, 0.125, Math.cos(0.0175F * f1) * 0.75);
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
