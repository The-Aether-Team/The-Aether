package com.aether.api.moa;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;

import net.minecraft.util.ResourceLocation;

public class MoaProperties {
	private final int maxJumps;
	private final float moaSpeed;
	private ResourceLocation customTexture = null;
	
	public MoaProperties(int maxJumps, float moaSpeed) {
		this.maxJumps = maxJumps;
		this.moaSpeed = moaSpeed;
	}
	
	public MoaProperties(int maxJumps, float moaSpeed, @Nonnull ResourceLocation customTexture) {
		this(maxJumps, moaSpeed);
		
		Validate.notNull(customTexture, "custom texture was null");
		
		this.customTexture = customTexture;
	}
	
	public int getMaxJumps() {
		return maxJumps;
	}
	
	public float getMoaSpeed() {
		return moaSpeed;
	}
	
	public boolean hasCustomTexture() {
		return customTexture != null;
	}
	
	public @Nullable ResourceLocation getCustomTexture(boolean isSaddled) {
		return customTexture;
	}
	
	protected boolean canEqual(Object obj) {
		return obj instanceof MoaProperties;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (this.canEqual(obj) && ((MoaProperties) obj).canEqual(this)) {
			MoaProperties properties = (MoaProperties) obj;
			
			return this.getMaxJumps() == properties.getMaxJumps() && this.getMoaSpeed() == properties.getMoaSpeed()
					&& (this.hasCustomTexture()? properties.hasCustomTexture() && this.getCustomTexture(false).equals(properties.getCustomTexture(false)) && this.getCustomTexture(true).equals(properties.getCustomTexture(true)) : !properties.hasCustomTexture());
		} else {
			return false;
		}
	}
	
}
