package com.aether.api;

import java.util.Collection;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;

import com.aether.Aether;
import com.aether.api.accessories.AetherAccessory;
import com.aether.api.enchantments.AetherEnchantmentFuel;
import com.aether.api.freezables.AetherFreezableFuel;
import com.aether.api.moa.MoaType;
import com.aether.capability.AetherCapabilities;
import com.aether.player.IAetherPlayer;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = Aether.MODID, bus = EventBusSubscriber.Bus.MOD)
public class AetherAPI {
	
	private static IForgeRegistry<AetherAccessory> accessoryRegistry;
	
	private static IForgeRegistry<AetherEnchantmentFuel> enchantmentFuelRegistry;
	
	private static IForgeRegistry<AetherFreezableFuel> freezableFuelRegistry;
	
	private static IForgeRegistry<MoaType> moaTypeRegistry;
	
	@SubscribeEvent
	public static void makeRegistries(RegistryEvent.NewRegistry event) {
		accessoryRegistry = makeRegistry("accessories", AetherAccessory.class);
		enchantmentFuelRegistry = makeRegistry("enchantment_fuels", AetherEnchantmentFuel.class);
		freezableFuelRegistry = makeRegistry("freezable_fuel", AetherFreezableFuel.class);
		moaTypeRegistry = makeRegistry("moa_types", MoaType.class);
//		LogManager.getLogger(AetherAPI.class).debug("registered aetherapi registries");
	}
	
	private static <T extends IForgeRegistryEntry<T>> IForgeRegistry<T> makeRegistry(String name, Class<T> type) {
		return new RegistryBuilder<T>()
				.setName(GameData.checkPrefix(name, true))
				.setType(type)
				.setIDRange(0, 32766)
				.create();
	}
	
	public static LazyOptional<IAetherPlayer> get(PlayerEntity player) {
		return player.getCapability(AetherCapabilities.AETHER_PLAYER_CAPABILITY, null);
	}
	
	public static boolean isAccessory(@Nonnull ItemStack stack) {
		return accessoryRegistry.containsKey(stack.getItem().getRegistryName());
	}
	
	public static boolean isAccessory(@Nonnull Item item) {
		return accessoryRegistry.containsKey(item.getRegistryName());
	}
	
	public static boolean isAccessory(@Nonnull Block block) {
		return accessoryRegistry.containsKey(block.getRegistryName());
	}
	
	public static @Nullable AetherAccessory getAccessory(@Nonnull ItemStack stack) {
		return accessoryRegistry.getValue(stack.getItem().getRegistryName());
	}
	
	public static @Nullable AetherAccessory getAccessory(@Nonnull Item item) {
		return accessoryRegistry.getValue(item.getRegistryName());
	}
	
	public static @Nullable AetherAccessory getAccessory(@Nonnull Block block) {
		return accessoryRegistry.getValue(block.getRegistryName());
	}

	public static boolean isEnchantmentFuel(@Nonnull Item item) {
		return enchantmentFuelRegistry.containsKey(item.getRegistryName());
	}	
	
	public static @Nullable AetherEnchantmentFuel getEnchantmentFuel(@Nonnull Item item) {
		return enchantmentFuelRegistry.getValue(item.getRegistryName());
	}
	
	public static  int getEnchantmentFuelTime(@Nonnull Item item) {
		AetherEnchantmentFuel fuel = getEnchantmentFuel(item);
		if (fuel == null) {
			return 0;
		}
		else {
			return fuel.getTimeGiven();
		}
	}
	
	public static Collection<AetherEnchantmentFuel> getEnchantmentFuelSet() {
		return enchantmentFuelRegistry.getValues();
	}
	
	public static boolean isFreezableFuel(@Nonnull Item item) {
		return freezableFuelRegistry.containsKey(item.getRegistryName());
	}
	
	public static @Nullable AetherFreezableFuel getFreezableFuel(@Nonnull Item item) {
		return freezableFuelRegistry.getValue(item.getRegistryName());
	}
	
	public static int getFreezableFuelTime(@Nonnull Item item) {
		AetherFreezableFuel fuel = getFreezableFuel(item);
		if (fuel == null) {
			return 0;
		}
		else {
			return fuel.getTimeGiven();
		}
	}
	
	public static Collection<AetherFreezableFuel> getFreezableFuelSet() {
		return freezableFuelRegistry.getValues();
	}
	
//	public @Nonnull Collection<AetherEnchantmentRecipe> getEnchantmentRecipes() {
//		return iEnchantmentRegistry.getValues();
//	}
//	
//	public @Nonnull Collection<AetherFreezableRecipe> getFreezableRecipes() {
//		return iFreezableRegistry.getValues();
//	}
	
	public static boolean hasMoaType(@Nonnull ResourceLocation id) {
		return moaTypeRegistry.containsKey(Validate.notNull(id, "id was null"));
	}
	
	public static @Nullable MoaType getMoaType(@Nonnull ResourceLocation id) {
		return moaTypeRegistry.getValue(Validate.notNull(id, "id was null"));
	}
	
	public static @Nonnull Collection<MoaType> getMoaTypes() {
		return moaTypeRegistry.getValues();
	}
	
	public static @Nonnull MoaType getRandomMoaType() {
		Collection<MoaType> types = moaTypeRegistry.getValues();
		return types.stream().skip(new Random().nextInt(types.size())).findAny().orElseThrow(() -> new IllegalStateException("no moa types were registered"));
	}
	
}
