package com.aetherteam.aether.data.resources.registries;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Optional;

public class AetherMoaTypes {
    public static final ResourceKey<Registry<MoaType>> MOA_TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "moa_type"));

    public static final ResourceKey<MoaType> BLUE = createKey("blue");
    public static final ResourceKey<MoaType> WHITE = createKey("white");
    public static final ResourceKey<MoaType> BLACK = createKey("black");

    private static ResourceKey<MoaType> createKey(String name) {
        return ResourceKey.create(AetherMoaTypes.MOA_TYPE_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(Aether.MODID, name));
    }

    public static void bootstrap(BootstapContext<MoaType> context) {
        context.register(BLUE, new MoaType(new ItemStack(AetherItems.BLUE_MOA_EGG.get()), 3, 0.155F, 100, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/moa/blue_moa.png"), ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/moa/moa_saddle.png")));
        context.register(WHITE, new MoaType(new ItemStack(AetherItems.WHITE_MOA_EGG.get()), 4, 0.155F, 50, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/moa/white_moa.png"), ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/moa/moa_saddle.png")));
        context.register(BLACK, new MoaType(new ItemStack(AetherItems.BLACK_MOA_EGG.get()), 8, 0.155F, 25, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/moa/black_moa.png"), ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/moa/black_moa_saddle.png")));
    }

    @Nullable
    public static ResourceKey<MoaType> getResourceKey(RegistryAccess registryAccess, String location) {
        return getResourceKey(registryAccess, ResourceLocation.withDefaultNamespace(location));
    }

    @Nullable
    public static ResourceKey<MoaType> getResourceKey(RegistryAccess registryAccess, ResourceLocation location) {
        MoaType moaType = getMoaType(registryAccess, location);
        if (moaType != null) {
            return registryAccess.registryOrThrow(AetherMoaTypes.MOA_TYPE_REGISTRY_KEY).getResourceKey(moaType).orElse(null);
        } else {
            return null;
        }
    }

    @Nullable
    public static ResourceKey<MoaType> getResourceKey(RegistryAccess registryAccess, MoaType moaType) {
        return registryAccess.registryOrThrow(AetherMoaTypes.MOA_TYPE_REGISTRY_KEY).getResourceKey(moaType).orElse(null);
    }

    @Nullable
    public static MoaType getMoaType(RegistryAccess registryAccess, String location) {
        return getMoaType(registryAccess, ResourceLocation.withDefaultNamespace(location));
    }

    @Nullable
    public static MoaType getMoaType(RegistryAccess registryAccess, ResourceLocation location) {
        return registryAccess.registryOrThrow(AetherMoaTypes.MOA_TYPE_REGISTRY_KEY).get(location);
    }



//    @Nullable
//    public static MoaType get(RegistryAccess registryAccess, String location) {
//        return get(registryAccess, ResourceLocation.withDefaultNamespace(location));
//    }
//
//    @Nullable
//    public static MoaType get(RegistryAccess registryAccess, ResourceLocation location) {
//        return registryAccess.registryOrThrow(AetherMoaTypes.MOA_TYPE_REGISTRY_KEY).get(location);
//    }
//
//    @Nullable
//    public static ResourceKey<MoaType> getKey(RegistryAccess registryAccess, String location) {
//        return getKey(registryAccess, ResourceLocation.withDefaultNamespace(location));
//    }
//
//    @Nullable
//    public static ResourceKey<MoaType> getKey(RegistryAccess registryAccess, ResourceLocation location) {
//        MoaType moaType = get(registryAccess, location);
//        if (moaType != null) {
//            return registryAccess.registryOrThrow(AetherMoaTypes.MOA_TYPE_REGISTRY_KEY).getResourceKey(moaType).orElse(null);
//        }
//        return null;
//    }

    /**
     * Gets a random {@link MoaType} with a weighted chance. This is used when spawning Moas in the world.<br>
     * A {@link SimpleWeightedRandomList} is built with all the {@link MoaType}s and their spawn chance weights, and one is randomly picked out of the list.
     *
     * @param registryAccess The {@link RegistryAccess} to use.
     * @param random The {@link RandomSource} to use.
     * @return The {@link MoaType}.
     */
    public static MoaType getWeightedChance(RegistryAccess registryAccess, RandomSource random) {
        Registry<MoaType> moaTypeRegistry = registryAccess.registryOrThrow(AetherMoaTypes.MOA_TYPE_REGISTRY_KEY);
        SimpleWeightedRandomList.Builder<MoaType> weightedListBuilder = SimpleWeightedRandomList.builder();
        moaTypeRegistry.holders().forEach((moaType) -> weightedListBuilder.add(moaType.value(), moaType.value().spawnChance()));
        SimpleWeightedRandomList<MoaType> weightedList = weightedListBuilder.build();
        Optional<MoaType> moaType = weightedList.getRandomValue(random);
        return moaType.orElse(moaTypeRegistry.get(AetherMoaTypes.BLUE));
    }
}
