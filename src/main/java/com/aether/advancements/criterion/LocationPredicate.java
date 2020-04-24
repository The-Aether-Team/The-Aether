package com.aether.advancements.criterion;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.MinMaxBounds.FloatBound;
import net.minecraft.block.BlockState;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

public class LocationPredicate extends net.minecraft.advancements.criterion.LocationPredicate {
	public static final LocationPredicate ANY = new LocationPredicate(MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, (Biome)null, (Structure<?>)null, (DimensionType)null, (BlockPredicate)null);
	private final BlockPredicate block;

	public LocationPredicate(FloatBound x, FloatBound y, FloatBound z, Biome biome, Structure<?> feature, DimensionType dimension, BlockPredicate block) {
		super(x, y, z, biome, feature, dimension);
		this.block = block;
	}
	
	@Override
	public boolean test(ServerWorld world, float x, float y, float z) {
		if (this == ANY) {
			return true;
		}
		if (super.test(world, x, y, z)) {
			if (block != null) {
				BlockPos pos = new BlockPos(x, y, z);
				BlockState state = world.getBlockState(pos);
				if (!block.test(state, world, pos)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public JsonElement serialize() {
		if (this == ANY) {
			return JsonNull.INSTANCE;
		}
		JsonElement result = super.serialize();
		if (result != JsonNull.INSTANCE) {
			JsonObject jsonobject = (JsonObject)result;
			if (block != null) {
				jsonobject.add("block", block.serialize());
			}
		}
		return result;
	}

	public static LocationPredicate deserialize(@Nullable JsonElement element) {
		if (element != null && !element.isJsonNull()) {
			JsonObject jsonobject = JSONUtils.getJsonObject(element, "location");
			JsonObject jsonobject1 = JSONUtils.getJsonObject(jsonobject, "position", new JsonObject());
			MinMaxBounds.FloatBound minmaxbounds$floatbound = MinMaxBounds.FloatBound.fromJson(jsonobject1.get("x"));
			MinMaxBounds.FloatBound minmaxbounds$floatbound1 = MinMaxBounds.FloatBound.fromJson(jsonobject1.get("y"));
			MinMaxBounds.FloatBound minmaxbounds$floatbound2 = MinMaxBounds.FloatBound.fromJson(jsonobject1.get("z"));
			DimensionType dimensiontype = jsonobject.has("dimension")
				? DimensionType.byName(new ResourceLocation(JSONUtils.getString(jsonobject, "dimension")))
				: null;
			Structure<?> structure = jsonobject.has("feature")
				? Feature.STRUCTURES.get(JSONUtils.getString(jsonobject, "feature"))
				: null;
			Biome biome = null;
			if (jsonobject.has("biome")) {
				ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(jsonobject, "biome"));
				biome = ForgeRegistries.BIOMES.getValue(resourcelocation);
				if (biome == null) {
					throw new JsonSyntaxException("Unknown biome '" + resourcelocation + "'");
				}
			}
			BlockPredicate block = jsonobject.has("block")? BlockPredicate.deserialize(jsonobject.get("block")) : null;

			return new LocationPredicate(minmaxbounds$floatbound, minmaxbounds$floatbound1, minmaxbounds$floatbound2,
				biome, structure, dimensiontype, block);
		}
		else {
			return ANY;
		}
	}
	
	public static class Builder {
	      private MinMaxBounds.FloatBound x = MinMaxBounds.FloatBound.UNBOUNDED;
	      private MinMaxBounds.FloatBound y = MinMaxBounds.FloatBound.UNBOUNDED;
	      private MinMaxBounds.FloatBound z = MinMaxBounds.FloatBound.UNBOUNDED;
	      @Nullable
	      private Biome biome;
	      @Nullable
	      private Structure<?> feature;
	      @Nullable
	      private DimensionType dimension;
	      @Nullable
	      private BlockPredicate block;

	      public LocationPredicate.Builder biome(@Nullable Biome p_218012_1_) {
	         this.biome = p_218012_1_;
	         return this;
	      }
	      
	      public LocationPredicate.Builder block(@Nullable BlockPredicate predicate) {
	    	  this.block = predicate;
	    	  return this;
	      }

	      public LocationPredicate build() {
	         return new LocationPredicate(this.x, this.y, this.z, this.biome, this.feature, this.dimension, this.block);
	      }
	   }

}
