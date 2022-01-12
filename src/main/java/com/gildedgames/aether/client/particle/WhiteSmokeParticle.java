package com.gildedgames.aether.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//Subclass of SmokeParticle to make white particles. TODO: Is this particle necessary?
public class WhiteSmokeParticle extends SmokeParticle {
    protected WhiteSmokeParticle(ClientLevel p_i232425_1_, double p_i232425_2_, double p_i232425_4_, double p_i232425_6_, double p_i232425_8_, double p_i232425_10_, double p_i232425_12_, float p_i232425_14_, SpriteSet p_i232425_15_) {
        super(p_i232425_1_, p_i232425_2_, p_i232425_4_, p_i232425_6_, p_i232425_8_, p_i232425_10_, p_i232425_12_, p_i232425_14_, p_i232425_15_);
        this.setColor(1.0F, 1.0F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteset;

        public Factory(SpriteSet p_i51045_1_) {
            this.spriteset = p_i51045_1_;
        }

        public Particle createParticle(SimpleParticleType p_199234_1_, ClientLevel p_199234_2_, double p_199234_3_, double p_199234_5_, double p_199234_7_, double p_199234_9_, double p_199234_11_, double p_199234_13_) {
            return new WhiteSmokeParticle(p_199234_2_, p_199234_3_, p_199234_5_, p_199234_7_, p_199234_9_, p_199234_11_, p_199234_13_, 1.0F, this.spriteset);
        }
    }
}
