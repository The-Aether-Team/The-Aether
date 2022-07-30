package com.gildedgames.aether.api;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.registers.DungeonType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AetherDungeonTypes {
	public static final ResourceKey<Registry<DungeonType>> DUNGEON_TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(Aether.MODID, "dungeon_type"));
	public static final DeferredRegister<DungeonType> DUNGEON_TYPES = DeferredRegister.create(DUNGEON_TYPE_REGISTRY_KEY, Aether.MODID);
	public static final Supplier<IForgeRegistry<DungeonType>> DUNGEON_TYPE_REGISTRY = DUNGEON_TYPES.makeRegistry(RegistryBuilder::new);

	public static final RegistryObject<DungeonType> BRONZE = DUNGEON_TYPES.register("bronze", DungeonType::new);
	public static final RegistryObject<DungeonType> SILVER = DUNGEON_TYPES.register("silver", DungeonType::new);
	public static final RegistryObject<DungeonType> GOLD = DUNGEON_TYPES.register("gold", DungeonType::new);

	public static DungeonType get(String id) {
		return DUNGEON_TYPE_REGISTRY.get().getValue(new ResourceLocation(id));
	}
}
