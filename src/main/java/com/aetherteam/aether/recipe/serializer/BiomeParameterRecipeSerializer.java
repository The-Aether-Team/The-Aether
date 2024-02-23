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
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;

public class BiomeParameterRecipeSerializer<T extends AbstractBiomeParameterRecipe> extends BlockStateRecipeSerializer<T> {
    private final BiomeParameterRecipeSerializer.CookieBaker<T> factory;
    private final Codec<T> codec;

    public BiomeParameterRecipeSerializer(BiomeParameterRecipeSerializer.CookieBaker<T> factory, Function3<BlockStateIngredient, BlockPropertyPair, String, T> superFactory) {
        super(superFactory);
        this.factory = factory;
        this.codec = RecordCodecBuilder.create(inst -> inst.group(
                ResourceKey.codec(Registries.BIOME).fieldOf("biome").orElse(null).forGetter(AbstractBiomeParameterRecipe::getBiomeKey),
                TagKey.codec(Registries.BIOME).fieldOf("biome").orElse(null).forGetter(AbstractBiomeParameterRecipe::getBiomeTag),
                BlockStateIngredient.CODEC.fieldOf("ingredient").forGetter(AbstractBlockStateRecipe::getIngredient),
                BlockPropertyPair.BLOCKSTATE_CODEC.fieldOf("result").forGetter(AbstractBlockStateRecipe::getResult),
                Codec.STRING.fieldOf("mcfunction").orElse("").forGetter(AbstractBlockStateRecipe::getFunctionString)
        ).apply(inst, factory));
    }

    @Override
    public Codec<T> codec() {
        return this.codec;
    }

    @Nullable
    @Override
    public T fromNetwork(FriendlyByteBuf buffer) {
        ResourceKey<Biome> biomeKey = BlockStateRecipeUtil.readBiomeKey(buffer);
        TagKey<Biome> biomeTag = BlockStateRecipeUtil.readBiomeTag(buffer);
        BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buffer);
        BlockPropertyPair result = BlockStateRecipeUtil.readPair(buffer);
        String functionString = buffer.readUtf();
        return this.factory.apply(biomeKey, biomeTag, ingredient, result, functionString);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        BlockStateRecipeUtil.writeBiomeKey(buffer, recipe.getBiomeKey());
        BlockStateRecipeUtil.writeBiomeTag(buffer, recipe.getBiomeTag());
        super.toNetwork(buffer, recipe);
    }

    public interface CookieBaker<T extends AbstractBiomeParameterRecipe> extends Function5<ResourceKey<Biome>, TagKey<Biome>, BlockStateIngredient, BlockPropertyPair, String, T> {
        @Override
        T apply(@Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient ingredient, BlockPropertyPair result, @Nullable String function);
    }
}
