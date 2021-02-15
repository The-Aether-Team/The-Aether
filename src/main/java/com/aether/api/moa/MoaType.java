package com.aether.api.moa;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.aether.Aether;
import com.aether.registry.AetherItemGroups;
import com.aether.util.AetherUtils;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class MoaType extends ForgeRegistryEntry<MoaType> {
	private static final ResourceLocation DEFAULT_SADDLE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/moa/saddle.png");
	
	private final int hexColor;
	private final ItemGroup group;
	private final int maxJumps;
	private final float moaSpeed;
	private ResourceLocation texture;
	private ResourceLocation saddleTexture;
	
	protected MoaType(MoaType.Properties propertiesIn) {
		this(propertiesIn.hexColor, propertiesIn.group, propertiesIn.maxJumps, propertiesIn.moaSpeed, propertiesIn.texture, propertiesIn.saddleTexture);
	}
	
	protected MoaType(int hexColor, ItemGroup group, int maxJumps, float moaSpeed, ResourceLocation texture, ResourceLocation saddleTexture) {
		this.hexColor = hexColor;
		this.group = group;
		this.maxJumps = maxJumps;
		this.moaSpeed = moaSpeed;
		this.texture = texture;
		this.saddleTexture = saddleTexture;
	}
	
	public @Nonnull ResourceLocation getMoaTexture() {
		ResourceLocation texture = this.texture;
		if (texture == null) {
			this.texture = texture = new ResourceLocation(this.getRegistryName().getNamespace(), "textures/entity/moa/" + this.getRegistryName().getPath() + ".png");
		}
		return texture;
	}
	
	public @Nonnull ResourceLocation getSaddleTexture() {
		ResourceLocation texture = this.saddleTexture;
		if (texture == null) {
			this.saddleTexture = texture = DEFAULT_SADDLE_TEXTURE;
		}
		return texture;
	}
	
	@Nullable 
	public ItemGroup getGroup() {
		return this.group;
	}
	
	public int getMoaEggColor() {
		return this.hexColor;
	}
	
	public int getMaxJumps() {
		return this.maxJumps;
	}
	
	public float getMoaSpeed() {
		return this.moaSpeed;
	}
	
	public ItemStack getItemStack() {
		// TODO return aether:moa_egg{MoaType:"..."}
		return new ItemStack(Items.EGG);
	}
	
	public static class Properties {
		private int hexColor;
		private ItemGroup group = AetherItemGroups.AETHER_MISC;
		private int maxJumps = 3;
		private float moaSpeed = 0.1F;
		private ResourceLocation texture;
		private ResourceLocation saddleTexture;
		
		public static MoaType.Properties from(MoaType moaTypeIn) {
			MoaType.Properties props = new MoaType.Properties();
			props.hexColor = moaTypeIn.hexColor;
			props.group = moaTypeIn.group;
			props.maxJumps = moaTypeIn.maxJumps;
			props.moaSpeed = moaTypeIn.moaSpeed;
			props.texture = moaTypeIn.texture;
			props.saddleTexture = moaTypeIn.saddleTexture;
			return props;
		}
		
		public MoaType.Properties eggColor(int color) {
			this.hexColor = color;
			return this;
		}
		
		public MoaType.Properties group(ItemGroup group) {
			this.group = group;
			return this;
		}
		
		public MoaType.Properties maxJumps(int maxJumps) {
			this.maxJumps = maxJumps;
			return this;
		}
		
		public MoaType.Properties moaSpeed(float moaSpeed) {
			this.moaSpeed = moaSpeed;
			return this;
		}
		
		public MoaType.Properties texture(ResourceLocation texture) {
			this.texture = texture;
			return this;
		}
		
		public MoaType.Properties texture(String texture) {
			int i = texture.indexOf(':');
			if (i == -1) {
				this.texture = new ResourceLocation(AetherUtils.defaultAetherNamespace(), texture);
			}
			else {
				this.texture = new ResourceLocation(texture);
			}
			return this;
		}
		
		public MoaType.Properties saddleTexture(ResourceLocation saddleTexture) {
			this.saddleTexture = saddleTexture;
			return this;
		}
		
		public MoaType.Properties saddleTexture(String saddleTexture) {
			int i = saddleTexture.indexOf(':');
			if (i == -1) {
				this.saddleTexture = new ResourceLocation(AetherUtils.defaultAetherNamespace(), saddleTexture);
			}
			else {
				this.saddleTexture = new ResourceLocation(saddleTexture);
			}
			return this;
		}
		
	}
	
}
