package com.aetherteam.aether.recipe.serializer;

import com.aetherteam.aether.recipe.AetherBookCategory;
import com.aetherteam.aether.recipe.recipes.item.AbstractAetherCookingRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;

/**
 * [CODE COPY] - {@link SimpleCookingSerializer}.<br><br>
 * Cleaned up.
 */
public class AetherCookingSerializer<T extends AbstractAetherCookingRecipe> implements RecipeSerializer<T> {
    private final AetherCookingSerializer.CookieBaker<T> factory;
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public AetherCookingSerializer(AetherCookingSerializer.CookieBaker<T> factory, int defaultCookingTime) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(AbstractCookingRecipe::getGroup),
                AetherBookCategory.CODEC.fieldOf("category").forGetter(AbstractAetherCookingRecipe::aetherCategory),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter((recipe) -> recipe.getIngredients().getFirst()),
                ItemStack.CODEC.fieldOf("result").forGetter(AbstractAetherCookingRecipe::getResult),
                Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(AbstractCookingRecipe::getExperience),
                Codec.INT.fieldOf("cookingtime").orElse(defaultCookingTime).forGetter(AbstractCookingRecipe::getCookingTime)
        ).apply(instance, factory::create));
        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    @Override
    public MapCodec<T> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return this.streamCodec;
    }

    public T fromNetwork(RegistryFriendlyByteBuf buffer) {
        String group = buffer.readUtf();
        AetherBookCategory aetherBookCategory = buffer.readEnum(AetherBookCategory.class);
        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
        float experience = buffer.readFloat();
        int cookingTime = buffer.readVarInt();
        return this.factory.create(group, aetherBookCategory, ingredient, result, experience, cookingTime);
    }

    public void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
        buffer.writeUtf(recipe.getGroup());
        buffer.writeEnum(recipe.aetherCategory());
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getIngredients().getFirst());
        ItemStack.STREAM_CODEC.encode(buffer, recipe.getResult());
        buffer.writeFloat(recipe.getExperience());
        buffer.writeVarInt(recipe.getCookingTime());
    }

    public interface CookieBaker<T extends AbstractAetherCookingRecipe> {
        T create(String group, AetherBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime);
    }
}
