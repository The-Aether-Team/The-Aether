package com.aether.api;

import java.util.Collection;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;

import com.aether.Aether;
import com.aether.api.accessories.AetherAccessory;
import com.aether.api.enchantments.AetherEnchantmentFuel;
import com.aether.api.enchantments.AetherEnchantmentRecipe;
import com.aether.api.freezables.AetherFreezableFuel;
import com.aether.api.freezables.AetherFreezableRecipe;
import com.aether.api.moa.AetherMoaType;
import com.aether.api.player.IPlayerAether;
import com.aether.capability.CapabilityPlayerAether;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = Aether.MODID)
public class AetherAPI {
	
	public static final String API_ID = "aetherapi";
	
	private static IForgeRegistry<AetherAccessory> iAccessoryRegistry;
	
	private static IForgeRegistry<AetherEnchantmentRecipe> iEnchantmentRegistry;
	
	private static IForgeRegistry<AetherEnchantmentFuel> iEnchantmentFuelRegistry;
	
	private static IForgeRegistry<AetherFreezableRecipe> iFreezableRegistry;
	
	private static IForgeRegistry<AetherFreezableFuel> iFreezableFuelRegistry;
	
	private static IForgeRegistry<AetherMoaType> iMoaTypeRegistry;
	
	public static final AetherAPI INSTANCE = new AetherAPI();
	
	@SubscribeEvent
	public static void onMakeRegistries(RegistryEvent.NewRegistry event) {
		iAccessoryRegistry = makeRegistry("accessories", AetherAccessory.class);
		iEnchantmentRegistry = makeRegistry("enchantments", AetherEnchantmentRecipe.class);
		iEnchantmentFuelRegistry = makeRegistry("enchantment_fuels", AetherEnchantmentFuel.class);
		iFreezableRegistry = makeRegistry("freezables", AetherFreezableRecipe.class);
		iFreezableFuelRegistry = makeRegistry("freezable_fuel", AetherFreezableFuel.class);
		iMoaTypeRegistry = makeRegistry("moa_types", AetherMoaType.class);
	}
	
	private static <T extends IForgeRegistryEntry<T>> IForgeRegistry<T> makeRegistry(String name, Class<T> type) {
		return new RegistryBuilder<T>()
				.setName(new ResourceLocation(AetherAPI.API_ID, name))
				.setType(type)
				.setIDRange(0, 32766)
				.create();
	}

	private AetherAPI() {}
	
	public LazyOptional<IPlayerAether> get(PlayerEntity player) {
		return player.getCapability(CapabilityPlayerAether.AETHER_PLAYER_CAPABILITY, null);
	}
	
	public boolean isAccessory(@Nonnull ItemStack stack) {
		return iAccessoryRegistry.containsKey(stack.getItem().getRegistryName());
	}
	
	public boolean isAccessory(@Nonnull Item item) {
		return iAccessoryRegistry.containsKey(item.getRegistryName());
	}
	
	public boolean isAccessory(@Nonnull Block block) {
		return iAccessoryRegistry.containsKey(block.getRegistryName());
	}
	
	public @Nullable AetherAccessory getAccessory(@Nonnull ItemStack stack) {
		return iAccessoryRegistry.getValue(stack.getItem().getRegistryName());
	}
	
	public @Nullable AetherAccessory getAccessory(@Nonnull Item item) {
		return iAccessoryRegistry.getValue(item.getRegistryName());
	}
	
	public @Nullable AetherAccessory getAccessory(@Nonnull Block block) {
		return iAccessoryRegistry.getValue(block.getRegistryName());
	}
	
	public boolean hasEnchantmentRecipeFor(@Nonnull ItemStack stack) {
		return iEnchantmentRegistry.containsKey(stack.getItem().getRegistryName());
	}
	
	public boolean hasEnchantmentRecipeFor(@Nonnull Item item) {
		return iEnchantmentRegistry.containsKey(item.getRegistryName());
	}
	
	public boolean hasEnchantmentRecipeFor(@Nonnull Block block) {
		return iEnchantmentRegistry.containsKey(block.getRegistryName());
	}
	
	public @Nullable AetherEnchantmentRecipe getEnchantmentRecipeFor(@Nonnull ItemStack stack) {
		return iEnchantmentRegistry.getValue(stack.getItem().getRegistryName());
	}
	
	public @Nullable AetherEnchantmentRecipe getEnchantmentRecipeFor(@Nonnull Item item) {
		return iEnchantmentRegistry.getValue(item.getRegistryName());
	}
	
	public @Nullable AetherEnchantmentRecipe getEnchantmentRecipeFor(@Nonnull Block block) {
		return iEnchantmentRegistry.getValue(block.getRegistryName());
	}
	
	public boolean isEnchantmentFuel(@Nonnull ItemStack stack) {
		return iEnchantmentFuelRegistry.containsKey(stack.getItem().getRegistryName());
	}
	
	public boolean isEnchantmentFuel(@Nonnull Item item) {
		return iEnchantmentFuelRegistry.containsKey(item.getRegistryName());
	}
	
	public boolean isEnchantmentFuel(@Nonnull Block block) {
		return iEnchantmentFuelRegistry.containsKey(block.getRegistryName());
	}
	
	public @Nullable AetherEnchantmentFuel getEnchantmentFuel(@Nonnull ItemStack stack) {
		return iEnchantmentFuelRegistry.getValue(stack.getItem().getRegistryName());
	}
	
	public @Nullable AetherEnchantmentFuel getEnchantmentFuel(@Nonnull Item item) {
		return iEnchantmentFuelRegistry.getValue(item.getRegistryName());
	}
	
	public @Nullable AetherEnchantmentFuel getEnchantmentFuel(@Nonnull Block block) {
		return iEnchantmentFuelRegistry.getValue(block.getRegistryName());
	}
	
	public boolean hasFreezableRecipeFor(@Nonnull ItemStack stack) {
		return iFreezableRegistry.containsKey(stack.getItem().getRegistryName());
	}
	
	public boolean hasFreezableRecipeFor(@Nonnull Item item) {
		return iFreezableRegistry.containsKey(item.getRegistryName());
	}
	
	public boolean hasFreezableRecipeFor(@Nonnull Block block) {
		return iFreezableRegistry.containsKey(block.getRegistryName());
	}
	
	public @Nullable AetherFreezableRecipe getFreezableRecipeFor(@Nonnull ItemStack stack) {
		return iFreezableRegistry.getValue(stack.getItem().getRegistryName());
	}
	
	public @Nullable AetherFreezableRecipe getFreezableRecipeFor(@Nonnull Item item) {
		return iFreezableRegistry.getValue(item.getRegistryName());
	}
	
	public @Nullable AetherFreezableRecipe getFreezableRecipeFor(@Nonnull Block block) {
		return iFreezableRegistry.getValue(block.getRegistryName());
	}
	
	public boolean isFreezableFuel(@Nonnull ItemStack stack) {
		return iFreezableFuelRegistry.containsKey(stack.getItem().getRegistryName());
	}
	
	public boolean isFreezableFuel(@Nonnull Item item) {
		return iFreezableFuelRegistry.containsKey(item.getRegistryName());
	}
	
	public boolean isFreezableFuel(@Nonnull Block block) {
		return iFreezableFuelRegistry.containsKey(block.getRegistryName());
	}
	
	public @Nullable AetherFreezableFuel getFreezableFuel(@Nonnull ItemStack stack) {
		return iFreezableFuelRegistry.getValue(stack.getItem().getRegistryName());
	}
	
	public @Nullable AetherFreezableFuel getFreezableFuel(@Nonnull Item item) {
		return iFreezableFuelRegistry.getValue(item.getRegistryName());
	}
	
	public @Nullable AetherFreezableFuel getFreezableFuel(@Nonnull Block block) {
		return iFreezableFuelRegistry.getValue(block.getRegistryName());
	}
	
	public @Nonnull Collection<AetherEnchantmentRecipe> getEnchantmentRecipes() {
		return iEnchantmentRegistry.getValues();
	}
	
	public @Nonnull Collection<AetherFreezableRecipe> getFreezableRecipes() {
		return iFreezableRegistry.getValues();
	}
	
	public boolean hasMoaType(@Nonnull ResourceLocation id) {
		return iMoaTypeRegistry.containsKey(Validate.notNull(id, "id was null"));
	}
	
	public @Nullable AetherMoaType getMoaType(@Nonnull ResourceLocation id) {
		return iMoaTypeRegistry.getValue(Validate.notNull(id, "id was null"));
	}
	
	public @Nonnull Collection<AetherMoaType> getMoaTypes() {
		return iMoaTypeRegistry.getValues();
	}
	
	public @Nonnull AetherMoaType getRandomMoaType() {
		Collection<AetherMoaType> types = iMoaTypeRegistry.getValues();
		return types.stream().skip(new Random().nextInt(types.size())).findAny().orElseThrow(() -> new IllegalStateException("no moa types were registered"));
	}
	
}
