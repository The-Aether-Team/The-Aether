package com.aether.world;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.BiFunction;

import com.aether.Aether;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AetherDimensions {

	public static final DeferredRegister<ModDimension> DIMENSIONS = new DeferredRegister<>(ForgeRegistries.MOD_DIMENSIONS, Aether.MODID);

	private static final RegistryObject<ModDimension> AETHER_DIMENSION_REGISTRY_OBJECT = DIMENSIONS.register("aether",
		() -> new ModDimension() {
			@Override
			public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
				return AetherDimension::new;
			}
		}
	);

	public static final DimensionType AETHER = null;

	@EventBusSubscriber(modid = Aether.MODID)
	public static final class Registration {

		private static final ResourceLocation AETHER_ID = new ResourceLocation(Aether.MODID, "aether");

		@SubscribeEvent
		public static void registerDimensions(RegisterDimensionsEvent event) {
			DimensionType dimensiontype = DimensionType.byName(AETHER_ID);
			if (dimensiontype == null) {
				dimensiontype = DimensionManager.registerDimension(AETHER_ID, AETHER_DIMENSION_REGISTRY_OBJECT.get(),
					null, true);
				DimensionManager.keepLoaded(dimensiontype, false);
			}
			// Use reflection to set the final field AetherDimensions.AETHER = dimensiontype
			try {
				field_AetherDimensions__AETHER.set(null, dimensiontype);
			}
			catch (IllegalArgumentException | IllegalAccessException e) {
				throw new AssertionError(e);
			}
		}

		private static final Field field_AetherDimensions__AETHER;

		static {
			Field field_AETHER = null;

			try {
				// Get the Field instance for the final field Field.modifiers
				Field field_modifiers = Field.class.getDeclaredField("modifiers");
				// Change it to public
				field_modifiers.setAccessible(true);
				// Get the Field instance for the final field AetherDimensions.AETHER
				field_AETHER = AetherDimensions.class.getDeclaredField("AETHER");
				// Change it to public
				field_AETHER.setAccessible(true);
				// Get its current modifiers bit field
				int mods = field_AETHER.getModifiers();
				// Use reflection to set the final field Field.modifiers = mods & ~Modifiers.FINAL, which
				// results in unsetting the FINAL flag for the AetherDimensions.AETHER field instance.
				// This will let us use .set() with the object.
				field_modifiers.setInt(field_AETHER, mods & ~Modifier.FINAL);
			}
			catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
				throw new AssertionError(e);
			}

			field_AetherDimensions__AETHER = field_AETHER;
		}

	}

}
