package com.aetherteam.aether.recipe.serializer;

import com.aetherteam.aether.recipe.recipes.block.AbstractBiomeParameterRecipe;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function5;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.util.Optional;

public class BiomeParameterRecipeSerializer<T extends AbstractBiomeParameterRecipe> extends BlockStateRecipeSerializer<T> {
    private final BiomeParameterRecipeSerializer.CookieBaker<T> factory;
    private final Codec<T> codec;

    public BiomeParameterRecipeSerializer(BiomeParameterRecipeSerializer.CookieBaker<T> factory, Function3<BlockStateIngredient, BlockPropertyPair, Optional<ResourceLocation>, T> superFactory) {
        super(superFactory);
        this.factory = factory;
        this.codec = RecordCodecBuilder.create(inst -> inst.group(
                ResourceKey.codec(Registries.BIOME).optionalFieldOf("biome").forGetter(AbstractBiomeParameterRecipe::getBiomeKey),
                TagKey.codec(Registries.BIOME).optionalFieldOf("biome").forGetter(AbstractBiomeParameterRecipe::getBiomeTag),
                BlockStateIngredient.CODEC.fieldOf("ingredient").forGetter(AbstractBlockStateRecipe::getIngredient),
                BlockPropertyPair.BLOCKSTATE_CODEC.fieldOf("result").forGetter(AbstractBlockStateRecipe::getResult),
                ResourceLocation.CODEC.optionalFieldOf("mcfunction").forGetter(AbstractBlockStateRecipe::getFunctionId)
        ).apply(inst, factory));
    }

    @Override
    public Codec<T> codec() {
        return this.codec;
    }

    @Nullable
    @Override
    public T fromNetwork(FriendlyByteBuf buffer) {
        Optional<ResourceKey<Biome>> biomeKey = BlockStateRecipeUtil.readBiomeKey(buffer);
        Optional<TagKey<Biome>> biomeTag = BlockStateRecipeUtil.readBiomeTag(buffer);
        BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buffer);
        BlockPropertyPair result = BlockStateRecipeUtil.readPair(buffer);
        Optional<ResourceLocation> function = buffer.readOptional(FriendlyByteBuf::readResourceLocation);
        return this.factory.apply(biomeKey, biomeTag, ingredient, result, function);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        BlockStateRecipeUtil.writeBiomeKey(buffer, recipe.getBiomeKey());
        BlockStateRecipeUtil.writeBiomeTag(buffer, recipe.getBiomeTag());
        super.toNetwork(buffer, recipe);
    }

    public interface CookieBaker<T extends AbstractBiomeParameterRecipe> extends Function5<Optional<ResourceKey<Biome>>, Optional<TagKey<Biome>>, BlockStateIngredient, BlockPropertyPair, Optional<ResourceLocation>, T> {
        @Override
        T apply(Optional<ResourceKey<Biome>> biomeKey, Optional<TagKey<Biome>> biomeTag, BlockStateIngredient ingredient, BlockPropertyPair result, Optional<ResourceLocation> function);
    }
}
