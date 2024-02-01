package com.aetherteam.aether.api;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.IForgeRegistry;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.neoforged.neoforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

public class AetherMoaTypes {
    public static final ResourceKey<Registry<MoaType>> MOA_TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(Aether.MODID, "moa_type"));
    public static final DeferredRegister<MoaType> MOA_TYPES = DeferredRegister.create(MOA_TYPE_REGISTRY_KEY, Aether.MODID);
    public static final Supplier<IForgeRegistry<MoaType>> MOA_TYPE_REGISTRY = MOA_TYPES.makeRegistry(() -> new RegistryBuilder<MoaType>().hasTags());

    public static final RegistryObject<MoaType> BLUE = MOA_TYPES.register("blue", () -> new MoaType(new MoaType.Properties().egg(AetherItems.BLUE_MOA_EGG).maxJumps(3).speed(0.155F).spawnChance(100).texture("textures/entity/mobs/moa/blue_moa.png")));
    public static final RegistryObject<MoaType> WHITE = MOA_TYPES.register("white", () -> new MoaType(new MoaType.Properties().egg(AetherItems.WHITE_MOA_EGG).maxJumps(4).speed(0.155F).spawnChance(50).texture("textures/entity/mobs/moa/white_moa.png")));
    public static final RegistryObject<MoaType> BLACK = MOA_TYPES.register("black", () -> new MoaType(new MoaType.Properties().egg(AetherItems.BLACK_MOA_EGG).maxJumps(8).speed(0.155F).spawnChance(25).texture("textures/entity/mobs/moa/black_moa.png").saddleTexture("textures/entity/mobs/moa/black_moa_saddle.png")));

    @Nullable
    public static MoaType get(String id) {
        return MOA_TYPE_REGISTRY.get().getValue(new ResourceLocation(id));
    }

    /**
     * Gets a random {@link MoaType} with a weighted chance. This is used when spawning Moas in the world.<br>
     * A {@link SimpleWeightedRandomList} is built with all the {@link MoaType}s and their spawn chance weights, and one is randomly picked out of the list.
     * @param random The {@link RandomSource} to use.
     * @return The {@link MoaType}.
     */
    public static MoaType getWeightedChance(RandomSource random) {
        SimpleWeightedRandomList.Builder<MoaType> weightedListBuilder = SimpleWeightedRandomList.builder();
        MOA_TYPE_REGISTRY.get().getValues().forEach((moaType) -> weightedListBuilder.add(moaType, moaType.getSpawnChance()));
        SimpleWeightedRandomList<MoaType> weightedList = weightedListBuilder.build();
        Optional<MoaType> moaType = weightedList.getRandomValue(random);
        return moaType.orElseGet(BLUE);
    }
}
