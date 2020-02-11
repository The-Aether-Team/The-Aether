package com.aether.client.particle;

import net.minecraft.client.particle.PortalParticle;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HolidayLeavesParticle extends PortalParticle {

	private HolidayLeavesParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		
		this.particleRed = 1.0f;
		this.particleGreen = 1.0f;
		this.particleBlue = 1.0f;
	}
	
}
