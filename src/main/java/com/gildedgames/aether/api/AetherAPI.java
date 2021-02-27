package com.gildedgames.aether.api;

import java.util.Collection;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.gildedgames.aether.api.moa.MoaType;
import com.gildedgames.aether.capability.AetherCapabilities;
import com.gildedgames.aether.player.IAetherPlayer;
import org.apache.commons.lang3.Validate;

import com.gildedgames.aether.Aether;

import net.minecraft.entity.player.PlayerEntity;
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

	private static IForgeRegistry<MoaType> moaTypeRegistry;
	
	@SubscribeEvent
	public static void makeRegistries(RegistryEvent.NewRegistry event) {
		moaTypeRegistry = makeRegistry("moa_types", MoaType.class);
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
