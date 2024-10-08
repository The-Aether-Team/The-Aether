package com.aetherteam.aether.recipe.serializer;

import com.aetherteam.aether.recipe.recipes.block.AbstractBiomeParameterRecipe;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public class BiomeParameterRecipeSerializer<T extends AbstractBiomeParameterRecipe> extends BlockStateRecipeSerializer<T> {
    private final BiomeParameterRecipeSerializer.Factory<T> factory;
    private final MapCodec<T> codec;

    public BiomeParameterRecipeSerializer(BiomeParameterRecipeSerializer.Factory<T> factory, AbstractBlockStateRecipe.Factory<T> superFactory) {
        super(superFactory);
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(inst -> inst.group(
                BlockStateRecipeUtil.KEY_CODEC.optionalFieldOf("biome").forGetter(AbstractBiomeParameterRecipe::getBiome),
                BlockStateIngredient.CODEC.fieldOf("ingredient").forGetter(AbstractBiomeParameterRecipe::getIngredient),
                BlockPropertyPair.CODEC.fieldOf("result").forGetter(AbstractBiomeParameterRecipe::getResult),
                ResourceLocation.CODEC.optionalFieldOf("mcfunction").forGetter(AbstractBiomeParameterRecipe::getFunctionId)
        ).apply(inst, factory::create));
    }

    @Override
    public MapCodec<T> codec() {
        return this.codec;
    }

    public T fromNetwork(RegistryFriendlyByteBuf buffer) {
        Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome = buffer.readOptional((buf) -> BlockStateRecipeUtil.STREAM_CODEC.decode((RegistryFriendlyByteBuf) buf));
        BlockStateIngredient ingredient = BlockStateIngredient.CONTENTS_STREAM_CODEC.decode(buffer);
        BlockPropertyPair result = BlockStateRecipeUtil.readPair(buffer);
        Optional<ResourceLocation> function = buffer.readOptional(FriendlyByteBuf::readResourceLocation);
        return this.factory.create(biome, ingredient, result, function);
    }

    public void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
        buffer.writeOptional(recipe.getBiome(), (buf, either) -> BlockStateRecipeUtil.STREAM_CODEC.encode((RegistryFriendlyByteBuf) buf, either));
        super.toNetwork(buffer, recipe);
    }

    public interface Factory<T extends AbstractBiomeParameterRecipe> {
        T create(Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BlockStateIngredient ingredient, BlockPropertyPair result, Optional<ResourceLocation> function);
    }
}
