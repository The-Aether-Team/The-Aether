package com.gildedgames.aether.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HolidayLeavesParticle extends PortalParticle
{
	protected HolidayLeavesParticle(ClientWorld worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);

		this.rCol = 1.0F;
		this.gCol = 1.0F;
		this.bCol = 1.0F;
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType>
	{
		private final IAnimatedSprite spriteSet;

		public Factory(IAnimatedSprite spriteSetIn) {
			this.spriteSet = spriteSetIn;
		}

		@Override
		public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			HolidayLeavesParticle leavesParticle = new HolidayLeavesParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
			leavesParticle.pickSprite(this.spriteSet);
			return leavesParticle;
		}
	}
}
