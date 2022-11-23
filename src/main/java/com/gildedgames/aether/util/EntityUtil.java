package com.gildedgames.aether.util;

import com.gildedgames.aether.mixin.mixins.common.accessor.EntityAccessor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public class EntityUtil {
    public static void copyRotations(Entity entity, Entity source) {
        entity.setYRot(source.getYRot() % 360.0F);
        entity.setXRot(source.getXRot() % 360.0F);
        entity.setYBodyRot(source.getYRot());
        entity.setYHeadRot(source.getYRot());
    }

    public static void spawnMovementExplosionParticles(Entity entity) {
        RandomSource random = ((EntityAccessor) entity).getRandom();
        double d0 = random.nextGaussian() * 0.02;
        double d1 = random.nextGaussian() * 0.02;
        double d2 = random.nextGaussian() * 0.02;
        double d3 = 10.0;
        double x = entity.getX() + ((double) random.nextFloat() * entity.getBbWidth() * 2.0) - entity.getBbWidth() - d0 * d3;
        double y = entity.getY() + ((double) random.nextFloat() * entity.getBbHeight()) - d1 * d3;
        double z = entity.getZ() + ((double) random.nextFloat() * entity.getBbWidth() * 2.0) - entity.getBbWidth() - d2 * d3;
        entity.level.addParticle(ParticleTypes.POOF, x, y, z, d0, d1, d2);
    }

    public static void spawnSummoningExplosionParticles(Entity entity) {
        RandomSource random = ((EntityAccessor) entity).getRandom();
        for (int i = 0; i < 20; ++i) {
            double d0 = random.nextGaussian() * 0.02;
            double d1 = random.nextGaussian() * 0.02;
            double d2 = random.nextGaussian() * 0.02;
            double d3 = 10.0;
            double x = entity.getX(0.0) - d0 * d3;
            double y = entity.getRandomY() - d1 * d3;
            double z = entity.getRandomZ(1.0) - d2 * d3;
            entity.level.addParticle(ParticleTypes.POOF, x, y, z, d0, d1, d2);
        }
    }
}
