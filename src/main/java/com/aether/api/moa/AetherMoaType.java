package com.aether.api.moa;

import java.awt.Color;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;

import com.aether.Aether;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class AetherMoaType extends ForgeRegistryEntry<AetherMoaType> {
	private final int hexColor;
	private final MoaProperties properties;
	private ItemGroup group;
	
	public AetherMoaType(int hexColor, @Nonnull MoaProperties properties) {
		this(hexColor, properties, ItemGroup.MISC);
	}
	
	public AetherMoaType(Color color, @Nonnull MoaProperties properties) {
		this(color.getRGB(), properties, ItemGroup.MISC);
	}
	
	public AetherMoaType(Color color, @Nonnull MoaProperties properties, ItemGroup group) {
		this(color.getRGB(), properties, group);
	}
	
	public AetherMoaType(int hexColor, @Nonnull MoaProperties properties, ItemGroup group) {
		Validate.notNull(properties, "moa properties were null");
		
		this.hexColor = hexColor;
		this.properties = properties;
		this.group = group;
	}
	
	private ResourceLocation texture_saved = null;
	
	public @Nonnull ResourceLocation getTexture() {
		ResourceLocation texture;
		
		if (properties.hasCustomTexture()) {
			texture = properties.getCustomTexture();
		}
		else {
			texture = texture_saved;
			
			if (texture == null) {
				texture_saved = texture = new ResourceLocation(Aether.MODID, "textures/entity/moa/" + this.getRegistryName().getPath() + ".png");
			}
		}
		
		return texture;
	}
	
	private ResourceLocation saddle_texture_saved = null;
	
	public @Nonnull ResourceLocation getSaddleTexture() {
		ResourceLocation texture;
		
		if (properties.hasCustomSaddleTexture()) {
			texture = properties.getCustomSaddleTexture();
		}
		else {
			texture = saddle_texture_saved;
			
			if (texture == null) {
				saddle_texture_saved = texture = new ResourceLocation(Aether.MODID, "textures/entity/moa/saddle.png");
			}
		}
		
		return texture;
	}
	
	public @Nonnull MoaProperties getMoaProperties() {
		return properties;
	}
	
	public @Nullable ItemGroup getGroup() {
		return group;
	}
	
	public int getMoaEggColor() {
		return hexColor;
	}
	
	protected boolean canEqual(Object obj) {
		return obj instanceof AetherMoaType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (this.canEqual(obj) && ((AetherMoaType) obj).canEqual(this)) {
			AetherMoaType moaType = (AetherMoaType) obj;
			
			return this.getMoaEggColor() == moaType.getMoaEggColor()
					&& this.getMoaProperties().equals(moaType.getMoaProperties())
					&& this.getTexture().equals(moaType.getTexture()) && this.getSaddleTexture().equals(moaType.getSaddleTexture());
		} else {
			return false;
		}
	}
	
	public ItemStack getItemStack() {
		// TODO return aether:moa_egg{MoaType:"..."}
		return new ItemStack(Items.EGG);
	}
	
}
