package com.aether.api.moa;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;

public class MoaProperties {
	private final int maxJumps;
	private final float moaSpeed;
	private ResourceLocation customTexture = null;
	private ResourceLocation customSaddleTexture = null;
	
	private MoaProperties(int maxJumps, float moaSpeed, ResourceLocation customTexture, ResourceLocation customSaddleTexture) {
		this.maxJumps = maxJumps;
		this.moaSpeed = moaSpeed;
		this.customTexture = customTexture;
		this.customSaddleTexture = customSaddleTexture;
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
	
	public @Nullable ResourceLocation getCustomTexture() {
		return customTexture;
	}
	
	public boolean hasCustomSaddleTexture() {
		return customSaddleTexture != null;
	}
	
	public @Nullable ResourceLocation getCustomSaddleTexture() {
		return customSaddleTexture;
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
					&& (this.hasCustomTexture()? properties.hasCustomTexture() && this.getCustomTexture().equals(properties.getCustomTexture()) : !properties.hasCustomTexture())
					&& (this.hasCustomSaddleTexture()? properties.hasCustomSaddleTexture() && this.getCustomSaddleTexture().equals(properties.getCustomSaddleTexture()) : !properties.hasCustomSaddleTexture());
		} else {
			return false;
		}
	}
	
	public static class Builder {
		private int maxJumps = 3;
		private float moaSpeed = 0.3F;
		private ResourceLocation texture;
		private ResourceLocation saddleTexture;
		
		public MoaProperties.Builder maxJumps(int maxJumps) {
			this.maxJumps = maxJumps;
			return this;
		}
		
		public MoaProperties.Builder speed(float speed) {
			this.moaSpeed = speed;
			return this;
		}
		
		public MoaProperties.Builder texture(ResourceLocation texture) {
			this.texture = texture;
			return this;
		}
		
		public MoaProperties.Builder texture(String texture) {
			return this.texture(new ResourceLocation(texture));
		}
		
		public MoaProperties.Builder saddleTexture(ResourceLocation saddleTexture) {
			this.saddleTexture = saddleTexture;
			return this;
		}
		
		public MoaProperties.Builder saddleTexture(String name) {
			return this.saddleTexture(new ResourceLocation(name));
		}
		
		public MoaProperties build() {
			return new MoaProperties(maxJumps, moaSpeed, texture, saddleTexture);
		}
		
	}
	
}
