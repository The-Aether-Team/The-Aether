package com.aether.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AetherPortalParticle extends PortalParticle {

	protected AetherPortalParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		
		float f = this.rand.nextFloat() * 0.6f + 0.4f;
		
		this.particleRed = this.particleGreen = this.particleBlue = 1.0f * f;
		this.particleRed *= 0.2f;
		this.particleGreen *= 0.2f;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;
		
		public Factory(IAnimatedSprite spriteSetIn) {
			this.spriteSet = spriteSetIn;
		}
		
		@Override
		public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			 AetherPortalParticle portalparticle = new AetherPortalParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
	         portalparticle.selectSpriteRandomly(this.spriteSet);
	         return portalparticle;
		}
		
	}

}
