package com.aetherteam.aether.entity;

import com.aetherteam.aether.capability.lightning.LightningTracker;
import com.aetherteam.aether.mixin.mixins.common.accessor.EntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public final class EntityUtil {
    /**
     * Copies rotation values from one entity to another.
     * @param entity The {@link Entity} to copy rotations to.
     * @param source The {@link Entity} to copy rotations from.
     */
    public static void copyRotations(Entity entity, Entity source) {
        entity.setYRot(source.getYRot() % 360.0F);
        entity.setXRot(source.getXRot() % 360.0F);
        entity.setYBodyRot(source.getYRot());
        entity.setYHeadRot(source.getYRot());
    }

    /**
     * Spawns explosion particles used for various entity movement effects.
     * @param entity The {@link Entity} to spawn the particles for.
     */
    public static void spawnMovementExplosionParticles(Entity entity) {
        RandomSource random = ((EntityAccessor) entity).aether$getRandom();
        double d0 = random.nextGaussian() * 0.02;
        double d1 = random.nextGaussian() * 0.02;
        double d2 = random.nextGaussian() * 0.02;
        double d3 = 10.0;
        double x = entity.getX() + ((double) random.nextFloat() * entity.getBbWidth() * 2.0) - entity.getBbWidth() - d0 * d3;
        double y = entity.getY() + ((double) random.nextFloat() * entity.getBbHeight()) - d1 * d3;
        double z = entity.getZ() + ((double) random.nextFloat() * entity.getBbWidth() * 2.0) - entity.getBbWidth() - d2 * d3;
        entity.getLevel().addParticle(ParticleTypes.POOF, x, y, z, d0, d1, d2);
    }

    /**
     * Spawns explosion particles used for various entity summoning effects.
     * @param entity The {@link Entity} to spawn the particles for.
     */
    public static void spawnSummoningExplosionParticles(Entity entity) {
        RandomSource random = ((EntityAccessor) entity).aether$getRandom();
        for (int i = 0; i < 20; ++i) {
            double d0 = random.nextGaussian() * 0.02;
            double d1 = random.nextGaussian() * 0.02;
            double d2 = random.nextGaussian() * 0.02;
            double d3 = 10.0;
            double x = entity.getX(0.0) - d0 * d3;
            double y = entity.getRandomY() - d1 * d3;
            double z = entity.getRandomZ(1.0) - d2 * d3;
            entity.getLevel().addParticle(ParticleTypes.POOF, x, y, z, d0, d1, d2);
        }
    }

    /**
     * Spawns particles for block removal interactions.
     * @param level The {@link Level} to spawn the particles in.
     * @param pos The {@link BlockPos} to spawn the particles at.
     */
    public static void spawnRemovalParticles(Level level, BlockPos pos) {
        double a = pos.getX() + 0.5 + (double) (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.375;
        double b = pos.getY() + 0.5 + (double) (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.375;
        double c = pos.getZ() + 0.5 + (double) (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.375;
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.POOF, a, b, c, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    /**
     * Summons a {@link LightningBolt}.
     * @param projectile The {@link Projectile} that is summoning lightning.
     */
    public static void summonLightningFromProjectile(Projectile projectile) {
        LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(projectile.getLevel());
        if (lightningBolt != null) {
            LightningTracker.get(lightningBolt).ifPresent(lightningTracker -> lightningTracker.setOwner(projectile.getOwner()));
            lightningBolt.setPos(projectile.getX(), projectile.getY(), projectile.getZ());
            projectile.getLevel().addFreshEntity(lightningBolt);
        }
    }
}
