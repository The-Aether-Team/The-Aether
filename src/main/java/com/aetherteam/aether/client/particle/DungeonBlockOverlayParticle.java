package com.aetherteam.aether.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DungeonBlockOverlayParticle extends TextureSheetParticle {
    protected DungeonBlockOverlayParticle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn);
        this.gravity = 0.0F;
        this.lifetime = 80;
        this.hasPhysics = false;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getQuadSize(float size) {
        return 0.5F;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSetIn) {
            this.spriteSet = spriteSetIn;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DungeonBlockOverlayParticle particle = new DungeonBlockOverlayParticle(worldIn, x, y, z);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }
}
