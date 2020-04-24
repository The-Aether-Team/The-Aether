package com.aether.world.storage.loot.conditions;

import com.aether.Aether;
import com.aether.advancements.criterion.LocationPredicate;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
public class LocationCheck implements ILootCondition {
	private final LocationPredicate predicate;

	private LocationCheck(LocationPredicate predicate) {
		this.predicate = predicate;
	}

	@Override
	public boolean test(LootContext context) {
		BlockPos blockpos = context.get(LootParameters.POSITION);
		return blockpos != null && this.predicate.test(context.getWorld(), blockpos.getX(),
			blockpos.getY(), blockpos.getZ());
	}

	public static ILootCondition.IBuilder builder(LocationPredicate.Builder builder) {
		return () -> {
			return new LocationCheck(builder.build());
		};
	}

	public static class Serializer extends ILootCondition.AbstractSerializer<LocationCheck> {
		public Serializer() {
			super(new ResourceLocation(Aether.MODID, "location_check"), LocationCheck.class);
		}

		@Override
		public void serialize(JsonObject json, LocationCheck value, JsonSerializationContext context) {
			json.add("predicate", value.predicate.serialize());
		}

		@Override
		public LocationCheck deserialize(JsonObject json, JsonDeserializationContext context) {
			LocationPredicate locationpredicate = LocationPredicate.deserialize(json.get("predicate"));
			return new LocationCheck(locationpredicate);
		}
	}
}
