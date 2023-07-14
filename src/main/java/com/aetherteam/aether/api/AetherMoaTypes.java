package com.aetherteam.aether.api;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AetherMoaTypes {
    public static final ResourceKey<Registry<MoaType>> MOA_TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(Aether.MODID, "moa_type"));
    public static final DeferredRegister<MoaType> MOA_TYPES = DeferredRegister.create(MOA_TYPE_REGISTRY_KEY, Aether.MODID);
    public static final Supplier<IForgeRegistry<MoaType>> MOA_TYPE_REGISTRY = MOA_TYPES.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<MoaType> BLUE = MOA_TYPES.register("blue", () -> new MoaType(new MoaType.Properties().egg(AetherItems.BLUE_MOA_EGG).maxJumps(3).speed(0.155F).spawnChance(100).texture("textures/entity/mobs/moa/blue_moa.png")));
    public static final RegistryObject<MoaType> WHITE = MOA_TYPES.register("white", () -> new MoaType(new MoaType.Properties().egg(AetherItems.WHITE_MOA_EGG).maxJumps(4).speed(0.155F).spawnChance(50).texture("textures/entity/mobs/moa/white_moa.png")));
    public static final RegistryObject<MoaType> BLACK = MOA_TYPES.register("black", () -> new MoaType(new MoaType.Properties().egg(AetherItems.BLACK_MOA_EGG).maxJumps(8).speed(0.155F).spawnChance(25).texture("textures/entity/mobs/moa/black_moa.png").saddleTexture("textures/entity/mobs/moa/black_moa_saddle.png")));

    public static MoaType get(String id) {
        return MOA_TYPE_REGISTRY.get().getValue(new ResourceLocation(id));
    }

    public static MoaType getWeightedChance(RandomSource random) {
        int totalChance = MOA_TYPE_REGISTRY.get().getValues().stream().map(MoaType::getSpawnChance).reduce(0, Integer::sum);
        int randomChance = random.nextInt(totalChance);
        for (MoaType moaType : MOA_TYPE_REGISTRY.get().getValues()) {
            if (randomChance < moaType.getSpawnChance()) {
                return moaType;
            }
            randomChance -= moaType.getSpawnChance();
        }
        return BLUE.get();
    }
}
