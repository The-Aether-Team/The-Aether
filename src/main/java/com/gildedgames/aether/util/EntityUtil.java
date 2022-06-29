package com.gildedgames.aether.util;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class EntityUtil {
    public static void copyRotations(Entity entity, Player player) {
        entity.setYRot(player.getYRot() % 360.0F);
        entity.setXRot(player.getXRot() % 360.0F);
        entity.setYBodyRot(player.getYRot());
        entity.setYHeadRot(player.getYRot());
    }

    public static void spawnMovementExplosionParticles(Entity entity) {
        double d0 = entity.random.nextGaussian() * 0.02;
        double d1 = entity.random.nextGaussian() * 0.02;
        double d2 = entity.random.nextGaussian() * 0.02;
        double d3 = 10.0;
        double x = entity.getX() + ((double) entity.random.nextFloat() * entity.getBbWidth() * 2.0) - entity.getBbWidth() - d0 * d3;
        double y = entity.getY() + ((double) entity.random.nextFloat() * entity.getBbHeight()) - d1 * d3;
        double z = entity.getZ() + ((double) entity.random.nextFloat() * entity.getBbWidth() * 2.0) - entity.getBbWidth() - d2 * d3;
        entity.level.addParticle(ParticleTypes.POOF, x, y, z, d0, d1, d2);
    }

    public static void spawnSummoningExplosionParticles(Entity entity) {
        for (int i = 0; i < 20; ++i) {
            double d0 = entity.random.nextGaussian() * 0.02;
            double d1 = entity.random.nextGaussian() * 0.02;
            double d2 = entity.random.nextGaussian() * 0.02;
            double d3 = 10.0;
            double x = entity.getX(0.0) - d0 * d3;
            double y = entity.getRandomY() - d1 * d3;
            double z = entity.getRandomZ(1.0) - d2 * d3;
            entity.level.addParticle(ParticleTypes.POOF, x, y, z, d0, d1, d2);
        }
    }
}
