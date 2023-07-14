package com.aetherteam.aether.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AetherPortalParticle extends PortalParticle
{
	protected AetherPortalParticle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		
		float f = this.random.nextFloat() * 0.6F + 0.4F;
		
		this.rCol = this.gCol = this.bCol = f;
		this.rCol *= 0.2F;
		this.gCol *= 0.2F;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;
		
		public Factory(SpriteSet spriteSetIn) {
			this.spriteSet = spriteSetIn;
		}
		
		@Override
		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			 AetherPortalParticle portalParticle = new AetherPortalParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
			 portalParticle.pickSprite(this.spriteSet);
	         return portalParticle;
		}
	}
}
