package com.gildedgames.aether.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AetherPortalParticle extends PortalParticle {

	protected AetherPortalParticle(ClientWorld worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		
		float f = this.random.nextFloat() * 0.6F + 0.4F;
		
		this.rCol = this.gCol = this.bCol = 1.0F * f;
		this.rCol *= 0.2F;
		this.gCol *= 0.2F;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;
		
		public Factory(IAnimatedSprite spriteSetIn) {
			this.spriteSet = spriteSetIn;
		}
		
		@Override
		public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			 AetherPortalParticle portalparticle = new AetherPortalParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
	         portalparticle.pickSprite(this.spriteSet);
	         return portalparticle;
		}

	}

}
